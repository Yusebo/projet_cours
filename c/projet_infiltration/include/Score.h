#ifndef __SCORE_H__
#define __SCORE_H__
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct{
    char name[3];
    struct timespec time;
    int notplayer;
}Score;

void writefile(Score player[], int size);
void readfile(Score player[]);

#endif