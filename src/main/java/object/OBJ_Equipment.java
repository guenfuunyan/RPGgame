package object;

import animation.TimedAnimation;
import java.awt.Rectangle;
import main.GamePanel;

public abstract class OBJ_Equipment extends SuperObject{
    protected int attackPoints, defensePoints;
    
    public OBJ_Equipment(GamePanel gp) {
        super(gp);
        defensePoints = 0;
        attackPoints = 0;
    }
    
    public TimedAnimation[] getSprites(Rectangle attackArea){return null;};
    public int getAttackPoints(){return attackPoints;};
    public int getDefensePoints(){return defensePoints;};

    @Override
    public boolean executeAction() {
        super.executeAction();
        return true;
    }

    @Override
    public boolean executeInventoryAction() {
        gp.getPlayer().equipShield(this);
        return false;
    }

}
