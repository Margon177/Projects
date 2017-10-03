#include <vcl.h>
#pragma hdrstop

#include "Unit1.h"

#include <math.h>


const int size = 200;
const int range = 50;
class complex{
 double re, im;
public:
	complex()
	{
		re=0;
		im=0;
	}
	complex(double x, double y)
	{
		re=x;
		im=y;
	}
	void SetValue(double Re, double Im)
	{
		re=Re;
		im=Im;
	}
	complex operator+(complex c){
		complex tmp;
		tmp.re=re+c.re;
		tmp.im=im+c.im;
		return tmp;
	}
	complex operator- (complex c){
		complex tmp;
		tmp.re=re-c.re;
		tmp.im=im-c.im;
		return tmp;
	}
	complex operator*(complex c)
	{
		complex tmp;
		tmp.re=re*c.re-im*c.im;
		tmp.im=re*c.im+im*c.re;
		return tmp;
	}
	double Module()
	{
		return sqrt(re*re+im*im);
	}
	
};
bool MandelbrotIsCoverage(complex p)
{
	complex z=p;
	for(int n=2;n<10000;n++)
	{
		z=z*z+p;;
		if(z.Module()>=2)
			return false;
	}
	return true;
}
bool JuliaIsCoverage(complex p,complex c)
{
	complex z=p;
	for(int n=0;n<10000;n++)
	{
		z=z*z+c;;
		if(z.Module()>=2)
			return false;
	}
	return true;
}
 void draw_Julia(TImage *img,complex c)
 {
	complex p;
	 Graphics::TBitmap * bmp = img->Picture->Bitmap;
	 bmp->Width = size;
	 bmp->Height = size;

	 img->Canvas->Brush->Color = clWhite;
	 img->Canvas->FloodFill(0, 0, clGreen, fsBorder);

	 bmp->Canvas->MoveTo(0,100);
	 bmp->Canvas->LineTo(200,100);
	 bmp->Canvas->MoveTo(100,0);
	 bmp->Canvas->LineTo(100,200);
	 for(int i=0;i<200;i++)
	 {
		if(i!=100)
		 for(int j=0;j<200;j++)
		 {
			if(j!=100)
			{
				p.SetValue(((double)(i-100))/range,((double)(j-100))/range);
				if(JuliaIsCoverage(p,c))
					bmp->Canvas->Pixels[i][j]=clBlue;
				if(j&15)
				img->Update();
			}
		 }

	 }

 }
 void draw_Mandelbrot(TImage *img)
 {
	complex p;
	 Graphics::TBitmap * bmp = img->Picture->Bitmap;
	 bmp->Width = size;
	 bmp->Height = size;

	 img->Canvas->Brush->Color = clWhite;
	 img->Canvas->FloodFill(0, 0, clGreen, fsBorder);

	 bmp->Canvas->MoveTo(0,100);
	 bmp->Canvas->LineTo(200,100);
	 bmp->Canvas->MoveTo(100,0);
	 bmp->Canvas->LineTo(100,200);
	 for(int i=0;i<200;i++)
	 {
		if(i!=100)
		 for(int j=0;j<200;j++)
		 {
			if(j!=100)
			{
				p.SetValue(((double)(i-100))/range,((double)(j-100))/range);
				if(MandelbrotIsCoverage(p))
					bmp->Canvas->Pixels[i][j]=clBlue;
				if(j&15)
				img->Update();
			}
		 }

	 }

 }
  void compare(TImage *img1,TImage *img2,complex c)
 {
	complex p;
	 Graphics::TBitmap * bmp1 = img1->Picture->Bitmap;
	 bmp1->Width = size;
	 bmp1->Height = size;
	 Graphics::TBitmap * bmp2 = img2->Picture->Bitmap;
	 bmp2->Width = size;
	 bmp2->Height = size;

	 img1->Canvas->Brush->Color = clWhite;
	 img1->Canvas->FloodFill(0, 0, clGreen, fsBorder);

	 bmp1->Canvas->MoveTo(0,100);
	 bmp1->Canvas->LineTo(200,100);
	 bmp1->Canvas->MoveTo(100,0);
	 bmp1->Canvas->LineTo(100,200);

     img2->Canvas->Brush->Color = clWhite;
	 img2->Canvas->FloodFill(0, 0, clGreen, fsBorder);

	 bmp2->Canvas->MoveTo(0,100);
	 bmp2->Canvas->LineTo(200,100);
	 bmp2->Canvas->MoveTo(100,0);
	 bmp2->Canvas->LineTo(100,200);
	 for(int i=0;i<200;i++)
	 {
		if(i!=100)
		 for(int j=0;j<200;j++)
		 {
			if(j!=100)
			{
				p.SetValue(((double)(i-100))/range,((double)(j-100))/range);
				if(JuliaIsCoverage(p,c))
					bmp1->Canvas->Pixels[i][j]=clBlue;
                p.SetValue(((double)(i-100))/range,((double)(j-100))/range);
				if(MandelbrotIsCoverage(p))
					bmp2->Canvas->Pixels[i][j]=clBlue;

				if(j&15)
				{
					img1->Update();
					img2->Update();
                }

			}
		 }

	 }

 }
 //

 