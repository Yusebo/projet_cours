#ifndef __PLAYER_H__
#define __PLAYER_H__
#include <stdlib.h>
#include "Map.h"

#define V 0.25

typedef enum {
    standby,
    up,
    down,
    right,
    left
}Direction;

typedef struct{
    double speed;
    double min_speed;
    double max_speed;
    Direction dir;
}Move;

typedef struct{
    double x;
    double y;
}Point;

typedef struct {
    Point pos;
    Move move;
    int mana;
    int isInvinsible;
}Player;


Player init_player();
void player_move_x(Player* player, Direction direction, int **tab);
void player_move_y(Player* player, Direction direction, int **tab);
int wall_hitbox_down(int ** tab, double x, double y);
int wall_hitbox_up(int ** tab, double x, double y);
int wall_hitbox_right(int ** tab, double x, double y);
int wall_hitbox_left(int ** tab, double x, double y);
void collect_mana(Player* player, Map* map);
void Overloaded_Acceleration(Player* player);
void invisibility(Player* player);
void player_accelaration(Player* player);


#endif