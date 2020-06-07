package model.gameobject;

import controller.GameMap;

import javax.swing.*;
import java.awt.*;

public class MapObject extends SuperObject {

    private ImageIcon img;
    public static final int PIXEL_X = 48;
    public static final int PIXEL_Y = 48;
    private int sx1, sy1, sx2, sy2;

    public MapObject(int i, int j, ImageIcon img, int sx1, int sy1, int sx2, int sy2, int scaleX,int scaleY){
        super((j+1-scaleX)*PIXEL_X + GameMap.getBiasX(),(i+1-scaleY)*PIXEL_Y + GameMap.getBiasY(),
                PIXEL_X*scaleX, PIXEL_Y*scaleY);
        this.img = img;
        this.sx1=sx1;
        this.sy1=sy1;
        this.sx2=sx2;
        this.sy2=sy2;
    }


    public void showObject(Graphics g){
        g.drawImage(img.getImage(), getx(), gety(), getx()+getw(), gety()+geth(),
                sx1, sy1, sx2, sy2, null);
    }
    public void move(){};

    public void destroy(){};

    public ImageIcon getImg(){
        return  img;
    }

    public void setImg(ImageIcon img){
        this.img = img;
    }
}
