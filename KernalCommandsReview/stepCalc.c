#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include<sys/wait.h>
int main(int argc, char *argv[])
{
    int max = atoi(argv[2]);
    int min = atoi(argv[1]);
    int processes = atoi(argv[3]);
    int range = max-min;
    int i,prev; 
    prev = min;
    int steps = range/processes;
     if(range < processes)
     {
         printf("range must be greater than num processes\n");
         exit(0);
     }
    for(i = 1; i <= processes; i++)
    {
        if(max >= prev + steps +1)
        {
            printf("going through steps %d through %d for process %d\n",prev , prev + steps, i);
        }
        else
        {
            printf("going through steps %d through %d for process %d\n",prev , max, i);
        }
        prev = prev + steps + 1;
    }
}