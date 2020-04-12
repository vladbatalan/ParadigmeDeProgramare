// C Program for Message Queue (Reader Process)
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <string.h>
#include <stdlib.h>


// structure for message queue
struct mesg_buffer {
	long mesg_type;
	char mesg_text[10000];
} message;

int isLetter(char c){
	char* ans = strchr("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890", c);
	return (ans != NULL);
}

int validate_html_content(char *msg){
	printf("Starting to study if is html\n");
	//create a copy to destroy using strtok
	int index = 0;
	
	printf("Search for <:\n");
	//search for '<'
	while(msg[index] != 0 && msg[index] != '<')
		index ++;
	if(msg[index] == 0) return 0;
	
	printf("Found < on index %d:\n", index);
	//found '<'
	//check tag
	char tag[100];
	char sectag[100];
	int sectag_size = 0;
	int tag_size = 0;

	//jump over '<'
	index ++;
	
	printf("Clear spaces:\n");
	//clear spaces
	while(msg[index] != 0 && msg[index] == ' ')
		index ++;
	if(msg[index] == 0) return 0;

	printf("Done:\n");
	printf("Collecting the tag from index %d:\n", index);
	//we have a word
	while(msg[index] != 0 && isLetter(msg[index])){
		tag[tag_size++] = msg[index];
		index ++;
	}
	if(msg[index] == 0) return 0;
	tag[tag_size] = 0;

	printf("Tag collected! It is: %s\nThe index is: %d\n", tag, index);
	while(1){
		printf("Search for second <:\n");
		//search for '<'
		while(msg[index] != 0 && msg[index] != '<')
			index ++;
		if(msg[index] == 0) return 0;
		printf("Found < on index %d:\n", index);

		index ++;
		printf("Clear spaces:\n");
		//clear spaces
		while(msg[index] != 0 && msg[index] == ' ')
			index ++;
		if(msg[index] == 0) return 0;
		
		printf("Done:\n");
		printf("Search / delimitator at index %d which contains %c:\n", index, msg[index]);
		if(msg[index] != '/') continue;
		index ++;
		printf("Found / delimitator on index %d:\n", index);

		printf("Clear spaces:\n");
		//clear spaces
		while(msg[index] != 0 && msg[index] == ' ')
			index ++;
		if(msg[index] == 0) return 0;
		printf("Done:\n");
		
		printf("Collecting the second tag on index %d:\n", index);
		sectag_size = 0;
		//we have a word
		while(msg[index] != 0 && isLetter(msg[index]))
		{
			sectag[sectag_size++] = msg[index];
			index ++;
		}
		if(msg[index] == 0) return 0;
		sectag[sectag_size] = 0;
		printf("Found second tag! It is: %s\n", sectag);
		
		if(strcmp(tag, sectag) == 0) return 1;
		printf("Unfortunatelly it does not match... next:\n");
	}
	return 0;
}



int main(){
	char *msg = NULL;
	// gcc receiver.c -o receiver
	// ./receiver    
	key_t key;
	int msgid;

	// ftok to generate unique key    
	key = ftok("message_queue_name",'B');
	
	// msgget creates a message queue and returns identifier
	msgid = msgget(key, 0666 | IPC_CREAT);    
	message.mesg_type = 1;

	// msgget creates a message queue
	// and returns identifier   
	char buffer[128];
	int sz;

	while(1){
		msgid = msgget(key, 0666 | IPC_CREAT);
		// msgrcv to receive message  
		msgrcv(msgid, &message, sizeof(message), 1, 0);
		// display the message
		
		if(msg != NULL)
			free(msg);
		msg = (char*)malloc(sizeof(char) * (strlen(message.mesg_text)+1));
		strcpy(msg, message.mesg_text);

		int answer = validate_html_content(msg);

		printf("\nCurrent data is html? Answ: %d\n", answer);
		
		
		if(answer == 1){
			printf("Write the name of the html file(no extension):\n");
			fgets(buffer, 99, stdin);
			
			//add extension
			sz = strlen(buffer);
			buffer[sz-1] = '.';
			buffer[sz++] = 'h';
			buffer[sz++] = 't';
			buffer[sz++] = 'm';
			buffer[sz++] = 'l';
			buffer[sz] = 0;

			//create file
			FILE *f = fopen(buffer, "w");
			fputs(msg, f);
			fclose(f);

			printf("File %s was created!\n", buffer);
			
		}
		
		// to destroy the message queue    
		msgctl(msgid, IPC_RMID,NULL);
	}

 	return 0;
}




