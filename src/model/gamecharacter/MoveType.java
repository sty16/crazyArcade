package model.gamecharacter;

public enum  MoveType {
    LEFT,TOP,RIGHT,DOWN,STOP;

    public static MoveType codeToMoveType(int code) {
        switch (code) {
            case 37:
            case 65:
                return MoveType.LEFT;
            case 38:
            case 87:
                return MoveType.TOP;
            case 39:
            case 68:
                return MoveType.RIGHT;
            case 40:
            case 83:
                return MoveType.DOWN;
            default:
                return MoveType.STOP;
        }
    }
}
