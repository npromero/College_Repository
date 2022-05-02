/*
Name: Niklas Romero
MTU username: npromero
Class: CS3411 systems prgramming
Teacher: Dr.  Soner Onder
TA: Tino Moore
program description: The program makes a basic malloc and free template using pointers and pointer arithmatic
*/
#include "hmalloc.h"
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
/*You may include any other relevant headers here.*/
/*Add additional data structures and globals here as needed.*/
void *free_list = NULL;
/* traverse
 * Start at the free list head, visit and print the length of each
 * area in the free pool. Each entry should be printed on a new line.
 */
void traverse(){
   /* Printing format:
	 * "Index: %d, Address: %08x, Length: %d\n"
	 *    -Index is the position in the free list for the current entry. 
	 *     0 for the head and so on
	 *    -Address is the pointer to the beginning of the area.
	 *    -Length is the length in bytes of the free area.
	 */
	if(free_list == NULL)
	{
		printf("No entries in free list");
	}
	else
	{
		void *tmp = free_list;
		int i = 0; // index increment
		int link = *(int *)(tmp + 4);
		do
		{
			link = *(int *)(tmp + 4); // getting the value of the pointer buffer
		int length = *(int *)tmp; // getting the value of the pointer length
		printf("Index: %d, Address: %08x, Length: %d \n",i,tmp , length);
		tmp = tmp + link ; // incrementing pointer
		i++;
		}
		while(link != 0);
	
	}
	
}

/* hmalloc
 * Allocation implementation.
 *    -will not allocate an initial pool from the system and will not 
 *     maintain a bin structure.
 *    -permitted to extend the program break by as many as user 
 *     requested bytes (plus length information).
 *    -keeps a single free list which will be a linked list of 
 *     previously allocated and freed memory areas.
 *    -traverses the free list to see if there is a previously freed
 *     memory area that is at least as big as bytes_to_allocate. If
 *     there is one, it is removed from the free list and returned 
 *     to the user.
 */
/*
char storage[4096];
int ffl = 0;
*/
void *hmalloc(int bytes_to_allocate){
	void *q;
	if(free_list == NULL) // If nothing has been freed
	{
		//make pointer structure: length|buffer/link field|user data 
		int size = bytes_to_allocate;
		q = sbrk(bytes_to_allocate + 8);
		*(int *)q = size;
		return (char *) q + 8;
	}
	else
	{

		void *temp = free_list;
		int link;
		void *prevnode = NULL;
		int prevlink = 0;
		do
		{

			link = *(int *)(temp + 4);
			int length = *(int *)temp;
			if(length >= bytes_to_allocate )
			{

				if(prevnode == NULL) // handling if the freelist pointer is the only available pointer in the free list
				{
					void * newtemp = temp;
					temp = temp + link;
					return (char *) (newtemp + 8);
				}
				else //remove from free list if a chunk of data in the free list was found
				{
					*(int *)(prevnode + 4) = (prevlink + link); //prevlink = link + prevlink
					*(int *)(temp + 4) = 0;
					return (char *) (temp + 8);
				}
					
			}
			//incrementing variables
		prevnode = temp;
		prevlink = link;
		temp = temp + link;
		}
		while(link != 0);
		//if nothing else had space make a new pointer
		int size = bytes_to_allocate;
		q = sbrk(bytes_to_allocate + 8);
		*(int *)q = size;
		return (char *) q + 8;
	}
	
		
}

/* hcalloc
 * Performs the same function as hmalloc but also clears the allocated 
 * area by setting all bytes to 0.
 */
void *hcalloc(int bytes_to_allocate){
	void * ptr = hmalloc(bytes_to_allocate); //make pointer with malloc
	void *tmp = ptr;
	int size = *(int *)(ptr - 8);
	for(int i =0; i < size; i++ )
	{
		*(int *)tmp = 0; // make all values of the pointer zero
		tmp = ptr + i;
		
	}
   return ptr; //placeholder to be replaced by proper return value
}

/* hfree
 * Responsible for returning the area (pointed to by ptr) to the free
 * pool.
 *    -simply appends the returned area to the beginning of the single
 *     free list.
 */

void hfree(void *ptr){
	//steart free list
	if(free_list == NULL)
	{
	int lk = 0;
	int *link = ptr - 4;
	*link = lk; 
	free_list = ptr - 8;
	}
	
	else
	{
		//add to free list
	void *tmp = free_list;
	free_list = ptr-8;
	int lk = tmp - free_list;
	int *link = free_list + 4;
	*link = lk; 

	}
	
}

/* For the bonus credit implement hrealloc. You will need to add a prototype
 * to hmalloc.h for your function.*/

/*You may add additional functions as needed.*/

