package resourceloader;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;

public class ResourceLoader {
    private static ResourceLoader resourceLoader;
    static {
        resourceLoader = new ResourceLoader();
    }
    private Properties pro;
    private HashMap<String, List<String>> gameInfo;
    private HashMap<String, ImageIcon> imageInfo;
    private HashMap<String, List<String>> mapInfo;
    private HashMap<String, List<String>> mapObjectInfo;
    //构造函数
    public ResourceLoader(){
        pro = new Properties();
        gameInfo = new HashMap<>();
        imageInfo = new HashMap<>();
        mapInfo = new HashMap<>();
        mapObjectInfo = new HashMap<>();
        try {
            readGameCfg();
            readMapObjectCfg();
            readMapCfg("Simple");
            readImageCfg();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //读取游戏配置文件
    public static ResourceLoader getResourceLoader(){
        return resourceLoader;
    }

    public void readGameCfg() throws IOException{
        InputStream in = ResourceLoader.class.getResourceAsStream("/config/Game.cfg");
        pro.clear();
        pro.load(in);
        Iterator itr = pro.keySet().iterator();
        while (itr.hasNext()){
            String mKey = (String)itr.next();
            String mInfo = pro.getProperty(mKey);
            gameInfo.put(mKey, StringToList(mInfo,","));
        }
    }

    public void readImageCfg() throws IOException{
        InputStream in = ResourceLoader.class.getResourceAsStream("/config/images.cfg");
        pro.clear();
        pro.load(in);
        Iterator itr = pro.keySet().iterator();
        while (itr.hasNext()){
            String mKey = (String)itr.next();
            String mIcon = pro.getProperty(mKey);
            imageInfo.put(mKey, new ImageIcon(mIcon));
        }
    }

    public void readMapCfg(String map) throws IOException{
        mapInfo.clear();
        String mapFile = "/config/mapStage/map"+ map +".cfg";
        InputStream in = ResourceLoader.class.getResourceAsStream(mapFile);
        pro.clear();
        pro.load(in);
        mapInfo.clear();
        Iterator itr = pro.keySet().iterator();
        while (itr.hasNext()){
            String mKey = (String)itr.next();
            String mInfo = pro.getProperty(mKey);
            if(mKey.equals("size")){
                mapInfo.put("size", StringToList(mInfo,","));
            }else {
                mapInfo.put(mKey, StringToList(mInfo,","));
            }
        }
    }

    public void readMapObjectCfg() throws IOException{
        InputStream in = ResourceLoader.class.getResourceAsStream("/config/mapObject.cfg");
        pro.clear();
        pro.load(in);
        Iterator itr = pro.keySet().iterator();
        while (itr.hasNext()){
            String mKey = (String)itr.next();
            String mInfo = pro.getProperty(mKey);
            mapObjectInfo.put(mKey, StringToList(mInfo,","));
        }
    }

    private List<String> StringToList(String info, String symbol){
        return Arrays.asList(info.split(symbol));
    }
    public HashMap<String, List<String>>getGameInfo(){
        return gameInfo;
    }

    public HashMap<String, ImageIcon> getImageInfo(){
        return imageInfo;
    }

    public HashMap<String, List<String>>getMapInfo() {return mapInfo;}

    public HashMap<String, List<String>> getMapObjectInfo() {return  mapObjectInfo;}
}
