package entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import entities.Entity;
import state.State;
import tilegame.Handler;
import tilegame.Inventory;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.util.User;
import tilegame.util.UserData;

public class Player extends Creature {
	private int currentLevel = 1;
	// Animation
	private Animation defendUP;
	private Animation defendDown;
	private Animation defendLeft;
	private Animation defendRight;
	private Animation attackLeft, attackRight, attackUp, attackDown;
	private Animation skillfulAttacking;
	// checking condition
	public boolean isAttacking = false, isUsingSkill = false, isExhausted = false;
	// Attack timer
	private long lastAttackTimer, attackCoolDown = 800, attackTimer = attackCoolDown;
	// inventory
	private Inventory inventory;
	// vector
	public static final int vectorDOWN = 0;
	public static final int vectorUP = 1;
	public static final int vectorLEFT = 2;
	public static final int vectorRIGHT = 3;
	public static int vector = vectorRIGHT;
	// bounds
	private Rectangle ar;
	// timer cho việc làm chậm đòn đánh, tg tick.
	private long timer = 0;
	private long timer2 = 0;
	private long timer3 = 0;

	// khởi tạo
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, 0, 0);
		// TODO Auto-generated constructor stub
		bounds.x = 23; // set bounds cho nhân vật
		bounds.y = 12;
		bounds.width = 16;
		bounds.height = 38;
		// animation initializing
		defendDown = new Animation(200, Assets.playerDefendDown);
		defendUP = new Animation(200, Assets.playerDefendUp);
		defendLeft = new Animation(200, Assets.playerDefendLeft);
		defendRight = new Animation(200, Assets.playerDefendRight);
		attackUp = new Animation(200, Assets.playerAttackUp);
		attackLeft = new Animation(200, Assets.playerAttackLeft);
		attackDown = new Animation(200, Assets.playerAttackDown);
		attackRight = new Animation(200, Assets.playerAttackRight);
		skillfulAttacking = new Animation(50, Assets.skillfulAttack);
		// inventory
		inventory = new Inventory(handler);
		// initilizing bounds khi tấn công
		Rectangle cb = getCollisionBounds(0, 0);
		ar = new Rectangle();
		int arSize = 60;
		ar.width = arSize;
		ar.height = arSize;
		if (handler.getKeyManager().aUp && !isExhausted) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
			setVector(vectorUP);
		} else if (handler.getKeyManager().aDown && !isExhausted) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
			setVector(vectorDOWN);
		} else if (handler.getKeyManager().aLeft && !isExhausted) {
			ar.x = cb.x - arSize;
			setVector(vectorLEFT);
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		} else if (handler.getKeyManager().aRight && !isExhausted) {
			ar.x = cb.x + cb.width;
			setVector(vectorRIGHT);
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		// Animation
		if (mana == 0) {
			isExhausted = true;
			isUsingSkill = false;
			isAttacking = false;
		} else if (mana >= 5) {
			isExhausted = false;
		}

		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)
				|| handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)
				|| handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)
				|| handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) {
			isAttacking = true;
			mana -= 1;
		} else {
			if (System.currentTimeMillis() - timer3 > 750) {
				isAttacking = false;
				timer3 = System.currentTimeMillis();
			}
		}
		defendDown.tick();
		defendUP.tick();
		defendLeft.tick();
		defendRight.tick();
		if (isAttacking) {
			attackRight.tick();
			attackDown.tick();
			attackUp.tick();
			attackLeft.tick();
		}
		skillfulAttacking.tick();
		getInput(); // thay đổi trong KeyListener -> thay đổi xMove, yMove,...
		move();
		handler.getGameCamera().centerOnEntity(this);
		checkAttacks();
		inventory.tick();
		usingBloodItem();
		usingEnergyItem();
		if (mana < 62) {
			if (System.currentTimeMillis() - timer > 1000) {
				mana += 1;
				timer = System.currentTimeMillis();
			}

		}
		if (mana >= 5 && isUsingSkill) {
			if (System.currentTimeMillis() - timer2 > 1000) {
				mana -= 5;
				timer2 = System.currentTimeMillis();
			}
		}
//		System.out.println(isExhausted);
//		System.out.println(mana);
	}

	// thay đổi move khi thao tác vs bàn phím
	private void getInput() {
		if (inventory.isActive())
			return;
		xMove = 0; // need
		yMove = 0; // need
		if (handler.getKeyManager().up) {
			yMove = -speed;
			isAttacking = false;
			setVector(vectorUP);
		}

		if (handler.getKeyManager().down) {
			yMove = speed;
			isAttacking = false;
			setVector(vectorDOWN);
		}

		if (handler.getKeyManager().left) {
			xMove = -speed;
			isAttacking = false;
			setVector(vectorLEFT);
		}

		if (handler.getKeyManager().right) {
			xMove = speed;
			isAttacking = false;
			setVector(vectorRIGHT);
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Q)) {
			isUsingSkill = !isUsingSkill;
			isAttacking = !isAttacking;
		}

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

		if (!isAttacking && !isUsingSkill) {
//			g.setColor(Color.red);
//			g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
//					(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
			g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), 50, 50, null);
		} else {

//			g.setColor(Color.red);
//			g.fillRect((int) (ar.x - handler.getGameCamera().getxOffset()),
//					(int) (ar.y - handler.getGameCamera().getyOffset()), ar.width, ar.height);
			g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), 50, 50, null);

		}
		// set máu, mana
		g.setColor(Color.black);
		g.fillRect(26, 585, 62, 30);
		g.setColor(Color.red);
		g.fillRect(26, 585, health, 5);
		g.setColor(Color.blue);
		g.fillRect(26, 590, mana, 5);
		g.drawImage(Assets.playerImage, 0, 534, null);

	}

	public void postRender(Graphics g) {
		inventory.render(g);
	}

	// using items.
	public void usingBloodItem() {
		if (inventory.isBloodUsed()) {
			inventory.usingBloodItem();
			inventory.setBloodUsed(false);
			if (health <= 52)
				health += 20;
			else
				health = 62;
		}
	}

	public void usingEnergyItem() {
		if (inventory.isEnergyUsed()) {
			inventory.usingEnergyItem();
			inventory.setEnergyUsed(false);
			if (mana <= 42)
				mana += 20;
			else
				mana = 62;
		}
	}

	// nếu nv chết -> chuyển trạng thái.
	public void die() {
		String name = JOptionPane.showInputDialog("Input your name: ");
		UserData.users.add(new User(name, String.valueOf(currentLevel)));
		State.setState(handler.getGame().gameOverState);
	}

	// kiểm tra tấn công
	public void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if (attackTimer < attackCoolDown) {
			return;
		}
		if (inventory.isActive()) {
			return;
		}
		Rectangle cb = getCollisionBounds(0, 0);
		ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;

		if (handler.getKeyManager().aUp && !isExhausted) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
			setVector(vectorUP);
		} else if (handler.getKeyManager().aDown && !isExhausted) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
			setVector(vectorDOWN);
		} else if (handler.getKeyManager().aLeft && !isExhausted) {
			ar.x = cb.x - arSize;
			setVector(vectorLEFT);
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		} else if (handler.getKeyManager().aRight && !isExhausted) {
			ar.x = cb.x + cb.width;
			setVector(vectorRIGHT);
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		} else if (isUsingSkill && !isExhausted) {
			ar.width = 50;
			ar.height = 50;
			ar.x = cb.x + cb.width / 2 - ar.width / 2;
			ar.y = cb.y + cb.height / 2 - ar.height / 2;
		}

		else {
			return;
		}

		attackTimer = 0;
		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this)) {
				continue;
			}
			if (e.getCollisionBounds(0, 0).intersects(ar)) {
				e.setBeingAttacked(true);
				if (isUsingSkill) {
					e.hurt(30);
				} else {
					e.hurt(10);
				}
				return;
			}
		}
	}

	// chưa sử dụng
	public void checkBeingAttacked() {

	}

	// lấy khung hình tạm thời cho animation
	private BufferedImage getCurrentAnimationFrame() {
		if (xMove < 0) {
			return defendLeft.getCurrentFrame();
		} else if (xMove > 0) {
			return defendRight.getCurrentFrame();
		} else if (yMove < 0) {
			return defendUP.getCurrentFrame();
		} else if (yMove > 0) {
			return defendDown.getCurrentFrame();
		} else if (isUsingSkill && !isExhausted) {
			return skillfulAttacking.getCurrentFrame();
		} else if (vector == vectorLEFT && isAttacking && !isExhausted) {
			return attackLeft.getCurrentFrame();
		} else if (vector == vectorRIGHT && isAttacking && !isExhausted) {
			return attackRight.getCurrentFrame();
		} else if (vector == vectorUP && isAttacking && !isExhausted) {
			return attackUp.getCurrentFrame();
		} else if (vector == vectorDOWN && isAttacking && !isExhausted) {
			return attackDown.getCurrentFrame();
		} else if (vector == vectorDOWN) {
			return Assets.playerDefendDown[0];
		} else if (vector == vectorUP) {
			return Assets.playerDefendUp[0];
		} else if (vector == vectorLEFT) {
			return Assets.playerDefendLeft[0];
		} else {
			return Assets.playerDefendRight[0];
		}
	}

	// get, set
	public static int getVector() {
		return vector;
	}

	public static void setVector(int vector) {
		Player.vector = vector;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public boolean isAttacking() {
		return isAttacking;
	}

	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
