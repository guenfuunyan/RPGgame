package entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import entities.creatures.Monsters;
import tilegame.Handler;

public abstract class Entity {
	// Entity
	protected float x, y; // tọa độ vật thể, để float để có độ cxac cao hơn
	protected int width, height; 
	protected int health;
	protected int mana;
	protected boolean isBeingAttacked = false;
	public static final int DEFAULT_HEALTH = 62;
	public static final int DEFAULT_POWER = 62;
	protected boolean active = true; // check entity có đang hoạt động hay ko(entity động)
	protected Rectangle bounds; 
	// handler
	protected Handler handler; // bộ xử lí các entity.
	// khởi tạo
	public Entity(Handler handler, float x, float y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = DEFAULT_HEALTH;
		mana = DEFAULT_POWER;
		bounds = new Rectangle(0, 0, width, height); // khởi tạo bounds từ góc trái của đối tg.
	}

	// kiểm tra va chạm, phục vụ cho việc check nhân vật có bị cản bởi entity khác ko.
	public boolean checkEntityCollision(float xOffset, float yOffset) {
		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this))
				continue; // đảm bảo entity ko bị trùng vs chính nó.
			if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				// kiểm tra va chạm giữa các entities.
				return true;
		}
		return false;
	}

	// lấy rectangle của bounds của entity va chạm, cần thêm offset vì thực thể có thể di chuyển.
	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width,
				bounds.height);
	}
	// phục vụ cho việc tấn công
	public void hurt(int amt) {
		health -= amt; // mỗi lần hurt - đi 1 lg máu.
		isBeingAttacked = true;
		if (health <= 0) {
			active = false; // set cho ko còn hoạt động nữa -> sau đó sẽ remove
			die();
		}
	}
	
	public boolean isMonster() {
		return this instanceof Monsters;
	}

	// get, set
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isBeingAttacked() {
		return isBeingAttacked;
	}

	public void setBeingAttacked(boolean isBeingAttacked) {
		this.isBeingAttacked = isBeingAttacked;
	}

	// abstract methods.
	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract void die();
}
