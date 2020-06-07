package model.gameobject;

import resourceloader.ResourceLoader;

import javax.swing.*;
import java.util.List;

public class MapObstacle extends MapObject {
    public MapObstacle(int i, int j, ImageIcon img, int sx1, int sy1, int sx2, int sy2, int scaleX, int scaleY){
        super(i, j, img, sx1, sy1, sx2, sy2, scaleX, scaleY);
    }

    public static MapObstacle createMapObstacle(int i, int j, List<String> ObstacleData){
        ImageIcon img = ResourceLoader.getResourceLoader().getImageInfo().get(ObstacleData.get(0));
        int sx1 = Integer.valueOf(ObstacleData.get(1));
        int sy1 = Integer.valueOf(ObstacleData.get(2));
        int sx2 = Integer.valueOf(ObstacleData.get(3));
        int sy2 = Integer.valueOf(ObstacleData.get(4));
        int scaleX = Integer.valueOf(ObstacleData.get(6));
        int scaleY = Integer.valueOf(ObstacleData.get(7));
        return  new MapObstacle(i, j, img, sx1, sy1, sx2, sy2, scaleX, scaleY);
    }
}
