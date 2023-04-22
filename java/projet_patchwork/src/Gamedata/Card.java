package Gamedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import Graphics.ASCII;
import Graphics.GraphicsMode;
import Mechanism.Game;

/**Record use to create and manage card 
 * @param first_effect
 *        first priority effect
 * @param second_effect
 *        second priority effect
 * @param third_effect
 *        last priority effect
 * @param max_cost
 *        max button automa can use
 * @param bonus_button
 *        bonus button automa earn
 * 
 * */
public record Card(Card_Effect first_effect, Card_Effect second_effect, Card_Effect third_effect, int max_cost, int bonus_button){

  private int patch_not_overtake(Card_Effect effect, HashMap<Integer,Patch>list, int index,int distance){
    if(distance > 0) {
      for(int i = 0; i < 3; i++) {
        if(index >= list.size()) {
          index = 0;
        }
        if(list.get(index).move() < distance && list.get(index).button() <= max_cost) {
          return index;
        }
        index++;
      }
    }
    return -1;
  }

  private int most_button(Card_Effect effect, HashMap<Integer,Patch>list, int index){
    int max_button_index = -1;
    int max_button = 0;
    for(int i = 0; i < 3; i++) {
      if(index >= list.size()) {
        index = 0;
      }
      if(list.get(index).button_on_patch() > max_button && list.get(index).button() <= max_cost) {
        max_button_index = index;
      }
      index++;
    }
    return max_button_index;
  }

  private int largest_patch(Card_Effect effect, HashMap<Integer,Patch>list, int index){
    int max_size_index = -1;
    int max_size = 0;
    for(int i = 0; i < 3; i++) {
      if(index >= list.size()) {
        index = 0;
      }
      if(list.get(index).size()> max_size && list.get(index).button() <= max_cost) {
        max_size_index = index;
      }
      index++;
    }
    return max_size_index;
  }

  private int furthes_patch(Card_Effect effect, HashMap<Integer,Patch>list, int index){
    for(int i = 0; i < 3; i++) {
      if(index >= list.size()) {
        index = 0;
      }
      index++;
    }
    if(list.get(index).button() <= max_cost) {
      return index;
    }
    return -1;
  }

  private int use_effect(HashMap<Integer,Patch>list, int index, Card_Effect effect, int distance) {
    return switch(effect) {
    case patch_not_overtake ->
    this.patch_not_overtake(effect, list, index , distance);
    case largest_patch ->
    this.largest_patch(effect, list, index);
    case most_button ->
    this.most_button(effect, list, index);
    case furthest_patch ->
    this.furthes_patch(effect, list, index);
    };

  }

  private int use_card(HashMap<Integer,Patch>list, int index, int distance) {
    int cursor = this.use_effect(list, index, this.first_effect, distance);
    if(cursor == -1) {
      cursor = this.use_effect(list, index, this.second_effect, distance);
      if(cursor == -1) {
        cursor = this.use_effect(list, index, this.third_effect, distance);
      }
    }
    return cursor;
  }
  
  private int card_all_effect(Player_automa[] player,HashMap<Integer,Patch>list, int index,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list, GraphicsMode mode, int cursor) {
      if(cursor == -1){
        Game.moving_action(player, 1, leather_list, button_list, mode);
        player[1].earn(this.bonus_button);
      }
      else {
        player[1].add_patch(list.get(cursor));
        Patch.list_patch_remove(list, cursor);
        return cursor;
      }
    return cursor;
  }
  
  /**This function manage all cases possible want automa draw a card
   
   * @param player
   *        A array with all player
   * @param list
   *        hashMap with all patch
   * @param index
   *        neutral token
   * @param leather_list
   *        ArrayList{@literal <}Integer> with all position of Leather patch
   * @param button_list
   *        ArrayList{@literal <}Integer> with all position in the map
   * @param mode
   *        inforamtion of game
   * @return position of neutral token after use card
   * */
  public int card_action(Player_automa player[],HashMap<Integer,Patch>list, int index,
      ArrayList<Integer> leather_list, ArrayList<Integer> button_list, GraphicsMode mode) {
    ASCII.objectnull(player, list, leather_list, button_list);
    Objects.requireNonNull(mode);
    if(this.max_cost > player[1].button()) {
      Game.moving_action(player, 1, leather_list, button_list, mode);
      player[1].earn(this.bonus_button);
    }
    else {
      int cursor = this.use_card(list, index, player[0].position() - player[1].position()); 
      this.card_all_effect(player, list, index, leather_list, button_list, mode, cursor);
    }
    return index;
  }

}
