#include<string.h>
#include <math.h>
#include <stdio.h>
#include <sys/types.h>
#include <x86intrin.h>
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

void square_rt( float* h, float* v, float* out, size_t n )
{
    size_t i = 0;
    for(; i <= n - 8; i += 8 )
    {
        // load
        __m256 _mh = _mm256_loadu_ps( h + i );
        __m256 _mv = _mm256_loadu_ps( v + i );
        
        // square
        __m256 _mh2 = _mm256_mul_ps( _mh, _mh );
        __m256 _mv2 = _mm256_mul_ps( _mv, _mv );
        
        // add
        __m256 _madd = _mm256_add_ps( _mh2, _mv2 );
        
        // sqrt
        __m256 _sqrt = _mm256_sqrt_ps( _madd );
        
        // store
        _mm256_storeu_ps( out + i, _sqrt );
    }
    
    // Remaining
    for(; i < n; i++)
    {
        float hval = (float)h[ i ];
        float vval = (float)v[ i ];
        out[ i ] = (float)sqrt(hval * hval + vval * vval);
    }
}

void filter( float* input, float* h, float* v, size_t* r, size_t* c, ssize_t* i, ssize_t* j, size_t* width, size_t* height )
{
    ssize_t k = *c - *j; 
    ssize_t l = *r - *i;
    
    size_t yo = (k >= 0 && k < *width && l >= 0 && l < *height);
    *h += ! yo ? 0 : input[l * *width + k] * hfilter[( *i + 1 ) * 3 + *j + 1];
    *v += ! yo ? 0 : input[l * *width + k] * vfilter[( *i + 1 ) * 3 + *j + 1];
}

Image* sobel( const Image* input )
{
    size_t width = (size_t)input->width;
    size_t height = (size_t)input->height;
    Image* output = alloc_image(width, height);
    
    // Iterate over each row in the image
    float h_val = 0;
    float v_val = 0;
    
    ssize_t j = 0, i = 0;
    for(size_t r = 0; r < height; r++)
    {
        //Now iterate over each column in the image
        for(size_t c = 0; c < width; c++)
        {
            h_val = 0;
            v_val = 0;
    
            j = -1, i = -1; filter( input->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
            j = -1, i = 0;  filter( input->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
            j = -1, i = 1;  filter( input->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
            j = 0,  i = -1; filter( input->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
            j = 0,  i = 0;  filter( input->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
            j = 0,  i = 1;  filter( input->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
            j = 1,  i = -1; filter( input->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
            j = 1,  i = 0;  filter( input->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
            j = 1,  i = 1;  filter( input->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
            
            output->pixels[ r * width + c ] = (float)sqrt( h_val * h_val + v_val * v_val);
        }
    }
    
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
