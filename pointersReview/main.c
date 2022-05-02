#include "hmalloc.h"
#include <stdio.h>
#include <string.h>
/*You may include any other relevant headers here.*/


/*	main()
 *	Use this function to develop tests for hmalloc. You should not 
 *	implement any of the hmalloc functionality here. That should be
 *	done in hmalloc.c
 *	This file will not be graded. When grading I will replace main 
 *	with my own implementation for testing.*/
int main(int argc, char *argv[]){
	char *p;
	p = (char *) hcalloc(10);
	//strcpy(p,"please");
	char *q = (char *) hmalloc(256);
	strcpy(q,"will this work");
	char *z = (char *) hmalloc(84);
	strcpy(z,"things will happen");
	char *a = (char *) hmalloc(44);
	char *b = (char *) hmalloc(21);
	printf("%s\n",p);
//	printf("%s\n",q);
	hfree(p);
	traverse();
	printf(" \n");
	hfree(q);
	traverse();
	printf(" \n");
	hfree(z);
	traverse();
	printf(" \n");
	hfree(a);
	traverse();
	printf(" \n");
	hfree(b);
	traverse();
	printf(" \n");
	char *c = (char *) hmalloc(39);
	hfree(c);
	traverse();
	printf(" \n");
	return 0;
}
