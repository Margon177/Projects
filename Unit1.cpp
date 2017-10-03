//---------------------------------------------------------------------------

#include <vcl.h>

#pragma hdrstop

#include "Unit1.h"
#include "Unit2.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm1 *Form1;
//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button1Click(TObject *Sender)
{
        int sel = RadioGroup1->ItemIndex;
	double re,im;
        complex c;
	switch(sel)
	{
		case 0:
			Label4->Hide();
			Label3->Show();
			re=StrToFloat(Edit1->Text);
			im=StrToFloat(Edit2->Text);
			c.SetValue(re,im);
			draw_Julia(Image1,c);
		break;
		case 1:
			Label3->Hide();
			Label4->Show();
			draw_Mandelbrot(Image1);
		break;
		default:
			Label3->Hide();
			Label4->Hide();
	}
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Button2Click(TObject *Sender)
{
        double re,im;
        re=StrToFloat(Edit1->Text);
        im=StrToFloat(Edit2->Text);
	TForm2 *Form = new TForm2( this );
        Form->c1.SetValue(re,im);
	Form->ShowModal();
}
//---------------------------------------------------------------------------
