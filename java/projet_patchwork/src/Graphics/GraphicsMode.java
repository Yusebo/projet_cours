package Graphics;

import Gamedata.Player_automa;


/**Interface use distinguish Graphics and ASCII
 * */
public sealed interface GraphicsMode permits Graphics, ASCII{
  
  /**if is it Graphics
   * @return true if is graphics else false
   * */
  boolean isgraphics();
  
  /**use for player to place leather patch
   * @param player
   *        player which place the leather
   * */
  void place_leather(Player_automa player);

}
