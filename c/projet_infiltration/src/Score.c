#include "../include/Score.h"
#include <string.h>

/**
 * @brief Comparation utilisé pour qsort
 * 
 * @param a 
 *          premier élement 
 * @param b 
 *          deuxieme element
 * @return int 
 */
int compare(const void *a,const void *b){
    Score * c = (Score *) a;
    Score * d = (Score *) b;
    if(c->notplayer == 1){
        return 1;
    }
    else if(d->notplayer == 1){
        return -1;
    }
    if(c->time.tv_sec - d->time.tv_sec == 0){
        return c->time.tv_nsec - d->time.tv_nsec;
    }
    return c->time.tv_sec - d->time.tv_sec;
}

/**
 * @brief Fonction pour ecrire les donnée en binaire
 * 
 * @param player 
 *          Liste de score dans le all of fame plus le score du joueur
 * @param size 
 *          Taille du all of fame
 */
void writefile(Score player[], int size){
    FILE* file;
    int i;
    file = fopen("All_of_fame", "w");
    qsort(player, 11, sizeof(Score), compare);
    for(i = 0; i < size; i++){
        fwrite(&player[i] , sizeof(Score) , 1 , file);
    }
    fclose(file);
}

/**
 * @brief Fonction lire un fichier binaire
 * 
 * @param player 
 *      Liste de score dans le all of fame plus le score du joueur
 */
void readfile(Score player[]){
    FILE* file;
    int i;
    file = fopen("All_of_fame", "r");
    i = 0;
    if(file != NULL){
        while(i < 10){
            fread(&player[i] , sizeof(Score) , 1 , file);
            i++;
        }   
        fclose(file);
    }
    else{
        for(int i = 0; i < 10; i++){
            strcpy(player[i].name, "aaa");
            player[i].time.tv_sec = 0;
            player[i].time.tv_nsec = 0; 
            player[i].notplayer = 1;
        }
    }
}

