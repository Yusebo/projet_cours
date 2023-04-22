package Gamedata;

/**Interface to manage player and automa
 * */
public sealed interface Player_automa permits Player, Automa{
  /**The function change position of player and limit at 64
   * @param move
   *        move of player
   * */
  void move(int move) ;
  
  /**The function remove piece in player or automa button
   * @param price
   *        the cost to retire at button
   * */
  void buy(int price) ;
  
  /**The function add coin_win in player or automa button
   * @param coin_win
   *        button earn by player or automa 
   * */
  void earn(int coin_win) ;

  /**The function add a special tiles
   * */
  void add_special_tiles();
  
  /**Player position information
   * @return position information
   * */
  int position() ;
  /**Player number information
   * @return number information
   * */
  int player();
  /**Player quiltboard information
   * @return quiltboard information ,null if it is a automa
   * */
  Quiltboard quiltboard();
  /**Player button information
   * @return button information
   * */
  int button();
  /**Player special_tiles information
   * @return special_tiles information
   * */
  int special_tiles();

  /**Add a patch if it is a automa
   * @param patch 
   *        the patch you want add at list
   * */
  void add_patch(Patch patch);
}
