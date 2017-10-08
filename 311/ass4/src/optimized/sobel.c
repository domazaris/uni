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

static inline __attribute__((always_inline)) size_t in_bounds(ssize_t* k, ssize_t* l, size_t* width, size_t* height )
{
    return (*k >= 0 && *k < *width && *l >= 0 && *l < *height);
}

static inline __attribute__((always_inline)) void filter( float* input, float* h, float* v, size_t* r, size_t* c, ssize_t* i, ssize_t* j, size_t* width, size_t* height )
{
    ssize_t k = *c - *j;
    ssize_t l = *r - *i;
    
    *h += ! in_bounds( &k, &l, width, height ) ? 0 : input[l * *width + k] * hfilter[( *i + 1 ) * 3 + *j + 1];
    *v += ! in_bounds( &k, &l, width, height ) ? 0 : input[l * *width + k] * vfilter[( *i + 1 ) * 3 + *j + 1];
}

static inline __attribute__((always_inline)) void full_filter( float* input, float* output, float* h, float* v, size_t* r, size_t* c, ssize_t* i, ssize_t* j, size_t* width, size_t* height )
{
    *h = 0;
    *v = 0;

    *j = -1, *i = -1; filter( input, h, v, r, c, i, j, width, height );
    *j = -1, *i = 0;  filter( input, h, v, r, c, i, j, width, height );
    *j = -1, *i = 1;  filter( input, h, v, r, c, i, j, width, height );
    *j = 0,  *i = -1; filter( input, h, v, r, c, i, j, width, height );
    *j = 0,  *i = 1;  filter( input, h, v, r, c, i, j, width, height );
    *j = 1,  *i = -1; filter( input, h, v, r, c, i, j, width, height );
    *j = 1,  *i = 0;  filter( input, h, v, r, c, i, j, width, height );
    *j = 1,  *i = 1;  filter( input, h, v, r, c, i, j, width, height );
    output[ *r * *width + *c ] = (float)sqrt( *h * *h + *v * *v);
}

void fast_filter8( float* input, __m256* m_input, __m256* h_val, __m256* v_val, ssize_t* k, ssize_t* l, size_t* width, 
                   size_t* height, __m256* h_tmp, __m256* v_tmp,__m256* h_filter, __m256* v_filter )
{
    if( in_bounds( k, l, width, height ) )
    {
        // Load input
        *m_input = _mm256_loadu_ps( &input[*l * *width + *k] );
        
        // Mul
        *h_tmp = _mm256_mul_ps( *m_input, *h_filter );
        *v_tmp = _mm256_mul_ps( *m_input, *v_filter );;
        
        // Add
        *h_val = _mm256_add_ps( *h_val, *h_tmp );
        *v_val = _mm256_add_ps( *v_val, *v_tmp );
    }
}

Image* sobel( const Image* input )
{
    size_t width = (size_t)input->width;
    size_t height = (size_t)input->height;
    Image* output = alloc_image(width, height);
    
    // Iterate over each row in the image
    float h_val = 0;
    float v_val = 0;
    
    __m256 m_h_val;
    __m256 m_v_val;
    __m256 m_h_tmp;
    __m256 m_v_tmp;
    __m256 m_input;
    __m256 m_out;
    __m256* m_h_filters = (__m256*)aligned_alloc( 32, sizeof(__m256) * 9 );
    __m256* m_v_filters = (__m256*)aligned_alloc( 32, sizeof(__m256) * 9 );
    for( size_t i = 0; i < 9; i++ )
    {
        m_h_filters[i] = _mm256_set1_ps( hfilter[i] );
        m_v_filters[i] = _mm256_set1_ps( vfilter[i] );
    }
    
    ssize_t j = 0, i = 0, k = 0, l = 0;
    for(size_t r = 0; r < height; ++r )
    {
        size_t c = 0;
        // Apply first column sequentialls for edge case
        full_filter( input->pixels, output->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
        ++c;
        
        //Now iterate over each column in the image
        for(; c < width - 8; c += 8)
        {
            m_h_val = _mm256_setzero_ps();
            m_v_val = _mm256_setzero_ps();

            // Top
            j = -1, i = -1, k = c - j, l = r - i;
            fast_filter8( input->pixels, &m_input, &m_h_val, &m_v_val, &k, &l, &width, &height, &m_h_tmp, &m_v_tmp, &m_h_filters[0], &m_v_filters[0] );
            
            j = -1, i = 0, k = c - j, l = r - i;
            fast_filter8( input->pixels, &m_input, &m_h_val, &m_v_val, &k, &l, &width, &height, &m_h_tmp, &m_v_tmp, &m_h_filters[1], &m_v_filters[1] );

            j = -1, i = 1, k = c - j, l = r - i;
            fast_filter8( input->pixels, &m_input, &m_h_val, &m_v_val, &k, &l, &width, &height, &m_h_tmp, &m_v_tmp, &m_h_filters[2], &m_v_filters[2] );

            j = 0, i = -1, k = c - j, l = r - i;
            fast_filter8( input->pixels, &m_input, &m_h_val, &m_v_val, &k, &l, &width, &height, &m_h_tmp, &m_v_tmp, &m_h_filters[3], &m_v_filters[3] );
            
            j = 0, i = 1, k = c - j, l = r - i;
            fast_filter8( input->pixels, &m_input, &m_h_val, &m_v_val, &k, &l, &width, &height, &m_h_tmp, &m_v_tmp, &m_h_filters[5], &m_v_filters[5] );

            j = 1, i = -1, k = c - j, l = r - i;
            fast_filter8( input->pixels, &m_input, &m_h_val, &m_v_val, &k, &l, &width, &height, &m_h_tmp, &m_v_tmp, &m_h_filters[6], &m_v_filters[6] );

            j = 1, i = 0, k = c - j, l = r - i;
            fast_filter8( input->pixels, &m_input, &m_h_val, &m_v_val, &k, &l, &width, &height, &m_h_tmp, &m_v_tmp, &m_h_filters[7], &m_v_filters[7] );

            j = 1, i = 1, k = c - j, l = r - i;
            fast_filter8( input->pixels, &m_input, &m_h_val, &m_v_val, &k, &l, &width, &height, &m_h_tmp, &m_v_tmp, &m_h_filters[8], &m_v_filters[8] );
            
            // "Response Energy" //
            // square
            m_h_val = _mm256_mul_ps( m_h_val, m_h_val );
            m_v_val = _mm256_mul_ps( m_v_val, m_v_val );
            
            // add
            m_out = _mm256_add_ps( m_h_val, m_v_val );
            
            // sqrt
            m_out = _mm256_sqrt_ps( m_out );
            
            // store
            _mm256_storeu_ps( &output->pixels[ r * width + c ], m_out );
        }
        
        //Now iterate over the remaining ( < 8 ) columns
        for(; c < width; ++c )
        {
            full_filter( input->pixels, output->pixels, &h_val, &v_val, &r, &c, &i, &j, &width, &height );
        }
    }
    
    free(m_h_filters);
    free(m_v_filters);
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
    free_image( img );
    free_image( output );
    return 0;
}
