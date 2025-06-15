package object;

import entity.Entity;
import main.GamePanel;

public class WP_HanBangKiem extends Entity {

    public WP_HanBangKiem(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = "Hàn Băng Kiếm";
        down1 = setup("/objects/HanBangKiem", gp.tileSize, gp.tileSize);
        weaponProjectile = true;
        
        projectileWeapon = new WPS_Bang(gp);
        	
        
        amount = 1;
        attackValue = 8;
        critPercent = 0;
        attackArea.width = 20;
        attackArea.height = 20;
        range = 20;
        description = "[" + name + "]\nThanh kiếm mang đến cái lạnh chết chóc cho\nquân thù,"
        		+ " cùng với các lưỡi dao có khả năng \nxuyên thủng bức tường thành kiên cố nhất\n";
        price = 500;
        knockBackPower = 10;
    }

}
