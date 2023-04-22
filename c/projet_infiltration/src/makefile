CC = gcc
CFLAGS =  -Wall -lMLV
LDFLAGS = -lm
EXEC = jeu
HEADER = ../include/

$(EXEC) : Main.o labyrinthe.o Graphique.o Player.o Guard.o Map.o Score.o Relique.o
	$(CC) -o $@ $^ $(CFLAGS) $(LDFLAGS)

Main.o : Main.c $(HEADER)labyrinthe.h $(HEADER)Graphique.h $(HEADER)Player.h $(HEADER)Guard.h $(HEADER)Map.h $(HEADER)Score.h $(HEADER)Relique.h
Player.o : Player.c $(HEADER)Player.h
Guard.o : Guard.c $(HEADER)Guard.h $(HEADER)Player.h
Relique.o : Relique.c $(HEADER)Relique.h $(HEADER)Guard.h $(HEADER)Player.h

%.o : %.c
	$(CC) -c $< $(CFLAGS)

clean :
	rm -f *.o