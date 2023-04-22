#include "../include/Map.h"

/**
 * @brief remplie la map de mana de mana sans le mettre dans les murs
 * 
 * @param wall_map 
 *        la carte des murs
 * @param mana_map 
 *          la carte de mana a remplir
 * @param x 
 *        taille max du tableau
 * @param y 
 *      taille max du tableau de tableau
 */
void fill_mana_map(int** wall_map,int** mana_map,int x, int y){
    int i ,j;
    for(i = 0; i < x; i++){
        for(j = 0; j < y; j++){
            if(wall_map[i][j] == 0){
                mana_map[i][j] = 1;
            }
        }
    }

}

/**
 * @brief Initialise la map
 * 
 * @param x 
 *        taille max du tableau
 * @param y 
 *      taille max du tableau de tableau
 * @param minside 
 *          taille minimal de la longueur d'un mur
 * @return Map 
 */
Map init_map(int x, int y, int minside){
    Map map;
    map.wall_map = (int **)malloc(sizeof(int*) * x);
    map.mana_map = (int **)malloc(sizeof(int*) * x);
    malloc_tabx2(map.wall_map, x, y);
    malloc_tabx2(map.mana_map, x, y);

    creer_tab(map.wall_map, x, y);
    create_border(map.wall_map, x, y);
        
    generation(minside, x-2, y-2, map.wall_map, 1, 1);
    fill_mana_map(map.wall_map, map.mana_map, x, y);
    return map;
}

/**
 * @brief Déalloue la map
 * 
 * @param map 
 * @param x 
 */
void freeMap(Map* map, int x){
    int i;
    for(i = 0; i < x; i++){
        free(map->wall_map[i]);
        free(map->mana_map[i]);
    }
    free(map->wall_map);
    free(map->mana_map);
}

