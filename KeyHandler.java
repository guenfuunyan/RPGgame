package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    // Trạng thái phím di chuyển
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    // Trạng thái phím hành động
    private boolean spacePressed; // Tấn công
    private boolean interactPressed; // Tương tác
    // Trạng thái phím UI
    private boolean pPressed; // Tạm dừng
    private boolean rPressed; // Khởi động lại

    // Phím mặc định (có thể tùy chỉnh sau)
    private int upKey = KeyEvent.VK_W;
    private int downKey = KeyEvent.VK_S;
    private int leftKey = KeyEvent.VK_A;
    private int rightKey = KeyEvent.VK_D;
    private int attackKey = KeyEvent.VK_SPACE;
    private int interactKey = KeyEvent.VK_E;
    private int pauseKey = KeyEvent.VK_P;
    private int restartKey = KeyEvent.VK_R;

    @Override
    public void keyTyped(KeyEvent e) {
        // Không cần xử lý
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if (code == upKey) upPressed = true;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if (code == downKey) downPressed = true;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if (code == leftKey) leftPressed = true;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if (code == rightKey) rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                if (code == attackKey) spacePressed = true;
                break;
            case KeyEvent.VK_E:
                if (code == interactKey) interactPressed = true;
                break;
            case KeyEvent.VK_P:
                if (code == pauseKey) pPressed = true;
                break;
            case KeyEvent.VK_R:
                if (code == restartKey) rPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if (code == upKey) upPressed = false;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if (code == downKey) downPressed = false;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if (code == leftKey) leftPressed = false;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if (code == rightKey) rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                if (code == attackKey) spacePressed = false;
                break;
            case KeyEvent.VK_E:
                if (code == interactKey) interactPressed = false;
                break;
            case KeyEvent.VK_P:
                if (code == pauseKey) pPressed = false;
                break;
            case KeyEvent.VK_R:
                if (code == restartKey) rPressed = false;
                break;
        }
    }

    // Getter để truy cập trạng thái phím
    public boolean isUpPressed() { return upPressed; }
    public boolean isDownPressed() { return downPressed; }
    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
    public boolean isSpacePressed() { return spacePressed; }
    public boolean isInteractPressed() { return interactPressed; }
    public boolean isPPressed() { return pPressed; }
    public boolean isRPressed() { return rPressed; }

    // Setter để tùy chỉnh phím
    public void setUpPressed(boolean upPressed) { this.upPressed = upPressed; }
    public void setDownPressed(boolean downPressed) { this.downPressed = downPressed; }
    public void setLeftPressed(boolean leftPressed) { this.leftPressed = leftPressed; }
    public void setRightPressed(boolean rightPressed) { this.rightPressed = rightPressed; }
    public void setSpacePressed(boolean spacePressed) { this.spacePressed = spacePressed; }
    public void setInteractPressed(boolean interactPressed) { this.interactPressed = interactPressed; }
    public void setPPressed(boolean pPressed) { this.pPressed = pPressed; }
    public void setRPressed(boolean rPressed) { this.rPressed = rPressed; }

    // Setter để tùy chỉnh phím (tùy chọn)
    public void setUpKey(int upKey) { this.upKey = upKey; }
    public void setDownKey(int downKey) { this.downKey = downKey; }
    public void setLeftKey(int leftKey) { this.leftKey = leftKey; }
    public void setRightKey(int rightKey) { this.rightKey = rightKey; }
    public void setAttackKey(int attackKey) { this.attackKey = attackKey; }
    public void setInteractKey(int interactKey) { this.interactKey = interactKey; }
    public void setPauseKey(int pauseKey) { this.pauseKey = pauseKey; }
    public void setRestartKey(int restartKey) { this.restartKey = restartKey; }
}