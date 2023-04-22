package Mechanism;
import java.util.ArrayList;
import java.util.Objects;

import Gamedata.Player_automa;
import Graphics.GraphicsMode;

/**The class Maps is the class with function linked at all action on the map
 * */
public class Maps{  
  /**the function add to ArrayList with position of leather patch
   * @param leather_list
   *        a list not null
   * */
  public static void setLeather(ArrayList<Integer> leather_list){
    Objects.requireNonNull(leather_list);
    for(int i = 0; i < 5; i++) {
      leather_list.add(6 * i + 21 + 2);
    }
  }

  /**the function add to ArrayList with position of button in the map
   * @param button_list
   *        a Array_list not null
   * */
  public static void setButton(ArrayList<Integer> button_list){
    Objects.requireNonNull(button_list);
    for(int i = 1; i < 9; i++) {
      button_list.add(6 * i + 2);
    }
  }


  /**The function control action when a player move
   * 
   * @param player 
   *        Player information
   * @param move
   *        the move of player
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   * @param mode
   *        inforamtion of game
   * */
  public static void move_with_leather_button(Player_automa player, int move,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list, GraphicsMode mode) {
    Objects.requireNonNull(player);
    Objects.requireNonNull(leather_list);
    Objects.requireNonNull(button_list);
    Objects.requireNonNull(mode);
    Maps.collect_button(player, move, button_list);
    player.move(move);
    Maps.collect_leather(player, leather_list, mode);

  }

  /**the function give at player button in quilt board if you move beyond a button in the map
   * 
   * @param player 
   *        Player information
   * @param move
   *         the move of player
   * @param button_list 
   *         ArrayList{@literal <}Integer> with all position in the map
   * */
  private static void collect_button(Player_automa player, int move, ArrayList<Integer> button_list) {
    int button_collected = 0;
    for(var button : button_list) {
      if(button - 2 > player.position() && button - 2 <= player.position() + move) {
        button_collected++;
      }
    }
    for(int i = 0; i < button_collected; i++) {
      player.quiltboard().button_in_quiltboard();
      player.earn(player.quiltboard().button_in_quiltboard());
    }
  }

  /**the function give at player a leather patch if you move beyond a leather patch in the map
   * 
   * @param player 
   *        Player information
   * @param leather_list
   *          ArrayList{@literal <}Integer> with all position of Leather patch
   * @param mode
   *        if it is a ASCII or Graphics
   * */
  private static void collect_leather(Player_automa player, ArrayList<Integer> leather_list, GraphicsMode mode) {
    int leather_collected = 0;
    for(var leather : leather_list) {
      if(leather <= player.position()) {
        leather_collected++;
      }
    }
    leather_list.removeIf(e -> (e <= player.position()));
    for(int i = 0; i < leather_collected; i++) {
        mode.place_leather(player);
    }
  }

  /** drawn lines of map
   * 
   * @param position_player1
   *            position of player1
   * @param position_player2
   *            position of player2
   * @param i 
   *          line position on map
   * @return String return the line of the map
   * */
  private static String Stringline(int position_player1, int position_player2, int i) {
    var builder = new StringBuilder();
    for (int j = 0; j < 8; j++) { 
      builder.append("+-");  
    }
    return builder.toString();
  }
  /**The function drawn a between 2 case if leather or button appears
   * @param i 
   *          line position on map
   * @param j 
   *          column position on map
   * @param leather_list
   *          ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list ArrayList{@literal <}Integer>
   *         ArrayList{@literal <}Integer> with all position in the map
   */
  private static String String_leather_button_or_Nothing(int i,int j,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list) {
    var builder = new StringBuilder();
    if(leather_list.contains(i*8 + j)) {
      builder.append("#");
    }
    else if(button_list.contains(i*8 + j)) {
      builder.append("o");
    }
    else {
      builder.append("|");
    }
    return builder.toString();
  }

  /**The function watch where place player
   * @param position_player1
   *            position of player1
   * @param position_player2
   *            position of player2
   * @param i 
   *          line position on map
   * @param j 
   *          column position on map
   * return player case
   * */
  private static String String_player(int position_player1, int position_player2, int i, int j) {
    var builder = new StringBuilder();
    if(position_player1 ==position_player2 && position_player1 == i*8 + j ) {
      builder.append("=");  
    }
    else if(position_player1 == i*8 + j ){
      builder.append("1");   
    }
    else if(position_player2 == i*8 + j ){
      builder.append("2");   
    }
    else{
      builder.append(" ");
    }
    return builder.toString();
  }

  /** drawn the box of map
   * 
   * @param position_player1
   *            position of player1
   * @param position_player2
   *            position of player2
   * @param i 
   *          line position on map
   * @param leather_list
   *          ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list ArrayList{@literal <}Integer>
   *         ArrayList{@literal <}Integer> with all position in the map
   * @return String 
   *           return the box of the map
   * */
  private static String Stringbox(int position_player1, int position_player2, int i,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list) {
    var builder = new StringBuilder();
    for(int j = 0; j < 8; j++) {
      builder.append(String_leather_button_or_Nothing(i, j, leather_list, button_list));
      builder.append(String_player(position_player1, position_player2, i, j));
    }
    return builder.toString();
  }

  /**drawn a map
   * @param position_player1
   *            position of player1
   * @param position_player2
   *            position of player2
   * @param leather_list
   *          ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list ArrayList{@literal <}Integer>
   *         ArrayList{@literal <}Integer> with all position in the map
   * @return String return  map
   * */
  public static String place_pion(int position_player1, int position_player2, 
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list) {
    Objects.requireNonNull(leather_list);
    Objects.requireNonNull(button_list);
    var builder = new StringBuilder();
    for(int i = 0; i < 8; i++)  {
      builder.append(Stringline(position_player1, position_player2, i));
      builder.append("+\n");
      builder.append(Stringbox(position_player1, position_player2, i, leather_list, button_list));
      builder.append("|\n");  
    }
    //draw final line
    for (int i = 0; i < 8; i++) {
      builder.append("+-");  
    }
    builder.append("+\n");
    return builder.toString();
  }
}  