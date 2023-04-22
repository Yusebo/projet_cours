package Graphics;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import Gamedata.Patch;
import Gamedata.Player;
import Gamedata.Player_automa;
import Gamedata.Quiltboard;
import Mechanism.Game;
import Mechanism.Maps;

/**The class manage game function on graphics aspect
 * */
public non-sealed class Graphics implements GraphicsMode{
  private ApplicationContext context;
  private Player[] player;
  private final HashMap<Integer,Patch> list;
  private final ArrayList<Integer> leather_list;
  private final ArrayList<Integer> button_list;
  private int player_playing;
  private int index;
  
  /**Constructor use for a save information
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
   * @param context
   *        Information of window and use to have graphics for drawing
   * */
  public Graphics(Player[] player, HashMap<Integer,Patch> list, int player_playing,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list, ApplicationContext context) {
    Graphics.objectnull(player, list, leather_list, button_list, context);
    this.context = context;
    this.player = player;
    this.list = list;
    this.leather_list = leather_list;
    this.button_list = button_list;
    this.player_playing = player_playing;
    this.index = all_param_initialisation(player, list, leather_list, button_list);
  }
  
  /**This constructor it use for leather place
   * @param context
   *        Information of window and use to have graphics for drawing
   * */
  public Graphics( ApplicationContext context) {
    this.context = context;
    this.player = null;
    this.list = new HashMap<>();
    this.leather_list = new ArrayList<>();
    this.button_list = new ArrayList<>();
    this.player_playing = 0;
    this.index = 0;
  }
  
  /**Draw a rectangle with borderline
   * @param graphics 
   *        Object Use for draw
   * @param color_line 
   *        Color of the borderline
   * @param color_in
   *        Color of the rectangle
   * @param x
   *        Starting point x on the top corner left of rectangle
   * @param y
   *        Starting point y on the top corner left of rectangle
   * @param length_case_x
   *        length on x
   * @param length_case_y
   *        length on y 
   * */
  public static void draw_rectangle_withborderline(Graphics2D graphics, Color color_line, Color color_in,
      double x, double y, double length_case_x, double length_case_y) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(color_line);
    Objects.requireNonNull(color_in);
    //exterior rectangle
    graphics.setColor(color_line);
    Rectangle2D.Double rect =  new Rectangle2D.Double(x, y, length_case_x, length_case_y);
    graphics.fill(rect);
    //interior rectangle 
    graphics.setColor(color_in);
    rect =  new Rectangle2D.Double(x + 2, y + 2 , length_case_x - 4, length_case_y - 4);
    graphics.fill(rect);
  }

  /**Draw only information in patch
   * @param graphics 
   *        Object use to draw
   * @param patch
   *        Patch you want draw
   * @param shift_x
   *        Starting point x on the top corner left of rectangle
   * @param shift_y
   *        Starting point y on the top corner left of rectangle
   * @param box_size
   *        lenght of each box
   * */
  private static void patch_information(Graphics2D graphics, Patch patch, double shift_x, double shift_y, int box_size) {
    graphics.drawString("price : " + patch.button(), (int) shift_x,
        (int)shift_y + -2 * box_size); 
    graphics.drawString("move : " + patch.move(), (int) shift_x,
        (int)shift_y + -1 * box_size); 
  }

  /**Draw a box in patch
   * @param graphics 
   *        Object use to draw
   * @param patch
   *        Patch you want draw
   * @param shift_x
   *        Starting point x on the top corner left of rectangle
   * @param shift_y
   *        Starting point y on the top corner left of rectangle
   * @param box_size
   *        lenght of each box
   * @param i
   *        read box on array
   * @param j
   *        read box on double array
   * */
  private static void draw_patch_box(Graphics2D graphics, Patch patch, double shift_x, double shift_y, int box_size, int i, int j) {
    if(patch.piece()[i][j] == 1) {
      draw_rectangle_withborderline(graphics, Color.white, Color.gray, shift_x  + j * box_size,
          shift_y + i * box_size, box_size, box_size);
    }
    else if(patch.piece()[i][j] == 3){
      draw_rectangle_withborderline(graphics, Color.white, Color.blue, shift_x  + j * box_size,
          shift_y + i * box_size, box_size, box_size);
    }
  }
  
  /**Draw only piece in patch
   * @param graphics 
   *        Object use to draw
   * @param patch
   *        Patch you want draw
   * @param shift_x
   *        Starting point x on the top corner left of rectangle
   * @param shift_y
   *        Starting point y on the top corner left of rectangle
   * @param box_size
   *        lenght of each box
   * */
  private static void draw_only_patch(Graphics2D graphics, Patch patch, double shift_x, double shift_y, int box_size) {
    graphics.setColor(Color.gray);
    for(int i = 0; i < patch.piece().length; i++) {
      for(int j = 0; j < patch.piece()[i].length; j++) {
        draw_patch_box(graphics, patch, shift_x, shift_y, box_size, i, j);
      }
    }
  }
  
  /**Draw patch
   * @param graphics 
   *        Object use to draw
   * @param patch
   *        Patch you want draw
   * @param shift_x
   *        Starting point x on the top corner left of rectangle
   * @param shift_y
   *        Starting point y on the top corner left of rectangle
   * @param box_size
   *        lenght of each box
   * */
  private static void draw_patch(Graphics2D graphics, Patch patch, double shift_x, double shift_y, int box_size) {
    graphics.setColor(Color.gray);
    patch_information(graphics, patch, shift_x, shift_y, box_size);
    draw_only_patch(graphics, patch, shift_x, shift_y, box_size);
  }
  
  /**Draw touch
   * @param graphics 
   *        Object Use for draw
   * @param color_line 
   *        Color of the borderline
   * @param color_in
   *        Color of the rectangle
   * @param color_in
   *        Color of word
   * @param x
   *        Starting point x on the top corner left of rectangle
   * @param y
   *        Starting point y on the top corner left of rectangle
   * @param length_case_x
   *        length on x
   * @param length_case_y
   *        length on y 
   * @param word
   *        word or sentance you want write
   * */
  private static void draw_click_button(Graphics2D graphics, Color color_line, Color color_in, Color color_word,
      int x, int y, int length_box_x, int length_box_y ,String word) {
     draw_rectangle_withborderline(graphics, color_line, color_in,
        x, y, length_box_x, length_box_y);
    graphics.setColor(color_word);
    graphics.drawString(word , x + (length_box_x / 3), y + (2 * length_box_y / 3)); 
  }
  
  /** Draw three next patch
   * @param graphics 
   *        Object use to draw
   * @param list 
   *        HashMap with all patch
   * @param index
   *        position of neutral token
   * @param width
   *        size of window on horizontal
   * @param height
   *        size of window on vertical
   * */
  private static void draw_three_patch(Graphics2D graphics, HashMap<Integer, Patch> list, float width, float height, int index) {
     draw_rectangle_withborderline(graphics, Color.white, Color.black,
        (width/3), (2*height/3), (2*width/3), (height/3));
    int tmp_index = index;
    for(int i = 0; i < 3; i++) {
      if(tmp_index >= list.size()) {
        tmp_index = 0;
      }
      draw_patch(graphics, list.get(tmp_index),
          (i * ((2*width/3)/4 + 40 ))+ (width/3) + 100 , (2.5*height/3), 23);
      tmp_index++;
    }
  }

  /** Draw all patch
   * @param list 
   *        HashMap with all patch
   * @param index
   *        position of neutral token
   * @param graphics 
   *        Object use to draw
   * @param width
   *        size of window on horizontal
   * @param height
   *        size of window on vertical
   * */
  private static void watch_all_patch(HashMap<Integer, Patch> list, int index, Graphics2D graphics, double width, double height) {
    int cursor = index;
    for(int i = 0; i < list.size(); i++) {
      if(cursor >= list.size()) {
        cursor = 0;
      }
      draw_patch(graphics, list.get(cursor),
          ( (i % 8 + 0.66) * (width / 9) ) , (int)((i / 8) + 1) * (width / 9)  , (int)(width / (9 * 10)));
      cursor++;
    }
  }

  /**Draw a button with all patch
   * @param graphics 
   *        Object use to draw
   * */
  private static void draw_patch_button(Graphics2D graphics, int base_position_y) {
    for(int i = 0; i < 3; i++) {
      draw_click_button(graphics, Color.white, Color.black, Color.YELLOW, 20, base_position_y + (40 * i), 100, 30, "Patch " + (i + 1));
    }
  }

  /** Draw interface 
   * @param list 
   *        HashMap with all patch
   * @param index
   *        position of neutral token
   * @param graphics 
   *        Object Use for draw
   * @param width
   *        size of window on horizontal
   * @param height
   *        size of window on vertical
   * */
  private static void draw_interface(Graphics2D graphics, HashMap<Integer, Patch> list, float width, float height, int index) {
    // hide the previous rectangle  
     Graphics.draw_rectangle_withborderline(graphics, Color.white, Color.black,
        0, (2*height/3), (width), (height/3));
    int base_position_y = (int) (2 * height / 3);
    draw_click_button(graphics, Color.white, Color.black, Color.YELLOW, 20, base_position_y + 20, 100, 30, "move");
    draw_patch_button(graphics, base_position_y + 60) ;
    draw_click_button(graphics, Color.white, Color.black, Color.YELLOW, 150, base_position_y + 20, 200, 30, "watch all patch");
    draw_three_patch(graphics, list, width, height, index);
  }
  
  /**Calculates hitbox of all interface
   * @param x
   *        Starting point x on the top corner left of rectangle
   * @param y
   *        Starting point y on the top corner left of rectangle
   * @param width
   *        length on x
   * @param height
   *        length on y   
   * */
  private int hitbox_intferface(float x, float y, float height, float width) {
    if(20 <= x && x <= 120 ){
      if(2 * height / 3 + 20 <= y && y <= 2 * height / 3 + 50) {
        efface(context, width, height);
        Game.moving_action(player, player_playing,leather_list, button_list, this);
      }
      else {
        int choice = this.patch_choice_hitbox(x, y, width, height);
        return this.purcharse_patch(choice, player[player_playing], width, height);
      }
    }
    else if(150 <= x && x <= 350 ){
      this.all_patch_interface(y, width, height);
    }
    return index;
  }
  
  /**Manages interface with all patch
   * @param y
   *        Starting point y on the top corner left of rectangle
   * @param width
   *        length on x
   * @param height
   *        length on y   
   * */
  private void all_patch_interface(float y, float width, float height) {
    if( 2 * height / 3 + 20 <= y && y <= 2 * height / 3 + 50) {
      efface(context, width, height);
      while(all_patch_quit(context)) {
        context.renderFrame(graphics -> {
          watch_all_patch(list, index, graphics , width, height);
          draw_click_button(graphics, Color.white, Color.black, Color.YELLOW, 0, 0, 80, 30, "quit"); 
        });
      }
      efface(context, width, height);
    }
  }

  /**Button for quit interface    
   * @param context
   *        use for read context of window or draw
   * */
  private static boolean all_patch_quit(ApplicationContext context) {
    Event event = clic(context);
    if(event != null) {
      Point2D.Float location = event.getLocation();
      float x = location.x; 
      float y = location.y;
      if(0 <= x && x <= 80 ){
        if(0 <= y && y <= 30) {
          return false;
        }
      }
    }
    return true;
  }

  private int clic_on_place_patch_interface(Player_automa player, int box_size, int choice, float width, float height) {
    Event event = clic(this.context);
    if(event != null) {
      Point2D.Float location = event.getLocation();
      if(this.patch_place_rotate(player, box_size, choice, location.x, location.y, width, height) == true) {
        efface(this.context, width, height);
        return choice;
      }
    }
    return -1;
  }

  private int place_patch_interface(Player_automa player,int choice) {
    ScreenInfo screenInfo = this.context.getScreenInfo();
    float width = screenInfo.getWidth();
    float height = screenInfo.getHeight();    
    while(true) {
      int box_size = (int) (height/20);
      context.renderFrame(graphics -> {
        draw_quiltboard_place_patch(graphics, player, width, height, box_size, this.list.get(choice)); 
      });
      int out = this.clic_on_place_patch_interface(player, box_size, choice, width, height) ;
      if(out != -1) {
        return out;
      }
    }
  }

  private int purcharse_patch(int choice, Player player, 
      float width, float height){
    if(choice >= 0) {
      if(this.list.get(choice).button() <= player.button()){
        player.buy(this.list.get(choice).button());
        efface(this.context, width, height);
        this.place_patch_interface(player, choice);
      }
      else{
        this.context.renderFrame(graphics -> {
          graphics.setColor(Color.red);
          graphics.drawString("You haven't enought button" , 0 , 2 * height / 3 - 10); 
        });
      }
      return choice;
    }
    return index;
  }
  /**
   * @param graphics
   *        Object use to draw
   * @param patch
   *        Patch you want draw
   * @param player 
   * @param box_size
   *        lenght of each box
   * @param width
   *        window length on x
   * @param height
   *        window length on y   
   * */
  private static void draw_quiltboard_place_patch(Graphics2D graphics, Player_automa player, float width,
      float height, int box_size, Patch patch) {
    int base_position_y = (int) (height / 3);
    draw_click_button(graphics, Color.white, Color.black, Color.BLUE, 40, base_position_y + 20, 100, 30, "reverse");
    draw_click_button(graphics, Color.white, Color.black, Color.BLUE, 40, base_position_y + 60, 100, 30, "right rotate");
    draw_click_button(graphics, Color.white, Color.black, Color.BLUE, 40, base_position_y + 100, 100, 30, "left rotate");
    draw_rectangle_withborderline(graphics, Color.white, Color.black,
        180, base_position_y, (height/3), (height/3));
    draw_only_patch(graphics, patch, 180 + (height/9) , base_position_y + (height/9), 22);
    draw_quiltboard(graphics, player.quiltboard(), (width/2) - box_size * 4.5, box_size * 4.5 , 2*(height/3));
  }
  
  /**Calculate hitbox to change patch
   * @param list 
   *        HashMap with all patch
   * @param index
   *        position of neutral token
   * @param x
   *        Starting point x on the top corner left of rectangle
   * @param y
   *        Starting point y on the top corner left of rectangle
   * @param width
   *        window length on x
   * @param height
   *        window length on y   
   * */
  private static void rotate_choice(HashMap<Integer, Patch> list,int index, float x, float y, float width, float height){
    Patch patch = list.get(index);
    int base_position_y = (int) (height/3) + 20;
    if(base_position_y <= y && y <= (base_position_y + 30)) {
      patch.mirror();
    }else if(base_position_y + 40 <= y && y <= (base_position_y + 70)){
      patch = patch.right_rotation();
    }else if(base_position_y + 80 <= y && y <= (base_position_y + 110)){
      patch = patch.left_rotation();
    }
    list.put(index, patch);
  }

  private static boolean patch_place_positon(ApplicationContext context, Player_automa player, HashMap<Integer,Patch> list,
      int index, int x, int y) {
    if(!player.quiltboard().place(list.get(index), x, y)) {
      context.renderFrame(graphics -> {
        graphics.setColor(Color.red);
        graphics.drawString("error piece doen't be able place here" , 0 , 30); 
      });
      return false;
    }
    efface(context, 100, 40);
    return true;
  }
   
  private boolean place_patch(Player_automa player, int index, int x, int y) {
    if(patch_place_positon(context, player, list, index, x, y)) {
      Maps.move_with_leather_button(player, list.get(index).move(), leather_list, button_list, this);
      Patch.list_patch_remove(list, index);
      return true;
    }
    return false;
  }

  /**Calculate hitbox to change patch and place patch
   * @param box_size
   *        length on x and y
   * @param index
   *        position of neutral token
   * @param x
   *        Starting point x on the top corner left of rectangle
   * @param y
   *        Starting point y on the top corner left of rectangle
   * @param width
   *        window length on x
   * @param height
   *        window length on y
   * @return true if patch it is placed alse false
   * */
  private boolean patch_place_rotate(Player_automa player,int box_size, int index, float x, float y, float width, float height) {
    double shift_x = (width/2) - box_size * 3.5  ;
    double shift_y = box_size * 4.5;
    box_size = (int) (2*(height/33));
    if(20 <= x && x <= 120 ) {
      rotate_choice(this.list, index, x, y, width, height);
    }
    else if(shift_x <= x && x <= shift_x + (box_size * 9)
        && shift_y <= y && y <= shift_y + (box_size * 9)){
      int box_x = (int) ((x - shift_x) / box_size); // tranformation to box
      int box_y = (int) ((y - shift_y) / box_size);
      return this.place_patch(player, index, box_x, box_y);
    }
    return false;
  }

  /**Calculate hitbox of button to change patch
   * @param x
   *        Starting point x on the top corner left of rectangle
   * @param y
   *        Starting point y on the top corner left of rectangle
   * @param width
   *        window length on x
   * @param height
   *        window length on y
   * @return true if patch it is placed alse false
   * */
  private int patch_choice_hitbox(float x, float y, float width, float height) {
    int base_position_y = (int) ((2*height/3) + 60);
    if(base_position_y <= y && y <= (base_position_y + 30)) {
      efface(this.context, width, height);
      return  Game.patch_choice(list.size(), this.index, 0);
    }else if(base_position_y + 40 <= y && y <= (base_position_y + 70)){
      efface(this.context, width, height);
      return Game.patch_choice(list.size(),this. index, 1);
    }else if(base_position_y + 80 <= y && y <= (base_position_y + 110)){
      efface(this.context, width, height);
      return Game.patch_choice(list.size(), this.index, 2);
    }
    return -1;
  }

  /**Draw a black rectangle for clear display
   * @param context
   *        Use for information of event and draw
   * @param width
   *        window length on x
   * @param height
   *        window length on y
   * */
  private static void efface(ApplicationContext context, float width, float height) {
    context.renderFrame(graphics -> {
      graphics.setColor(Color.black);
      Rectangle2D.Double rect =  new Rectangle2D.Double(0, 0, width, height);
      graphics.fill(rect);
    });
  }
  
  private static void draw_quiltboard_box(Graphics2D graphics, Quiltboard quiltboard,
      double shift_x, double shift_y, int box_size, int i, int j) {
    if(quiltboard.board()[i][j] == 1) {
       draw_rectangle_withborderline(graphics, Color.white, Color.gray, shift_x  + j * box_size,
          shift_y + i * box_size, box_size, box_size);
    }
    else if(quiltboard.board()[i][j] == 3){
       draw_rectangle_withborderline(graphics, Color.white, Color.blue, shift_x  + j * box_size,
          shift_y + i * box_size, box_size, box_size);
    }
    else {
       draw_rectangle_withborderline(graphics, Color.white, Color.black, shift_x  + j * box_size,
          shift_y + i * box_size, box_size, box_size);
    }
  }

  private static void draw_quiltboard(Graphics2D graphics, Quiltboard quiltboard, double shift_x, double shift_y, float width) {
    int box_size = (int) (width/11); 
    graphics.setColor(Color.gray);
    for(int i = 0; i < quiltboard.board()[0].length; i++) {
      for(int j = 0; j < quiltboard.board().length; j++) {
        draw_quiltboard_box(graphics, quiltboard, shift_x + box_size, shift_y, box_size, i, j);
      }
    }
  }

  private static void player_information(Graphics2D graphics, Player player, double shift_x, double shift_y, int box_size) {
    graphics.setColor(Color.yellow);
    graphics.drawString("button : " + player.button(), (int) shift_x,
        (int)shift_y + -2 * box_size); 
    graphics.drawString("special_tiles : " + player.special_tiles(), (int) shift_x,
        (int)shift_y + -1 * box_size); 
  }

  private void draw_player_information(Graphics2D graphics, Player[] player, float width, float height) {
    for(int i = 0; i < 2; i++) {
      draw_rectangle_withborderline(graphics, Color.white, Color.black,
          (i + 3)*(width/5), 0, (width/5), (2*height/3));
      Graphics.draw_click_button(graphics, Color.white, Color.black, Color.yellow,
          (int)((i + 3)*(width/5) + width/15), 20, 100, 30, "Player " + player[i].player());
      who_play( graphics, i, width, this.player_playing);
      draw_quiltboard(graphics, player[i].quiltboard(), (i + 3)*(width/5), 100, width/5);
      player_information(graphics, player[i], (i + 3)*(width/5) + (width/55), 100 + 12 *(width/55), (int) (width/50));
    }
  }
   
  /**Draw a little green circle who player plays
   * @param graphics 
   *        Object use to draw
   * @param i
   *        actual player read information
   * @param width
   *        window length on x us
   * @param player_playing
   *        which player plays
   * */
  private static void who_play(Graphics2D graphics, int i, float width, int player_playing) {
    if(i == player_playing) {
      graphics.setColor(Color.green);
    }
    else {
      graphics.setColor(Color.black);
    }
    var eclipse = new Ellipse2D.Float((int)((i + 3)*(width/5) + width/15) + 110 ,25, 15, 15);
    graphics.fill(eclipse);
  }
  
  /**Read clic action
   * @param context
   *        Information of window and use to have graphics for drawing
   * @return event or null if nothing is done
   * */
  private static Event clic(ApplicationContext context) {
    Event event = context.pollOrWaitEvent(10);
    if (event != null)  {  // no event
      Action action = event.getAction();
      if(action == Action.POINTER_DOWN){
        return event;
      }
    }
    return null;
  }

  /**Read action between quit , buy patch , move, or watch all patch
   * */
  private boolean game_event_in_the_main_the_interface(float height, float width) {
    Event event = this.context.pollOrWaitEvent(10);
    if (event != null) {  // no event
      Action action = event.getAction();
      if (action != Action.POINTER_UP) {
        return quit_action(this.context, event);
      }
      else{
        Point2D.Float location = event.getLocation();
        this.hitbox_intferface(location.x, location.y, height, width);
      }
    }
    return true;
  }

  /**if use if you want quit without finish game
   * @param context
   *        Use for information of event
   * @param event
   *        actual Event
   * */
  private static boolean quit_action(ApplicationContext context, Event event) {
    if(event.getKey() == KeyboardKey.Q) {
      System.out.println("abort abort !");
      context.exit(0);
      return false;
    }
    return true;
  }

  /**It use went you take a leather patch
   * @param player
   *        Information of player
   * */
  public void place_leather(Player_automa player) {
    Objects.requireNonNull(player);
    Objects.requireNonNull(this);
    var leather = new Graphics(this.context);
    Patch patch = Patch.leather_patch();
    leather.list.put(0, patch);
    leather.place_patch_interface(player, 0);
  }
  
  /** Draw all interface
   * @param width
   *        size of window on horizontal
   * @param height
   *        size of window on vertical
   * */
  private void graphics_interface(float width, float height) {
    int board7x7 = 1;
    while(this.player[0].position() <= 61 && this.player[1].position() <= 61) {
      this.context.renderFrame(graphics -> {
        this.draw_player_information(graphics, this.player, width, height);
        GraphicsMaps.matrix_spiral(graphics, this.player, this.leather_list, this.button_list, 8);
        Graphics.draw_interface(graphics, this.list, width, height, this.index);  
      });
      if(!this.game_event_in_the_main_the_interface( height, width)) {
        return;
      }
      board7x7 = Game.board7x7_complete_or_not(player[player_playing], board7x7);
      player_playing = Game.next_player(player, player_playing);
    }   
  }

  /**The function print the winner
   * @param context
   *        Use for drawing on the window
   * @param player  
   *        a Player Array which all player
   * @param width
   *        size of window on horizontal
   * @param height
   *        size of window on vertical
   * */
  private static void winner_is(ApplicationContext context, Player[] player, float width, float height) {
    int winner = Game.who_win(player);

    context.renderFrame(graphics -> {
      graphics.setColor(Color.red);
      if(winner == 0) {
        graphics.drawString("Equality", width/2 - 20, height/2); 
      }
      else {
        graphics.drawString("Player" + winner + " win", width/2 - 20, height/2); 
      }
    });
  }

  /**This function it use for initialise all paramater and return the starting point of index
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
   * @return the starting point of index
   * */
  private static int all_param_initialisation(Player[] player, HashMap<Integer,Patch> list,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list) {
    Player.init_player_graphics(player);
    Patch.list_patch(list);
    Maps.setLeather(leather_list);
    Maps.setButton(button_list);
    return Game.minimal_patch(list);
  }
  
  /**It use if the type is graphics
   * */
  public boolean isgraphics() {
    return true;
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
   * @param context
   *        use for read context of window or draw
   * */
  public static void objectnull(Player[] player,HashMap<Integer,Patch> list,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list, ApplicationContext context) {
    Objects.requireNonNull(player);
    Objects.requireNonNull(list);
    Objects.requireNonNull(leather_list);
    Objects.requireNonNull(button_list);
    Objects.requireNonNull(context);
  }
  
  /**It use for start a game on graphics mode
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
  public static void game(Player[] player, HashMap<Integer,Patch> list, int player_playing,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list){
    ASCII.objectnull(player, list, leather_list, button_list);
    Application.run(Color.black, context -> {
      // get the size of the screen
      var information = new Graphics(player, list, player_playing, leather_list, button_list, context);
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float height = screenInfo.getHeight();
      information.graphics_interface(width, height);
      winner_is(context, player, width, height);
      while(context.pollOrWaitEvent(10)== null); // wiat a final mouse clic
      context.exit(0);
    });
  }
}
