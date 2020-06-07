package model.gameobject;

public enum MapObjectType{

    FLOOR('0'),OBSTACLE('1'),FRAGILITY('2'),PROPS('3'),PLAYER_1('6'),PLAYER_2('7'),NPC('8'),BUBBLE('9');

    private char value;

    private MapObjectType(char value){
        this.value=value;
    }

    public static MapObjectType valueOf(char value){
        switch (value){
            case '0':  return FLOOR;	//地板
            case '1':  return OBSTACLE;	//障碍物
            case '2':  return FRAGILITY;//可破坏物
            case '3':  return PROPS;  	//游戏道具
            case '6':  return PLAYER_1;	//玩家1
            case '7':  return PLAYER_2;	//玩家2
            case '8':  return NPC;		//NPC
            case '9':  return BUBBLE;  	//泡泡
            default:
                return null;
        }
    }

    public char getVal(){
        return this.value;
    }
}
