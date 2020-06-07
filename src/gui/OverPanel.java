package gui;

import controller.ObjectController;
import main.StartGame;
import resourceloader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OverPanel extends JPanel {
    private ImageIcon img;
    private int w;
    private int h;
    private static JButton result = new JButton();
    private JLabel label;
    private JButton restart;

    public OverPanel(){
        List<String> size = ResourceLoader.getResourceLoader().getGameInfo().get("windowSize");
        img = ResourceLoader.getResourceLoader().getImageInfo().get("gameover");
        w = Integer.valueOf(size.get(0));
        h = Integer.valueOf(size.get(1));
        init();
    }

    public void init(){
        this.setLayout(null);
        img.setImage(img.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        label = new JLabel(img);
        label.setBounds(0, 0, w, h);


        ImageIcon restratImg = ResourceLoader.getResourceLoader().getImageInfo().get("restart");
        restratImg.setImage(restratImg.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH));
        restart = new JButton(restratImg);
        restart.setBounds(w/2-150, 3*h/4, 300, 100);
        restart.setBorderPainted(false);
        restart.setFocusPainted(false);
        restart.setContentAreaFilled(false);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartGame.changePanel("start");
            }
        });

        result.setFont(new Font("Times New Roman", Font.BOLD, 40));
        result.setBounds(w/2-200, 2*h/4-200, 400, 400);
        result.setHorizontalTextPosition(SwingConstants.CENTER);
        result.setVerticalTextPosition(SwingConstants.CENTER);
        result.setBorderPainted(false);
        result.setContentAreaFilled(false);

        this.add(result);
        this.add(restart);
        this.add(label);
        this.setVisible(true);
        this.setOpaque(false);
    }

    public static JButton getResultButton(){
        return result;
    }
}
