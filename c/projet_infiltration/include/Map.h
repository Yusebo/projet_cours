#ifndef __MAP_H__
#define __MAP_H__

#include <stdlib.h>
#include "labyrinthe.h"


typedef struct{
    int** wall_map;
    int** mana_map;
}Map;


Map init_map(int x, int y, int minside);
void freeMap(Map* map, int x);

#endif