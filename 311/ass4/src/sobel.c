#include <math.h>
#include <stdio.h>

#include "imageio.h"

//The kernel for the horizontal response
float hfilter[] = {
    1.0f, 0.0f, -1.0f,
    2.0f, 0.0f, -2.0f,
    1.0f, 0.0f, -1.0f
};

//The kernel for the vertical response
float vfilter[] = {
    1.0f, 2.0f, 1.0f,
    0.0f, 0.0f, 0.0f,
    -1.0f, -2.0f, -1.0f
};

void apply_filter(const float *input, int width, int height, const float *kernel, float *output)
{
    //Iterate over each column in the image
    for(int c = 0; c < width; c++)
    {
        //Now iterate over each row in the image
        for(int r = 0; r < height; r++)
        {
            //Set the output to 0, to start with
            output[r * width + c] = 0;

            //These two loops iterate over a 3x3 subwindow of the image centred on the coordinates given by r and c
            for(int j = 1; j >= -1; j--)
            {
                for(int i = 1; i >= -1; i--)
                {
                    int k = c - j;
                    int l = r - i;

                    //Check that we aren't going to cause a buffer overflow (or spill onto an adjacent scan line)
                    if(k >= 0 && k < width && l >= 0 && l < height)
                    {
                        output[r * width + c] += input[l * width + k] * kernel[(i + 1) * 3 + j + 1];
                    }
                }
            }
        }
    }
}

Image *sobel(const Image *input)
{
    //Allocate some temporary images for horizontal and vertical responses
    Image *h = alloc_image(input->width, input->height);
    Image *v = alloc_image(input->width, input->height);

    //Apply the horizontal filter
    apply_filter(input->pixels, input->width, input->height, hfilter, h->pixels);

    //Apply the vertical filter
    apply_filter(input->pixels, input->width, input->height, vfilter, v->pixels);

    Image *output = alloc_image(input->width, input->height);

    //Compute the "response energy"---this is magnitude of the vector (h->pixels[i], v->pixels[i]) for all values of i
    for(int c = 0; c < input->width; c++)
    {
        for(int r = 0; r < input->height; r++)
        {
            int idx = r * input->width + c;

            float hval = (float)h->pixels[idx];
            float vval = (float)v->pixels[idx];
            output->pixels[idx] = (float)sqrt(hval * hval + vval * vval);
        }
    }

    //Free the temporary images
    free_image(h);
    free_image(v);

    return output;
}

int main(int argc, char **argv)
{
    if(argc != 3)
    {
        fprintf(stderr, "Usage: sobel <input file> <output file>\n");
        return 0;
    }

    //Load an image (in pgm format) specified by the first command line argument
    Image *img = load_image(argv[1]);
    
    //Check that the image was loaded correctly
    if(img == 0)
    {
        fprintf(stderr, "Error loading image file\n");
        return 0;
    }

    //Apply the edge detection algorithm to the input image we have just loaded
    Image *output = sobel(img);

    //Save the image!
    save_image(argv[2], output);
    
    return 0;
}