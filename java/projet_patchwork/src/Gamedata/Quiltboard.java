package Gamedata;

/**The class create Quiltboard and function linked
 * */
public class Quiltboard{
  private int [][]board;
  
  /**A constructor of Quiltboard object
   * */
  public Quiltboard() {
    this.board = new int[9][9];
    for(int i = 0; i < 9; i++){
      for(int j = 0; j < 9; j++) {
        this.board[i][j] = 0;
      }
    }
  }

  /**the function copy a double array
   * @param board
   *        a array[][] you want copy
   * @return a copy
   * */
  private int[][] copy_board(int[][]board){
    var copy_board = new int[9][9];
    for(int i = 0; i < this.board.length; i++){
      for(int j = 0; j < this.board[0].length; j++) {
        copy_board[i][j] = board[i][j];
      }
    }
    return copy_board;
  }

  /**the function place a patch in a quiltboard
   * @param patch
   *        patch you will place
   * @param x
   *        line position 
   * @param y
   *        column position
   * @return true if patch is placed else false if there is a problem
   * */
  public boolean place(Patch patch, int x, int y){
    var fake_board =  copy_board(this.board);
    if(this.board.length >= (patch.piece().length + y) && 
        this.board[0].length >= (patch.piece()[0].length + x)) {
      for(int i = 0; i < patch.piece().length ; i++) {
        for(int j = 0; j < patch.piece()[0].length; j++) {
          fake_board[y + i][x + j] += patch.piece()[i][j];
        }	
      }
    }
    else {
      return false;
    }
    if(!verification_board(fake_board)){
      return false;
    }
    this.board = fake_board;
    return true;
  }

  /**Watch if the quiltboard have a patch which overlap a other patch
   * @param board
   *        The board which you will verified.
   * @return true if nothing overlap in board
   * */
  private static boolean verification_board(int[][] board) {
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++) {
        if(board[i][j] % 2 == 0 && board[i][j] != 0) {
          return false;	
        }	
      }
    }
    return true;
  }
  
  /**The function count number of button in the map
   * @return Return number of button in quiltboard
   * */
  public int button_in_quiltboard() {
    int button = 0;
    for(int i = 0; i < board.length; i++) {
      for(int j = 0; j < board[i].length; j++) {
        if(this.board[i][j] == 3) {
          button++;
        }
      }
    }
    return button;
  }
  
  /**The function watch if a board have a board 7 by 7 is full
   * @return true if board have a board 7 by 7 is full
   * */
  public boolean test_board7x7() {
    int test = 1;
    for(int l = 0; l < 3; l++){
      for(int k = 0; k < 3; k++){
        test = 1;
        for(int i = 0; i < 7 ; i++){
          for(int j = 0; j < 7 ; j++) {
            if(board[i + k][j + l] % 2 == 0 ) {
              test = 0;
            } 
          }
        }
        if(test == 1) {
          return true;
        }
      }
    }
    return false;
  }

  /** The function count the number area void in quiltboard 
   * @return the number area void in quiltboard 
   * */
  public int patch_not_placed(){
    int not_placed = 0;
    for(int i = 0; i < board.length ; i++) {
      for(int j = 0; j < board[i].length; j++) {
        if(board[i][j] % 2 == 0) {}
        not_placed ++;
      } 
    }
    return not_placed;
  }

  @Override
  public String toString() {
    var builder = new StringBuilder();
    for(int i = 0; i < this.board.length; i++ ) {
      for(int j = 0; j < this.board[i].length; j++){
        if(this.board[i][j] == 1) {
          builder.append('*');
        }
        else if(this.board[i][j] == 3) {
          builder.append('x');
        }else {
          builder.append('o');
        }
      }
      builder.append('\n');
    }  
    return builder.toString();	  
  }

  /**Information of quiltboard
   * @return quiltboard
   * */
  public int[][] board() {
    return this.board;
  }
}