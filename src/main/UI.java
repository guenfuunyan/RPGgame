package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.base.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaPotion;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/Little-Thiing-Regular.otf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        
        Entity crystal = new OBJ_ManaPotion(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;

        Entity bronzeCoin = new OBJ_Coin_Bronze(gp,1);
        coin = bronzeCoin.down1;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMonsterLife();
            drawMessage();
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        // CHARACTER STATE
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player, true, 0, 0);
        }
        
        // SKILL STATE
        if (gp.gameState == gp.skillState) {
            drawCharacterScreen();
            drawSkillInventory(gp.player, true);
        }


        // OPTION STATE
        if (gp.gameState == gp.optionState) {
            drawOptionScreen();
        }

        // GAME OVER STATE
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }

        // TRANSITION STATE
        if (gp.gameState == gp.transitionState) {
            teleportDialog();
        }

        // TRADE STATE
        if (gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }
        
        // END GAME
        if(gp.gameState == gp.endGame) {
        	drawEndGameScreen();
        }
        
        if(gp.gameState == gp.ranniState) {
        	drawDialogueScreen();
        	drawPlayerChoice();
        }
    }

    

	public void drawPlayerLife() {
        // Position and dimensions of the health bar
        int x = gp.tileSize ;
        int y = gp.tileSize /2;
        int i =0;
        int barWidth = gp.tileSize * 5; // Adjust the width as needed
        int barHeight = gp.tileSize / 2; // Adjust the height as needed
        int arcWidth = 15; // Width of the corner arcs
        int arcHeight = 15; // Height of the corner arcs
        
        // Calculate the current health width
        double healthPercentage = (double) gp.player.life / gp.player.maxLife;
        int currentHealthBarWidth = (int) (barWidth * healthPercentage);
        
        // Draw the background (empty health)
        g2.setColor(Color.WHITE); // Background color
        g2.fillRoundRect(x, y, barWidth , barHeight, arcWidth, arcHeight);

        // Draw the current health (filled part of the bar)
        g2.setColor(Color.RED); // Health bar color
        g2.fillRoundRect(x, y, currentHealthBarWidth, barHeight, arcWidth, arcHeight);

        // Optional: Draw a border around the health bar
     // Set a thicker stroke for the border
        g2.setStroke(new BasicStroke(5)); // Replace '5' with the desired thickness
        g2.setColor(Color.BLACK); // Border color
        g2.drawRoundRect(x, y, barWidth, barHeight, arcWidth, arcHeight);
        // Reset the stroke to default after drawing, if needed
        g2.setStroke(new BasicStroke(1));
        
        

        // DRAW MAX MANA
        x = gp.tileSize ;
        y = (int) (gp.tileSize  * 1.25);



        // Calculate the current mana width
        double manaPercentage = (double) gp.player.mana / gp.player.maxMana;
        int currentManaBarWidth = (int) (barWidth * manaPercentage);

     // Draw the background (empty mana) with rounded corners
        g2.setColor(Color.WHITE); // Background color
        g2.fillRoundRect(x, y, barWidth, barHeight, arcWidth, arcHeight);


        // Draw the current mana (filled part of the bar) with rounded corners
        g2.setColor(Color.BLUE); // Mana bar color
        g2.fillRoundRect(x, y, currentManaBarWidth, barHeight, arcWidth, arcHeight);
        // Optional: Draw a border around the mana bar with rounded corners
     // Set a thicker stroke for the border
        g2.setStroke(new BasicStroke(5)); // Replace '5' with the desired thickness
        g2.setColor(Color.BLACK); // Border color
        g2.drawRoundRect(x, y, barWidth, barHeight, arcWidth, arcHeight);
       

        // Reset the stroke to default after drawing, if needed
        g2.setStroke(new BasicStroke(1));
        
    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);

                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
        	
        	//BACKGROUND
        	BufferedImage image;
        	UtilityTool uTool = new UtilityTool();
        	try {
				image = ImageIO.read(getClass().getResourceAsStream("/maps/background_1.png"));
				image = uTool.scaleImage(image, 1280, 768);
				g2.drawImage(image, 0, 0, null);
			} catch (IOException e) {
				e.printStackTrace();
			}

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "FFRPG";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            
            //FRAME
            
            drawSubWindow(x - 100, y - 100 , 500 , 500 );

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + gp.tileSize + 5);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y + gp.tileSize);


            // MAIN MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "PHẦN CHƠI MỚI";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - 48, y);
            }

            text = "TIẾP TỤC";
            if(gp.newGameChoose == false) {
            	g2.setColor(Color.gray);
            }
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - 48, y);
            }

            text = "THOÁT GAME";
            g2.setColor(Color.white);
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - 48, y);
            }
        }

        if (titleScreenState == 1) {
            // CLASS SELECTION SCREEN
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "Tạm Dừng";

        int x = getXforCenteredText(text);

        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawMonsterLife()
    {
            for (int i = 0;i<gp.monster[1].length;i++)
            {
                    
                    Entity monster = gp.monster[gp.currentMap][i];
                    
                    if (monster!=null && monster.inCamera()==true)
                    {
                            if (monster.hpBarOn == true && monster.boss == false) {
                        double oneScale = (double) gp.tileSize / monster.maxLife;
                        double hpBarValue = oneScale * monster.life;
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 16, gp.tileSize + 2, 12);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int) hpBarValue, 10);
                        monster.hpBarCounter++;
                        if (monster.hpBarCounter > 600) {
                            monster.hpBarCounter = 0;
                            monster.hpBarOn = false;
                        }
                    }
                            else if (monster.boss==true)
                            {
                                      double oneScale = (double) gp.tileSize*8 / monster.maxLife;
                          double hpBarValue = oneScale * monster.life;
                          
                          int x = gp.screenWidth/2 - gp.tileSize*4;
                          int y = gp.tileSize*10;
                          
                          g2.setColor(new Color(35, 35, 35));
                          g2.fillRect(x - 1, y - 16, gp.tileSize*8 + 2, 22);
                          g2.setColor(new Color(255, 0, 30));
                          g2.fillRect(x, y - 15, (int) hpBarValue, 20);
                          
                          g2.setFont(g2.getFont().deriveFont(Font.BOLD,24f));
                          g2.setColor(Color.white);
                          g2.drawString(monster.name, x+4, y-10);
                            }
                    }
            }
            
            
            
    }
    public void drawCharacterScreen() {
        // FRAME
        final int frameX = gp.tileSize ;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 8;
        final int frameHeight = gp.tileSize * 9;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // NAMES
        g2.drawString("Cấp độ", textX, textY);
        textY += lineHeight;
        g2.drawString("Máu", textX, textY);
        textY += lineHeight;
        g2.drawString("Năng lượng", textX, textY);
        textY += lineHeight;
        g2.drawString("Sức mạnh", textX, textY);
        textY += lineHeight;
        g2.drawString("Tấn công", textX, textY);
        textY += lineHeight;
        g2.drawString("Phòng thủ", textX, textY);
        textY += lineHeight;
        g2.drawString("Kinh nghiệm", textX, textY);
        textY += lineHeight;
        g2.drawString("Kinh nghiệm lên cấp", textX, textY);
        textY += lineHeight;
        g2.drawString("Vàng", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Vũ khí", textX, textY);
        textY += lineHeight + 25;
        g2.drawString("Khiên", textX, textY);
        textY += lineHeight;
        

        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize; // RESET textY
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
       

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize*1.8;
        textX = getXforAlignToRightText(value, tailX);
        value = "#" + String.valueOf(gp.newGameTimes);
        g2.drawString(value, textX + 20 , textY);

    }
    
   
    
    public void drawInventory(Entity entity, boolean cursor, int x, int y) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            // FRAME
            frameX = gp.tileSize * 9;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
            
        } else {
            // FRAME
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // FRAME
        drawSubWindow(frameX + x, frameY + y, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20 + x;
        final int slotYstart = frameY + 20 + y;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // DRAW PLAYER'S ITEM
        for (int i = 0; i < entity.inventory.size(); i++) {
            if (entity.inventory.get(i) == entity.currentWeapon
                    || entity.inventory.get(i) == entity.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            // DISPLAY AMOUNT
            if(entity == gp.player && entity.inventory.get(i).amount > 1) {
                    g2.setFont(g2.getFont().deriveFont(32f));
                    int amountX;
                    int amountY;
                    String s = "" + entity.inventory.get(i).amount;
                    amountX = getXforAlignToRightText(s,slotX + 44);
                    amountY = slotY + gp.tileSize;
                    
                    //shadow
                    g2.setColor(new Color(60,60,60));
                    g2.drawString(s,amountX,amountY);
                    
                    //number
                    g2.setColor(Color.white);
                    g2.drawString(s,amountX - 3 ,amountY - 3 );
            }
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // CURSOR
        if (cursor == true) {
            int cursorX = slotXstart + (slotSize * slotCol) ;
            int cursorY = slotYstart + (slotSize * slotRow) ;
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // DRAW CURSOR
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // DESCRIPTION ITEM FRAME
            int dFrameX = frameX + x;
            int dFrameY = frameY + frameHeight + y;
            int dFrameWidth = gp.tileSize * 10 ;
            int dFrameHeight = gp.tileSize * 4 ;

            // DRAW DESCRIPTION TEXT
            int textX = dFrameX + 20 ;
            int textY = dFrameY + 40 ;
            g2.setFont(g2.getFont().deriveFont(28f));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                drawSubWindow(frameX+frameWidth + x, frameY + y, gp.tileSize*4, frameHeight);

                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
                
                textX = frameX+frameWidth+15 + x;
                textY = frameY + 50 + y;
                
                switch(entity.inventory.get(itemIndex).type) {
                case 3: //sword type
                	g2.drawString("Sát thương: +" + entity.inventory.get(itemIndex).attackValue, textX ,textY);
               		textY += 32;
               		g2.drawString("Tầm: " + entity.inventory.get(itemIndex).range, textX ,textY);
               		textY += 32;
               		g2.drawString("Đẩy lùi: " + entity.inventory.get(itemIndex).knockBackPower, textX, textY);
               		textY += 32;
               		if(entity.inventory.get(itemIndex).weaponProjectile) {
               			g2.drawString("Sát thương skill: " + entity.inventory.get(itemIndex).projectileWeapon.attack,textX ,textY);
               			textY += 32;
               			g2.drawString("Yêu cầu: " + entity.inventory.get(itemIndex).projectileWeapon.useCost +" mana",textX ,textY);
               			textY += 32;
               		}
               		g2.drawString("Bấm phím [O] để",textX ,textY);
               		textY += 32;
               		g2.drawString("hiển thị tầm ",textX ,textY);
                	break;
                case 4: //axe type
                	g2.drawString("Sát thương: +" + entity.inventory.get(itemIndex).attackValue, textX ,textY);
               		textY += 32;
               		g2.drawString("Tầm: " + entity.inventory.get(itemIndex).range, textX ,textY);
               		textY += 32;
               		g2.drawString("Bấm phím [O] để",textX ,textY);
               		textY += 32;
               		g2.drawString("hiển thị tầm ",textX ,textY);
                	break;
                case 5: //shield type
                	g2.drawString("Phòng thủ: +" + entity.inventory.get(itemIndex).defenseValue, textX ,textY);
                	break;
                case 6: //consumable type
                	g2.drawString("Hồi: " + entity.inventory.get(itemIndex).value, textX ,textY);
                	break;
                }
                
            }
        }
    }
    
    public void drawSkillInventory(Entity entity, boolean cursor) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            // FRAME
            frameX = gp.tileSize * 9;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            // FRAME
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // FRAME
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // DRAW PLAYER'S ITEM
        for (int i = 0; i < entity.skillInventory.size(); i++) {
            if (entity.skillInventory.get(i) == entity.currentSkillI 
            		|| entity.skillInventory.get(i) == entity.currentSkillF) {
            	
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
                if(entity.skillInventory.get(i) == entity.currentSkillI){
                	g2.setColor(Color.red);
            		g2.setFont(g2.getFont().deriveFont(26f));
            		g2.drawString("I", slotX, slotY + 10);
            	}
            	if(entity.skillInventory.get(i) == entity.currentSkillF) {
            		g2.setColor(Color.red);
            		g2.setFont(g2.getFont().deriveFont(26f));
            		g2.drawString("F", slotX+52, slotY + 10);
            	}
            }

            g2.drawImage(entity.skillInventory.get(i).appear, slotX, slotY, null);
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // CURSOR
        if (cursor == true) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // DRAW CURSOR
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // DESCRIPTION ITEM FRAME
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 4;

            // DRAW DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize - 20;
            g2.setFont(g2.getFont().deriveFont(28f));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (itemIndex < entity.skillInventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                drawSubWindow(frameX+frameWidth, frameY, gp.tileSize*4, frameHeight);


                for (String line : entity.skillInventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
                textX = frameX+frameWidth+15;
                textY = frameY + 50;
                
                g2.drawString("Sát thương: " + entity.skillInventory.get(itemIndex).attack, textX ,textY);
           		textY += 32;
           		
           		g2.drawString("Tầm đánh: " + entity.skillInventory.get(itemIndex).range, textX ,textY);
           		textY += 32;
           		
           		g2.drawString("Tốc độ: " + entity.skillInventory.get(itemIndex).speed, textX ,textY);
           		textY += 32;
           		
           		g2.drawString("Yêu cầu: " + entity.skillInventory.get(itemIndex).useCost + " Mana", textX ,textY);
           		textY += 32;
            }
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Thất bại";

        // SHADOW
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        // MAIN TEXT
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);
        
        
        //CONTENT
        text = "Bạn đã hết máu";
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50f));
        g2.setColor(Color.white);
        x = getXforCenteredText(text);
        g2.drawString(text, x - 4, y + 80);
        
        //CHECKPOINT
        if(gp.checkPointChange) {
        	g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));
        	g2.setColor(Color.red);
        	text = "Vị trí hồi sinh: Nơi ở của ma cà rồng";
        	x = getXforCenteredText(text);
        	g2.drawString(text, x - 4, y + 150);
        }
        else {
        	g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));
        	g2.setColor(Color.red);
        	text = "Vị trí hồi sinh: Nơi xuất hiện đầu tiên";
        	x = getXforCenteredText(text);
        	g2.drawString(text, x - 4, y + 150);
        }
        
        //MORSE
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f));
        g2.setColor(Color.white);
        text = ".-. --- .-.. .-.. -... .- -.-. -.-";
    	x = getXforCenteredText(text);
    	g2.drawString(text, x - 4, y + 180);

        // RETRY
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Hồi sinh";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);

        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        // QUIT
        text = "Thoát";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);

        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
       
    }

    public void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));

        // SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
                break;
        }

        gp.keyH.JPressed = false;
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Cài đặt";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Toàn màn hình", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.JPressed == true) {
                if (gp.fullScreenOn == false) {
                    gp.fullScreenOn = true;
                } else if (gp.fullScreenOn = true) {
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        // MUSIC
        textY += gp.tileSize;
        g2.drawString("Âm thanh", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // SOUND EFFECT
        textY += gp.tileSize;
        g2.drawString("Hiệu ứng nền", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        // CONTROL
        textY += gp.tileSize;
        g2.drawString("Điều khiển", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.JPressed == true) {
                subState = 2;
                commandNum = 0;
            }
        }

        // END GAME
        textY += gp.tileSize;
        g2.drawString("Kết thúc", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.JPressed == true) {
                subState = 3;
                commandNum = 0;
            }
        }

        // BACK
        textY += gp.tileSize * 2;
        g2.drawString("Trở lại", textX, textY);
        if (commandNum == 5) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.JPressed == true) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        // FULL SCREEN CHECK BOX
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 42;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);

        if (gp.fullScreenOn == true) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // MUSIC VOLUME BOX
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // SOUND EFFECT VOLUME BOX
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Thay đổi sẽ áp dụng sau khi\nkhởi động lại trò chơi.";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK OPTION
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Quay lại", textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.JPressed == true) {
                subState = 0;
            }
        }
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Điều khiển";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;

        g2.drawString("Di chuyển", textX, textY);
        textY += 48;
        g2.drawString("Tương tác/Tấn công", textX, textY);
        textY += 48;
        g2.drawString("Bắn/Sử dụng chiêu", textX, textY);
        textY += 48;
        g2.drawString("Bộ chiêu thức", textX, textY);	// them skill set
        textY += 48;
        g2.drawString("Trạng thái/Túi đồ", textX, textY);	
        textY += 48;
        g2.drawString("Tạm dừng", textX, textY);
        textY += 48;
        g2.drawString("Cài đặt", textX, textY);
        textY += 48;
        g2.drawString("Tăng tốc", textX, textY);
        textY += gp.tileSize;

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;

        g2.drawString("WASD", textX, textY);
        textY += 48;
        g2.drawString("J", textX, textY);
        textY += 48;
        g2.drawString("F/I", textX, textY);
        textY += 48;
        g2.drawString("Q", textX, textY);	// them nut Q vao huong dan cho skill set
        textY += 48;
        g2.drawString("C", textX, textY);
        textY += 48;
        g2.drawString("P", textX, textY);
        textY += 48;
        g2.drawString("ESC", textX, textY);
        textY += 48;
        g2.drawString("SHIFT", textX, textY);
        textY += gp.tileSize;

        // BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Quay lại", textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.JPressed == true) {
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Thoát game và trở lại\nmàn hình chính?";

        for (String line : currentDialogue.split("\n")) {
        	textX = getXforCenteredText(line);
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // YES
        String text = "Có";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.JPressed) {
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }

        // NO
        text = "Không";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);

        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.JPressed) {
                subState = 0;
                commandNum = 4;
            }
        }
    }

    public void drawTransition() {
        
    	
    	counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if (counter == 50) {
            counter = 0;

            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;

            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;

            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }

    public void drawTradeScreen() {
        switch (subState) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;
        }

        gp.keyH.JPressed = false;
    }

    public void trade_select() {
        drawDialogueScreen();

        // DRAW WINDOW
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int) (gp.tileSize * 3.5);

        drawSubWindow(x, y, width, height);

        // DRAW TEXTS
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Mua", x, y);

        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);

            if (gp.keyH.JPressed == true) {
                subState = 1;
            }
        }

        y += gp.tileSize;

        g2.drawString("Bán", x, y);

        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);

            if (gp.keyH.JPressed == true) {
                subState = 2;
            }
        }

        y += gp.tileSize;

        g2.drawString("Rời đi", x, y);

        if (commandNum == 2) {
            g2.drawString(">", x - 24, y);

            if (gp.keyH.JPressed == true) {
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Lần sau lại đến nữa nhé, khách quý.";
            }
        }
    }

    public void trade_buy() {
        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, false, gp.tileSize*3, 0);

        // DRAW NPC INVENTORY
        drawInventory(npc, true, 0, 0);

        // DRAW HINT WINDOW
        int x = gp.tileSize * 16;
        int y = gp.tileSize * 8;
        int width = gp.tileSize * 2;
        int height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC]", x + 34, y + 56);
        g2.drawString("Quay lại", x + 14, y + 90);
        // DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 6;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("Vàng hiện có: " + gp.player.coin, x + 24, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()) {
            x = (gp.tileSize * 12);
            y = (gp.tileSize * 8);
            width = (gp.tileSize * 4);
            height = gp.tileSize * 2;

            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 8, y + 16, 64, 64, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "Giá: " + price;
            g2.drawString(text, x + 64, y + 60);

            // BUY AN ITEM
            if (gp.keyH.JPressed == true) {
                if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "Không đủ tiền";
                    drawDialogueScreen();
                }                
                else {
                	if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
                		gp.player.coin -= npc.inventory.get(itemIndex).price;
                	}
                	else {
                		subState = 0;
                		gp.gameState = gp.dialogueState;
                		currentDialogue = "Túi đã đầy!";
                	}
                }
            }
        }
    }

    public void trade_sell() {
        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, true, -gp.tileSize*7, 0);
        
        
        // DRAW SELL INFORMATION
        int x = gp.tileSize * 12;
        int y = gp.tileSize * 1;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 5;
        drawSubWindow(x, y, width, height);
        g2.drawString("Giá trị sản phẩm bán đi chỉ", x + 26, y + 100);
        g2.drawString("bằng một nửa giá gốc", x + 50, y + 150);
        
        // DRAW HINT WINDOW
        x = gp.tileSize * 16;
        y = gp.tileSize * 8;
        width = gp.tileSize * 2;
        height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC]", x + 34, y + 56);
        g2.drawString("Quay lại", x + 14, y + 90);
        // DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 6;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("Vàng hiện có: " + gp.player.coin, x + 24, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gp.player.inventory.size()) {
            x = (gp.tileSize * 12);
            y = (gp.tileSize * 8);
            width = (gp.tileSize * 4);
            height = gp.tileSize * 2;

            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 8, y + 16, 64, 64, null);

            int price = (int) gp.player.inventory.get(itemIndex).price/2;
            String text = "Bán: " + price;
            g2.drawString(text, x + 64, y + 60);

            // SELL AN ITEM
            if (gp.keyH.JPressed == true) {
                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon
                        || gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "Không được bán vũ khí đang sử dụng!";
                } else {
                	if(gp.player.inventory.get(itemIndex).amount > 1) {
                		gp.player.inventory.get(itemIndex).amount --;
                	}
                	else {
                        gp.player.inventory.remove(itemIndex);
                	}
                    gp.player.coin += price;
                }
            }
        }
    }
    
    public void teleportDialog() {
    	int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);


        int textX = x + 40;
        int textY = y + 50;
        g2.setFont(g2.getFont().deriveFont(35f));
        String dialog ="Bạn có chắc muốn dịch chuyển để đương đầu với thử \nthách không? Nếu anh hùng thất bại, mọi thứ sẽ kết\nthúc.";
        for (String line : dialog.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 32;
        }
        
        g2.setFont(g2.getFont().deriveFont(40f));
        String text = "Có";
        x = getXforCenteredText(text) - 200; 
        y += gp.tileSize * 4;
        drawSubWindow(x - 75 , y , 600, 80);
        g2.drawString(text, x, y + 50);

        if (commandNum == 0) {
            g2.drawString(">", x - 40, y + 50);
        }

        
        text = "Không";
        x = getXforCenteredText(text) + 200; 
        g2.drawString(text, x, y + 50);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y + 50);
        }
    }
    
    
    public void drawPlayerChoice() {
    	int x = 0, y = 0;
        g2.setFont(g2.getFont().deriveFont(34f));
        String text = "Ôm";
        x =  gp.tileSize * 6; 
        y += gp.tileSize * 4.4;
        drawSubWindow(x - 65 , y , 650, 160);
        g2.drawString(text, x, y + 50);

        if (commandNum == 0) {
            g2.drawString(">", x - 40, y + 50);
        }

        
        text = "Không em nhé, anh là chill guy";
        x = gp.tileSize* 6 ; 
        g2.drawString(text, x, y + 50 + gp.tileSize);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y + 50 + gp.tileSize);
        }
    }
    
    
    public void drawEndGameScreen() {
		
		if(gp.endGameID == 3) {
			g2.setColor(new Color(0, 0, 0, 150));
	        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

	        int x;
	        int y;
	        String text;
	        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));

	        text = "Ending 3";

	        // SHADOW
	        g2.setColor(Color.black);
	        x = getXforCenteredText(text);
	        y = gp.tileSize*2;
	        g2.drawString(text, x, y);

	        // MAIN TEXT
	        g2.setColor(Color.white);
	        g2.drawString(text, x - 4, y - 4);
	        
	        // NOI DUNG
	        int textX = x - 190;
	        int textY = y + 50;
	        drawSubWindow(x - 200, y + 10, x + 200, gp.tileSize*8);
	        g2.setFont(g2.getFont().deriveFont(35f));
	        String dialog =
	        			 "Mọi thứ đã kết thúc, thế giới đã sụp đổ,\ndự án phát triển AI "
	        			+ "có lẽ đã phải hủy bỏ,\ntôi không còn phải tiếp tục vòng lặp "
	        			+ "bất\ntận nữa, có lẽ giờ tôi đã được nghỉ ngơi.";
	        for (String line : dialog.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 40;
            }

	        // RETRY
	        g2.setFont(g2.getFont().deriveFont(50f));
	        text = "Thử lại";
	        x = getXforCenteredText(text) - 200; // sua lai code
	        y += gp.tileSize * 7.8;
	        g2.drawString(text, x, y);

	        if (commandNum == 0) {
	            g2.drawString(">", x - 40, y);
	        }

	        // QUIT
	        text = "Thoát";
	        x = getXforCenteredText(text) + 200; // sua lai code
//	        y += 55;							 // sua lai code
	        g2.drawString(text, x, y);

	        if (commandNum == 1) {
	            g2.drawString(">", x - 40, y);
	        }
		}
		
		
		if(gp.endGameID == 2) {
			
			g2.setColor(new Color(0, 0, 0, 150));
	        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

	        int x;
	        int y;
	        String text;
	        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));

	        text = "Ending 2";

	        // SHADOW
	        g2.setColor(Color.black);
	        x = getXforCenteredText(text);
	        y = gp.tileSize*2;
	        g2.drawString(text, x, y);

	        // MAIN TEXT
	        g2.setColor(Color.white);
	        g2.drawString(text, x - 4, y - 4);
	        
	        // NOI DUNG
	        int textX = x - 175;
	        int textY = y + 50;
	        drawSubWindow(x - 200, y + 10, x + 200, gp.tileSize*8);
	        g2.setFont(g2.getFont().deriveFont(35f));
	        String dialog =
	        				"Tôi đã đánh bại kẻ thù cuối cùng. Thế\nnhưng tôi có cảm giác tôi đã "
	        				+ "trải qua \nvòng lặp này rất nhiều lần rồi";
	        for (String line : dialog.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 40;
            }

	        // RETRY
	        g2.setFont(g2.getFont().deriveFont(50f));
	        text = "Thử lại";
	        x = getXforCenteredText(text) - 200; // sua lai code
	        y += gp.tileSize * 7.8;
	        g2.drawString(text, x, y);

	        if (commandNum == 0) {
	            g2.drawString(">", x - 40, y);
	        }

	        // QUIT
	        text = "Thoát";
	        x = getXforCenteredText(text) + 200; // sua lai code
//	        y += 55;							 // sua lai code
	        g2.drawString(text, x, y);

	        if (commandNum == 1) {
	            g2.drawString(">", x - 40, y);
	        }
			
		}
		
		
		
		if(gp.endGameID == 1) {
			
			g2.setColor(new Color(0, 0, 0, 150));
	        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

	        int x;
	        int y;
	        String text;
	        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));

	        text = "Ending 1";

	        // SHADOW
	        g2.setColor(Color.black);
	        x = getXforCenteredText(text);
	        y = gp.tileSize*2;
	        g2.drawString(text, x, y);

	        // MAIN TEXT
	        g2.setColor(Color.white);
	        g2.drawString(text, x - 4, y - 4);
	        
	        // NOI DUNG
	        int textX = x - 190;
	        int textY = y + 50;
	        drawSubWindow(x - 200, y + 10, x + 200, gp.tileSize*8);
	        g2.setFont(g2.getFont().deriveFont(35f));
	        String dialog =
	        		"Thế giới trước mắt tôi bỗng tối đen như\nmực, tôi không còn nhận thức "
	        		+ "về thời\ngian, không gian được nữa. Cứ như, tôi\nđã bị xóa đi vĩnh viễn khỏi thế gian này.";
	        for (String line : dialog.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 40;
            }

	        // QUIT
	        g2.setFont(g2.getFont().deriveFont(50f));
	        text = "Thoát";
	        x = getXforCenteredText(text) ; // sua lai code
	        y += gp.tileSize*7.8;							 // sua lai code
	        g2.drawString(text, x, y);
	        g2.drawString(">", x - 40, y);

		}
			

		
	}

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 5);

        return itemIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
