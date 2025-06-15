package object;

import entity.base.GameObject;
import main.GamePanel;

public class OBJ_Door extends GameObject {

    public OBJ_Door(GamePanel gp) {
        super(gp);

        name = "Door";
        down1 = setup("/objects/door", gp.tileSize, gp.tileSize);

        collision = true;
        type = type_obstacle;
    }
}
