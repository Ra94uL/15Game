package Game15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Håller spelbräde och logik för flyttning av brickor
public class GameModel {
    private final int rows;
    private final int cols;
    private int[] board;        // värden 1..15, 0 = tom ruta
    private int emptyIndex;     // index för tom ruta
    private int moves;          // antal drag

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new int[rows * cols];
        resetToSolved();
    }
    // Lägger brickorna i ordning (1..15, tom sist).
    public void resetToSolved() {
        for (int i = 0; i < board.length - 1; i++) {
            board[i] = i + 1;
        }
        board[board.length - 1] = 0;
        emptyIndex = board.length - 1;
        moves = 0;
    }

    //Blandar brickorna slumpmässigt.
    public void shuffle() {
        List<Integer> list = new ArrayList<>();
        for (int v : board) list.add(v);
        Collections.shuffle(list);
        for (int i = 0; i < board.length; i++) {
            board[i] = list.get(i);
            if (board[i] == 0) emptyIndex = i;
        }
        moves = 0;
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getSize() { return board.length; }
    public int getValueAt(int index) { return board[index]; }
    public int getMoves() { return moves; }

    //Försöker flytta en bricka om den ligger intill den tomma platsen.
    public boolean tryMove(int index) {
        if (isAdjacent(index, emptyIndex)) {
            board[emptyIndex] = board[index];
            board[index] = 0;
            emptyIndex = index;
            moves++;
            return true;
        }
        return false;
    }

    // Kollar om två rutor ligger bredvid varandra (inte diagonalt). a och b är index i arrayen som representerar spelplanen.
    // Logiken delas upp i två fall
    // om rutan ligger på samma rad eller i samma kolumn. Om skillnaden bara är 1, så ligger de bredvid varandra

    private boolean isAdjacent(int a, int b) {
        int rowA = a / cols;
        int colA = a % cols;
        int rowB = b / cols;
        int colB = b % cols;

        // Samma rad, men kolumnskillnaden är 1
        boolean horizontal = (rowA == rowB) && (Math.abs(colA - colB) == 1);

        // Samma kolumn, men radskillnaden är 1
        boolean vertical = (colA == colB) && (Math.abs(rowA - rowB) == 1);

        return horizontal || vertical;
    }

    // Kollar om spelet är löst.
    public boolean isSolved() {
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i] != i + 1) return false;
        }
        return board[board.length - 1] == 0;
    }
}
