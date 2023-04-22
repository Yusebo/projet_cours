#include <time.h>
#include "../include/labyrinthe.h"
#include "../include/Graphique.h"
#include "../include/Player.h"
#include "../include/Guard.h"
#include "../include/Map.h"
#include "../include/Score.h"
#include "../include/Relique.h"

int main(){
    int minside, x, y, i, j, out, nb_guardian, taille_case, nb_relique;
    int width, height;
    double frametime, extratime;
    Score player_score, all_score[11];
    Player player;
    Guard guard[5];
    Map map;
    Relique r[3];
    struct timespec end_time, new_time ,start_game, end_game;
    MLV_Button_state button_state[2] ;
    MLV_Keyboard_button button[2];
    width = 1280;
    height = 720;
    x = 60;
    y = 45;
    taille_case = height / (y + 1);
    nb_guardian = 5;
    nb_relique = 3;
    minside = 9;
    out = 1;

    button_state[0] = MLV_RELEASED;
    button_state[1] = MLV_RELEASED;

    srand(time(NULL));
    player = init_player();
    map = init_map(x, y, minside);
    
    for(i = 0; i < nb_guardian; i++){
       guard[i] = init_guard(map.wall_map); 
    }
    for(i = 0; i < nb_relique; i++){
        r[i] = init_relique(map.wall_map);
    }
    readfile(all_score);
    creer_fenetre();
    recupere_nom_du_joueur(&player_score, width, height);
    clock_gettime(CLOCK_REALTIME, &start_game);
    while(out && player_exit(player, map.wall_map, r, nb_relique)){
       
        out = 1;
        clock_gettime(CLOCK_REALTIME, &end_time);
        /*action du joueur*/
        move(&player, &out, button, button_state, &map);
        dessine_carte(map, x, y, taille_case);
        dessine_player(player, taille_case, width, height);
        /*score*/
        dessiner_score(all_score, taille_case, width, height);
        /*action lie au relique*/
        for(i = 0; i < nb_relique; i++){
            find_relique(&r[i], player);
            dessine_reilque(r[i], taille_case);
        }
        /*action lié au garde*/
        for(i = 0; i < nb_guardian; i++){
            guard_move(&guard[i], map.wall_map);
            dessine_guard(guard[i], taille_case);
            random_change_of_direction(&guard[i], guard[i].move.dir);
            if(detection_range(guard[i], player, map.wall_map)){
                out = 0;
            }
            for(j = 0; j < nb_relique; j++){
                interaction_guard_relique(&r[j], &guard[i], map.wall_map, guard, nb_guardian);
            }
            guard_panic_timer(&guard[i]);

        }
            
        MLV_actualise_window();
        clock_gettime(CLOCK_REALTIME, &new_time) ;

        frametime = new_time.tv_sec - end_time.tv_sec ;
        frametime += ( new_time.tv_sec - end_time.tv_sec ) / 1.0E9 ;

        extratime = 1.0 / 60 - frametime ;
        if(extratime > 0 ) {
            MLV_wait_milliseconds ( (int) (extratime * 1000)) ;
        }
        MLV_draw_filled_rectangle(0, 0, 1200 , 1000, MLV_COLOR_BLACK);
        
        player.isInvinsible = 0;/* reintialisé les pouvoir*/
        player.move.max_speed = 0.9 * V;
    }
    clock_gettime(CLOCK_REALTIME, &end_game);
    /*partie rajoue des scores*/
    if(out){
        player_score.notplayer =  0;
        player_score.time.tv_sec = end_game.tv_sec - start_game.tv_sec;
        if(end_game.tv_nsec - start_game.tv_nsec < 0){
            player_score.time.tv_nsec = 1000000000 + end_game.tv_nsec - start_game.tv_nsec;
            player_score.time.tv_sec -= 1;
        }else{
            player_score.time.tv_nsec = end_game.tv_nsec - start_game.tv_nsec;
        }
        all_score[10] = player_score;
        writefile(all_score, 10);
    }
    fermer_fenetre();
    freeMap(&map, x);
    return 0;
}