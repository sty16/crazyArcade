package controller;

public class GameController {
    private static boolean isRunning = false;

    public static boolean isGameRunning(){
        return isRunning;
    }

    public static void setGameRunning(boolean state){
        GameController.isRunning = state;
    }
}
