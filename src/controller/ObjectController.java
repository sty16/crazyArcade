package controller;

import model.gameobject.MapObject;
import model.gameobject.SuperObject;
import resourceloader.ResourceLoader;

import javax.naming.spi.ObjectFactoryBuilder;
import java.util.*;

public class ObjectController {
    private static ObjectController objectController;
    static {
        objectController = new ObjectController();
    }

    private HashMap<String, List<SuperObject>> map;
    private HashMap<String, Integer> priorityObj;
    private GameMap gameMap;

    public ObjectController(){
        init();
    }

    private void init(){
        List<String> size = ResourceLoader.getResourceLoader().getGameInfo().get("windowSize");
        gameMap = new GameMap(Integer.valueOf(size.get(0)),Integer.valueOf(size.get(1)));
        map = new HashMap<>();
        priorityObj = new HashMap<>();
        map.put("floor", new ArrayList<SuperObject>());
        map.put("player", new ArrayList<SuperObject>());
        map.put("obstacle", new ArrayList<SuperObject>());
        map.put("fragility", new ArrayList<SuperObject>());
        map.put("gameprops", new ArrayList<SuperObject>());
        map.put("bubble", new ArrayList<SuperObject>());
        map.put("bubbleExplode", new ArrayList<SuperObject>());
        priorityObj.put("floor", -100);
        priorityObj.put("player", 600);
        priorityObj.put("obstacle", 400);
        priorityObj.put("fragility",200);
        priorityObj.put("gameprops",300);
        priorityObj.put("bubble", 100);
        priorityObj.put("bubbleExplode", 350);
        //TODO:
    }

    public void loadMap(String map){
        gameMap.createMap(map);
    }

    public Comparator<String> getMapObjCmp(){
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int p1 = priorityObj.get(o1);
                int p2 = priorityObj.get(o2);
                if(p1>p2){
                    return 1;
                }else if(p1<p2){
                    return -1;
                }else {
                    return 0;
                }
            }
        };
    }

    public static ObjectController getObjController(){
        return objectController;
    }

    public HashMap<String, List<SuperObject>> getMap(){
        return map;
    }

    public static List<Integer> getPosIndex(int x, int y){
        List<Integer> posIndex = new ArrayList<>();
        posIndex.add((y-GameMap.getBiasY())/MapObject.PIXEL_Y);
        posIndex.add((x-GameMap.getBiasX())/MapObject.PIXEL_X);
        return posIndex;
    }

    public static List<Integer> getPos(int i, int j){
        List<Integer> pos = new ArrayList<>();
        pos.add(j*MapObject.PIXEL_X + GameMap.getBiasX());
        pos.add(i*MapObject.PIXEL_Y + GameMap.getBiasY());
        return  pos;
    }

    public GameMap getGameMap(){
        return gameMap;
    }

    public void gameClean(){
        ObjectController.getObjController().getMap().get("player").clear();
        ObjectController.getObjController().getMap().get("floor").clear();
        ObjectController.getObjController().getMap().get("obstacle").clear();
        ObjectController.getObjController().getMap().get("fragility").clear();
        ObjectController.getObjController().getMap().get("gameprops").clear();
        ObjectController.getObjController().getMap().get("bubble").clear();
        ObjectController.getObjController().getMap().get("bubbleExplode").clear();
    }
}
