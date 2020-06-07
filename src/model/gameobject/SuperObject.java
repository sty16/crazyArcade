package model.gameobject;

import java.awt.*;

public abstract class SuperObject {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean alive;

    public SuperObject(int x, int y, int w, int h){
        this.x=x;
        this.y=y;
        this.width=w;
        this.height=h;
        this.alive=true;
    };

    public abstract void move();
    public abstract void showObject(Graphics g);
    public abstract void destroy();

    public void update(){
        move();
        destroy();
    }

    public boolean collision(SuperObject so){
        Rectangle r1 = new Rectangle(x, y, width, height);
        Rectangle r2 = new Rectangle(so.x, so.y, so.width, so.height);
        return r1.intersects(r2);
    }

    public void setCoordinate(int x, int y, int w, int h){
        this.x=x;
        this.y=y;
        this.width=w;
        this.height=h;
    }
    public int gettopbound() {
        return y;
    }

    public int getleftbound() {
        return x;
    }

    public int getrightbound() {
        return x+width;
    }

    public int getbottombound() {
        return y+height;
    }

    public int getx() {
        return x;
    }
    public void setx(int x) {
        this.x = x;
    }
    public int gety() {
        return y;
    }
    public void sety(int y) {
        this.y = y;
    }
    public int getw() {
        return width;
    }
    public void setw(int w) {
        this.width = w;
    }
    public int geth() {
        return height;
    }
    public void seth(int h) {
        this.height = h;
    }
    public boolean isalive() {
        return alive;
    }
    public void setalive(boolean alive) {
        this.alive = alive;
    }

}
