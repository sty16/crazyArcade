package model.gamecharacter;

import controller.GameMap;
import controller.ObjectController;
import gui.StartPanel;
import model.gameobject.MapBubble;
import model.gameobject.MapObject;
import model.gameobject.SuperObject;
import resourceloader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends Character {
    private ImageIcon img;
    private int moveX;    //表示人物的运动状态总共16(4, 4)种
    private int moveY;
    private boolean attack;//记录攻击状态，默认为false
    private boolean keepAttack;//记录是否为一直按着攻击键，每次按键指释放一个泡泡
    private boolean dying;
    private boolean keepDying;
    private int dyingTime;
    private int playerIndex;

    public Player(int x, int y, int w, int h, ImageIcon img) {
        super(x, y, w, h);
        this.img = img;
        moveX = 0;
        moveY = 0;
        attack = false;
        keepAttack = false;
        dead = false;
        dying = false;
        keepDying = false;
        dyingTime = 5000;
    }

    public static Player createPlayer(int i, int j, List<String> playerInfo){
        int x = j*MapObject.PIXEL_X + GameMap.getBiasX();
        int y = i*MapObject.PIXEL_Y + GameMap.getBiasY();
        int w = MapObject.PIXEL_X;
        int h = MapObject.PIXEL_Y;
        HashMap<String, ImageIcon> imageInfo = ResourceLoader.getResourceLoader().getImageInfo();
        return  new Player(x, y, w, h, imageInfo.get(playerInfo.get(0)+String.valueOf(StartPanel.playerIndex)));
    }

    @Override
    public void showObject(Graphics g) {
        if(isShowing==false){
            return;
        }
        g.drawImage(img.getImage(), getx(), gety(),getx()+getw(), gety()+geth(),
                (moveX/6)*100+27, moveY*100+43,(moveX/6)*100+72, moveY*100+99, null);
    }

    @Override
    public void move() {
        if(dying) {
            if(!keepDying){
                speed = INIT_SPEED/4;
                keepDying = true;
                img = ResourceLoader.getResourceLoader().getImageInfo().get("player"+String.valueOf(StartPanel.playerIndex)+"dying");
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if(dying){
                            dead = true;
                        }
                    }
                };
                timer.schedule(task, 5000);
            }
            dyingTime = dyingTime - 20;
        }else{
            speed = INIT_SPEED;
            keepDying = false;
            dyingTime = 5000;
        }
        int tempx = getx();
        int tempy = gety();
        switch (moveType){
            case TOP: tempy-=speed;break;
            case LEFT: tempx-=speed;break;
            case RIGHT: tempx+=speed;break;
            case DOWN: tempy+=speed;break;
            case STOP:  break;
            default:
                break;
        }
        boolean collision1 = collisionDetect(tempx,tempy, ObjectController.getObjController().getMap().get("obstacle"));
        boolean collision2 = collisionDetect(tempx, tempy, ObjectController.getObjController().getMap().get("fragility"));
//        boolean collision3 = collisionDetect(tempx, tempy, ObjectController.getObjController().getMap().get("gameprops"));

        if(collision1&&collision2){
            setx(tempx);
            sety(tempy);
        }
    }

    private boolean collisionDetect(int tx, int ty, List<SuperObject> objects){
    //没有碰撞返回true
        int threshold = 25;
        Rectangle playerRect = new Rectangle(tx, ty, getw()*4/5, geth()*4/5);
        for(SuperObject so:objects){
            Rectangle objRect = new Rectangle(so.getx(), so.gety(), so.getw()*3/4, so.geth()*3/4);
            if(playerRect.intersects(objRect)){
                return false;
            }
        }
        return true;
    }

    public void updateImage(){
        if(moveType == MoveType.STOP){
            return;
        }
        moveX ++;
        if(moveX>=24){
            moveX = 0;
        }
        switch (moveType){
            case TOP:
                moveY = 3;break;
            case DOWN:
                moveY = 0;break;
            case LEFT:
                moveY = 1;break;
            case RIGHT:
                moveY = 2;break;
            default:
                break;
        }
    }

    public void plantBubble(){
        int i = ObjectController.getPosIndex(getx()+getw()/2, gety()+geth()/2).get(0);
        int j = ObjectController.getPosIndex(getx()+getw()/2, gety()+geth()/2).get(1);
        GameMap gameMap = ObjectController.getObjController().getGameMap();
        if(attack && !dead && !gameMap.isBubble(i, j) && bubbleNum > 0){
            List<SuperObject> bubble = ObjectController.getObjController().getMap().get("bubble");
            List<String> bubbleData = ResourceLoader.getResourceLoader().getMapObjectInfo().get("90");
            bubble.add(MapBubble.createMapBubble(i, j,  bubbleData, getBubblePower()));
            attack = false;
            bubbleNum--;
        }
    }

    @Override
    public void update() {
        if(!dead){
            move();
            plantBubble();
            updateImage();
        }
    }

    @Override
    public void destroy() {

    }

    public int getMoveX() {
        return moveX;
    }

    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    public int getMoveY() {
        return moveY;
    }

    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public boolean isKeepAttack() {
        return keepAttack;
    }

    public void setKeepAttack(boolean keepAttack) {
        this.keepAttack = keepAttack;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setDying(boolean dying){
        this.dying = dying;
    }

    public void setKeepDying(boolean keepDying){
        this.keepDying = keepDying;
    }

    public boolean isDying(){
        return dying;
    }

    public int getDyingTime(){
        return dyingTime;
    }

    public void setNormalImg(){
        img = ResourceLoader.getResourceLoader().getImageInfo().get("player"+String.valueOf(StartPanel.playerIndex));
    }
}
