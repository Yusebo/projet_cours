package Gamedata;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;



/**The object Patch 
 * @param piece
 *        form of patch
 * @param move
 *        information of move 
 * @param button
 *        price of patch
 * */
public record Patch(int [][]piece, int move , int button) {

  /** Constructor of Patch (to avert value NULL)
   * 
   * */
  public Patch{
    Objects.requireNonNull(piece);
    if(move < 0){
      throw new IllegalArgumentException();
    }
    if(button < 0){
      throw new IllegalArgumentException();
    }
  }

  /** change the double array to mirror it
   * */
  public void mirror(){//or reverse function with utility to return a piece
    int permutation;
    for(int i = 0; i < this.piece.length; i++ ) {
      for(int j= 0; j < this.piece[i].length/2; j++){
        permutation = this.piece[i][j];
        this.piece[i][j] = this.piece[i][this.piece[i].length - 1 - j] ;
        this.piece[i][this.piece[i].length - 1 - j] = permutation;
      }
    }
  }

  /** Rotate a patch to the right
   * 
   * @return return a Patch with change
   * */  
  public Patch right_rotation(){
    int[][] new_piece = new int[piece[0].length][piece.length];
    for(int i = 0; i < piece.length; i++ ) {
      for(int j= 0; j < piece[i].length; j++){
        new_piece[j][piece.length - 1 - i] = piece[i][j];
      }
    }
    return new Patch(new_piece, move, button);
  }

  /** Rotate a patch to the left
   * 
   * @return return a Patch with change
   * */  
  public Patch left_rotation(){
    int[][] new_piece = new int[piece[0].length][piece.length];
    for(int i = 0; i < piece.length; i++ ) {
      for(int j= 0; j < piece[i].length; j++){
        new_piece[piece[i].length - 1 - j][i] = piece[i][j];
      }
    }
    return new Patch(new_piece, move, button);
  }

  /**Create a leather_patch
   * @return return a Patch with 1 on 1 with 0 in other parameter
   * */
  public static Patch leather_patch() {
    return new Patch(new int[][]{{1}}, 0, 0) ;
  }

  /**Create a hash map with all patch on the step 1
   * @param list 
   *        HashMap with all patch
   * @return Return a hash map with position and patch
   * */
  public static HashMap<Integer,Patch> list_patch_step1(HashMap<Integer,Patch> list){
    Objects.requireNonNull(list);
    int[][] array  = new int[][] {{ 1 , 1 } , {1 ,1}  };
    int[][] array2  = new int[][] {{ 1 , 3 } , {1 ,1}  };
    for(int i = 0; i < 40; i++) {
      if(i % 2 == 0) {
        list.put(i, new Patch(array2 , 4, 3));
      }
      else {
        list.put(i, new Patch(array, 2, 2));
      }
    }
    return list;
  }


  /**The function remove a patch a change hash map so that the hash map remains a continuous sequence
   * Exemple: in the Hash map with key from 1 to 5
   * if we remove key 2
   * with this function key 3 become 2, 4 become 3, 5 become 4
   * 
   * @param list 
   *            HashMap{@literal <}Integer,Patch> which you want remove a element
   * @param index
   *            key for remove
   * */
  public static void list_patch_remove(HashMap<Integer,Patch> list, int index){
    for(int i = index; i < list.size(); i++) {
      list.put(i, list.get(i + 1));
    }
    list.remove(list.size() - 1);
  }


  /**The function transform piece to String
   *  @return Return piece.ToSrring()
   * */
  public String patchToString() {
    var builder = new StringBuilder();
    for(int i = 0; i < piece.length; i++ ) {
      for(int j = 0; j < piece[i].length; j++){
        if(piece[i][j] == 1) {
          builder.append('*');
        }
        else if(piece[i][j] == 3) {
          builder.append('x');
        }else {
          builder.append(' ');
        }
      }
      builder.append('\n');
    }
    return builder.toString();
  }

  @Override
  public String toString() {
    var builder = new StringBuilder();
    builder.append(this.patchToString());
    builder.append("move  :");
    builder.append(move + "\n");
    builder.append("button :");
    builder.append(button + "\n");  
    return builder.toString();

  }

  /**The function create a HashMap with 33 patch.
   * 
   * @param list 
   *        HashMap empty
   * 
   * @return A HashMap with 33 patch
   * */
  public static HashMap<Integer,Patch> list_patch(HashMap<Integer,Patch> list) {
    Objects.requireNonNull(list);
    ArrayList<Patch> array = null;
    try {
      array = Patch.readPatchfile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    int i = 0;
    for(var patch : array) {
      list.put(i, patch);
      i++;
    }
    return list;
  }

  /**Read line when they are a move or button
   * @param reader
   *         BufferedReader for read next line
   * @return 0 if move or button isn't exist
   * @throws IOException
   *          the file is prepared beforehand 
   * */
  private static int move_or_button_patch(BufferedReader reader) throws IOException {
    String line;
    if((line = reader.readLine()) != null){
      return Integer.parseInt(line);
    }
    return 0;
  }

  /**The function build a patch when they read a BufferedReader
   * @param x 
   *        length of piece
   * @param y 
   *        length of piece[x]
   * @param reader
   *         BufferedReader for read next line
   * @return A patch
   * @throws IOException
   *          the file is prepared beforehand 
   * @throws NumberFormatException
   *          the file is prepared beforehand there the number are in a good Format
   * */
  private static Patch build_patch(int x, int y, BufferedReader reader) throws NumberFormatException, IOException {
    var piece = new int[x][y];
    String line;
    for(int j = 0; j < x; j++) {
      if((line = reader.readLine()) != null){
        var line_patch = line.split(","); 
        for(int i = 0; i < line_patch.length; i++) {
          piece[j][i] = Integer.parseInt(line_patch[i]);
        }
      }
    }
    var move = move_or_button_patch(reader);    
    var button = move_or_button_patch(reader);
    return new Patch(piece, move, button);
  }

  /**The function read a file and create a arraylist after that the arraylist are shuffle
   * 
   *  @return the arraylist in the file
   *  @throws IOException
   *          the file is prepared beforehand
   * */
  private static ArrayList<Patch> readPatchfile() throws IOException{
    var arraylist = new ArrayList<Patch>();
    try(var reader = Files.newBufferedReader(Path.of("patch"))){
      String line;
      while ((line = reader.readLine()) != null) {
        var size = line.split("x");
        int x = Integer.parseInt(size[0]);
        int y = Integer.parseInt(size[1]);
        arraylist.add(Patch.build_patch(x, y, reader));

      }
    }
    Collections.shuffle(arraylist);
    return arraylist;
  }
  
  /**The function count all square full
   * @return the number of square full
   * */
  public int size() {
    int size = 0;
    for(int i = 0; i < piece.length; i++) {
      for(int j = 0; j < piece[i].length; j++) {
        if(piece[i][j] % 2 == 1) {
          size++;
        }
      }
    }
    return size;
  }
  
  /**The function count all square full
   * @return the number of square full
   * */
  public int button_on_patch() {
    int button = 0;
    for(int i = 0; i < piece.length; i++) {
      for(int j = 0; j < piece[i].length; j++) {
        if(piece[i][j] % 2 == 3) {
          button++;
        }
      }
    }
    return button;
  }

}
