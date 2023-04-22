package Mechanism;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import Gamedata.Patch;
import Gamedata.Player;
import Gamedata.Player_automa;
import Graphics.GraphicsMode;


/**The class manage game function
 * */
public class Game{
  /** The function manage player move and consequence 
   * @param player
   *        A array with all player
   * @param player_playing
   *        which player plays
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   * @param mode
   *        inforamtion of game
   * */
  public static void moving_action(Player_automa[] player, int player_playing,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list, GraphicsMode mode) {
    objectnull(player, leather_list, button_list);
    Objects.requireNonNull(mode);
    int move ;
    int box_dont_count = 0;
    if(player_playing == 1) {
      move = player[0].position() - player[player_playing].position();
    }
    else{
      move = player[1].position() - player[player_playing].position();
    }
    if(mode.isgraphics() && player[player_playing].position() > 60){
      box_dont_count = player[player_playing].position() - 61;
    }
    player[player_playing].earn(move + 1 - box_dont_count);
    Maps.move_with_leather_button(player[player_playing], move + 1, leather_list, button_list, mode);
  }

  /** The function test if a object is null
   * @param player  
   *        a Player Array which all player
   * @param player_playing
   *        which player plays
   * @param list 
   *        HashMap with all patch
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map   
   * */
  private static void objectnull(Player_automa[] player,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list) {
    Objects.requireNonNull(player);
    Objects.requireNonNull(leather_list);
    Objects.requireNonNull(button_list);
  }

  /**The function watch who are in the last position and return
   * @param player
   *        A array with all player
   * @param player_playing
   *        Actual player plays
   * @return The next player who play
   * */
  public static int next_player(Player[] player , int player_playing) {
    Objects.requireNonNull(player);

    if(player[0].position() > player[1].position()) {
      player_playing = 1;
    }
    else if(player[1].position() > player[0].position()){
      player_playing = 0;
    }
    return player_playing;
  }


  /**which player have winning the game
   * @param player
   *        A array with all player
   * @return return the results 1 if player1 winning ,
   * 2 if player2 winning and 0 if it is equality
   * */
  public static int who_win(Player[] player) {
    Objects.requireNonNull(player);
    if(player != null) {
      int player1 = player[0].button() - player[0].quiltboard().patch_not_placed() * 2
          + player[0].special_tiles() * 7;
      int player2 = player[1].button() - player[1].quiltboard().patch_not_placed() * 2
          + player[0].special_tiles() * 7;   
      if(player1 > player2) {
        return 1;
      }
      else if(player1 < player2) {
        return 2;
      }
    }
    return 0;
  }


  /**the function read you choice from 1 to 3 and 0 for exit 
   * 
   * @param list_size
   *        size of list of Patch
   * @param index
   *        key for the starting point
   * @param choice_p
   *        which patch player have choice  
   * @return Return you action - 1
   * */
  public static int patch_choice(int list_size, int index, int choice_p) {
    if(choice_p == -1) {
      return -1;
    }
    else if(index + choice_p >= list_size) {
      index = (index + choice_p) - list_size;
    }
    else {
      index = index + choice_p;
    }
    return index;
  }

  /**the function research minimal patch 
   * @param list 
   *        HashMap with all patch
   * @return the neutral token
   * */
  public static int minimal_patch(HashMap<Integer,Patch> list) {
    Objects.requireNonNull(list);
    int min = 50;
    int key_min = 0;
    int size ;
    for(var key : list.keySet()) {
      size = list.get(key).size();
      if(size < min) {
        key_min = key ; 
        min = size;
      }
    }
    if(key_min == list.size()) {
      return 0;
    }
    return key_min + 1;
  }

  /**The function manage who earn special tile
   * @param player
   *        who is playing
   * @param board7x7
   *        if the special_tiles is always use
   * @return if special tiles is use
   * */
  public static int board7x7_complete_or_not(Player player, int board7x7) {
    Objects.requireNonNull(player);
    if(player.quiltboard().test_board7x7() && board7x7 == 1) {//if square 7x7 is full
      board7x7 = 0;
      player.add_special_tiles();
    }
    return board7x7;
  }

  /**This function it use to choice a terminal or graphics mode
   * @param read
   *        A scanner for read console 
   * @return true if you want play on graphics mode else false
   * */
  public static boolean choice_mode(Scanner read) {
    Objects.requireNonNull(read);
    while(true) {
      System.out.println("Which mode you want Graphics or ASCII:(g/a)");
      String action = read.next();
      if(action.equals("g")){
        return false;
      }else if(action.equals("a")) { // patch choice
        return true;
      }
    }
  }
}



