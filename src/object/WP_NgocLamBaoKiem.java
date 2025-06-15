package object;

import entity.base.Entity;
import main.GamePanel;

public class WP_NgocLamBaoKiem extends Entity {

    public WP_NgocLamBaoKiem(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = "Ngọc Lam Bảo Kiếm";
        down1 = setup("/objects/Kiemtrieuhoi", gp.tileSize, gp.tileSize);
        weaponProjectile = true;
        amount = 1;
        projectileWeapon = new WPS_Splash(gp);
        
        attackValue = 6;
        critPercent = 0;
        attackArea.width = 20;
        attackArea.height = 20;
        range = 20; // 3 thông 3 dòng này nên cùng bằng nhau
        description = "[" + name + "]\nThanh gươm được yểm một sức mạnh ma thuật\n"
        		+ "huyền bí, có khả năng triệu hồi các lưỡi kiếm "
        		+ "\ntấn công quân thù";
        price = 450;
        knockBackPower = 10;
    }

}
