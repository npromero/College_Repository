/*
Creator: Niklas Romero
Class: CS3331 concurrent programming
teacher: Jean Mayo
Description: The program creates a group of password crackers made from pwfind. The program trys to make the specified number
of processes in an equal number of of search lengths
*/
#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include<sys/wait.h>
int main(int argc, char *argv[])
{
    //checking for input errors
	if(argc < 4)
    {
		printf("not enough parameters usage <8chars int int int> for <code min max processes> \n");
		exit(0);
	}
    else if (argc > 5)
    {
        printf("too many parameters usage <8chars int int int> for <code min max processes> \n");
		exit(0);
    }

    //settting up nessicary variables
    int max = atoi(argv[3]);
    int min = atoi(argv[2]);
    int processes = atoi(argv[4]);

    //checking for more input errors
    if(min > max)
    {
        printf("The minimum value cannot be greater than the max value in the range input parameters <code min max processes>\n");
		exit(0);
    }
    if(processes <= 0)
    {
        printf("The number of proceses cannot be zero or less usage: <code min max processes>\n");
		exit(0);
    }
    if(max <= 0 || min < 0)
    {
        printf("max must be > 0 & min must be >= 0\n");
		exit(0);
    }
    char* code = argv[1];
    char* file = "pwfind";
    char arguments[2][100];
    sprintf(arguments[2], "%s", code);
    int range = max-min + 1;
    int i,prev; 
    prev = min;
    char copy[100];
    int steps = range/processes;
    //Start of loop for the process creation
    for(i = 1; i <= processes; i++)
    {
        // Making sure the process does not somehow create too many proceses
        if(max < prev)
        {
            processes = processes - 1;
        }
        else if(prev + steps >= max )
        {
            //making child to start pwfind as the main proccess continues
            if(fork() == 0)
            {
                sprintf(arguments[0], "%d", prev);
                sprintf(arguments[1], "%d", max);
                 char* const filename = file; 
                char* const args[] = {file,arguments[0],arguments[1],code,NULL};
               printf("%s %s %s %s\n",args[0],args[1],args[2],args[3],args[4]);
               //starting pwfind
                 if(execl( file, file,args[1], args[2], code,NULL) == -1)
                 {
                     printf("Failed to exec\n");
                     exit(0);
                 }
            }
        }
        else if(prev + steps <= max && i == processes )
        {
             //making child to start pwfind as the main proccess continues
            if(fork() == 0)
            {
                sprintf(arguments[0], "%d", prev);
                sprintf(arguments[1], "%d", max);
                  char* const filename = file; 
                char* const args[] = {file,arguments[0],arguments[1],code,NULL};
               printf("%s %s %s %s\n",args[0],args[1],args[2],args[3],args[4]);
               //starting pwfind
                 if(execl( file, file,args[1], args[2], code,NULL) == -1)
                 {
                     printf("Failed to exec\n");
                     exit(0);
                 }
            }
        }
        else
        {
            int tmp = prev + steps;
             //making child to start pwfind as the main proccess continues
            if(fork() == 0)
            {
                sprintf(arguments[0], "%d", prev);
                sprintf(arguments[1], "%d", tmp);
                  char* const filename = file; 
                 char* const args[] = {file,arguments[0],arguments[1],code,NULL};
               printf("%s %s %s %s\n",args[0],args[1],args[2],args[3],args[4]);
               //starting pwfind
                if(execl( file, file,args[1], args[2], code,NULL) == -1)
                 {
                     printf("Failed to exec\n");
                     exit(0);
                 }
            }
        }
        prev = prev + steps + 1;
    }
    //waiting for all proceses to finish executing
    for(i = 1; i <= processes; i++)
    {
         wait(NULL);
    }
   
    printf("finished waiting\n");
}