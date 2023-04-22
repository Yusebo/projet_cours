package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import Gamedata.Patch;
import Gamedata.Player;
import Graphics.ASCII;
import Graphics.Graphics;
import Mechanism.Game;

/**Main
 * */
public class Main{
  /**Main
   * @param args
   *        argument when function is read
   * */
  public static void main(String[] args) {
    var player = new Player[2];
    var leather_list = new ArrayList<Integer>(); 
    var button_list = new ArrayList<Integer>();
    var list = new HashMap<Integer, Patch>();
    Scanner read = new Scanner(System.in);
    int player_playing = 0;
    if(Game.choice_mode(read)) {
      Player.init_player(player); 
      ASCII.game(player, player_playing, list, leather_list, button_list, read);
    }
    else {
      read.close();
      Player.init_player_graphics(player);
      Graphics.game(player, list, player_playing, leather_list, button_list);
    }
  }
}
