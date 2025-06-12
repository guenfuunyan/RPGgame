package entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.Entity;
import tilegame.Handler;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.items.Item;
import tilegame.tiles.Tile;

public class Monsters extends Creature {
	// Animation
	private Animation goUP;
	private Animation goDown;
	private Animation goRight;
	private Animation goLeft;
	private Animation attackingLeft, attackingRight, attackingUp, attackingDown;
	// vector
	public static final int vectorDOWN = 0;
	public static final int vectorUP = 1;
	public static final int vectorLEFT = 2;
	public static final int vectorRIGHT = 3;
	public static int vector = vectorRIGHT;
	// condition
	private long lastAttackTimer, attackCoolDown = 800, attackTimer = attackCoolDown;
	public boolean isCrazy = true, isShutDown = false;
	// timer
	private long timer1 = 0;
	// bounds
	private Rectangle ar;

	// khởi tạo
	public Monsters(Handler handler, float x, float y) {
		super(handler, x, y, 0, 0);
		// TODO Auto-generated constructor stub
		bounds.x = 6;
		bounds.y = 6;
		bounds.width = 21;
		bounds.height = 21;
		// animation initializing
		goDown = new Animation(200, Assets.monsterGoDown);
		goLeft = new Animation(200, Assets.monsterGoLeft);
		goRight = new Animation(200, Assets.monsterGoRight);
		goUP = new Animation(200, Assets.monsterGoUP);
		attackingDown = new Animation(200, Assets.monsterAttackDown);
		attackingLeft = new Animation(200, Assets.monsterAttackLeft);
		attackingRight = new Animation(200, Assets.monsterAttackRight);
		attackingUp = new Animation(200, Assets.monsterAttackUP);
		// set bounds cho attack
		Rectangle cb = getCollisionBounds(0, 0);
		ar = new Rectangle();
		int arSize = 100;
		ar.width = arSize;
		ar.height = arSize;
		ar.x = (int) (cb.x + cb.width / 2 - arSize / 2);
		ar.y = (int) (cb.y + cb.height / 2 - arSize / 2);
	}

	// tick + render
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		// Animation
		attackingDown.tick();
		attackingLeft.tick();
		attackingRight.tick();
		attackingUp.tick();
		goLeft.tick();
		goUP.tick();
		goRight.tick();
		goDown.tick();
		checkAttacks();
		// check hướng bắn
		float x1 = handler.getWorld().getEntityManager().getPlayer().getX();
		float y1 = handler.getWorld().getEntityManager().getPlayer().getY();
		float e1 = (x + bounds.x + bounds.width - handler.getGameCamera().getxOffset()) / 2;
		float e2 = (y + bounds.y + bounds.height - handler.getGameCamera().getyOffset()) / 2;
		if (isEnemy(x1, y1)) {
			// (set lại hướng, chưa đúng)
			xMove = 0;
			yMove = 0;
			if ((y1 - e2) > -(x1 - e1) && (y1 - e2) < (x1 - e1))
				setVector(3);
			else if ((y1 - e2) >= -(x1 - e1) && (y1 - e2) >= (x1 - e1))
				setVector(0);
			else if ((y1 - e2) > (x1 - e1) && (y1 - e2) < -(x1 - e1))
				setVector(2);
			else
				setVector(1);
		} else {
			if (System.currentTimeMillis() - timer1 > 200) {
//				getInput();
				move();
				setVector(setRandomVector());
				setMove();
				move();
				if (setVectorWhenCollision()) {
					setMove();
				}
				timer1 = System.currentTimeMillis();
			}
		}

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

		if (!isCrazy) {
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
	}

	// cheking attack
	public void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if (attackTimer < attackCoolDown) {
			return;
		}
		Rectangle cb = getCollisionBounds(0, 0);
		ar = new Rectangle();
		int arSize = 100;
		ar.width = arSize;
		ar.height = arSize;
		ar.x = (int) (cb.x + cb.width / 2 - arSize / 2);
		ar.y = (int) (cb.y + cb.height / 2 - arSize / 2);

		attackTimer = 0;
		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this)) {
				continue;
			}
			if (e.getCollisionBounds(0, 0).intersects(ar) && isCrazy) {
				e.setBeingAttacked(true);
				e.hurt(5);
				return;
			}
		}
	}

	// di chuyển của quái vật
	public void setMove() {
		xMove = 0;
		yMove = 0;
		if (vector == vectorDOWN) {
			yMove += 2;
		} else if (vector == vectorUP) {
			yMove -= 2;
		} else if (vector == vectorLEFT) {
			xMove -= 2;
		} else {
			xMove += 2;
		}
	}

	// thay đổi hướng khi va chạm với entities.
	public boolean setVectorWhenCollision() {
		if (xMove > 0) // moving right
		{
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;

			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
				return false;
			} else {
				vector = vectorLEFT;
//					x += xMove;
				return true;
			}
		}

		else if (xMove < 0) // moving left
		{
			int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;
			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) && // goc tren trai
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) // goc duoi trai
			{
				return false;

			} else {
//				x+=xMove;
				vector = vectorRIGHT;
				return true;
			}
		} else if (yMove < 0) // up
		{
			int ty = (int) (y + bounds.y + yMove) / Tile.TILE_HEIGHT;
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
				return false;

			} else {
				vector = vectorDOWN;
//				y+=yMove;
				return true;
			}
		} else if (yMove > 0) // down
		{
			int ty = (int) (y + bounds.y + yMove + bounds.height) / Tile.TILE_HEIGHT;
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
				return false;

			} else {
				vector = vectorUP;
//				y+=yMove;
				return true;
			}
		} else
			return false;
	}

	// tạo ra vector ngẫu nhiên từ 0 -> 3 để nv tự do di chuyển.
	public int setRandomVector() {
		Random rd = new Random();
		int x = rd.nextInt(3);
		return x;
	}

	// trong tầm bắn thì sẽ chuyển trạng thái từ phòng thủ sang tấn công.
	public boolean isEnemy(float x1, float y1) {
		double distance = Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
//		System.out.println(distance);
		if (distance < 70) {
			isCrazy = true;
			return true;
		} else {
			isCrazy = false;
			return false;
		}
	}

	@Override
	// khi chết trả ra item để nv có thể thu nhặt
	public void die() {
		handler.getWorld().getItemManager().addItem(Item.healItem.creatNew((int) x, (int) y));
		handler.getWorld().getEntityManager().setPoint(handler.getWorld().getEntityManager().getPoint() + 1);
		System.out.println(handler.getWorld().getEntityManager().getPoint());
	}

	// tg tự player
	private BufferedImage getCurrentAnimationFrame() {
		if (xMove < 0) {
			return goLeft.getCurrentFrame();
		} else if (xMove > 0) {
			return goRight.getCurrentFrame();
		} else if (yMove < 0) {
			return goUP.getCurrentFrame();
		} else if (yMove > 0) {
			return goDown.getCurrentFrame();
		} else if (vector == vectorLEFT && isCrazy) {
			return attackingLeft.getCurrentFrame();
		} else if (vector == vectorRIGHT && isCrazy) {
			return attackingRight.getCurrentFrame();
		} else if (vector == vectorUP && isCrazy) {
			return attackingUp.getCurrentFrame();
		} else if (vector == vectorDOWN && isCrazy) {
			return attackingDown.getCurrentFrame();
		} else if (vector == vectorDOWN) {
			return Assets.monsterGoDown[0];
		} else if (vector == vectorUP) {
			return Assets.monsterGoUP[0];
		} else if (vector == vectorLEFT) {
			return Assets.monsterGoLeft[0];
		} else {
			return Assets.monsterGoRight[0];
		}
	}

	// get,set
	public static int getVector() {
		return vector;
	}

	public static void setVector(int vector) {
		Monsters.vector = vector;
	}
}
