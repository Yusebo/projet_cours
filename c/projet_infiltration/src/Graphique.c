#include "../include/Graphique.h"

/**
 * @brief Ouvre la fenetre mlv
 * 
 */
void creer_fenetre(){
    MLV_create_window("fenetre",NULL, 1280 , 720);
}

/**
 * @brief Dessine la carte
 * 
 * @param tab 
 * @param x 
 * @param y 
 */
void dessine_carte(Map map, int x, int y, int taille_case){
    int i,j;
    for(i = 0; i< x ; i++){
        for(j = 0; j < y ; j++){           
            if(map.wall_map[i][j] == 1){ /*mur*/
                MLV_draw_filled_rectangle(10 + i * taille_case, 20 + j * taille_case, 
                taille_case, taille_case, MLV_COLOR_BLACK);
            }
            else if(map.wall_map[i][j] == 2){ /*sortie*/
                MLV_draw_filled_rectangle(10 + i * taille_case, 20 + j * taille_case, 
                taille_case, taille_case, MLV_COLOR_GREEN);
            }
            else{ /*case normal*/
                MLV_draw_filled_rectangle(10 + i * taille_case, 20 + j * taille_case, 
                taille_case, taille_case, MLV_COLOR_GREY);
                MLV_draw_rectangle(10 + i * taille_case, 20 + j * taille_case,
                 taille_case, taille_case, MLV_COLOR_BLACK);
            }
            if(map.mana_map[i][j] > 0){ /*si y'a de la mana*/
                MLV_draw_rectangle(10 + i * taille_case + 2, 20 + j * taille_case + 2,
                 taille_case - 4, taille_case - 4, MLV_COLOR_CYAN);
            }
        }
    }

    MLV_draw_rectangle(10 , 20 , 
                x * taille_case, y * taille_case, MLV_COLOR_GREY);
}

/**
 * @brief Ferme la denetre mlv
 * 
 */
void fermer_fenetre(){
    MLV_wait_mouse(NULL, NULL);
    MLV_wait_seconds(1);
    MLV_free_window(); 
}

/**
 * @brief Permet de
 * 
 * @param player 
 */
static void gestion_mana(Player* player){
    if (player->isInvinsible == 1)
        player->mana -= 1;

    else if (player->move.max_speed == 1.2 * V)
        player->mana -= 1;

    else
        return;
}


/**
 * @brief Lis la touche choisis et regarde si la touche et relié a une fonctionalité
 * 
 * @param player 
 * @param button 
 * @return int 
 */
static int button_player_move(Player* player, MLV_Keyboard_button button, int **tab){
    switch (button){
        case MLV_KEYBOARD_z:
            player_move_y(player, up, tab);
            break;
        case MLV_KEYBOARD_s:
            player_move_y(player, down, tab);
            break;
        case MLV_KEYBOARD_q:
            player_move_x(player, left, tab);
            break;
        case MLV_KEYBOARD_d:
            player_move_x(player, right, tab);
            break;
        case MLV_KEYBOARD_LSHIFT:
            Overloaded_Acceleration(player);
            break;
        case MLV_KEYBOARD_SPACE:
            invisibility(player);
            break;
        case MLV_KEYBOARD_a:
            return 0;
            break;
        default:
            break;
    }
    return 1;
}




/**
 * @brief La fonction gere les mouvements du joueur
 * 
 * @param player 
 * @param out 
 *      variable servant a arreter le jeu si besoin
 */
void move(Player* player, int * out, MLV_Keyboard_button *button, MLV_Button_state *button_state, Map* map){
    int i;
    MLV_Keyboard_button  button_press;
    MLV_Button_state prec_state;
    button_press = button[0];
    prec_state = button_state[0];
    
    MLV_get_event(&button[0], NULL, NULL, NULL, NULL,
    NULL, NULL, NULL, &button_state[0]);
    if(button_state[0] == MLV_RELEASED){
        if(button[0] == button[1]){
            button[0] = button_press;
            button_state[0] = prec_state ;
            button_state[1] = MLV_RELEASED ;
        }
    }
    if(button[0] != button_press){
        button[1] = button_press;
        button_state[1] = prec_state ;
    }
    if(button[1] == MLV_KEYBOARD_LSHIFT){
        if(button_state[1] == MLV_PRESSED && button_state[0] == MLV_PRESSED ){
            if(!button_player_move(player, button[1], map->wall_map) || !button_player_move(player, button[0], map->wall_map)){
                *out = 0;       
            } 
        }
    }
    else{
        for(i = 0; i < 2; i++){
            if(button_state[i] == MLV_PRESSED){
                if(!button_player_move(player, button[i], map->wall_map)){
                    *out = 0;
                }
            }
        }
    }
    collect_mana(player, map);
    gestion_mana(player);
}

/**
 * @brief dessine la tableau des score
 * 
 * @param player 
 *          information du tableau des score
 * @param taille_case 
 *          taille de la case
 * @param width 
 *          taille de la fenetre en longueur
 * @param height 
 *          taille de la fenetre en largeur
 */
void dessiner_score(Score player[], int taille_case, int width, int height){
    int i;
    MLV_draw_text (taille_case * 62 + 133, 90, "All of fame",  MLV_COLOR_YELLOW);
    MLV_draw_rectangle(taille_case * 62 + 20 , 115,  (width - (taille_case * 62.5 + 40)) , taille_case * 12, MLV_COLOR_YELLOW);
    for(i = 0; i < 10; i++){
        if(player[i].notplayer == 0){
            MLV_draw_text (taille_case * 62 + 40, 115 + taille_case * (i +1), "%s",  MLV_COLOR_YELLOW, player[i].name);
            MLV_draw_text (taille_case * 62 + (width - (taille_case * 62.5 + 40)) - 60, 115 + taille_case * (i +1), "%ld.%lds",  MLV_COLOR_YELLOW,
            player[i].time.tv_sec, player[i].time.tv_nsec / 1000000);
        }
    }
}

/**
 * @brief barre graphique servant a recuperé le nom que je joueur vaux
 * 
 * @param player 
 * @param width 
 * @param height 
 */
void recupere_nom_du_joueur(Score *player, int width, int height){
    char *nom[100];
    int len;
    len = 5;
    while(len> 3){
    MLV_wait_input_box 	(width/2 - 150, height/2 - 20,
		300,  40,
		MLV_COLOR_WHITE, MLV_COLOR_YELLOW, MLV_COLOR_BLACK,
		"Nom du Joueur(3 lettre max):",
		nom);
        len = strlen(*nom);
    }
    strcpy((*player).name, *nom);
}

/**
 * @brief Affiche dans la fenetre le joueur
 * 
 * @param player 
 */
void dessine_player(Player player, int taille_case, int width, int height){
    MLV_draw_filled_circle(0, 0,
     taille_case / 2, MLV_COLOR_BLUE);
    MLV_draw_filled_circle(10 + player.pos.x * taille_case, 20 + player.pos.y * taille_case,
     taille_case / 2, MLV_COLOR_RED);
    MLV_draw_rectangle(taille_case * 62, 0,  width - taille_case * 62.5, height, MLV_COLOR_WHITE);
    MLV_draw_text_box(taille_case * 62 + 100, 25, 2 * height / 10 , 50 , "mana : %d", 4,
    MLV_COLOR_WHITE, MLV_COLOR_YELLOW, MLV_COLOR_BLACK ,
    MLV_TEXT_CENTER, MLV_HORIZONTAL_CENTER, MLV_VERTICAL_CENTER,  player.mana
    );
}

/**
 * @brief Affiche dans la fenetre le garde et ça porté de detection
 * 
 * @param guard 
 * @param taille_case 
 */
void dessine_guard(Guard guard, int taille_case){
    MLV_Color guard_color, range_color;
    guard_color = range_color = MLV_COLOR_BLUE;
    if(guard.panic_mode == 1){
        guard_color = MLV_COLOR_ORANGE;
        range_color =  MLV_COLOR_RED;
    }
    MLV_draw_filled_circle(10 + guard.pos.x * taille_case, 20 + guard.pos.y * taille_case,
        taille_case / 2, guard_color);
    MLV_draw_circle(10 + guard.pos.x * taille_case, 20 + guard.pos.y * taille_case,
        taille_case * guard.range, range_color);  
    
}

/**
 * @brief dessine une relique
 * 
 * @param r 
 * @param taille_case 
 */
void dessine_reilque(Relique r, int taille_case){
    int vx[] = {10 + (r.pos.x + 0.5) * taille_case, 10 + (r.pos.x + 0.75) * taille_case,
    10 + (r.pos.x + 0.5) * taille_case, 10 + (r.pos.x + 0.25) * taille_case};
    int vy[] = {20 + (r.pos.y + 0.25) * taille_case, 20 + (r.pos.y + 0.5) * taille_case,
    20 + (r.pos.y + 0.75) * taille_case, 20 + (r.pos.y + 0.5) * taille_case};
    if(r.isFind == 1){
        MLV_draw_filled_polygon(vx, vy, 4, MLV_COLOR_RED);  
    }
    else{
        MLV_draw_filled_polygon(vx, vy, 4, MLV_COLOR_YELLOW); 
    }
    
}