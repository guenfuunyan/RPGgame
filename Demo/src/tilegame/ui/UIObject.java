package tilegame.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
// lớp tg tác vs các UI
public abstract class UIObject {
	protected float x, y; //set vị trí các Object tg tác (button, ...)
	protected int width , height; // set độ rộng, độ cao
	protected Rectangle bounds; // set bounds cho button
	protected boolean hovering = false; // kiểm tra chuột có nằm trên button ko
	// khởi tạo 
	public UIObject(float x , float y, int width , int height)
	{
		this.x = x;
		this.height  = height;
		this.y = y;
		this.width = width;
		bounds = new Rectangle((int) x, (int) y , width, height);
	}
	// abstract methods
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void onClick();
	// kiểm tra xem khi di chuyển, chuột có nằm trên button hay ko.
	public void onMouseMove(MouseEvent e)
	{
		if(bounds.contains(e.getX(), e.getY()))
			hovering = true;
		else 
			hovering = false;
	}
	// kiểm tra nếu mouseRelease tức là đã click chuột
	public void onMouseReleased(MouseEvent e)
	{
		if(hovering ) 
			onClick();
		
	}
	// get, set
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
	public boolean isHovering() {
		return hovering;
	}
	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}
	
}
