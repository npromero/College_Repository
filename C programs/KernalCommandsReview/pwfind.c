/*========================================================================
 pwfind.c
 -------------------------------------------------------------------------
  Functionality: 
  (1) Generate all character strings comprised of lower case a through z 
      that have a length between the value given by argv[1]
      and the value given by argv[2]
  (2) Hashes each string using MD5 and compares the hexadecimal value of 
      the first MATCHBYTES bytes of the MD5 hash to argv[3]
  (3) If there is a match, output the string and exit

  COMPLIATION: gcc -o pwfind pwfind.c -lcrypto
  INVOCATION:  ./pwfind num1 num2 partialhash

  NOTES: - Writes are not atomic in the debug modes.
         - unsigned char is frequently used to hold values that do not 
           map to characters. Printing these to the terminal can create
           problems.  "stty sane" may help.  Creating a new terminal 
           session is sure to help.
  ========================================================================
*/

#include <openssl/evp.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>

#define MD5LENGTH  16  /* MD5 Digest size in bytes */
#define MATCHBYTES  4  /* Must match this number of bytes from MD5SUM */

/*=================================================================
  =================================================================
*/
void fprint_string_as_hex(FILE * f, unsigned char *s, int len)
{
       /* Don't need zero termination */
       /* Notice that each byte results in two characters */
	int             i;
	for (i = 0; i < len; i++) fprintf(f, "%02x", s[i]);	
}
/*=================================================================
    Get the MD5 hash.  
  -----------------------------------------------------------------
    unsigned char *str points to the bytes to hash
    unsigned char *hash points to the hash

    Uses the envelope interface to OpenSSL.  "man evp" for more.
   =================================================================*/
int getmd5(unsigned char *str, unsigned char *hash)
{

	EVP_MD_CTX      *hashctx;            /* Context for MD5 sum */
	int             hashlen;             /* Length of MD5 sum */

       
	hashctx=EVP_MD_CTX_new();  /* Allocate a context */
        EVP_MD_CTX_init(hashctx);  /* Initialize it */
	EVP_DigestInit_ex(hashctx, EVP_md5(), NULL); /* Specify the algorithm */
	EVP_DigestUpdate(hashctx, str, strlen(str)); /* Add data to hash */
	EVP_DigestFinal_ex(hashctx, hash, &hashlen); /* No more data */
        EVP_MD_CTX_destroy(hashctx);  /* Free the context */
        if (hashlen != MD5LENGTH) return(0);
	
#ifdef DEBUGMD5
	fprintf(stdout, "GETMD5: Digest of: <%s>", str);
	fprint_string_as_hex(stdout, hash, hashlen);
	fprintf(stdout, ">\n");
#endif
	
        return(hashlen);
	
}
/*=================================================================
   Checks whether the hashed password matches the input hash value 
  -----------------------------------------------------------------
   char *pw is a pointer to the password being checked
   pwhash is the value that must be matched.
   =================================================================*/
void checkPw(char *pw,char *pwhash)
{
  int hashlen;  /* Length of hash returned from hash implementation */
  unsigned char   hash[MD5LENGTH]; /* The hash value.  This probably contains
                                      unprintable characters.  It is the "raw"
                                      hash value */
  char writeBuf[200];              /* Roughly sized buffer to hold output so
                                      that it can be printed atomically with
                                      the write system call */
  int i;                           /* Loop counter */
  char hexstr[3];                  /* Char representation of one byte of hash in hex */
  
  
               bzero(hash,MD5LENGTH);
               hashlen=getmd5(pw,hash);                                       
#ifdef DEBUG                                                                   
               fprintf(stdout, "CHECKPW: Digest of: <%s> is: <", pw);
               fprint_string_as_hex(stdout, hash, hashlen);
               fprintf(stdout, ">\n");
	       printf("Hashlen is %d\n",hashlen);
	       
#endif
	       i=0;

	       /* One byte of hash produces two characters of input to match */
	       for (i=0;i<MATCHBYTES;i++)
		 {
 	         snprintf(hexstr,3,"%02x",hash[i]);
	         if ((hexstr[0]!=pwhash[i*2])||(hexstr[1]!=pwhash[i*2+1]))break;
		 if (i==(MATCHBYTES-1))
		   {
		     bzero(writeBuf,200);
		     snprintf(writeBuf,200,"<%d> Found! Password is <%s>\n",getpid(),pw);
		     write(1,writeBuf,strlen(writeBuf));
		        
#ifdef DEBUG
		     fprintf(stdout, "Digest of: <%s> is: <", pw);
                     fprint_string_as_hex(stdout, hash, hashlen);
                     fprintf(stdout, ">\n");
#endif
                     exit(1);
		   }
		 }
	       
}
/*=================================================================
 Generates all strings of specified length using recursion.  Runs a
   function against each string.
  -----------------------------------------------------------------
   char *str - character set from which to generate the strins
   char *data - holds a string of the set being generated
   int last - length of string being generated for this invocation
   int index - character of string being generated
   void *oneach - function to call for each string, first argument is the
                  generated string, second is user specified
   char *arg2 - the user specified argument to oneach
/*=================================================================*/
void permRecur (char *str, char* data, int last, int index, void oneach(char *, char *),char *arg2)
{
    int i, len = strlen(str);
    
    for ( i=0; i<len; i++ )
    {
        data[index] = str[i] ;
        if (index == last)
	  {
#ifdef DEBUGSTRINGS
            printf("%s\n", data);
#endif
	    oneach(data,arg2);
	  }
	
	else
	    permRecur (str, data, last, index+1, oneach, arg2);
    }
    return;
	
}
 
  
int
main(int argc, char **argv)
{
	char            charset[]="abcdefghijklmnopqrstuvwxyz";
        int             start; /* Smallest string length */
	int             end;   /* Largest string length */
	int             ctr;   /* Loop counter */
	unsigned char   *pw;             /* Candidate password */
        char            bufForAtomic[200]; /* Roughly sized buffer for
                                              for atomic writes */
	

	/* Failing silently on all these checks.*/
        /* Check the arguments */
	if (argc!=4) exit(1);
	

	/* Only generate strings for lengths between 1 and 100 */
	start=atoi(argv[1]);
        if ((start<=0)||(start>100))exit(1);
	
	end=atoi(argv[2]);
        if ((end<=0)||(end>100))exit(2);

	if (end < start) exit(2);
	
	pw=malloc(end+1);

	/* Check that each character of hash is a digit or a
           lowercase character a-f
	*/
        if (strlen(argv[3])!=MATCHBYTES*2)exit(3);
	for (ctr=0;ctr<strlen(argv[3]);ctr++)
	  if ( !(isdigit(argv[3][ctr])) &&
	       !((argv[3][ctr]>=97)&&(argv[3][ctr]<=102)))
	     exit(3);

	bzero(bufForAtomic,200);
	snprintf(bufForAtomic,200,"Process %d invoked as: %s %s %s\n",
		 getpid(),argv[1],argv[2],argv[3]);
	write(1,bufForAtomic,strlen(bufForAtomic));
	
        /* Generate the strings and check them along the way */
	for (ctr=start;ctr<=end;ctr++)
	  {
	    bzero(pw,end-1);
   	    bzero(bufForAtomic,200);
	    snprintf(bufForAtomic,200,"<%d> Generating strings of length %d\n",
		     getpid(),ctr);
	    write(1,bufForAtomic,strlen(bufForAtomic));
	    permRecur(charset,pw,ctr-1,0,checkPw,argv[3]);
	  }
		
}


