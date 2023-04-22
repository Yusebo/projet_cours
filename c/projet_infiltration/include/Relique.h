#ifndef _RELIQUE_H_
#define _RELIQUE_H_
#include "Player.h"
#include "Guard.h"



typedef struct {
  Point pos;
  int isAlwaysFindbyGuard;
  int isFind; //Cette valeur est égale à 0 si la relique est pas trouvée sinon elle est égale à 1
} Relique;

Relique init_relique(int **tab);
int find_relique(Relique *r, Player p);
int interaction_guard_relique(Relique *r, Guard *guard, int **tab, Guard *all_guard, int nb_guard);
int player_exit(Player player, int **wallmap, Relique* r, int nb_relique);

#endif