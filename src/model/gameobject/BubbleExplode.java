package model.gameobject;

import controller.GameMap;
import controller.ObjectController;
import resourceloader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.sql.Struct;
import java.util.Timer;
import java.util.TimerTask;

public class BubbleExplode extends SuperObject {
    private static ImageIcon imgCenter = ResourceLoader.getResourceLoader().getImageInfo().get("explodeCenter");
    private static ImageIcon imgUp = ResourceLoader.getResourceLoader().getImageInfo().get("explodeUp");
    private static ImageIcon imgDown = ResourceLoader.getResourceLoader().getImageInfo().get("explodeDown");
    private static ImageIcon imgLeft = ResourceLoader.getResourceLoader().getImageInfo().get("explodeLeft");
    private static ImageIcon imgRight = ResourceLoader.getResourceLoader().getImageInfo().get("explodeRight");

    private int power;
    private int upRange;
    private int downRange;
    private int leftRange;
    private int rightRange;

    public BubbleExplode(int x, int y, int w, int h, int power){
        super(x, y, w, h);
        this.power = power;
        setExplodeRange();
    }

    public static BubbleExplode createBubbleExplode(int x, int y, int power){
        return new BubbleExplode(x, y, MapObject.PIXEL_X, MapObject.PIXEL_Y, power);
    }

    private int getExplodeRange(int i, int j, String direction){
        int di = 0;
        int dj = 0;
        switch (direction){
            case "up" :di = -1;break;
            case "down":di = 1;break;
            case "left":dj = -1;break;
            case "right":dj = 1;break;
            default:break;
        }
        GameMap gameMap = ObjectController.getObjController().getGameMap();
        int range = 0;
        for(int k = 0;k < power; k++){
            i += di;
            j += dj;
            if(gameMap.outOfMap(i, j) || gameMap.isObstacle(i, j)){
                break;
            }else {
                range++;
                if(gameMap.isFragility(i, j)){
                    break;
                }
            }
        }
        return range;
    }

    public void setExplodeRange(){
        int i = ObjectController.getPosIndex(getx(), gety()).get(0);
        int j = ObjectController.getPosIndex(getx(), gety()).get(1);
        upRange = Math.min(getExplodeRange(i, j, "up"), power);
        downRange = Math.min(getExplodeRange(i, j, "down"), power);
        leftRange = Math.min(getExplodeRange(i, j, "left"), power);
        rightRange = Math.min(getExplodeRange(i, j, "right"), power);
    }

    @Override
    public void move() {

    }

    @Override
    public void showObject(Graphics g) {
        g.drawImage(imgCenter.getImage(), getx(), gety(), getx()+MapObject.PIXEL_X, gety()+MapObject.PIXEL_Y,
        0, 0, 32, 32, null);
        g.drawImage(imgUp.getImage(), getx(), gety()-upRange*MapObject.PIXEL_Y, getx()+MapObject.PIXEL_X, gety(),
                0,0, 32, 64, null);
        g.drawImage(imgDown.getImage(), getx(), gety()+MapObject.PIXEL_Y, getx()+MapObject.PIXEL_X, gety()+(downRange+1)*MapObject.PIXEL_Y,
                0,0,32,64, null);
        g.drawImage(imgLeft.getImage(), getx()-leftRange*MapObject.PIXEL_X, gety(), getx(), gety()+MapObject.PIXEL_Y,
                0, 0, 64, 32, null);
        g.drawImage(imgRight.getImage(), getx()+MapObject.PIXEL_X, gety(), getx()+(rightRange+1)*MapObject.PIXEL_X, gety()+MapObject.PIXEL_Y,
                0, 0, 64, 32, null);
    }

    @Override
    public void destroy() {
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setalive(false);
            }
        };
        timer.schedule(task, 500);
    }

    @Override
    public boolean collision(SuperObject so) {
        int fineTuning=5;
        //防止误伤加入微调。
        Rectangle explodeHorizontal = new Rectangle(getx()-leftRange*MapObject.PIXEL_X+fineTuning,
                gety()+fineTuning, (leftRange+rightRange+1)*MapObject.PIXEL_X-fineTuning,MapObject.PIXEL_Y-fineTuning);
        Rectangle explodeVertical = new Rectangle(getx()+fineTuning, gety()-upRange*MapObject.PIXEL_Y+fineTuning,
                MapObject.PIXEL_X-fineTuning, (upRange+downRange+1)*MapObject.PIXEL_Y-fineTuning);
        Rectangle obj = new Rectangle(so.getx()+fineTuning, so.gety()+fineTuning, so.getw()-fineTuning, so.geth()-fineTuning);
        boolean horizCrash = explodeHorizontal.intersects(obj);
        boolean vertiCrash = explodeVertical.intersects(obj);
        return (horizCrash || vertiCrash);
    }
}
