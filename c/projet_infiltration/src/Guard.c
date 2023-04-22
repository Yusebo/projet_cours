#include "../include/Guard.h"
#include <stdio.h>

double distance(double x1, double y1, double x2, double y2){
    return sqrt(((x2 - x1) * (x2 -x1)) + ((y2 - y1) * (y2 - y1)));
}

/**
 * @brief place le garde de qu'il soit éloigné du joueur et pas dans un mur
 * 
 * @param x 
 * @param y 
 * @param tab 
 */
static void guard_not_in_wall(double* x, double* y, int** tab){
    int tmp_x, tmp_y;
    tmp_x = (rand() % 58) + 1;
    tmp_y = (rand() % 43) + 1;
    while(tab[tmp_x][tmp_y] == 1 || distance(2, 2, tmp_x, tmp_y)  < 20){ /* 2e condition pour evité de tuer les joueurs a l'appartion*/
        tmp_x = (rand() % 58) + 1;
        tmp_y = (rand() % 43) + 1;
    }
    *x = tmp_x + 0.5;
    *y = tmp_y + 0.5;
}


/**
 * @brief Change la direction et la vitesse du garde 
 * 
 * @param guard 
 * @param direction 
 */
void change_of_direction(Guard* guard, Direction direction){
    Direction new_direction;
    int diff_min_max, min_speed;
    min_speed = guard->move.min_speed / V * 10;
    diff_min_max = ((guard->move.max_speed - guard->move.min_speed) / V) * 10 ;
    while (1){
        new_direction = rand() % 4 + 1 ;
        if(new_direction != direction){
            guard->move.dir = new_direction;
            if(diff_min_max == 0){
                guard->move.speed = min_speed / 10.0 * V;
            }
            else{
                guard->move.speed = ((double)(rand() % diff_min_max) + min_speed) / 10.0 * V;
            }
            
            return;
        }
    }  
    
}

/**
 * @brief appel change_of_direction de façon aléatoire pour changer la direction du guard
 * 
 * @param guard 
 * @param direction 
 */
void random_change_of_direction(Guard* guard, Direction direction){
    if(rand() % 300 == 0){
        change_of_direction(guard, direction);
    }
}


/**
 * @brief Calcul les mouvement sur la vertical
 * 
 * @param guard 
 * @param direction 
 */
static void guard_calcul_move_x(Guard* guard, Direction direction, int **tab){
    double future_position;
    if(direction == right){
        future_position = guard->pos.x + guard->move.speed;
        if(wall_hitbox_right(tab, future_position , guard->pos.y) && 
        tab[(int)(guard->pos.x + 0.5 + guard->panic_mode)][(int)(guard->pos.y)] != 1){
            guard->pos.x += guard->move.speed;
        }
        else{
            change_of_direction(guard, guard->move.dir);
        }
    }else{
        future_position = guard->pos.x - guard->move.speed;
        if(wall_hitbox_left(tab, future_position , guard->pos.y) && 
        tab[(int)(guard->pos.x - 0.5 -  guard->panic_mode)][(int)(guard->pos.y)] != 1){
            guard->pos.x -= guard->move.speed;
        }else{
            change_of_direction(guard, guard->move.dir);
        }
    }      
}

/**
 * @brief Calcul les mouvement sur l'horizontal
 * 
 * @param guard 
 * @param direction 
 */
static void guard_calcul_move_y(Guard* guard, Direction direction, int **tab){
    double future_position;
    if(direction == down){
        future_position = guard->pos.y + guard->move.speed;
        if(wall_hitbox_down(tab, guard->pos.x , future_position) && 
        tab[(int)(guard->pos.x)][(int)(guard->pos.y + 0.5 + guard->panic_mode)] != 1){
            guard->pos.y += guard->move.speed;
        }
        else{
            change_of_direction(guard, guard->move.dir);
        }
    }else{
        future_position = guard->pos.y - guard->move.speed ;
        if(wall_hitbox_up(tab, guard->pos.x , future_position) && 
        tab[(int)(guard->pos.x)][(int)(guard->pos.y - 0.5 - guard->panic_mode)] != 1){
            guard->pos.y -= guard->move.speed;
        }
        else{
            change_of_direction(guard, guard->move.dir);
        }
    }
}

/**
 * @brief deplacement du garde
 * 
 * @param guard 
 * @param direction
 */
void guard_move(Guard* guard, int **tab){
    switch (guard->move.dir){
        case up:
            guard_calcul_move_y(guard, guard->move.dir, tab);
            break;
        case down:
            guard_calcul_move_y(guard, guard->move.dir, tab);
            break;
        case left:
            guard_calcul_move_x(guard, guard->move.dir, tab);
            break;
        case right:
            guard_calcul_move_x(guard, guard->move.dir, tab);
            break;
        default:
            random_change_of_direction(guard, guard->move.dir);
            break;
    }
}

/**
 * @brief detection des guard en avec x = a
 * cherhce les point ya a partir de a
 * 
 * @param guard 
 * @param player 
 * @param x 
 * @param a 
 * @param pa 
 * @param ya 
 * @return double 
 */
double detection_a(Point guard, Point player, double x, double* a, double* pa, double* ya){
    if(player.x < guard.x){
       *a = x - 0.5; 
    }
    else{
       *a = x + 0.5; 
    }
    *pa = (*a - guard.x) / (player.x - guard.x); 
    if(*pa <= 1 && *pa >= 0){
        *ya = *pa * player.y + (1 - *pa) * guard.y;
        return distance(*a, *ya, guard.x, guard.y);
    }
    return -1;
}

/**
 * @brief detection des guard en avec y = b
 *  cherhce les point xb a partir de b
 * 
 * @param guard 
 * @param player 
 * @param y 
 * @param b 
 * @param pb 
 * @param xb 
 * @return double 
 */
double detection_b(Point guard, Point player, double y, double* b, double* pb, double* xb){
    if(player.y < guard.y){
       *b = y - 0.5; 
    }
    else{
       *b = y + 0.5; 
    }
    *pb = (*b - guard.y) / (player.y - guard.y); 
    if(*pb <= 1 && *pb >= 0){
        *xb = *pb * player.x + (1 - *pb) * guard.x;
        return distance(*xb, *b, guard.x, guard.y);
    }
    return -1;
}

/**
 * @brief 
 * 
 * @param guard 
 * @param player 
 * @param tab 
 * @return int 
 */
static int detection(Guard guard, Player player, int** tab){
    double x, y;
    double distance_a, distance_b;
    double pa, a, ya;
    double pb, b, xb;

    distance_a = 0;
    distance_b = 0;
    x = floor(guard.pos.x) + 0.5;
    y = floor(guard.pos.y) + 0.5; 

    while(distance_a != -1 || distance_b != -1){
        distance_a = detection_a(guard.pos, player.pos, x, &a, &pa, &ya);
        if(distance_a != -1 && tab[(int)a][(int)ya] == 1){;
            return 0;
        }

        distance_b = detection_b(guard.pos, player.pos, y, &b, &pb, &xb);
        if(distance_b != -1 && tab[(int)xb][(int)b] == 1){
            return 0;
        }

        /*increase if player is to the right and decrease if is it to the left*/
        if(player.pos.x > guard.pos.x){
            x++; 
        }
        else{
            x--;
        }
         /*increase if player is to the down and decrease if is it to the up*/
        if(player.pos.y > guard.pos.y){
            y++; 
        }
        else{
            y--;
        }  
    }
    return 1;
}

/**
 * @brief la porte pour activé la fonction de detection
 * 
 * @param guard 
 * @param player 
 * @param tab 
 * @return int 
 */
int detection_range(Guard guard, Player player, int** tab){
    if(distance(player.pos.x, player.pos.y, guard.pos.x, guard.pos.y) <= guard.range && player.isInvinsible == 0){
        return detection(guard, player, tab);
    }
    return 0;
}

/**
 * @brief caracteristique du mode normal des garde
 * 
 * @param guard 
 */
void normal_mode(Guard* guard){
    guard->move.min_speed = 0.3 * V;
    guard->move.max_speed = 0.8 * V;
    guard->range = 4;
    guard->panic_mode = 0;
    guard->panic_timer = 0;
}

/**
 * @brief caracteristique du mode panic des garde
 * 
 * @param guard 
 */
void panic_mode(Guard* guard){
    guard->move.min_speed = V;
    guard->move.max_speed = V;
    guard->range = 6;
    guard->panic_mode = 1;
    guard->panic_timer = 60 * 30; // 60 frame = 1 seconde * 30 pour faire 30 second
}

void guard_panic_timer(Guard *guard){
    if(guard->panic_mode == 1 && guard->panic_timer ==0){
        normal_mode(guard);
    }
    else if(guard->panic_mode == 1){
        guard->panic_timer --;
    }
}


/**
 * @brief Initailise un garde
 * 
 * @param tab 
 * @return Guard 
 */
Guard init_guard(int ** tab){
    Guard guard;  
    guard_not_in_wall(&(guard.pos.x), &(guard.pos.y) , tab);
    guard.move.speed = 0;
    normal_mode(&guard);
    change_of_direction(&guard, standby);
    return guard;
}




