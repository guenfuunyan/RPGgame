package tilegame.gfx;

import entities.Entity;
import tilegame.Handler;
import tilegame.tiles.Tile;
// thực chất gamecamera là lớp để dịch chuyển các entity lệch đi một khoảng offset(- offset là dịch trái; +offset là dịch phải)
// để nhân vật luôn nằm ở giữa màn hình
public class GameCamera {
	private Handler handler;
	private float xOffset, yOffset;  // độ lệch giữa entity khác với nhân vật
	public GameCamera(Handler handler, float xOffset, float yOffset)
	{
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		
	}
	//tinh chỉnh cho thực thể nằm ở giữa màn hình
	public void centerOnEntity(Entity e)
	{
		// độ lệch so với tâm của nhân vật khi nhân vật di chuyển
		xOffset = e.getX() + e.getWidth()/2 - handler.getWidth()/2 ; // giữa x của nhân vật - giữa x frame game
		yOffset = e.getY() + e.getHeight()/2- handler.getHeight()/2 ; // tg tự
		checkBlankSpace();
	}
	// 
	public void move(float xAmt, float yAmt)
	{
		xOffset += xAmt; 
		yOffset += yAmt;
		checkBlankSpace();
	}
	// hàm này dùng để kiểm tra, nếu nhân vật đang ở screen mà không còn bản đồ nữa thì nhân vật
	// sẽ tự do di chuyển ko cần camera(camera mặc định là ở giữa screen)
	// 
	public void checkBlankSpace()
	{
		if(xOffset < 0) // nhân vật đang ở trái so với giữa frame -> chỉnh về giữa frame.
		{
			xOffset = 0;
		}
		// khoảng lệch lớn hơn so với một screen quy định -> chỉnh về đúng khoảng lệch đó 
		else if(xOffset > handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth())
		{
			xOffset = handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth();
		}
		if(yOffset < 0)
		{
			yOffset = 0;
		}
		else if(yOffset > handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight())
		{
			yOffset = handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight();
		}
	}
	// get ,set
	public float getxOffset() {
		return xOffset;
	}
	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}
	public float getyOffset() {
		return yOffset;
	}
	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
}
