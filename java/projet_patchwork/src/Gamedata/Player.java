package Gamedata;

/**The class is representation of player
 * */
public non-sealed class Player implements Player_automa{
  private final int player;
  private int position;
  private int button ; 
  private Quiltboard quiltboard;
  private int special_tiles;
  
  /** Constructor of Player
   *  and initiate position, button(money) and quiltboard
   *  @param player
   *        number of player
   * */
  public Player(int player) {
    if(player <= 0){
      throw new IllegalArgumentException();
    }
    this.player = player;
    this.position = 0;
    this.button = 5;
    this.quiltboard = new Quiltboard();
    this.special_tiles = 0;
  }
  

  /**The function change position of player and limit at 64
   * @param move
   *        move of player
   * */
  public void move(int move) {
    this.position += move;
    if(this.position >= 64) {
      this.position = 63;
    }
  }
  
  /**The function remove piece in player button
   * @param price
   *        the cost to retire at button
   * */
  public void buy(int price) {
    this.button -= price;
  }
  
  /**The function add coin_win in player button
   * @param coin_win
   *        button earn by player
   * */
  public void earn(int coin_win) {
    this.button += coin_win;
  }
  
  /**The function add a special tiles
   * */
  public void add_special_tiles(){
    this.special_tiles = 1;
  }
  
  /**Player position information
   * @return position information
   * */
  public int position() {
    return this.position;
  }
  /**Player number information
   * @return number information
   * */
  public int player() {
    return this.player;
  }
  /**Player quiltboard information
   * @return quiltboard information
   * */
  public Quiltboard quiltboard() {
    return this.quiltboard;
  }
  /**Player button information
   * @return button information
   * */
  public int button() {
    return this.button;
  }
  /**Player special_tiles information
   * @return special_tiles information
   * */
  public int special_tiles() {
    return this.special_tiles;
  }
  /**The function initialize a player array
   * @param player
   *        A player array
   * */
  public static void init_player(Player[] player) {
    player[0] = new Player(1);
    player[1] = new Player(2);
  }
  /**The function initialize a player array on Graphics mode
   * @param player
   *        A player array
   * */
  public static void init_player_graphics(Player[] player) {
    player[0] = new Player(1);
    player[1] = new Player(2);
    player[0].position = 3;
    player[1].position = 3;
  }

  /**this function is empty because player doe'nt have patch list
   * @param patch
   *        Patch use
   * */
  public void add_patch(Patch patch) {

  }
  
}