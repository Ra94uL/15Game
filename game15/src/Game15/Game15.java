package game15;

import javax.swing.*;

//Startklass för 15-spelet
public class Game15 {
    public static void main(String[] args) {
        GameModel model = new GameModel(4, 4);
        GameView view = new GameView(model);
    }
}