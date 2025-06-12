package entities.creatures;

import entities.Entity;
import tilegame.Handler;
import tilegame.tiles.Tile;

public abstract class Creature extends Entity {
	// data entity
	public static final float DEFAULT_SPEED = 4.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 40;
	public static final int DEFAULT_CREATURE_HEIGHT = 40;
	protected float speed;
	protected float xMove, yMove;

	// khởi tạo
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height); // goi khoi tao cho class Entity
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;

	}

	// ham move co chuc nang kiem tra va cham, neu ko co va cham se thuc hien move
	// theo x , y
	public void move() {
		if (!checkEntityCollision(xMove, 0f))// ngoai viec kiem tra va cham voi tile phai chac chan la
			// no khong va cham voi cac entity khac -> moi move duoc.
			moveX(); // x tang , co kiem tra collision
		if (!checkEntityCollision(0f, yMove))
			moveY(); // y tang, co kiem tra collision
	}

	public void moveX() {
		// x + bounds.x la toa do bat dau cua bound , so voi toan bo frame
		// kiem tra dieu kien va cham de co the thuc thi lenh move hay khong
		if (xMove > 0) // moving right
		{
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH; // delta dich chuyen cua bound so
																					// voi frame
			// tx tra ra gia tri cua tile ma Entity dang chuan bi va cham vao.
			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) && // goc tren phai cua tile
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) // goc duoi phai
																										// cua Tile
			{
				x += xMove; // neu va cham khong xay ra giua goc tren , goc duoi cua Entity voi Tiel thi x
				// moi move;
			} else {
				// mot vai pixel bi lech va khi va cham, bound khong cham toi solidtile.
				// do do chung ta phai chinh cho bound vua khit so voi tile ta va cham.
				x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
				// - 1 pixel de co the move up, down muot ma hon (tranh truong hop va cham va bi
				// stuck trong solidtile do)
			}
		}

		else if (xMove < 0) // moving left
		{
			int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;
			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) && // goc tren trai
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) // goc duoi trai
			{
				x += xMove;

			} else {
				x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
				// dang o Tile va cham, ta phai cong them 1 tile nua moi lay duoc bound do x o
				// goc tren ben trai
			}
		}
	}

	// tuong tu voi moveY
	public void moveY() {
		if (yMove < 0) // up
		{
			int ty = (int) (y + bounds.y + yMove) / Tile.TILE_HEIGHT;
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
				y += yMove;

			} else {
				y = ty * Tile.TILE_HEIGHT - bounds.y + Tile.TILE_HEIGHT;
			}
		} else if (yMove > 0) // down
		{
			int ty = (int) (y + bounds.y + yMove + bounds.height) / Tile.TILE_HEIGHT;
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
				y += yMove;

			} else {
				y = ty * Tile.TILE_HEIGHT - bounds.height - bounds.y - 1;
			}
		}
	}

	// neu tile la solid thi tra ra true
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}

	// get,set
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public static float getDefaultSpeed() {
		return DEFAULT_SPEED;
	}

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}
}
