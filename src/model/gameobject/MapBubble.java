package model.gameobject;

import controller.ObjectController;
import resourceloader.ResourceLoader;

import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MapBubble extends MapObject{
    private int power;
    public MapBubble(int i, int j, ImageIcon img, int sx1, int sy1, int sx2, int sy2, int scaleX, int scaleY, int power){
        super(i, j, img, sx1, sy1, sx2, sy2, scaleX, scaleY);
        this.power = power;
    }

    public static MapBubble createMapBubble(int i, int j, List<String> bubbleData, int power){
        ImageIcon img = ResourceLoader.getResourceLoader().getImageInfo().get(bubbleData.get(0));
        int sx1 = Integer.valueOf(bubbleData.get(1));
        int sy1 = Integer.valueOf(bubbleData.get(2));
        int sx2 = Integer.valueOf(bubbleData.get(3));
        int sy2 = Integer.valueOf(bubbleData.get(4));
        int scaleX = Integer.valueOf(bubbleData.get(6));
        int scaleY = Integer.valueOf(bubbleData.get(7));
        return  new MapBubble(i, j, img, sx1, sy1, sx2, sy2, scaleX, scaleY, power);

    }

    @Override
    public void move() {
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setalive(false);
            }
        };
        timer.schedule(task, 3000);
    }

    @Override
    public void destroy() {
        if(!isalive()){
            List<SuperObject> bubbleExplode = ObjectController.getObjController().getMap().get("bubbleExplode");
            bubbleExplode.add(BubbleExplode.createBubbleExplode(getx(), gety(), power));
        }

    }

    public int getPower(){
        return power;
    }
}
