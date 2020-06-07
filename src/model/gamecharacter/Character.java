package model.gamecharacter;

import model.gameobject.SuperObject;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

//游戏中玩家人物的父类
public class Character extends SuperObject{
    public final static int INIT_SPEED = 4;
    //设置为protected子类可访问
    protected boolean dead;//记录是否存活
    protected MoveType moveType;
    protected int speed;//移动速度
    protected int speedItemCount;//生效中的加速卡数量
    protected int changeDirectionCount;//生效中的方向改变卡数量
    protected int stopitemCount;//生效中的其他玩家停止卡数量
    protected int bubblePower;//炮弹威力
    protected int bubbleNum;//记录玩家已经放了多少个炸弹
    protected int bubbleLargest;//玩家最多可以放多少个炸弹，初始值为1
    protected int powerLargest;
    public int score;
    protected int heathPoint;//玩家生命值
    protected boolean isUnstoppable;//玩家是否获得无敌
    protected int unstoppableCount;//无敌卡数量
    protected int magicBubbleCount;
    protected int magicPowerCount;
    protected int magicSaveCount;
    protected boolean isShowing;//是否要展示元素

    public Character(int x, int y, int w, int h){
        super(x, y, w, h);
        moveType = MoveType.STOP;
        speedItemCount = 0;
        changeDirectionCount=0;
        stopitemCount=0;
        bubblePower = 1;
        bubbleNum = 5;
        magicBubbleCount = 0;
        magicPowerCount  = 0;
        magicSaveCount = 0;
        bubbleLargest = 10;
        powerLargest = 10;
        heathPoint = 1;
        isUnstoppable = false;
        unstoppableCount = 0;
        isShowing = true;
        speed = INIT_SPEED;
        score = 0;
        dead = false;
    }

    public void changeSpeed(double times, int lastTime){
        speed = (int)(speed*times);
        Timer timer = new Timer(true);
        speedItemCount++;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                speedItemCount--;
                if(speedItemCount==0) {
                    speed = INIT_SPEED;
                }
            }
        };
        timer.schedule(task, lastTime*1000);
        //修改一段时间的移动速度
    }


    public void setHeathPoint(int change){
        heathPoint += change;
        if(heathPoint<0){
            setDead(dead);
        }
    }

    public void setDead(boolean dead){
        this.dead=dead;
    }

    public boolean isDead() {
        return dead;
    }

    public void bubbleAddPower() {
        bubblePower++;
    }

    public MoveType getMoveType(){
        return moveType;
    }

    public void setMoveType(MoveType moveType){
        this.moveType=moveType;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeedItemCount() {
        return speedItemCount;
    }

    public void setSpeedItemCount(int speedItemCount) {
        this.speedItemCount = speedItemCount;
    }

    public int getBubblePower() {
        return bubblePower;
    }

    public void setBubblePower(int bubblePower) {
        if(bubblePower<=powerLargest){
            this.bubblePower = bubblePower;
        }else {
            bubblePower = powerLargest;
        }
    }

    public int getBubbleNum() {
        return bubbleNum;
    }

    public void setBubbleNum(int bubbleNum) {
        if(bubbleNum <= bubbleLargest){
            this.bubbleNum = bubbleNum;
        }else {
            this.bubbleNum = bubbleLargest;
        }
    }

    public int getBubbleLargest() {
        return bubbleLargest;
    }

    public void setBubbleLargest(int bubbleLargest) {
        this.bubbleLargest = bubbleLargest;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getChangeDirectionCount() {
        return changeDirectionCount;
    }

    public void setChangeDirectionCount(int changeDirectionCount) {
        this.changeDirectionCount = changeDirectionCount;
    }

    public int getStopitemCount() {
        return stopitemCount;
    }

    public void setStopitemCount(int stopitemCount) {
        this.stopitemCount = stopitemCount;
    }

    public int getHeathPoint() {
        return heathPoint;
    }

    public boolean isisUnstoppable() {
        return isUnstoppable;
    }

    public void setisUnstoppable(boolean unstoppable) {
        this.isUnstoppable = unstoppable;
    }

    public void setMagicBubbleCount(int num){
        magicBubbleCount =  num;
    }

    public int getMagicBubbleCount() {return magicBubbleCount;}

    public void setMagicPowerCount(int num){
        magicPowerCount = num;
    }

    public int getMagicPowerCount() {return magicPowerCount;}

    public void setMagicSaveCount(int num) {magicSaveCount = num;}

    public int getMagicSaveCount() {return  magicSaveCount;}
    @Override
    public void showObject(Graphics g) {}

    @Override
    public void move() {}

    @Override
    public void destroy() {}


}
