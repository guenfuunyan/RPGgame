package tilegame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	private boolean[] keys, justPressed, cantPress;
	public boolean up, down, left, right;
	public boolean aUp, aDown, aLeft, aRight;
	public boolean bUp, bDown, bLeft, bRight;
	public boolean blood, energy, skill;

	public KeyManager() {
		keys = new boolean[200];
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];

	}

	// tick + render
	public void tick() {
		// vòng lặp for này để khi nhấn key, key chỉ thực hiện 1 hành động, sau đó key release thì trạng thái khác đc 
		// thiết lập, nếu ko thì khi tick, trong 1s nó sẽ thực hiện chỉ 1 công vc, di chuyển sẽ di chuyễn mãi.
		for (int i = 0; i < keys.length; i++) {
			if (cantPress[i] && !keys[i]) {
				cantPress[i] = false;
			} else if (justPressed[i]) {
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if (!cantPress[i] && keys[i]) {
				justPressed[i] = true;
			}

		}
		// key di chuyển
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		// key tấn công
		aUp = keys[KeyEvent.VK_UP];
		aDown = keys[KeyEvent.VK_DOWN];
		aLeft = keys[KeyEvent.VK_LEFT];
		aRight = keys[KeyEvent.VK_RIGHT];
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return; // nằm ngoài vùng key thì bỏ.
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = false;
	}

	// trả ra giá trị key vừa nhấn(true/ false)
	public boolean keyJustPressed(int keyCode) {
		if (keyCode < 0 || keyCode >= keys.length)
			return false;
		return justPressed[keyCode];
	}

}
