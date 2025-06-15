package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    boolean canTouchEvent = true;

    public boolean upPressed, downPressed, leftPressed, rightPressed, JPressed, 
    IPressed, shotKeyPressed,shift,buttonK, OPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }

        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        }

        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        // CHARACTER STATE
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }
        
        else if(gp.gameState == gp.skillState) {
        	skillState(code);
        }
        
        //TRANSITION STATE
        else if(gp.gameState == gp.transitionState) {
        	transitionState(code);
        }

        // OPTION STATE
        else if (gp.gameState == gp.optionState) {
            optionState(code);
        }

        // GAME OVER STATE
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }

        // TRADE STATE
        else if (gp.gameState == gp.tradeState) {
            tradeState(code);
        }
        
        //END GAME
        else if(gp.gameState == gp.endGame) {
        	endGame(code);
        }
        
        //RANNI STATE
        else if(gp.gameState == gp.ranniState) {
        	ranniState(code);
        }
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            // W button
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }

        if (code == KeyEvent.VK_S) {
            // S button
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_J) {
            if (gp.ui.commandNum == 0) {
                // NEW GAME
                gp.gameState = gp.playState;
                gp.playMusic(0);
                gp.newGameChoose = true;
            }
            if (gp.ui.commandNum == 1) {
            	if(gp.newGameChoose == true) {
            		gp.gameState = gp.playState;
            		gp.retry();
            	}
            }
            if (gp.ui.commandNum == 2) {
                System.exit(0);
            }
        }

        if (gp.ui.titleScreenState == 0) {

        } else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                // W button
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }

            if (code == KeyEvent.VK_S) {
                // S button
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_J) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNum == 1) {
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNum == 2) {
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNum == 3) {
                    gp.ui.titleScreenState = 0;
                }
            }
        }
    }

    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            // W button
            upPressed = true;
        }
        if (code == KeyEvent.VK_K) {
            // K for shot button
            buttonK = true;
        }

        if (code == KeyEvent.VK_S) {
            // S button
            downPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            // A button
            leftPressed = true;
        }

        if (code == KeyEvent.VK_D) {
            // D button
            rightPressed = true;
        }
        
        if (code == KeyEvent.VK_O) {
        	OPressed = true;
        	gp.OIndex = !gp.OIndex;
        }
        
        if (code == KeyEvent.VK_P) {
            // P button
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_Q) {
        	gp.gameState = gp.skillState;
        }
        if (code == KeyEvent.VK_J) {
            // Enter button
        	JPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            // K for shot button
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_I) {
            // K for shot button
            IPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            // ESC for settings button
            gp.gameState = gp.optionState;
        }
        if (code == KeyEvent.VK_SHIFT) {
            // K for shot button
            shift = true;
        }
        
        if (code == KeyEvent.VK_R) {
            switch (gp.currentMap) {
                case 0:
                    gp.tileM.loadMap("/maps/worldV3.txt", 0);
                    break;
                case 1:
                    gp.tileM.loadMap("/maps/interior01.txt", 1);
                    break;
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            // P button
            gp.gameState = gp.playState;
        }
    }
    public void teleport(int map, int col, int row) {
//        gp.gameState = gp.transitionState;
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize*col;
        gp.player.worldY = gp.tileSize*row;
        canTouchEvent = false;
        gp.playSE(13);
    }
    public void dialogueState(int code) {
    	if (code == KeyEvent.VK_J) {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            // C button
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_J) {
            gp.player.selectInventoryItem();
        }

        playerInventory(code);
    }
    public void skillState(int code) {
    	if(code == KeyEvent.VK_Q) {
    		gp.gameState = gp.playState;
    	}
    	
    	if(code == KeyEvent.VK_I) {
    		gp.player.selectSkillInventoryItem(code);
    	}
    	
    	if(code == KeyEvent.VK_F) {
    		gp.player.selectSkillInventoryItem(code);
    	}
    	
    	playerSkillInventory(code);
    }
    
    public void transitionState(int code) {
    	if (code == KeyEvent.VK_A) {
            gp.ui.commandNum--;

            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }

            gp.playSE(9);
        }

        if (code == KeyEvent.VK_D) {
            gp.ui.commandNum++;

            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }

            gp.playSE(9);
        }

        if (code == KeyEvent.VK_J) {
            if (gp.ui.commandNum == 1) {
                gp.gameState = gp.playState;
            } else if (gp.ui.commandNum == 0) {
                gp.ui.drawTransition();
                gp.bossContact = true;
                teleport(1, 59, 83);
                gp.gameState = gp.playState;
                
            }
        }
    }

    public void optionState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_J) {
            JPressed = true;
        }

        int maxCommandNum = 0;

        switch (gp.ui.subState) {
            case 0:
                maxCommandNum = 5;
                break;
            case 3:
                maxCommandNum = 1;
                break;
        }

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(9);

            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(9);

            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }

                if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }

                if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(9);
                }
            }
        }
    }

    public void gameOverState(int code) {
    	gp.stopMusic();
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum--;

            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }

            gp.playSE(9);
        }

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum++;

            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }

            gp.playSE(9);
        }

        if (code == KeyEvent.VK_J) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            } else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }

    public void tradeState(int code) {
        if (code == KeyEvent.VK_J) {
            JPressed = true;
        }

        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;

                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }

                gp.playSE(9);
            }

            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;

                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }

                gp.playSE(9);
            }
        }

        if (gp.ui.subState == 1) {
            npcInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }

        if (gp.ui.subState == 2) {
            playerInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    }
    
    public void endGame(int code) {
    	 if(gp.endGameID != 1) {
    		if (code == KeyEvent.VK_A) {
                gp.ui.commandNum--;

                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }

                gp.playSE(9);
            }

            if (code == KeyEvent.VK_D) {
                gp.ui.commandNum++;

                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }

                gp.playSE(9);
            }
    	 }
    	
    	 

         if (code == KeyEvent.VK_J) {
             if (gp.ui.commandNum == 0) {
            	 gp.bossAlive = true;
                 gp.gameState = gp.playState;
                 gp.retry();
//                 gp.playMusic(0);
             } else if (gp.ui.commandNum == 1) {
                 gp.gameState = gp.titleState;
                 gp.restart();
             }
         }
    }
    
    public void playerSkillInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }
    
    public void ranniState(int code) {
    	if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;

            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }

            gp.playSE(9);
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;

            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }

            gp.playSE(9);
        }

        if (code == KeyEvent.VK_J) {
        	//Khong
            if (gp.ui.commandNum == 1) {
                gp.gameState = gp.playState;
            //Co
            } else if (gp.ui.commandNum == 0) {
            	gp.checkPointChange = true;
            	gp.playSE(14);
                gp.gameState = gp.gameOverState;
                
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            // W button
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            // S button
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            // A button
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            // D button
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            // K for shot button
            shotKeyPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            // K for shot button
            shift = false;
        }
        if (code == KeyEvent.VK_K) {
            // K for shot button
            buttonK = false;
        }
        if (code == KeyEvent.VK_I) {
            // K for shot button
            IPressed = false;
        }
        if (code == KeyEvent.VK_O) {
        	OPressed = false;
        }
    }
}
