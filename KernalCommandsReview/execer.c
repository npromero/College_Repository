#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include<sys/wait.h>
int main(int argc, char *argv[])
{
    if(fork() == 0)
    {
        char* const file = "pwfind";
        char* const min = "1";
        char* const max = "2";
        char* const code = "4124bc0a";
        execl( file, file,min,  max, code,NULL);
    }
}