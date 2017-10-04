#include "imageio.h"

#include <stdio.h>
#include <stdlib.h>

Image *alloc_image(int width, int height)
{
    Image *img = (Image *)malloc(sizeof(Image));
    img->width = width;
    img->height = height;
    img->pixels = malloc(width * height * sizeof(float));

    return img;
}

Image *load_image(const char *filename)
{
    //Try to open the file
    FILE *fp = fopen(filename, "r");

    if(fp == 0)
    {
        return 0;
    }

    //Check the magic number for PGM files
    if(getc(fp) != 'P' || getc(fp) != '5')
    {
        fclose(fp);
        return 0;
    }

    while(getc(fp) != '\n') {}
    
    while(getc(fp) == '#')
    {
        while(getc(fp) != '\n') {}
    }

    fseek(fp, -1, SEEK_CUR);

    //Get the dimensions
    int width;
    int height;
    int maxval;
    if(fscanf(fp, "%d", &width) != 1 ||
       fscanf(fp, "%d", &height) != 1 ||
       fscanf(fp, "%d", &maxval) != 1)
    {
        fclose(fp);
        return 0;
    }

    Image *img = alloc_image(width, height);

    //Read in the pixel values
    unsigned char *buf = malloc(width);

    //Rows are stored in reverse order for this file format
    for(int r = height - 1; r >= 0; r--)
    {
        //Read in a byte for each pixel
        if(fread(buf, 1, width, fp) != width)
        {
            fclose(fp);
            free(buf);
            free_image(img);
            return 0;
        }

        for(int i = 0; i < width; i++)
        {
            img->pixels[r * width + i] = (float)buf[i];
        }
    }

    free(buf);
    
    //Close the file
    fclose(fp);

    return img;
}

void save_image(const char *filename, Image *img)
{
    FILE *fp = fopen(filename, "w");

    fprintf(fp, "P5\n");
    fprintf(fp, "%d %d\n%d\n", img->width, img->height, 255);

    unsigned char *buf = malloc(img->width);

    for(int r = img->height - 1; r >= 0; r--)
    {
        for(int i = 0; i < img->width; i++)
        {
            buf[i] = (unsigned char)img->pixels[r * img->width + i];
        }

        fwrite(buf, 1, img->width, fp);
    }

    free(buf);

    fclose(fp);
}

void free_image(Image *img)
{
    free(img->pixels);
    free(img);
}