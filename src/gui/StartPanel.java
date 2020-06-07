package gui;

import controller.ObjectController;
import main.StartGame;
import resourceloader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class StartPanel extends JPanel {
    public static int playerIndex;
    private JLabel startLabel;
    private JLabel introLabel;
    private JButton gameSatrtButton;
    private JButton gameMiddleBtn;
    private JButton gameAdvancedBtn;
    private JButton introButton;
    private JLabel selectPlayer;
    private JButton selectButton;
    private JButton changePlayer;
    private JButton ensureButton;
    ImageIcon img;

    public StartPanel(){
        init();
    }

    private void init(){
        playerIndex = 1;
        List<String> size = ResourceLoader.getResourceLoader().getGameInfo().get("windowSize");
        int width = Integer.valueOf(size.get(0));
        int height = Integer.valueOf(size.get(1));

        ImageIcon startBg = ResourceLoader.getResourceLoader().getImageInfo().get("startBackGround");
        startBg.setImage(startBg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        ImageIcon intro = ResourceLoader.getResourceLoader().getImageInfo().get("introduce");
        intro.setImage(intro.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH));
        startLabel = new JLabel(startBg);
        introLabel = new JLabel(intro);
        startLabel.setBounds(0,0, width, height);
        introLabel.setBounds(width/2, height/3,400,400);
        introLabel.setVisible(false);

        img = ResourceLoader.getResourceLoader().getImageInfo().get("player" + String.valueOf(playerIndex)+"img");
        img.setImage(img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        selectPlayer = new JLabel(img);
        selectPlayer.setBounds(width/2, height/3, 200, 200);
        selectPlayer.setVisible(false);

        changePlayer = new JButton();
        ImageIcon change = ResourceLoader.getResourceLoader().getImageInfo().get("change");
        change.setImage(change.getImage().getScaledInstance(160, 50, Image.SCALE_SMOOTH));
        changePlayer.setIcon(change);
        changePlayer.setBorderPainted(false);
        changePlayer.setContentAreaFilled(false);
        changePlayer.setFocusPainted(false);
        changePlayer.setBounds(width/2-60, height/3+200, 160, 50);
        changePlayer.addActionListener(e -> changePlayerActionPerformed(e));
        changePlayer.setVisible(false);


        ensureButton = new JButton();
        ImageIcon ensure = ResourceLoader.getResourceLoader().getImageInfo().get("ensure");
        ensure.setImage(ensure.getImage().getScaledInstance(160,50, Image.SCALE_SMOOTH));
        ensureButton.setIcon(ensure);
        ensureButton.setBorderPainted(false);
        ensureButton.setFocusPainted(false);
        ensureButton.setContentAreaFilled(false);
        ensureButton.setBounds(width/2+110, height/3+200, 160, 50);
        ensureButton.setVisible(false);
        ensureButton.addActionListener(e -> ensureButtonActionPerformed(e));

        gameSatrtButton = new JButton();
        gameSatrtButton.setIcon(ResourceLoader.getResourceLoader().getImageInfo().get("primaryBtn"));
        gameSatrtButton.setBounds(width/6, height/3, 240, 80);
        gameSatrtButton.setBorderPainted(false);
        gameSatrtButton.setFocusPainted(false);
        gameSatrtButton.setContentAreaFilled(false);
        gameSatrtButton.addActionListener(e -> gameStartActionPerformed(e));

        gameMiddleBtn = new JButton();
        gameMiddleBtn.setIcon(ResourceLoader.getResourceLoader().getImageInfo().get("middleBtn"));
        gameMiddleBtn.setBounds(width/6, height/3 + 100, 240, 80);
        gameMiddleBtn.setBorderPainted(false);
        gameMiddleBtn.setFocusPainted(false);
        gameMiddleBtn.setContentAreaFilled(false);
        gameMiddleBtn.addActionListener(e -> gameMiddleBtnActionPerformed(e));

        gameAdvancedBtn = new JButton();
        gameAdvancedBtn.setIcon(ResourceLoader.getResourceLoader().getImageInfo().get("advancedBtn"));
        gameAdvancedBtn.setBounds(width/6, height/3 + 200, 240, 80);
        gameAdvancedBtn.setBorderPainted(false);
        gameAdvancedBtn.setFocusPainted(false);
        gameAdvancedBtn.setContentAreaFilled(false);
        gameAdvancedBtn.addActionListener(e -> gameAdvancedBtnActionPerformed(e));

        introButton = new JButton();
        introButton.setIcon(ResourceLoader.getResourceLoader().getImageInfo().get("introBtn"));
        introButton.setBounds(width/6, height/3+300,240,80);
        introButton.setBorderPainted(false);
        introButton.setFocusPainted(false);
        introButton.setContentAreaFilled(false);
        introButton.addActionListener(e -> introBtnActionPerformed(e));

        selectButton = new JButton();
        selectButton.setIcon(ResourceLoader.getResourceLoader().getImageInfo().get("selectBtn"));
        selectButton.setBounds(width/6, height/3+400, 240, 80);
        selectButton.setBorderPainted(false);
        selectButton.setFocusPainted(false);
        selectButton.setContentAreaFilled(false);
        selectButton.addActionListener(e -> selectButtonActionPerformed(e));


        this.setLayout(null);
        this.add(gameSatrtButton);
        this.add(gameMiddleBtn);
        this.add(gameAdvancedBtn);
        this.add(introButton);
        this.add(selectButton);
        this.add(selectPlayer);
        this.add(changePlayer);
        this.add(ensureButton);
        this.add(introLabel);
        this.add(startLabel);
        this.setVisible(true);
        this.setOpaque(true);
    }
    private void gameStartActionPerformed(ActionEvent e){
        StartGame.startGame("Simple");
    }

    private void gameMiddleBtnActionPerformed(ActionEvent e){
        System.out.println("middle");
        StartGame.startGame("Middle");
    }

    private void gameAdvancedBtnActionPerformed(ActionEvent e){
        StartGame.startGame("Advanced");
    }

    private void introBtnActionPerformed(ActionEvent e){
        if(introLabel.isVisible()){
            introLabel.setVisible(false);
        }else {
            introLabel.setVisible(true);
        }
    }

    private void selectButtonActionPerformed(ActionEvent e){
        if(changePlayer.isVisible()){
            changePlayer.setVisible(false);
        }else {
            changePlayer.setVisible(true);
        }
        if(ensureButton.isVisible()){
            ensureButton.setVisible(false);
        }else {
            ensureButton.setVisible(true);
        }
        if(selectPlayer.isVisible()){
            selectPlayer.setVisible(false);
        }else {
            selectPlayer.setVisible(true);
        }
    }

    private void changePlayerActionPerformed(ActionEvent e){
        playerIndex = playerIndex + 1;
        if(playerIndex > 2){
            playerIndex = 1;
        }
        img = ResourceLoader.getResourceLoader().getImageInfo().get("player" + String.valueOf(playerIndex)+"img");
        img.setImage(img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        selectPlayer.setIcon(img);
    }

    private void ensureButtonActionPerformed(ActionEvent e){
        changePlayer.setVisible(false);
        ensureButton.setVisible(false);
        selectPlayer.setVisible(false);
    }
}

