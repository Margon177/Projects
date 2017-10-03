#include <iostream>
#include <time.h>
#include <stdlib.h>
#define rozmiar 10

//wypelnia tablice liczbami od -100 do 100
void wypelnij(int tab[]){
int i,random;
random= time(NULL);
srand(random);	
for(i=0;i<rozmiar;++i){
tab[i]=100-rand()%200;
}	
}

//wyswietla tablice
void wyswietl(int tab[]){
int i;
for(i=0;i<rozmiar;++i){	
printf(" %d ",tab[i]);
}
}
//quicksort na dwie czesci pierw dzielenie na dwie tablice jedna mniejsze lub rowne jakiejs liczbie podanej w procedurze 
int podzial(int tab[], int p, int r){
int x = tab[p]; // obieramy x
int i = p, j = r, w; // i, j - indeksy w tabeli
while (true) 
	{
		while (tab[j] > x) // dopoki elementy sa wieksze od x
		j--;
		while (tab[i] < x) // dopoki elementy sa mniejsze od x
			i++;
	if (i < j) 
	{
	w = tab[i];
	tab[i] = tab[j];
	tab[j] = w;
	i++;
	j--;
}
else // gdy i >= j zwracamy j jako punkt podzialu tablicy
return j;
}
}
 //funkcja rekurencyjna na podzielonej tablicy
void quicksort(int tablica[], int p, int r) 
{
int q;
if (p < r)
{  
q = podzial(tablica,p,r); 
quicksort(tablica, p, q); 
quicksort(tablica, q+1, r); 
}
}
 

//babelkowe sortowanie
void bubblesort(int tab[])
{
int i, j, temp;
	for (i = 0; i<rozmiar-1; i++)
        {
		for (j=0; j<rozmiar-1-i; j++)
		{
			if (tab[j] > tab[j+1])
			{
				temp = tab[j+1];
				tab[j+1] = tab[j];
				tab[j] = temp;
			}
		}
        }
}
int main(){
	float poczatek,koniec=0;
	float pomiar=0;
	int tab[rozmiar];
	int tab2[rozmiar];
	int i;	
	
wypelnij(tab);
printf("tablica losowo wypelniona");
printf("\n");
wyswietl(tab);
printf("\n");
for(i=0;i<rozmiar;++i){	
tab2[i]=tab[i];
}

poczatek=clock();
bubblesort(tab);
koniec=clock();

printf("tablica po wykonaniu sorowania babelkowego: \n");
wyswietl(tab);
pomiar=(koniec-poczatek/CLK_TCK)*0.001;

printf("\n pomiar czasu wykoniania algorytmu: %f s\n",pomiar);
printf("\n");

poczatek=clock();
quicksort(tab2,0,rozmiar-1);
koniec=clock();

printf("tablica po wykonaniu sorowania quicksort: \n");
wyswietl(tab2);
pomiar=(koniec-poczatek/CLK_TCK)*0.001;
printf("\n pomiar czasu wykoniania algorytmu: %f s",pomiar);
return 0;
}
