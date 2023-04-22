#include "../include/labyrinthe.h"

/**
 * @brief Alloue la 2e dimension d'un tableau
 * 
 * @param tab 
 * @param x 
 *        taille max du tableau
 * @param y 
 *      taille max du tableau de tableau
 */
void malloc_tabx2(int **tab, int x ,int y){
    int i;
    for(i = 0; i < x; i++){
        tab[i] = (int *)malloc(sizeof(int) * y);
    }
}

/**
 * @brief Initialise un tableau de x par y
 * 
 * @param tab 
 * @param x 
 *        taille max du tableau
 * @param y 
 *      taille max du tableau de tableau
 */
void creer_tab(int **tab,int x, int y){
    int i,j;
    for(i = 0; i< x ; i++){
        for(j = 0; j < y ; j++){
            tab[i][j] = 0;
        }
    }
}

/**
 * @brief place un mur dans les coordonnée donnée
 * 
 * @param tab 
 * @param taille longueur du mur sans porte
 * @param mur position du mure
 * @param inverse 
 * @param sense 
 * @param x 
 *      cordonnée de départ dans le tableau
 * @param y 
 *      cordonnée de départ dans le tableau de tableau
 */
static void placer_mur(int **tab, int taille, int mur, int inverse, char sense, int x , int y){
    int i, debut, fin;
    if(sense == 'h'){ /* permettre de place la porte haut ou en bas*/
        debut = 3;
        fin = 0;
    }
    else{
        debut = 0;
        fin = 3;
    }
    if(inverse == 0){
        for(i = debut;i < taille - fin; i++){
            tab[x + mur][y + i] = 1;
        }
    }
    else{
        for(i = debut;i <  taille - fin; i++){
            tab[x + i][y + mur] = 1;      
        }
    }
}


/**
 * @brief fonction permettant de généré la map
 * 
 * @param minside taille minimun de largeur des murs
 * @param taille_x taille en x
 * @param taille_y taille en y
 * @param tab 
 *         carte a remplir avec les murs
 * @param x 
 *      cordonnée de départ dans le tableau
 * @param y 
 *      cordonnée de départ dans le tableau de tableau
 */
void generation(int minside, int taille_x, int taille_y, int ** tab , int x, int y){
    int mur, tmp , inverse;
    inverse = 0;
    if(taille_y > taille_x){
        tmp = taille_x;
        taille_x = taille_y;
        taille_y = tmp;
        inverse =1;
    }
    if(taille_x < 2 * minside + 1){
        return;
    }
    else if(taille_x < 4 * minside && (rand() % 2) == 0 ){
        return;
    }
    else{
        mur = (rand() % ( taille_x - (minside*2) )) + minside;
        if((rand() % 2)){
            placer_mur(tab, taille_y, mur, inverse, 'h',x, y);
        }
        else{
            placer_mur(tab, taille_y, mur, inverse, 'b', x, y);
        }
        if(inverse == 0){
            generation(minside, mur, taille_y , tab, x, y);
            generation(minside, taille_x - (mur + 1), taille_y ,tab, x + mur + 1, y );
        }
        else{
            generation(minside, taille_y ,  mur , tab, x, y);
            generation(minside, taille_y , taille_x - (mur + 1),tab, x , y + mur + 1);
        }

    }
}

/**
 * @brief Fonction qui ajoute les bordures du labyrinthe
 * 
 * @param tab tableau double avec les murs
 * @param x taille en x
 * @param y taille en y
 */
void create_border(int **tab, int x, int y){
    int i ;
    for(i = 0; i < y; i++){
        tab[0][i] = 1;
        tab[x-1][i] = 1;
    }
    for(i = 1; i < x - 1; i++){
        tab[i][0] = 1;
        tab[i][y-1] = 1;
    }
    tab[1][1] = 2;
    tab[1][2] = 2;
    tab[2][1] = 2;
    tab[2][2] = 2;
}