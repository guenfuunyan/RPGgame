package entity.object;

import main.GamePanel;

public class OBJ_Door extends Object {

    public OBJ_Door(GamePanel gp) {
        super(gp);

        name = "Door";
        down1 = setup("/objects/door", gp.tileSize, gp.tileSize);

        collision = true;
    }
}
