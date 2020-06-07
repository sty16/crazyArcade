package model.gameobject;

import controller.GameMap;
import controller.ObjectController;
import model.gamecharacter.Player;
import resourceloader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapGameProps extends MapObject {

    private boolean eaten;
    private String type;
    private static HashMap<String, List<String>> objectInfo = ResourceLoader.getResourceLoader().getMapObjectInfo();
    private static HashMap<String, Integer> propsChance = new HashMap<String, Integer>();
    private int playerIndex;
    static {
        propsChance.put("0", 60);
        propsChance.put("1", 25);
        propsChance.put("2", 15);
    }

    public MapGameProps(int i, int j, ImageIcon img, int sx1, int sy1, int sx2, int sy2, int scaleX, int scaleY, String type){
        super(i, j, img, sx1, sy1, sx2, sy2, scaleX, scaleY);
        eaten=false;
        this.type=type;
    }

    public static String getChanceProps(){
        Integer sum = 0;
        for(Integer val:propsChance.values()){
            sum += val;
        }
        Integer rand = new Random().nextInt(sum)+1;
        for(Map.Entry<String,Integer> entry:propsChance.entrySet()){
            rand -= entry.getValue();
            if(rand<=0){
                return entry.getKey();
            }
        }
        return null;
    }
    
    public static MapGameProps createMapGameProps(int i, int j){
        String propType = getChanceProps();
        propType = "3" + propType;
        List<String> propsData = objectInfo.get(propType);
        ImageIcon img = ResourceLoader.getResourceLoader().getImageInfo().get(propsData.get(0));
        int sx1 = Integer.valueOf(propsData.get(1));
        int sy1 = Integer.valueOf(propsData.get(2));
        int sx2 = Integer.valueOf(propsData.get(3));
        int sy2 = Integer.valueOf(propsData.get(4));
        int scaleX = Integer.valueOf(propsData.get(6));
        int scaleY = Integer.valueOf(propsData.get(7));
        return new MapGameProps(i, j, img, sx1, sy1, sx2, sy2, scaleX, scaleY, propType);
    }

    //减小碰撞体积
    @Override
    public boolean collision(SuperObject so) {
        Rectangle r1 = new Rectangle(getx()+getw()/3, gety()+geth()/3, getw()/2, geth()/2);
        Rectangle r2 = new Rectangle(so.getx()+so.getw()/3, so.gety()+so.geth()/3, so.getw()/2, so.geth()/2);
        return r1.intersects(r2);
    }

    @Override
    public void destroy() {
        if(eaten){
            GameMap gameMap = ObjectController.getObjController().getGameMap();
            int i = ObjectController.getPosIndex(getx(), gety()).get(0);
            int j = ObjectController.getPosIndex(getx(), gety()).get(1);
            gameMap.setMapListObj(i, j, MapObjectType.FLOOR);
            List<SuperObject> playerList = ObjectController.getObjController().getMap().get("player");
            Player player = (Player)playerList.get(playerIndex);
            switch (type){
                case "30":
                    player.setMagicBubbleCount(player.getMagicBubbleCount() + 1);
                    break;
                case "31":
                    player.setMagicPowerCount(player.getMagicPowerCount() + 1);
                    break;
                case "32":
                    player.setMagicSaveCount(player.getMagicSaveCount() + 1);
                    break;
                default:
                    break;
            }
            eaten = false;
            setalive(false);
        }


    }

    public boolean isEaten(){
        return eaten;
    }

    public void setEaten(boolean eaten){
        this.eaten=eaten;
    }

    public void setPlayerIndex(int playerIndex){
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex()   {return playerIndex;}
}
