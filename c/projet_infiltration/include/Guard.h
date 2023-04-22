#ifndef __GUARD_H__
#define __GUARD_H__

#include "Player.h"
#include "math.h"

typedef struct {
    Point pos;
    double range;
    int panic_mode;
    int panic_timer;
    Move move;
}Guard;

Guard init_guard(int ** tab);
int detection_range(Guard guard, Player player, int** tab);
void random_change_of_direction(Guard* guard, Direction direction);
void guard_move(Guard* guard, int **tab);
double distance(double x1, double y1, double x2, double y2);
double detection_a(Point guard, Point player, double x, double* a, double* pa, double* ya);
double detection_b(Point guard, Point player, double y, double* b, double* pb, double* xb);
void panic_mode(Guard* guard);
void guard_panic_timer(Guard *guard);

#endif