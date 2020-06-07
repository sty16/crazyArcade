package controller;

import model.gamecharacter.Player;
import model.gameobject.*;
import resourceloader.ResourceLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameMap {
    private static int width;
    private static int height;
    private static int rows;
    private static int cols;
    private static int biasX;
    private static int biasY;
    private List<List<String>> mapList;

    public GameMap(int width, int height){
        this.width=width;
        this.height=height;
        mapList = new ArrayList<>();
        initMapList();
        biasX = (width - MapObject.PIXEL_X*cols)/4;
        biasY = (height - MapObject.PIXEL_Y*rows)/2;
    }

    public void initMapList(){
        HashMap<String, List<String>> mapInfo = ResourceLoader.getResourceLoader().getMapInfo();
        rows = Integer.valueOf(mapInfo.get("size").get(0));
        cols = Integer.valueOf(mapInfo.get("size").get(1));
        for(int i=0;i<rows;i++){
            mapList.add(mapInfo.get(String.valueOf(i+1)));
        }
    }

    public void createMap(String map){
        try {
            ResourceLoader.getResourceLoader().readMapCfg(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        HashMap<String, List<String>> mapInfo = ResourceLoader.getResourceLoader().getMapInfo();
        mapList.clear();
        rows = Integer.valueOf(mapInfo.get("size").get(0));
        cols = Integer.valueOf(mapInfo.get("size").get(1));
        for(int i=0;i<rows;i++){
            mapList.add(mapInfo.get(String.valueOf(i+1)));
        }
        biasX = (width - MapObject.PIXEL_X*cols)/4;
        biasY = (height - MapObject.PIXEL_Y*rows)/2;
        mapList.clear();
        for(int i=0;i<rows;i++){
            mapList.add(mapInfo.get(String.valueOf(i+1)));
        }
        ObjectController.getObjController().gameClean();
        createMapObject();
    }

    private void createMapObject(){
        try {
            ResourceLoader r = new ResourceLoader();
            r.readMapObjectCfg();
        }catch (Exception e){
            e.printStackTrace();
        }
        HashMap<String, List<String>> objectInfo = ResourceLoader.getResourceLoader().getMapObjectInfo();
        HashMap<String, List<String>>  mapInfo = ResourceLoader.getResourceLoader().getMapInfo();
        HashMap<String, List<SuperObject>> map = ObjectController.getObjController().getMap();
        //创建地板
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                map.get("floor").add(MapFloor.createMapFloor(i, j, objectInfo.get("01")));
            }
        }
        //创建物品与人物
        for(int i=0;i<rows;i++){
            List<String> objs = mapInfo.get(String.valueOf(i+1));
            for(int j=0;j<objs.size();j++){
                String type = objs.get(j);
                switch (type.charAt(0)){
                    case '1':
                        map.get("obstacle").add(MapObstacle.createMapObstacle(i,j,objectInfo.get(type)));
                        break;
                    case '2':
                        map.get("fragility").add(MapFragility.createMapFragility(i, j, objectInfo.get(type)));
                        break;
                    case  '3':
                        map.get("gameprops").add(MapGameProps.createMapGameProps(i, j));
                    case  '6':
                        initPlayer(i, j);
                    default:
                        break;

                }
            }
        }
        //TODO:
    }

    public boolean isBlockWalkable(int i, int j){
        String type = mapList.get(i).get(j);
        switch (type.charAt(0)){
            case '1':
            case '2':
            case '3':
                return false;
            default:
                return true;
        }
    }

    private void initPlayer(int i, int j){
        List<SuperObject> playerList = ObjectController.getObjController().getMap().get("player");
        HashMap<String, List<String>> objectInfo = ResourceLoader.getResourceLoader().getMapObjectInfo();
        List<String> playInfo = objectInfo.get("player");
        Player player = Player.createPlayer(i, j, playInfo);
        playerList.add(player);
    }

    public boolean outOfMap(int i, int j){
        if(i < 0 || i >= rows || j < 0 || j >= cols){
            return true;
        }else {
            return false;
        }
    }

    public boolean isObstacle(int i, int j){
        if(outOfMap(i, j)){
            return  true;
        }
        String mapObject = mapList.get(i).get(j);
        if(MapObjectType.valueOf(mapObject.charAt(0))==MapObjectType.OBSTACLE){
            return true;
        }else {
            return false;
        }
    }

    public boolean isFragility(int i, int j){
        if(outOfMap(i, j)){
            return true;
        }
        String mapObject = mapList.get(i).get(j);
        if(MapObjectType.valueOf(mapObject.charAt(0))==MapObjectType.FRAGILITY){
            return true;
        }else {
            return false;
        }
    }

    public boolean isBubble(int i, int j){
        if(outOfMap(i, j)){
            return true;
        }
        String mapObject = mapList.get(i).get(j);
        if(MapObjectType.valueOf(mapObject.charAt(0))==MapObjectType.BUBBLE){
            return true;
        }else {
            return false;
        }
    }

    public List<List<String>> getMapList(){
        return mapList;
    }

    public void setMapListObj(int i, int j, MapObjectType Objtype){
        mapList.get(i).set(j, Objtype.getVal()+"");
    }

    public static int getBiasX() {
        return biasX;
    }
    public static int getBiasY() {
        return biasY;
    }
    public static int getMapRows() {
        return rows;
    }
    public static int getMapCols() {
        return cols;
    }
}
