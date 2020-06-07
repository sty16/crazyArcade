package gui;

import controller.GameController;
import resourceloader.ResourceLoader;
import thread.GameKeyListener;
import thread.GameThread;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameFrame extends JFrame {
    private JPanel contentPane;
    private StartPanel startPanel;
    private GamePanel gamePanel;
    private OverPanel overPanel;
    private static GameThread gameThread;


    public GameFrame(){
        init();
    }

    private void init(){
        this.setTitle("PaoPaoTang");
        List<String> size = ResourceLoader.getResourceLoader().getGameInfo().get("windowSize");
        this.setSize(Integer.valueOf(size.get(0)), Integer.valueOf(size.get(1)));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);        //将窗口设置于屏幕中央
        CardLayout cardLayout = new CardLayout();
        this.contentPane = new JPanel();
        this.setContentPane(contentPane);
        this.contentPane.setLayout(cardLayout);
        this.startPanel = new StartPanel();
        this.contentPane.add("start",startPanel);
        this.overPanel = new OverPanel();
        this.contentPane.add("over", overPanel);
        ((CardLayout)this.contentPane.getLayout()).show(contentPane,"start");
        this.setVisible(true);
    }

    public void startGame(String map){
        gameThread = new GameThread(map);
        gameThread.start();
        gamePanel = new GamePanel();
        contentPane.add("game", gamePanel);
        new Thread(gamePanel).start();
    }

    public void switchPanel(String panelName){
        ((CardLayout)this.contentPane.getLayout()).show(contentPane, panelName);
        if(panelName == "game"){
            gamePanel.requestFocus();
        }
    }

    public static GameThread getGameThread(){
        return gameThread;
    }
}
