#ifndef __LABYRINTHE_H__
#define __LABYRINTHE_H__

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/**
 * @brief fonction permettant de généré la map
 * 
 * @param minside 
 * @param taille_x 
 * @param taille_y 
 * @param tab 
 * @param x 
 * @param y 
 */
void generation(int minside, int taille_x, int taille_y, int ** tab , int x, int y);

void creer_tab(int **tab,int x, int y);

void malloc_tabx2(int **tab, int x ,int y);

void create_border(int **tab, int x, int y);

#endif