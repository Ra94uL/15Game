package Game15;

public class Board {

    private final int size = 4;
    private int[][] tiles;

    public Board(){

        tiles = new int[size][size];


    }

    public void resetToGoal(){
        int n = 1;

        for (int r = 0; r < size ; r++) {  // loopar igenom rader
            for (int c = 0; c < size; c++) {  // loppar igenom kolumner
                if (r == size -1 && c == size -1){
                    tiles[r][c] = 0;  // sista rutan = tom
                }else {
                    tiles[r][c] = n;  // annars sätt in siffran
                    n++;

                }

            }

        }

    }

    public int getAt(int r, int c){
        return tiles[r][c];
    }

    public int[] findZero(){
        for (int r = 0; r < size ; r++) {
            for (int c = 0; c < size; c++) {
                if (tiles[r][c] == 0) {
                    return new int[]{r,c};
                }

            }

        }
        return null;
    }

    public boolean isSolved(){
        int n = 1;

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (r == size -1 && c == size -1){
                    if (tiles[r][c] != 0) return false;
                }else {
                    if (tiles[r][c] != n) return false;
                    n++;
                }

            }

        }
        return true;
    }


    static void main() {
        Board b = new Board();
        b.resetToGoal();
        System.out.println(b.getAt(0, 0)); // ska skriva 0

        int[] pos = b.findZero();
        System.out.println("Tomrutan ligger på rad " + pos[0] + ", kolumn " + pos[1]);
        System.out.println("Är spelet löst? " + b.isSolved());


    }







}
