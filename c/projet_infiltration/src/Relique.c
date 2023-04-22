#include "../include/Relique.h"
#include <stdio.h>

/**
 * @brief Initialisation des informations d'une relique
 * 
 * @param tab 
 *          tableau contenant la carte avec les mur
 * 
 * @return Relique 
 */
Relique init_relique(int **tab){
    Relique tmp;
    tmp.pos.x = (double)(rand() % 60);
    tmp.pos.y = (double)(rand() % 45); 
    while(tab[(int)tmp.pos.x][(int)tmp.pos.y] != 0 ){
        tmp.pos.x = (double)(rand() % 60) ;
        tmp.pos.y = (double)(rand() % 45) ;
    }
    tmp.isAlwaysFindbyGuard = 0;
    tmp.isFind = 0;
    return tmp;
}

/**
 * @brief Fonction permettant l'interaction du joueur avec une relique
 * 
 * @param r 
 * @param p 
 * @return int, 1 si la relique est trouvée, sinon 0
 */
int find_relique(Relique *r, Player p){
    if((r->pos.x == floor(p.pos.x)) && (r->pos.y == floor(p.pos.y)) 
    && (r->isFind == 0)){
        r->isFind = 1;
        return 1;
    }
    return 0;    
}

/**
 * @brief Fonction de detection de relique avec 
 * 
 * @param guard 
 *          Le garde
 * @param r 
 *          La relique
 * @param tab 
 *          carte avec les murs
 * @return int 
 */
static int detection_relique(Guard guard, Relique r, int** tab){
    double x, y;
    double distance_a, distance_b;
    double pa, a, ya;
    double pb, b, xb;

    distance_a = 0;
    distance_b = 0;
    x = floor(guard.pos.x) + 0.5;
    y = floor(guard.pos.y) + 0.5; 

    while(distance_a != -1 || distance_b != -1){
        distance_a = detection_a(guard.pos, r.pos, x, &a, &pa, &ya);
        if(distance_a != -1 && tab[(int)a][(int)ya] == 1){;
            return 0;
        }

        distance_b = detection_b(guard.pos, r.pos, y, &b, &pb, &xb);
        if(distance_b != -1 && tab[(int)xb][(int)b] == 1){
            return 0;
        }

        /*increase if player is to the right and decrease if is it to the left*/
        if(r.pos.x > guard.pos.x){
            x++; 
        }
        else{
            x--;
        }
         /*increase if player is to the down and decrease if is it to the up*/
        if(r.pos.y > guard.pos.y){
            y++; 
        }
        else{
            y--;
        }  
    }
    return 1;
}

/**
 * @brief Fonction permettant l'interaction d'un garde avec une relique
 * 
 * @param r 
 *        la relique
 * @param guard 
 *        le garde donc on utilse pour connaitre la vision
 * @param tab 
 *        La carte avec les murs
 * @param all_guard 
 *        l'ensembe des gardes
 * @param nb_guard 
 *        le nombre de garde
 * @return int int, 1 si la relique disparue est détectée, sinon 0
 */
int interaction_guard_relique(Relique *r, Guard *guard, int **tab, Guard *all_guard, int nb_guard){
    int i;
    if(distance(r->pos.x, r->pos.y, guard->pos.x, guard->pos.y) <= guard->range && r->isFind == 1 
    && r->isAlwaysFindbyGuard == 0){
        if(detection_relique(*guard, *r, tab)){
            for(i = 0; i < nb_guard; i++){
                panic_mode(&all_guard[i]);
            }
            r->isAlwaysFindbyGuard = 1;
        }
        return 1;
    }
    return 0;
}

/**
 * @brief Si le joueur prend la sortie
 * 
 * @param player 
 *         Le joueur et ces information
 * @param wallmap 
 *         La carte avec les murs
 * @param r 
 *         La liste de relique avec c'est position
 * @param nb_relique 
 *         le nombre de relique
 * @return int 
 */
int player_exit(Player player, int **wallmap, Relique* r, int nb_relique){
    int i, player_relique;
    player_relique = 0;
    for(i = 0; i < nb_relique; i++){
        if(r[i].isFind == 1){
            player_relique ++;
        }
    }
    if(wallmap[(int)player.pos.x][(int)player.pos.y] == 2 && nb_relique == player_relique){
        return 0;
    }
    return 1;
}