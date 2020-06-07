package main;

import controller.GameController;
import controller.ObjectController;
import gui.GameFrame;
import resourceloader.ResourceLoader;

public class StartGame {
    private static GameFrame gameFrame;
    private static ResourceLoader resourceLoader;
    public static void main(String[] args) {
        ObjectController obj = new ObjectController();
        gameFrame = new GameFrame();
        gameFrame.setVisible(true);
    }
    public static void startGame(String map){
        gameFrame.startGame(map);
        changePanel("game");
    }

    public static void changePanel(String panelName){
        if(panelName.equals("game")){
            GameController.setGameRunning(true);
        }else {
            GameController.setGameRunning(false);
        }
        gameFrame.switchPanel(panelName);
        gameFrame.setVisible(true);
    }
}
