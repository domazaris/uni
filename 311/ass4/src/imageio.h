#ifndef __IMAGEIO_H__
#define __IMAGEIO_H__

typedef struct
{
    int width;
    int height;
    float *pixels;
} Image;

Image *alloc_image(int width, int height);
Image *load_image(const char *filename);
void save_image(const char *filename, Image *img);
void free_image(Image *img);

#endif