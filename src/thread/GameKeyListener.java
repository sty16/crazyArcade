package thread;

import controller.GameController;
import controller.ObjectController;
import model.gamecharacter.MoveType;
import model.gamecharacter.Player;
import model.gameobject.SuperObject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Stack;

public class GameKeyListener implements KeyListener {

    private Stack<Integer> pressStack = new Stack<>();
    private List<SuperObject> playerList;

    @Override
    public void keyPressed(KeyEvent e) {
        playerList = ObjectController.getObjController().getMap().get("player");
        Player player = (Player) playerList.get(0);
        int code = e.getKeyCode();
        if(!player.isDead() && GameController.isGameRunning()){
            switch (code){
                case 32:
                    if(player.isKeepAttack()){
                        player.setAttack(false);
                    }else {
                        if(!player.isDying()){
                            player.setAttack(true);
                            player.setKeepAttack(true);
                        }
                    }
                    break; //放置泡泡
                case 37:
                case 38:
                case 39:
                case 40:
                case 87:
                case 65:
                case 68:
                case 83:
                    if(!pressStack.contains(code)){
                        pressStack.push(code);
                        player.setMoveType(MoveType.codeToMoveType(code));
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        playerList = ObjectController.getObjController().getMap().get("player");
        Player player = (Player) playerList.get(0);
        int code = e.getKeyCode();
        if(!player.isDead()&&GameController.isGameRunning()){
            switch (code){
                case 32:
                    player.setKeepAttack(false);
                    player.setAttack(false);
                    break; //
                case 37:
                case 38:
                case 39:
                case 40:
                case 87:
                case 65:
                case 68:
                case 83:
                    if(pressStack.peek()!=code){
                        pressStack.remove(Integer.valueOf(code)); //integer.valueof构造函数
                    }else {
                        pressStack.pop();
                        if(pressStack.size() == 0){
                            player.setMoveType(MoveType.STOP);
                        }else {
                            player.setMoveType(MoveType.codeToMoveType(pressStack.peek()));
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void clearPressStack(){
        pressStack.clear();
    }
}
