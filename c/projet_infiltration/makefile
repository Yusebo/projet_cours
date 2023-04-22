CC = gcc
CFLAGS =  -Wall -lMLV
LDFLAGS = -lm
EXEC = jeu
SRC = ./src/

all : install

$(EXEC) : 
	make -C src

%.o : %.c
	$(CC) -c $< $(CFLAGS)

clean :
	rm -f ./obj/*.o

install: $(EXEC) object
	mv $(SRC)$(EXEC) ./bin/$(EXEC)

uninstall: clean
	rm -f ./bin/$(EXEC)


object:
	mv $(SRC)*.o ./obj/	