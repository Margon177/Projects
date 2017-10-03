#include <stdio.h>
#include <stdlib.h>
#include <string.h>



struct dll_node
{
	int id;
	char* artysta;
	char* tytul;
	int rok;
	char* gatunek;
	int stan;
	int przesluchany;
	struct dll_node *prev,*next;
};

//============================================================================================

struct dll_node *create_list(int id,char* art,char* tyt,int year,char* gat,int st,int przes)
{
	struct dll_node*front=(struct dll_node*) malloc(sizeof(struct dll_node));
	if(NULL!=front)
		{
		front->id=id;
		front->artysta=art;
		front->tytul=tyt;
		front->rok=year;
		front->gatunek=gat;
		front->stan=st;
		front->przesluchany=przes;
		front->prev=NULL;
		front->next=NULL;
		}
	return front;
}

//============================================================================================

struct dll_node*insert_front(struct dll_node*front,struct dll_node*new_node)
{
	new_node->next=front;
	front->prev=new_node;
	return new_node;
}

//============================================================================================

struct dll_node*find_spot(struct dll_node*front,int id)
{
	struct dll_node*prev=NULL;
	while((NULL != front)&&(front->id>id))
	{
		prev=front;
		front=front->next;
	}
	return prev;
}

//============================================================================================

void insert_after(struct dll_node*node,struct dll_node*new_node)
{
	new_node->prev=node;
	new_node->next=node->next;
	node->next->prev=new_node;
	node->next=new_node;
}

//============================================================================================

void insert_back(struct dll_node*back,struct dll_node*new_node)
{
	back->next=new_node;
	new_node->prev=back;
}

//============================================================================================

struct dll_node*insert_node(struct dll_node*front,int id,char* art,char* tyt,int year,char* gat,int st,int przes)
{
	if(NULL==front)
		return NULL;

	struct dll_node*new_node=(struct dll_node*)malloc(sizeof(struct dll_node));
	if(NULL!= new_node)
	{
		new_node->id=id;
		new_node->artysta=art;
		new_node->tytul=tyt;
		new_node->rok=year;
		new_node->gatunek=gat;
		new_node->stan=st;
		new_node->przesluchany=przes;
		new_node->prev=new_node->next=NULL;

		if(NULL!=new_node)
		{
			new_node->id=id;
			new_node->artysta=art;
			new_node->tytul=tyt;
			new_node->rok=year;
			new_node->gatunek=gat;
			new_node->stan=st;
			new_node->przesluchany=przes;
			new_node->prev=new_node->next=NULL;
			new_node->prev=new_node->next=NULL;
		}
		if(front->id<=id)
			return insert_front(front,new_node);
		else
		{
			struct dll_node*node= find_spot(front,id);
			if(NULL!=node->next)
				insert_after(node,new_node);
			else
				insert_back(node,new_node);
		}
	}
	return front;
}

//============================================================================================

struct dll_node*delete_front(struct dll_node*front)
{
	struct dll_node*next=front->next;
	if(NULL!=next)
	next->prev=NULL;
	free(front);
	return next;
}

//============================================================================================

struct dll_node*find_node(struct dll_node*front,int id)
{
	while((NULL!=front)&&(front->id!=id))
	front=front->next;
	return front;
}

//============================================================================================

void delete_within(struct dll_node*node)
{
	node->next->prev=node->prev;
	node->prev->next=node->next;
	free(node);
}

//============================================================================================

void delete_back(struct dll_node*back)
{
	back->prev->next=NULL;
	free(back);
}

//============================================================================================

struct dll_node*delete_node(struct dll_node*front,int id)
{
	if(NULL==front)
		return NULL;

	if(front->id==id)
		return delete_front(front);

	struct dll_node*node=find_node(front,id);
	if(NULL!=node)
	{
		if(NULL!=node->next)
			delete_within(node);
		else
			delete_back(node);
	}
	return front;
}

//============================================================================================

void print_list(struct dll_node*front)
{
	char* tprzesluchane;
	char* tstan;
	for(;NULL!=front;front=front->next)
	{
		if(front->stan==1)
			tstan="dostepne";
		else
			tstan="niedostepne";
		if(front->przesluchany==1)
			tprzesluchane="tak";
		else
			tprzesluchane="nie";

		printf("id:%d artysta:%s tytul:%s rok:%d gatunek:%s stan:%s przesluchane:%s",front->id,front->artysta,front->tytul,front->rok,front->gatunek,tstan,tprzesluchane);
        printf("\n");
        //cout<<"id:"<<front->id<<" artysta:"<<front->artysta<<" tytul:"<<front->tytul<<" rok:"<<front->rok<<" gatunek:"<<front->gatunek<<" stan:"<<tstan<<" przesluchane:"<<tprzesluchane<<endl;
	}
	printf("\n");
}

//============================================================================================

void print_list_backwards(struct dll_node*front)
{
	char* tprzesluchane;
	char* tstan;
	if(NULL!=front)
	{
		for(;NULL!=front->next;front=front->next);
			for(;NULL!=front;front=front->prev)
			{
				if(front->stan==1)
					tstan="dostepne";
				else
					tstan="niedostepne";
				if(front->przesluchany==1)
					tprzesluchane="przesluchane";
				else
					tprzesluchane="nie przesluchane";
                printf("id:%d artysta:%s tytul:%s rok:%d gatunek:%s stan:%s przesluchane:%s",front->id,front->artysta,front->tytul,front->rok,front->gatunek,tstan,tprzesluchane);
				printf("/n");
				//cout<<"id:"<<front->id<<" artysta:"<<front->artysta<<" tytul:"<<front->tytul<<" rok:"<<front->rok<<" gatunek:"<<front->gatunek<<" stan:"<<tstan<<" przesluchane:"<<tprzesluchane<<endl;
			}
	}
	printf("/n");
}

//============================================================================================

void remove_list(struct dll_node**front)
{
	while(NULL!=*front)
	{
		printf("usunieto wpis: %d",(*front)->id);
		struct dll_node*next=(*front)->next;
		free(*front);
		*front=next;
	}
}

//============================================================================================

void sort_asc(struct dll_node*front)
{
	if(front==NULL)
	{
		printf("\n lista jest pusta \n");
	}
	else
	{
	int i=0;
	struct dll_node*tmp=(struct dll_node*)malloc(sizeof(struct dll_node));
	for(;NULL!=front->next;front=front->next);
	i++;
	for(i;i>=0;i--)
	{
	if(tmp->id < front->id)
	{
		tmp->id=front->id;
		tmp->artysta=front->artysta;
		tmp->tytul=front->tytul;
		tmp->rok=front->rok;
		tmp->gatunek=front->gatunek;
		tmp->stan=front->stan;
		tmp->przesluchany=front->przesluchany;
		tmp=tmp->next;
		tmp->next->prev=tmp;
		front=front->next;
	}
	else if(tmp->id==front->id)
			{
				tmp->id=front->id;
				tmp->artysta=front->artysta;
				tmp->tytul=front->tytul;
				tmp->rok=front->rok;
				tmp->gatunek=front->gatunek;
				tmp->stan=front->stan;
				tmp->przesluchany=front->przesluchany;
				tmp=tmp->next;
				front=front->next;
			}
			else
			{
			front=front->next;
			i++;
			}
	}
	for(;NULL!=front->next;front=front->next);
	{
		front->id=tmp->id;
		front->artysta=tmp->artysta;
		front->tytul=tmp->tytul;
		front->rok=tmp->rok;
		front->gatunek=tmp->gatunek;
		front->stan=tmp->stan;
		front->przesluchany=tmp->przesluchany;
		tmp=tmp->next;
		front=front->next;
	}
	free(tmp);
	}
}



//============================================================================================

void sort_desc(struct dll_node*front)
{
	if(front==NULL)
	{
		printf("\n lista jest pusta \n");
	}
	else
	{
		for(;NULL!=front->next;front=front->next);
		struct dll_node*tmp=(struct dll_node*)malloc(sizeof(struct dll_node));
		tmp->id=front->id;
		while(NULL!=front)
		{
			if(tmp->id > front->id)
			{
				tmp->id=front->id;
				tmp->artysta=front->artysta;
				tmp->tytul=front->tytul;
				tmp->rok=front->rok;
				tmp->gatunek=front->gatunek;
				tmp->stan=front->stan;
				tmp->przesluchany=front->przesluchany;
				tmp=tmp->next;
				tmp->next->prev=tmp;
				front=front->prev;
			}
			else if(tmp->id==front->id)
			{
				tmp->id=front->id;
				tmp->artysta=front->artysta;
				tmp->tytul=front->tytul;
				tmp->rok=front->rok;
				tmp->gatunek=front->gatunek;
				tmp->stan=front->stan;
				tmp->przesluchany=front->przesluchany;
				tmp=tmp->next;
				front=front->prev;
			}
			else
			{
			front=front->prev;
			}
		}
	}
}

//============================================================================================

void zapisz_do_pliku(struct dll_node*front)
{
	struct dll_node*tmp=(struct dll_node*)malloc(sizeof(struct dll_node));
	FILE *zapisz=NULL;
	char nazwa_pliku[]="baza_albumow.txt";
	if(front==NULL)
		printf("brak elementow do zapisania");
	else
	{
		zapisz=fopen(nazwa_pliku,"w");
			if(zapisz==NULL)
				printf("blad otwarcia pliku");
			else
			{
				tmp=front;
				while(tmp!=NULL)
				{
					fprintf(zapisz,"%d; ",tmp->id);
					fprintf(zapisz,"%s; ",tmp->artysta);
					fprintf(zapisz,"%s; ",tmp->tytul);
					fprintf(zapisz,"%d; ",tmp->rok);
					fprintf(zapisz,"%s; ",tmp->gatunek);
					fprintf(zapisz,"%d; ",tmp->stan);
					fprintf(zapisz,"%d; ",tmp->przesluchany);
					fprintf(zapisz,"\n");
					tmp=tmp->next;
				}
			printf("lista zostala zapisania");
			}
		fclose(zapisz);
	}
}

//============================================================================================
//problem z konwersj1 bufora z char* na int
void wczytaj_z_pliku(struct dll_node*front)
{
	FILE *wczytaj=NULL;
	int id;
	char* artysta;
	char* tytul;
	int rok;
	char* gatunek;
	int stan;
	int przesluchany;
	char* tmp;
	char nazwa_pliku[]="baza_albumow.txt";
	char bufor[30+1];
	wczytaj=fopen(nazwa_pliku,"r");
	if(wczytaj==NULL)
		printf("blad otwarcia pliku");
	else
	{
		while(fscanf(wczytaj, "%s",bufor)!=EOF)
		{
			tmp=bufor;
            id=atoi(bufor);
			tmp="";

			fscanf(wczytaj, "%s",bufor);
			//strcpy(artysta.c_str(),bufor);
			artysta=bufor;

			fscanf(wczytaj, "%s",bufor);
			//strcpy(tytul.c_str(),bufor);

			fscanf(wczytaj, "%s",bufor);
			//strcpy(tmp.c_str(),bufor);
			tmp=bufor;
			rok=atoi(bufor);
			tmp="";

			fscanf(wczytaj, "%s",bufor);
			//strcpy(gatunek.c_str(),bufor);
			gatunek=bufor;

			fscanf(wczytaj, "%s",bufor);
			//strcpy(tmp.c_str(),bufor);
			tmp=bufor;
			stan=atoi(bufor);
			tmp="";

			fscanf(wczytaj, "%s",bufor);
			//strcpy(tmp.c_str(),bufor);
			tmp=bufor;
			przesluchany=atoi(bufor);
			tmp="";

			front=insert_node(front,id,artysta,tytul,rok,gatunek,stan,przesluchany);
		}
		printf("plik zostal wczytany");
	}
	fclose(wczytaj);
}

//============================================================================================

int main()
{
	int id=0;
	char* art="a";
	char* szukane="";
	char* tyt="a";
	int year=2000;
	char* gat="";
	int select=11;
	int select_sort=0;
	int sort_asc_sel=0;
	int sort_desc_sel=0;
	char* tprzes;
	char* tst;
	int i=0;
	int przes=0;
	int st=0;

	struct dll_node*front=create_list(id,art,tyt,year,gat,st,przes);

	while(select!=0)
	{
		printf("=======LISTA ALBUMOW========\n");
		printf("I 1.aktualna lista         I\n");
		printf("I 2.dodaj do listy         I\n");
		printf("I 3.edytuj wpis            I\n");
		printf("I 4.wyszukaj wpis          I\n");
		printf("I 5.usun wpis              I\n");
		printf("I 6.wczytaj z bazy         I\n");
		printf("I 7.zapisz do bazy         I\n");
		printf("I 8.usun cala liste        I\n");
		printf("I 9.sortowanie             I\n");
		printf("I 0.zakoncz i wyjdz        I\n");
		printf("============================\n");
		printf("\n");
		printf("podaj komende: ");
		scanf("%d",&select);

		switch(select)
		{
		case 1:
			print_list(front);
		break;

		case 2:

            i++;
			printf("podaj artyste/artystow:");
			scanf("%s",&art);
			printf("podaj tytul:");
			scanf("%s",&tyt);
			printf("podaj rok:");
			scanf("%d",&year);
			printf("podaj gatunek muzyczny:");
			scanf("%s",&gat);
			front=insert_node(front,i,art,tyt,year,gat,1,0);
		break;

		case 3:
			printf("podaj szukane id:");
			scanf("%d",&id);
			while((NULL!=front)&&(front->id!=id))
				front=front->next;
			if(front->id==id)
			{
			printf("podaj artyste/artystow:\n");
			scanf("%s",art);
			printf("podaj tytul:\n");
			scanf("%s",tyt);
			printf("podaj gatunek muzyczny:\n");
			scanf("%s",gat);
			printf("podaj rok:\n");
			scanf("%d",&year);
			front->artysta=art;
			front->tytul=tyt;
			front->gatunek=gat;
			front->rok=year;
			front->przesluchany=0;
			front->stan=1;
			}
		break;

		case 4:
			printf("podaj tytul szukanej piosenki:\n");
			scanf("%s",szukane);
			while((NULL!=front)&&(front->tytul!=szukane))
				front=front->next;

				if(front->stan==1)
					tst="dostepne";
				else
					tst="niedostepne";
				if(front->przesluchany==1)
					tprzes="przesluchane";
				else
					tprzes="nie przesluchane";
			printf("id:%d artysta:%s tytul:%s rok:%d gatunek:%s stan:%s przesluchane:%s",front->id,front->artysta,front->tytul,front->rok,front->gatunek,tst,tprzes);
			printf("\n");
			//cout<<"id:"<<front->id<<" artysta:"<<front->artysta<<" tytul:"<<front->tytul<<" rok:"<<front->rok<<" gatunek:"<<front->gatunek<<" stan:"<<tst<<" przesluchane:"<<tprzes<<endl;

		break;

		case 5:
			printf("podaj ktory wpis ma byc usuniety:");
			scanf("%d",&id);
			if(front->id==id)
			{
				front=delete_node(front,id);
				i--;
			}
		break;

		case 6:
			printf("wczytuje z bazy...\n");
			wczytaj_z_pliku(front);
		break;

		case 7:
			printf("zapisuje do bazy...");
			zapisz_do_pliku(front);
		break;

		case 8:
			printf("usuwam liste...");
			remove_list(&front);
		break;

		case 9:
			printf("asc:1/desc:2/return:3");
			scanf("%d",&select_sort);
			switch(select_sort)
			{
				case 1:
				printf("po jakim elemencie chcesz sortowaa?\n");
				printf("1.tytul,2.artysta,3.rok,4.rok,5.stan,6.czy przesluchany:\n");
				scanf("%d",&sort_asc_sel);
				switch(sort_asc_sel)
				{
					case 1:
					printf("posortowane po tytule");

					break;

					case 2:
					printf("posortowane po artyscie");

					break;

					case 3:
					printf("posortowane po roku");

					break;

					case 4:
					printf("posortowane po gatunku");

					break;

					case 5:
					printf("posortowane po stanie");

					break;

					case 6:
					printf("posortowane czy przesluchany");

					break;

					default:

					printf("\n zly wybor, powrot do glownego menu...");

					break;
					}
					break;

			case 2:
				printf("po jakim elemencie chcesz sortowaa?\n");
				printf("1.tytul,2.artysta,3.rok,4.gatunek,5.stan,6.czy przesluchany:\n");
				scanf("%d",&sort_asc_sel);
				switch(sort_desc_sel)
				{
					case 1:
					printf("posortowane po tytule");

					break;

					case 2:
					printf("posortowane po artyscie");

					break;

					case 3:
					printf("posortowane po roku");

					break;

					case 4:
					printf("posortowane po gatunku");

					break;

					case 5:
					printf("posortowane po stanie");

					break;

					case 6:
					printf("posortowane czy przesluchany");

					break;

					default:

					printf("\n zly wybor, powrot do glownego menu...");

					break;
					}
					break;
			break;

			default:
			printf("powrot do wczesniejszego menu...");
			break;
			}
		break;

		case 0:
			exit(1);
		break;

		default:
			printf("nie znajduje sie na liscie wyborów");
		break;
		}

	}
	return 0;
}

