package Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import Gamedata.Patch;
import Gamedata.Player;
import Gamedata.Player_automa;
import Mechanism.Game;
import Mechanism.Maps;

/**The class manage game function on the terminal
 * */
public non-sealed class ASCII implements GraphicsMode{
  private Player[] player;
  private final HashMap<Integer,Patch> list;
  private final ArrayList<Integer> leather_list;
  private final ArrayList<Integer> button_list;
  private int player_playing;
  private int index;

  /**Tis object contains all information of game on ASCII
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
  public ASCII(Player[] player, HashMap<Integer,Patch> list, int player_playing,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list) {
    ASCII.objectnull(player, list, leather_list, button_list);
    this.player = player;
    this.list = list;
    this.leather_list = leather_list;
    this.button_list = button_list;
    this.player_playing = player_playing;
    this.index = Game.minimal_patch(list);
  }


  /**Print all information when player turn start
   * */
  private void begin() {
    System.out.println(Maps.place_pion(player[0].position(),player[1].position(), leather_list , button_list));//print map
    System.out.println(ASCII.str_three_patch(list, index, player[player_playing]));
    System.out.println("Player" + player[player_playing].player() + " what are you doing:");
    System.out.println("m(move forward), p(choice a patch), w(watch all patch)");
  }

  /**the function read you choice from 1 to 3 and 0 for exit on ASCII
   * 
   * @param list_size
   *        size of list of Patch
   * @param index
   *             key for the starting point
   * @param read
   *        A scanner for read console
   * @return Return you action - 1
   * */
  private static int read_patch(int list_size, int index, Scanner read) {
    int choix_p = read.nextInt() - 1;
    return Game.patch_choice(list_size, index, choix_p);
  }

  /**Write a 3 patch which you can buy and player button
   * @param list
   *             HashMap{@literal <}Integer,Patch> with the element we want print
   * @param index
   *             key for the starting point
   * @param player
   *              Player
   * @return Return a String with 3 patch after index and player button
   * */
  private static String str_three_patch(HashMap<Integer,Patch> list, int index,Player player) {
    var memo = new StringBuilder();
    var tmp_index = index;
    if(list != null) {
      for(int j = 0; j < 3; j++) {
        if(tmp_index >= list.size()) {
          tmp_index = 0;
        }
        memo.append(list.get(tmp_index).toString());
        tmp_index++; 
      }
      memo.append("Vous avez " + player.button()+ " bouton\n");
    }
    return memo.toString();
  }

  private static int patch_place_positon(Player player, HashMap<Integer,Patch> list,
      int index, Scanner read) {
    int x = read.nextInt();
    int y = read.nextInt();
    if(!player.quiltboard().place(list.get(index), x, y)) {
      System.out.println("error piece doen't be able place here");
      return 0;
    }
    return 1;
  }

  /**The function read and manage where you place your patch
   * @param player
   *        A array with all player
   * @param list 
   *        HashMap with all patch
   * @param index
   *        the position of neutral token
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   * @param read
   *      A scanner for read console
   * @return the quiltboard after modification 
   * */
  private String place_patch(Player player, Scanner read) {
    int successful_place_patch = 0;
    System.out.println("joueur " + player.player() + " give the poistion where placer:(x, y)");
    while(successful_place_patch == 0) {
      successful_place_patch = ASCII.patch_place_positon(player, list, index, read);
    }
    Maps.move_with_leather_button(player, list.get(index).move(), leather_list, button_list, this);
    Patch.list_patch_remove(list, index);
    return player.quiltboard().toString();
  }

  /**the function read with which patch list you want play
   * @param list 
   *        HashMap with all patch
   * @param read
   *        A scanner for read console
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   * */
  private static void step_1_2(HashMap<Integer,Patch> list, ArrayList<Integer> leather_list,
      ArrayList<Integer> button_list, Scanner read) {
    boolean step = false;
    String action;
    while(step == false) { // which patch list
      action = read.next();
      if(action.equals("1")) {
        list = Patch.list_patch_step1(list);
        step = true;
      }else if(action.equals("2")){
        Patch.list_patch(list);
        Maps.setLeather(leather_list);
        Maps.setButton(button_list);
        step = true;
      }
    }
  }

  /**The function print the winner
   * @param player  
   *        a Player Array which all player
   * */
  private static void winner_is(Player[] player) {
    int winner = Game.who_win(player);
    if(winner == 0) {
      System.out.println("Equality");
    }
    else {
      System.out.println("Player" + winner + " win");
    }
  }

  /**the function read console for purchase patch and control the possibilities
   * @param player
   *        player who play
   * @param list 
   *        HashMap with all patch
   * @param index
   *        the position of neutral token
   * @param read
   *        A scanner for read console
   * @return -1 when player what go out without buy a patch 
   * or choice is position of player choice
   * */
  private static int purchare_action(Player player, HashMap<Integer,Patch> list, int index, Scanner read) {
    int successful_purchase = 0;
    int choice = 0;
    while(successful_purchase == 0) {
      choice = ASCII.read_patch(list.size(), index, read);
      if(choice < 0) {
        successful_purchase = 1;
      }
      else {
        if(list.get(choice).button() <= player.button()){
          successful_purchase = 1;
          player.buy(list.get(choice).button());
        }
        else {
          System.out.println("You haven't enought button");
        }
      }
    }
    return choice;
  }

  /**Case of all tranformation of patch
   * @param patch 
   *        the Patch use
   * @param option
   *        which option you use
   * */
  private static Patch switch_rotate_choice(Patch patch ,String option) {
    switch(option) {
    case "l" :
      patch = patch.left_rotation();
      System.out.println( patch.patchToString());
      break;
    case "r" :
      patch = patch.right_rotation();
      System.out.println( patch.patchToString());
      break;
    case "m":
      patch.mirror();
      System.out.println( patch.patchToString());
      break;
    };
    return patch;
  }

  /**the function read console And  patch are modifying in connection of request
   * 
   * @param patch 
   *          Patch which player modifying rotation or reverse
   * @param read
   *        the scanner for read console
   * @return Patch with modification
   * */
  private static Patch rotate_choice(Patch patch,Scanner read) {
    Objects.requireNonNull(patch);
    System.out.println("r/l/m/q");
    System.out.println("(right/left/mirror/quit)");
    while(true) {
      var option = read.next();
      patch = ASCII.switch_rotate_choice(patch, option);
      if(option.equals("q")) {
        return patch;
      } 
    } 
  }

  /**The function read if player want modifying patch or not
   * @param read
   *      A scanner for read console
   * @return true want player want or false
   * */
  private static boolean rotate_mirror_or_nothing(Scanner read) {
    while(true) {
      var option = read.next();
      if(option.equals("y")) {;
      return true;
      }
      else if(option.equals("n")){
        return false;
      }
    }
  }

  /**The function print and read action for place and rotate patch
   * @param read
   *        A scanner for read console
   * */
  private void patch_place_rotate(Scanner read) {
    System.out.println("do you want turn or reverse the patch?(y/n)");
    if(ASCII.rotate_mirror_or_nothing(read)) {
      list.put(index, ASCII.rotate_choice(list.get(index), read));
    }
    // place patch
    System.out.println(player[player_playing].quiltboard().toString());
    System.out.println(list.get(index));
    System.out.println(this.place_patch(player[player_playing], read));
  }

  /**The function place a leather patch
   * 
   * @param player
   *        Player information
   * */
  public void place_leather(Player_automa player) {
    Objects.requireNonNull(player);
    Scanner read;
    int successful_place_patch = 0;
    System.out.println("joueur " + player.player() + " give the position where place leather:(x, y)");
    while(successful_place_patch == 0) {
      read = new Scanner(System.in);
      int x = read.nextInt();
      int y = read.nextInt();
      if(!player.quiltboard().place(Patch.leather_patch(), x, y)) {
        System.out.println("error piece doen't be able place here");
      }
      else {
        successful_place_patch = 1;
      }
    }
  }


  /**The function manage all action when you choice to buy a patch
   * @param player  
   *        a Player Array which all player
   * @param player_playing
   *        which player plays
   * @param list 
   *        HashMap with all patch
   * @param index
   *        the position of neutral token
   * @param read
   *        A scanner for read console
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   * @return where you are in the index       
   *        */
  private int patch_action(Scanner read) {
    int choice, purchare_finish;
    System.out.println("Player " + player[player_playing].player() + " what do you want:(1, 2 ou 3)");
    System.out.println("(0 si vous annulez l'action d'achat)");
    choice = ASCII.purchare_action(player[player_playing], list, index, read);
    if(choice < 0) {
      purchare_finish = 0;
    }
    else {
      index = choice;
      purchare_finish = 1;
    }
    if(purchare_finish == 1) {//player have bought 
      this.patch_place_rotate(read);
    }
    return index;
  }
  /** The function test if a object is null
   * @param player  
   *        a Player Array which all player
   * @param list 
   *        HashMap with all patch
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map   
   * */
  public static void objectnull(Player_automa[] player,HashMap<Integer,Patch> list,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list) {
    Objects.requireNonNull(player);
    Objects.requireNonNull(list);
    Objects.requireNonNull(leather_list);
    Objects.requireNonNull(button_list);
  }

  /**The function print the question read what which step you want   
   * @param list 
   *        HashMap with all patch
   * @param read
   *        A scanner for read console
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   * */
  private static void list_patch_set_up(HashMap<Integer,Patch> list,ArrayList<Integer> leather_list,
      ArrayList<Integer> button_list,  Scanner read) {
    System.out.println("You want play with step 1 or step 2:(1/2)");
    ASCII.step_1_2(list, leather_list, button_list, read);
  }

  /**The choice of what do you want do
   * */
  private void option( Scanner read) {
    String action = read.next();
    if(action.equals("m")){
      Game.moving_action(player, player_playing,leather_list, button_list, this);
    }else if(action.equals("p")) { // patch choice
      index = this.patch_action(read);
    }else if(action.equals("w")) {
      ASCII.watch_all_patch(list, index);
    }
  }

  /**Function use for see all patch
   * */
  private static void watch_all_patch(HashMap<Integer, Patch> list, int index) {
    int cursor = index;
    for(int i = 0; i < list.size(); i++) {
      if(cursor >= list.size()) {
        cursor = 0;
      }
      System.out.println(list.get(cursor));
      cursor++;
    }
  }
  /**It use if the type is graphics
   * */
  public boolean isgraphics() {
    return false;
  }

  /**
   * @param read
   *        A scanner for read console
   * @return true if player want play alone
   * */
  private static boolean automa_mode(Scanner read) {
    while(true) {
      System.out.println("Do you play with automa:(y/n)");
      String action = read.next();
      if(action.equals("y")){
        return true;
      }else if(action.equals("n")) { // patch choice
        return false;
      }
    }
  }

  /**This function manages all turns on in game
   * @param read
   *        A scanner for read console
   @ @param automa
             if player want play alone
   * */
  private void turns(Scanner read, boolean automa) {
    int board7x7 = 1;
    while(player[0].position() <= 63 && player[1].position() <= 63 ) { // end condition
      
      if(!(automa == true && player_playing == 1)) {
        this.begin();
        this.option(read);
        board7x7 = Game.board7x7_complete_or_not(player[player_playing], board7x7);
      }
      else{

      }
      this.player_playing = Game.next_player(player, player_playing);//changing player
    }
  }


  /**The function is use for play the game on using terminal
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
   * @param read
   *        A scanner for read console    
   **/
  public static void game(Player[] player, int player_playing, HashMap<Integer,Patch> list,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list, Scanner read) {
    Objects.requireNonNull(read);
    ASCII.objectnull(player, list, leather_list, button_list);
      /*boolean automa = automa_mode(read);*/
      boolean automa = false;
      ASCII.list_patch_set_up(list, leather_list, button_list, read);
      var information = new ASCII(player, list, player_playing, leather_list, button_list); 
      information.turns(read, automa);
      winner_is(player);
      read.close();
    
  }
}