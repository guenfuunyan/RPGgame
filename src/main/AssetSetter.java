package main;

import entity.npc.NPCFemale1;
import entity.npc.NPCFemale2;
import entity.npc.NPCFemale3;
import entity.npc.NPCFemale4;
import entity.npc.NPCFemale5;
import entity.npc.NPCLinhHon;
import entity.npc.NPCMale2;
import entity.npc.NPCMale3;
import entity.npc.NPCMale4;
import entity.npc.NPCMale5;
import entity.npc.NPC_NguoiHuongDan;
import entity.npc.NPC_ThuongNhan1;
import entity.npc.NPC_ThuongNhan2;
import entity.npc.NPC_ThuongNhanBanBo;
import entity.npc.NPC_bloodthirsty;
import entity.monster.MON_Cucda;
import entity.monster.MON_Diamondhead;
import entity.monster.MON_Fourarms;
import entity.monster.MON_GolemBoss;
import entity.monster.MON_Pinkky;
import entity.monster.MON_Saubuom;
import entity.monster.MON_StarGuardian;
import object.OBJ_Bug;
import object.OBJ_Pokeball;
import object.OBJ_HealthPotion;
import object.OBJ_ManaPotion;
import object.OBJ_Teleport;
import object.WP_HanBangKiem;
import object.WP_KiemKhoiNguyen;
import object.WP_NgocLamBaoKiem;
import object.WP_ThanhKiem;
import tile_interactive.IT_DryTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;

        gp.obj[mapNum][i] = new OBJ_Teleport(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 81;
        gp.obj[mapNum][i].worldY = gp.tileSize * 82;
        i++;

        gp.obj[mapNum][i] = new WP_ThanhKiem(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 84;
        gp.obj[mapNum][i].worldY = gp.tileSize * 46;
        i++;

        gp.obj[mapNum][i] = new OBJ_Pokeball(gp, new WP_KiemKhoiNguyen(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 46;
        gp.obj[mapNum][i].worldY = gp.tileSize * 69;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Pokeball(gp, new OBJ_HealthPotion(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 33;
        gp.obj[mapNum][i].worldY = gp.tileSize * 78;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Pokeball(gp, new OBJ_Bug(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 81;
        gp.obj[mapNum][i].worldY = gp.tileSize * 56;
        i++;
        
        
        gp.obj[mapNum][i] = new OBJ_Pokeball(gp, new WP_HanBangKiem(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 20;
        gp.obj[mapNum][i].worldY = gp.tileSize * 13;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Pokeball(gp, new WP_NgocLamBaoKiem(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 41;
        gp.obj[mapNum][i].worldY = gp.tileSize * 79;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Pokeball(gp, new OBJ_ManaPotion(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = gp.tileSize * 68;
        i++;
    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;
        
        gp.npc[mapNum][i] = new NPCMale2(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 23;
        gp.npc[mapNum][i].worldY = gp.tileSize * 84;
        i++;
        
        gp.npc[mapNum][i] = new NPCMale3(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 56;
        gp.npc[mapNum][i].worldY = gp.tileSize * 55;
        i++;
        
        gp.npc[mapNum][i] = new NPCMale4(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 31;
        gp.npc[mapNum][i].worldY = gp.tileSize * 55;
        i++;
        
        gp.npc[mapNum][i] = new NPCFemale1(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 39;
        gp.npc[mapNum][i].worldY = gp.tileSize * 55;
        i++;
        
        gp.npc[mapNum][i] = new NPCFemale2(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 31;
        gp.npc[mapNum][i].worldY = gp.tileSize * 52;
        i++;
        
        gp.npc[mapNum][i] = new NPCFemale2(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 16;
        gp.npc[mapNum][i].worldY = gp.tileSize * 53;
        i++;
        
        gp.npc[mapNum][i] = new NPCMale2(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 21;
        gp.npc[mapNum][i].worldY = gp.tileSize * 51;
        i++;
        
        gp.npc[mapNum][i] = new NPCMale5(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 42;
        gp.npc[mapNum][i].worldY = gp.tileSize * 52;
        i++;
        
        gp.npc[mapNum][i] = new NPCFemale5(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 32;
        gp.npc[mapNum][i].worldY = gp.tileSize * 55;
        i++;
        
        gp.npc[mapNum][i] = new NPCFemale3(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 17;
        gp.npc[mapNum][i].worldY = gp.tileSize * 64;
        i++;
        
        gp.npc[mapNum][i] = new NPCFemale4(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 82;
        gp.npc[mapNum][i].worldY = gp.tileSize * 53;
        i++;

        gp.npc[mapNum][i] = new NPCLinhHon(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 82;
        gp.npc[mapNum][i].worldY = gp.tileSize * 43;
        i++;
        
        gp.npc[mapNum][i] = new NPC_NguoiHuongDan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 19;
        gp.npc[mapNum][i].worldY = gp.tileSize * 81;
        i++;
        
        gp.npc[mapNum][i] = new NPC_ThuongNhanBanBo(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 80;
        gp.npc[mapNum][i].worldY = gp.tileSize * 60;
        i++;

        gp.npc[mapNum][i] = new NPC_bloodthirsty(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 81;
        gp.npc[mapNum][i].worldY = gp.tileSize * 26;
        i++;

        gp.npc[mapNum][i] = new NPC_ThuongNhan1(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 45;
        gp.npc[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        
        gp.npc[mapNum][i] = new NPC_ThuongNhan2(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 39;
        gp.npc[mapNum][i].worldY = gp.tileSize * 24;
        i++;
    }

    public void setMonster() {
        int mapNum = 0;

        int i = 0;
        gp.monster[mapNum][i] = new MON_Pinkky(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 41;
        gp.monster[mapNum][i].worldY = gp.tileSize * 75;
        i++;
        gp.monster[mapNum][i] = new MON_Pinkky(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 38;
        gp.monster[mapNum][i].worldY = gp.tileSize * 69;
        i++;
        gp.monster[mapNum][i] = new MON_Pinkky(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 39;
        gp.monster[mapNum][i].worldY = gp.tileSize * 64;
        i++;
        gp.monster[mapNum][i] = new MON_Pinkky(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 35;
        gp.monster[mapNum][i].worldY = gp.tileSize * 69;
        i++;
        gp.monster[mapNum][i] = new MON_Diamondhead(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 41;
        gp.monster[mapNum][i].worldY = gp.tileSize * 76;
        i++;
        
        gp.monster[mapNum][i] = new MON_Cucda(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 31;
        i++;
        gp.monster[mapNum][i] = new MON_Cucda(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 18;
        gp.monster[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        gp.monster[mapNum][i] = new MON_Cucda(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 21;
        gp.monster[mapNum][i].worldY = gp.tileSize * 24;
        i++;
       
        gp.monster[mapNum][i] = new MON_Fourarms(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 80;
        gp.monster[mapNum][i].worldY = gp.tileSize * 34;
        i++;
        gp.monster[mapNum][i] = new MON_Fourarms(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 75;
        gp.monster[mapNum][i].worldY = gp.tileSize * 33;
        i++;
        gp.monster[mapNum][i] = new MON_Fourarms(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 70;
        gp.monster[mapNum][i].worldY = gp.tileSize * 31;
        i++;
        
        
        gp.monster[mapNum][i] = new MON_StarGuardian(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 66;
        gp.monster[mapNum][i].worldY = gp.tileSize * 75;
        i++;
        
        gp.monster[mapNum][i] = new MON_StarGuardian(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 64;
        gp.monster[mapNum][i].worldY = gp.tileSize * 79;
        i++;
        
        gp.monster[mapNum][i] = new MON_Saubuom(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 67;
        gp.monster[mapNum][i].worldY = gp.tileSize * 80;
        i++;
        
        gp.monster[mapNum][i] = new MON_Saubuom(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 69;
        gp.monster[mapNum][i].worldY = gp.tileSize * 81;
        i++;
        
        
        gp.monster[mapNum][i] = new MON_Diamondhead(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 65;
        gp.monster[mapNum][i].worldY = gp.tileSize * 77;
        i++;
        
        
        mapNum = 1;
        gp.monster[mapNum][i] = new MON_GolemBoss(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 71;
        gp.monster[mapNum][i].worldY = gp.tileSize * 59;
        i++;
    }


    public void setInteractiveTile() {
        int mapNum = 0;

        int i = 0;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 57, 58);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 58, 58);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 55, 34);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 56, 34);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 55, 39);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 59, 39);
        i++;
    }
}
