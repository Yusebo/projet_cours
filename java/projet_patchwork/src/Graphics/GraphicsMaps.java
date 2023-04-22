package Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

import Gamedata.Player;

/**This class it use for drawing timeboard on spiral
 * */
public class GraphicsMaps {
  /**change the direction turned 90° 
   * 
   * @param move
   *        a direction (right , left ,up , down)
   * @return a direction
   * */
  private static String direction(String move) {
    //switch-case to rotate the movement 
    return switch (move)  {  
    case "right" -> "down";   
    case "down" -> "left";   
    case "left" -> "up";    
    case "up" -> "right";
    default -> null;
    };
  } 

  /**the function it use for move on row or column
   * 
   * @param move
   *        a direction (right , left ,up , down)
   * @param rowcol
   *        a array with information of row and column
   * @return a direction
   * */
  private static void move_case(String move, int[] rowcol) {
    switch (move){  // direction up down right left
    case "right":  
      rowcol[1] += 1;
      break;
    case "left": 
      rowcol[1] -= 1;
      break;  
    case "up":
      rowcol[0] -= 1;
      break;  
    case "down": 
      rowcol[0] += 1;
      break;  
    }
  }
  
  /**
   * @return the position of button relative to where is up ,down ,left or right on the box
   * */
  private static Float button(String move, int box_size, int[] rowcol, Graphics2D graphics, int shift_x, int shift_y) {
    return switch(move) {
    case "right" ->  new Ellipse2D.Float((float) (shift_x + rowcol[1] * box_size - (box_size * 0.2)),
        (float) (shift_y +rowcol[0] * box_size + (box_size * 0.35)) ,
        box_size/3, box_size/3);
    case "left" ->  new Ellipse2D.Float((float) (shift_x + rowcol[1] * box_size + (box_size * 0.8)),
        (float) (shift_y +rowcol[0] * box_size + (box_size * 0.35)) ,
        box_size/3, box_size/3);
    case "up" ->  new Ellipse2D.Float((float) (shift_x + rowcol[1] * box_size + (box_size * 0.35)),
        (float) (shift_y +rowcol[0] * box_size + (box_size * 0.8)) ,
        box_size/3, box_size/3);
    case "down" ->  new Ellipse2D.Float((float) (shift_x + rowcol[1] * box_size + (box_size * 0.35)),
        (float) (shift_y +rowcol[0] * box_size - (box_size * 0.2)) ,
        box_size/3, box_size/3);
    default -> throw new IllegalArgumentException("Unexpected value: " + move);
    };
  }
  /**
   * @return the position of leather relative to where is up ,down ,left or right on the box
   * */
  private static Rectangle2D.Float leather(String move, int box_size, int[] rowcol, Graphics2D graphics, int shift_x,
      int shift_y) {
    return switch(move) {
    case "right" ->  new Rectangle2D.Float((float) (shift_x + rowcol[1] * box_size - (box_size * 0.2)),
        (float) (shift_y +rowcol[0] * box_size + (box_size * 0.35)) ,
        box_size/3, box_size/3);
    case "left" ->  new Rectangle2D.Float((float) (shift_x + rowcol[1] * box_size + (box_size * 0.8)),
        (float) (shift_y +rowcol[0] * box_size + (box_size * 0.35)) ,
        box_size/3, box_size/3);
    case "up" ->  new Rectangle2D.Float((float) (shift_x + rowcol[1] * box_size + (box_size * 0.35)),
        (float) (shift_y +rowcol[0] * box_size + (box_size * 0.8)) ,
        box_size/3, box_size/3);
    case "down" ->  new Rectangle2D.Float((float) (shift_x + rowcol[1] * box_size + (box_size * 0.35)),
        (float) (shift_y +rowcol[0] * box_size - (box_size * 0.2)) ,
        box_size/3, box_size/3);
    default -> throw new IllegalArgumentException("Unexpected value: " + move);
    };
  }


  
  /**Draw a single button on the map
   * @param graphics 
   *      Use for draw
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   * */
  private static void draw_button(Graphics2D graphics,  ArrayList<Integer> button_list,
      int i,int j, int box_size, int[] rowcol, int shift_x, int shift_y,
      String move, String prev_move) {
    if(button_list.contains(i + j)) { 
      graphics.setColor(Color.blue);
      Float ellipse;
      if(j == 0) {
        ellipse = GraphicsMaps.button(prev_move, box_size, rowcol, graphics, shift_x, shift_y);
      }
      else {
        ellipse = GraphicsMaps.button(move, box_size, rowcol, graphics, shift_x, shift_y);
      }
      graphics.fill(ellipse);
    }
  }
  /**Draw a single leather on the map
   * @param graphics 
   *      Use for draw
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * */
  private static void draw_leather_patch(Graphics2D graphics,  ArrayList<Integer> leather_list,
      int i,int j, int box_size, int[] rowcol, int shift_x, int shift_y,
      String move, String prev_move) {
    if(leather_list.contains(i + j)) {
      graphics.setColor(new Color(110, 51, 0)); //color light brown
      Rectangle2D.Float leather ;
      if(j == 0) {
        leather = GraphicsMaps.leather(prev_move, box_size, rowcol, graphics, shift_x, shift_y);
      }
      else {
        leather = GraphicsMaps.leather(move, box_size, rowcol, graphics, shift_x, shift_y);
      }
      graphics.fill(leather);
    } 
  }
  
  /**Manage all possibilty off type box
   * @param graphics 
   *      Use for draw
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   *        */
  private static void draw_box_on_spiral(Graphics2D graphics,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list,
      int i, int j, int[] rowcol, int box_size, int shift_x, int shift_y){
    if(i + j == 1) {
      Graphics.draw_rectangle_withborderline(graphics, Color.blue, Color.gray, shift_x  + rowcol[1] * box_size,
          shift_y + rowcol[0] * box_size, box_size * 3, box_size);
    }
    else if(i + j > 3 && i + j < 61) {
      Graphics.draw_rectangle_withborderline(graphics, Color.blue, Color.gray, shift_x  + rowcol[1] * box_size,
          shift_y + rowcol[0] * box_size, box_size, box_size);
    }
    else if(i + j == 61) {
      Graphics.draw_rectangle_withborderline(graphics, Color.blue, Color.gray,  shift_x  + rowcol[1] * box_size,
          shift_y + rowcol[0] * box_size, box_size * 2, box_size * 2);
    }
  }
  /**Function draw all leather patch available on the map
   * @param graphics 
   *      Use for draw
   * @param player
   *        A array with all player
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   *        */
  private static void draw_spiral_with_leather_button(Graphics2D graphics, Player[] player,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list,
      int i, int[] rowcol, int limit, int rotate, String move, String prev_move){
    int box_size = 50;
    int shift_x = 100, shift_y = 100;
    for(int j = 0; j < limit; j++) {
      draw_box_on_spiral(graphics, leather_list, button_list, i, j, rowcol, box_size, shift_x, shift_y);
      draw_button(graphics, button_list, i, j, box_size, rowcol, shift_x, shift_y, move, prev_move);
      draw_leather_patch(graphics, leather_list, i, j, box_size, rowcol, shift_x, shift_y, move, prev_move);
      draw_all_player(graphics, player, i, j, box_size, rowcol, shift_x, shift_y, move);
      GraphicsMaps.move_case(move, rowcol);
    }
  }

  private static void draw_player(Graphics2D graphics, int box_size, int row, int col, int shift_x, int shift_y) {
    Float ellipse = new Ellipse2D.Float((float) (shift_x + col * box_size + (box_size * 0.2)),
        (float) (shift_y +row * box_size + (box_size * 0.2)) ,
        box_size / 2, box_size / 2);
    graphics.fill(ellipse);
  }

  /**the function draw all players
   * @param graphics 
   *      Use for draw
   * */
  private static void draw_all_player(Graphics2D graphics,  Player[] player,
      int i,int j, int box_size, int[] rowcol, int shift_x, int shift_y,
      String move) {
    if(player[0].position() == (i + j)) { 
      graphics.setColor(Color.red);
      draw_player(graphics, box_size, rowcol[0], rowcol[1], shift_x, shift_y);
    }
    if(player[1].position() == (i + j)) {
      graphics.setColor(Color.yellow);
      if(i + j == 3) {
        draw_player(graphics, box_size, rowcol[0] , rowcol[1] - 1, shift_x, shift_y);
      }
      else {
        draw_player(graphics, box_size, rowcol[0], rowcol[1], shift_x, shift_y);
      }
    }
  }
  
  /**This function it use for manage you do change direction
   * 
   * @param graphics 
   *      Use for draw
   * @param player
   *        A array with all player
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   * */
  private static void matrix_action(Graphics2D graphics, Player[] player, 
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list,
      int length, int[] rowcol, int limit, int rotate, String move, String prev_move) {
    for (int i = 1; i < length * length + 1; ){  
      draw_spiral_with_leather_button(graphics, player, leather_list, button_list, i, rowcol, limit, rotate, move, prev_move);
      if(rotate%2 == 0 && rotate != 0 && limit != 1)  {  
        limit -= 1;
        i++;
      }
      prev_move = String.copyValueOf(move.toCharArray()) ;
      move = direction(move);
      rotate++;
      i += limit;
    }
  }

  /** The function create a spiral matrix with spiral form
   * @param graphics 
   *        Object Use for draw
   * @param player
   *        A array with all player
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   * @param length
   *        length on x and y of square
   */
  public static void matrix_spiral(Graphics2D graphics, Player[] player,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list,
      int length) {  
    Objects.requireNonNull(player);
    Objects.requireNonNull(leather_list);
    Objects.requireNonNull(button_list);
    Objects.requireNonNull(graphics);
    int[] rowcol = new int[] {0, 0};
    int limit = length - 1;  
    int rotate = 0;  
    String move = "right";
    String prev_move = "right";
    matrix_action(graphics, player, leather_list, button_list, length, rowcol, limit, rotate, move, prev_move);
  }
}
