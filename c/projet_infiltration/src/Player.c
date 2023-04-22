#include "../include/Player.h"
#include <stdio.h>
#include <math.h>

/**
 * @brief Initialise les informations d'un joueur
 * 
 * @return player 
 */
Player init_player(){
    Player p;
    p.mana = 0;    
    p.pos.x = 2;
    p.pos.y = 2;
    p.move.speed = 0;
    p.move.min_speed = 0;
    p.move.max_speed = 0.9 * V;
    p.move.dir = standby;
    p.isInvinsible = 0;
    return p;
}

/**
 * @brief La fonction gère le calcul de l'accélération surchargé
 * 
 * @param player 
 */
void Overloaded_Acceleration(Player* player){
    if (player->mana > 2)
        player->move.max_speed = 1.2 * V;
}

/**
 * @brief Permet la gestion du pouvoir d'invisibilité
 * 
 * @param player 
 */
void invisibility(Player* player){

    if (player->mana > 0 && player->isInvinsible == 0) 
        player->isInvinsible = 1;
}

/**
 * @brief La fonction calcul l'augmentation de l'accelération
 * 
 * @param player 
 */
void player_accelaration(Player* player){
    if(player->move.speed +  0.3 * V < player->move.max_speed){
        player->move.speed += 0.3 * V;
    }
    else{
        player->move.speed = player->move.max_speed;
    }
}

/**
 * @brief Calcule les diagonales vers le bas
 * 
 * @param x 
 *      coordoonée sur l'horizontal
 * @param right_diagonal 
 *      la diagonal de droite a recupere
 * @param left_diagonal 
 *      la diagonal de gauche a recupere
 */
void down_diagonale(double x, double* right_diagonal,double* left_diagonal){    
    *left_diagonal = (1.0 / 4.0) - (x - floor(x)) * (x - floor(x));
    *right_diagonal = (1.0 / 4.0) - (floor(x) + 1 - x) * (floor(x) + 1 - x);
    if(*right_diagonal < 0){
        *right_diagonal = 0;
    }
    if(*left_diagonal < 0){
        *left_diagonal = 0;
    }
}

/**
 * @brief Calcule les diagonales vers le Haut
 * 
 * @param x 
 *      coordoonée sur l'horizontal
 * @param right_diagonal 
 *      la diagonal de droite a recupere
 * @param left_diagonal 
 *      la diagonal de gauche a recupere
 */
void up_diagonale(double x, double* right_diagonal,double* left_diagonal){    
    *left_diagonal = (1.0 / 4.0) - (x - floor(x)) * (x - floor(x));
    *right_diagonal = (1.0 / 4.0) - (floor(x) + 1 - x) * (floor(x) + 1 - x);
    if(*right_diagonal < 0){
        *right_diagonal = 0;
    }
    if(*left_diagonal < 0){
       *left_diagonal = 0;
    }
}

/**
 * @brief Calcule les diagonales vers la droite
 * 
 * @param y
 *      coordoonée sur la vertical
 * @param right_diagonal 
 *      la diagonal de droite a recupere
 * @param left_diagonal 
 *      la diagonal de gauche a recupere
 */
void right_diagonale(double y, double* right_diagonal, double* left_diagonal){    
    *left_diagonal = (1.0 / 4.0) - (y - floor(y)) * (y - floor(y));
    *right_diagonal = (1.0 / 4.0) - (floor(y) + 1 - y) * (floor(y) + 1 - y);
    if(*right_diagonal < 0){
        *right_diagonal = 0;
    }
    if(*left_diagonal < 0){
        *left_diagonal = 0;
    }
}

/**
 * @brief Calcule les diagonales vers le gauche
 * 
 * @param y
 *      coordoonée sur la vertical
 * @param right_diagonal 
 *      la diagonal de droite a recupere
 * @param left_diagonal 
 *      la diagonal de gauche a recupere
 */
void left_diagonale(double y, double* right_diagonal,double* left_diagonal){    
    *left_diagonal = (1.0 / 4.0) - (y - floor(y)) * (y - floor(y));
    *right_diagonal = (1.0 / 4.0) - (floor(y) + 1 - y) * (floor(y) + 1 - y);
    if(*right_diagonal < 0){
        *right_diagonal = 0;
    }
    if(*left_diagonal < 0){
        *left_diagonal = 0;
    }
}


/**
 * @brief La fonction sert a gere les collision du mur dans la direction en bas
 * + ou - 0.01 est la pour ce deplacer quand le joueur est pile au centre a coté du carré a coté du mur
 * @param tab 
 *        tableau representant la carte et les murs quel comporte
 * @param x 
 *       coordoonée sur l'horizontal
 * @param y 
 *      coordoonée sur la vertical
 * @return int 1 s'il n'y a pas de collision
 */
int wall_hitbox_down(int ** tab, double x, double y){
    double right_diagonal, left_diagonal;        
    down_diagonale(x, &right_diagonal, &left_diagonal);
    if(tab[(int)(x)][(int)(y + 0.5)] != 1){
        if(tab[(int)(x - 0.01 + (x - floor(x)))][(int)(y + sqrt(right_diagonal))] !=1 && 
        tab[(int)(x + 0.01 - (floor(x) + 1 - x))][(int)(y + sqrt(left_diagonal))] !=1){
            return 1;
        }
    }
    return 0;
}



/**
 * @brief La fonction sert a gere les collision du mur dans la direction en haut
 * + ou - 0.01 est la pour ce deplacer quand le joueur est pile au centre a coté du carré a coté du mur
 * @param tab 
 *        tableau representant la carte et les murs quel comporte
 * @param x 
 *       coordoonée sur l'horizontal
 * @param y 
 *      coordoonée sur la vertical
 * @return int 1 s'il n'y a pas de collision
 */
int wall_hitbox_up(int ** tab, double x, double y){
    double right_diagonal, left_diagonal;        
    up_diagonale(y, &right_diagonal, &left_diagonal);
    if(tab[(int)(x)][(int)(y - 0.5)] != 1){
        if(tab[(int)(x - 0.01 + (x - floor(x)))][(int)(y - sqrt(right_diagonal))] !=1 && 
        tab[(int)(x + 0.01 - (floor(x) + 1 - x))][(int)(y - sqrt(left_diagonal))] !=1){
            return 1;
        }
    }
    return 0;
}



/**
 * @brief La fonction sert a gere les collision du mur dans la direction a droite
 * + ou - 0.01 est la pour ce deplacer quand le joueur est pile au centre a coté du carré a coté du mur
 * @param tab 
 *        tableau representant la carte et les murs quel comporte
 * @param x 
 *       coordoonée sur l'horizontal
 * @param y 
 *      coordoonée sur la vertical
 * @return int 1 s'il n'y a pas de collision
 */
int wall_hitbox_right(int ** tab, double x, double y){
    double right_diagonal, left_diagonal;
    right_diagonale(y, &right_diagonal, &left_diagonal);    
    
    if(tab[(int)(x + 0.5)][(int)(y)] != 1){
        if(tab[(int)(x + sqrt(right_diagonal))][(int)(y - 0.01 + (y - floor(y)))] !=1 && 
        tab[(int)(x + sqrt(left_diagonal))][(int)(y + 0.01 - (floor(y) + 1 - y))] !=1){
            
            return 1;
        }
    }
    return 0;
}

/**
 * @brief La fonction sert a gere les collision du mur dans la direction a gauche
 * + ou - 0.01 est la pour ce deplacer quand le joueur est pile au centre a coté du carré a coté du mur
 * @param tab 
 *        tableau representant la carte et les murs quel comporte
 * @param x 
 *       coordoonée sur l'horizontal
 * @param y 
 *      coordoonée sur la vertical
 * @return int 1 s'il n'y a pas de collision
 */
int wall_hitbox_left(int ** tab, double x, double y){
    double right_diagonal, left_diagonal;
    left_diagonale(y, &right_diagonal, &left_diagonal);
    if(tab[(int)(x - 0.5)][(int)(y)] != 1){
        if(tab[(int)(x - sqrt(right_diagonal))][(int)(y - 0.01 + (y - floor(y)))] !=1 && 
        tab[(int)(x - sqrt(left_diagonal))][(int)(y + 0.01 - (floor(y) + 1 - y))] !=1){
            return 1;
        }
    }
    return 0;
}


/**
 * @brief Calcul les mouvement sur la vertical
 * 
 * @param player 
 * @param direction 
 */
static void player_calcul_move_x(Player* player, Direction direction, int **tab){
    double future_position;
    if(direction == right){ //
        future_position = player->pos.x + player->move.speed;
        if(wall_hitbox_right(tab, future_position, player->pos.y)){
            player->pos.x += player->move.speed;
        }
        else{
            future_position = player->pos.x + (floor(player->pos.x + 1) - 0.5 - player->pos.x);
            if(wall_hitbox_right(tab, future_position, player->pos.y)){
                player->pos.x += (floor(player->pos.x + 1) - 0.5 - player->pos.x);
            }
        } 
    }else{
        future_position = player->pos.x - player->move.speed;
        if(wall_hitbox_left(tab, future_position , player->pos.y)){
            player->pos.x -= player->move.speed;
        }
        else {
            future_position = player->pos.x - (player->pos.x - floor(player->pos.x) - 0.5);
            if(wall_hitbox_left(tab, future_position, player->pos.y)){
                player->pos.x -= (player->pos.x - floor(player->pos.x) - 0.5);
            }
        }
    }      
}

/**
 * @brief Calcul les mouvement sur l'horizontal
 * 
 * @param player 
 * @param direction 
 */
static void player_calcul_move_y(Player* player, Direction direction, int **tab){
    double future_position;
    if(direction == down){
        future_position = player->pos.y + player->move.speed;
        if(wall_hitbox_down(tab, player->pos.x, future_position)){
            player->pos.y += player->move.speed;
        }
        else {
            future_position = player->pos.y + (floor(player->pos.y - 0.5 + 1) - player->pos.y);
            if(wall_hitbox_down(tab, player->pos.x, future_position)){
                player->pos.y += (floor(player->pos.y + 1) - 0.5 - player->pos.y);
            }
        }
    }else{
        future_position = player->pos.y - player->move.speed ;
        if(wall_hitbox_up(tab, player->pos.x , future_position)){
            player->pos.y -= player->move.speed;
        }
        else {
            future_position = player->pos.y - (player->pos.y - floor(player->pos.y) - 0.5);
            if(wall_hitbox_up(tab, player->pos.x, future_position)){
                player->pos.y -= (player->pos.y - floor(player->pos.y) - 0.5);
            }

        }
    }
}

/**
 * @brief deplacement en x
 * 
 * @param player 
 * @param direction
 */
void player_move_x(Player* player, Direction direction, int **tab){    
    if(player->move.dir == standby 
        || player->move.dir != direction){
        player->move.speed = 0.1 * V;
        player_calcul_move_x(player, direction, tab);
        player->move.dir = direction;
    }
    else{
        player_accelaration(player);
        player_calcul_move_x(player, direction, tab);
    }
}

/**
 * @brief deplacement en y
 * 
 * @param player 
 * @param direction
 */
void player_move_y(Player* player, Direction direction, int **tab){    
    if(player->move.dir == standby 
        || player->move.dir != direction){
        player->move.speed = 0.1 * V;
        player_calcul_move_y(player, direction, tab);
        player->move.dir = direction;
    }
    else{
        player_accelaration(player);
        player_calcul_move_y(player, direction, tab);
    }
}

/**
 * @brief La fonction gere les collect de mana du joueur de vidé celle de la carte
 * 
 * @param player 
 * @param map 
 */
void collect_mana(Player* player, Map* map){
    if(map->mana_map[(int) player->pos.x][(int) player->pos.y] != 0){
        player->mana += map->mana_map[(int) player->pos.x][(int) player->pos.y];
        map->mana_map[(int) player->pos.x][(int) player->pos.y] = 0;
    }
}

