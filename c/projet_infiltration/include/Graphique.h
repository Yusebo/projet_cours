#ifndef __GRAPH_H__
#define __GRAPH_H__

#include <MLV/MLV_all.h>
#include "Player.h"
#include "Guard.h"
#include "Map.h"
#include "Relique.h"
#include "Score.h"

void creer_fenetre();
void dessine_carte(Map map, int x, int y, int taille_case);
void fermer_fenetre();
void move(Player *player, int * out , MLV_Keyboard_button *button ,MLV_Button_state *button_state, Map* map);
void dessine_player(Player player, int taille_case, int width, int height);
void dessine_guard(Guard guard, int taille_case);
void dessine_reilque(Relique r, int taille_case);
void dessiner_score(Score player[], int taille_case, int width, int height);
void recupere_nom_du_joueur(Score *player, int width, int height);

#endif