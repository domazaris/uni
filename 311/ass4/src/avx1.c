#include<string.h>
#include <math.h>
#include <stdio.h>
#include <sys/types.h>
#include <x86intrin.h>
#include "imageio.h"

/**
 * The kernel for the horizontal response
 */
float hfilter[] = {
    1.0f, 0.0f, -1.0f,
    2.0f, 0.0f, -2.0f,
    1.0f, 0.0f, -1.0f
};

/**
 * The kernel for the vertical response
 */
float vfilter[] = {
    1.0f, 2.0f, 1.0f,
    0.0f, 0.0f, 0.0f,
    -1.0f, -2.0f, -1.0f
};

/**
 * Checks whether the value is in bounds of the image (not off the edge )
 */
static inline __attribute__((always_inline)) size_t in_bounds(ssize_t* k, ssize_t* l, size_t* width, size_t* height )
{
    return (*k >= 0 && *k < *width && *l >= 0 && *l < *height);
}

Image* sobel( const Image* input )
{
    // Store height and width for readability
    size_t width = (size_t)input->width;
    size_t height = (size_t)input->height;
    
    // Create the image
    Image* output = alloc_image(width, height);
    
    // Iterators
    ssize_t i, j, k, l;
    
    // Set up filters in registers (ignore middles as they are both 0 (no filter)
    __m256 m_h_filter = _mm256_setr_ps( hfilter[0], hfilter[1], hfilter[2], hfilter[3], hfilter[5], hfilter[6], hfilter[7], hfilter[8] );
    __m256 m_v_filter = _mm256_setr_ps( vfilter[0], vfilter[1], vfilter[2], vfilter[3], vfilter[5], vfilter[6], vfilter[7], vfilter[8] );

    // AVX vals
    __m256 h_mul, v_mul, m_input, dst;

    // Pre 'response energy' vals
    size_t h_val, v_val;
    
    // input vals (for loading into avx reg
    float i0, i1, i2, i3, i4, i5, i6, i7;
    
    // Filtered vals
    float* filtered_vals = (float*)aligned_alloc( 32, sizeof(float) * 8 );
    
    // Iterate over each row in the image
    for(size_t r = 0; r < height; ++r )
    {
        //Now iterate over each column in the image
        size_t c = 0;
        for(; c < width; ++c)
        {
            j = -1, i = -1, k = c - j, l = r - i;
            i0 = ! in_bounds( &k, &l, &width, &height ) ? 0 : input->pixels[ l * width + k ];

            j = -1, i = 0, k = c - j, l = r - i;
            i1 = ! in_bounds( &k, &l, &width, &height ) ? 0 : input->pixels[ l * width + k ];

            j = -1, i = 1, k = c - j, l = r - i;
            i2 = ! in_bounds( &k, &l, &width, &height ) ? 0 : input->pixels[ l * width + k ];

            j = 0, i = -1, k = c - j, l = r - i;
            i3 = ! in_bounds( &k, &l, &width, &height ) ? 0 : input->pixels[ l * width + k ];

            j = 0, i = 1, k = c - j, l = r - i;
            i4 = ! in_bounds( &k, &l, &width, &height ) ? 0 : input->pixels[ l * width + k ];

            j = 1, i = -1, k = c - j, l = r - i;
            i5 = ! in_bounds( &k, &l, &width, &height ) ? 0 : input->pixels[ l * width + k ];

            j = 1, i = 0, k = c - j, l = r - i;
            i6 = ! in_bounds( &k, &l, &width, &height ) ? 0 : input->pixels[ l * width + k ];
            
            j = 1, i = 1, k = c - j, l = r - i;
            i7 = ! in_bounds( &k, &l, &width, &height ) ? 0 : input->pixels[ l * width + k ];

            // Load input
            m_input = _mm256_setr_ps( i0, i1, i2, i3, i4, i5, i6, i7 );
            
            // filter mul
            h_mul = _mm256_mul_ps( m_input, m_h_filter );
            v_mul = _mm256_mul_ps( m_input, m_v_filter );

            // Hadd
            dst = _mm256_hadd_ps( h_mul, v_mul );
            dst = _mm256_hadd_ps( dst, dst );
            
            // add to mv
            _mm256_store_ps( filtered_vals, dst );
            
            // Calc values
            h_val = filtered_vals[ 0 ] + filtered_vals[ 4 ];
            v_val = filtered_vals[ 1 ] + filtered_vals[ 5 ];
            
            output->pixels[ r * width + c ] = (float)sqrt( h_val * h_val + v_val * v_val);
        }
    }
    
    free( filtered_vals );
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
