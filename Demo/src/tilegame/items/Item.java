package tilegame.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.gfx.Assets;


public class Item{
	// item handler 
	public static Item[] items = new Item[200];
	public static Item manaItem = new Item(Assets.mana, "Mana", 0);
	public static Item healItem = new Item(Assets.heal, "Heal", 1);
	
	// class
	public static int ITEM_WIDTH = 32, ITEM_HEIGHT = 32;
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	protected Rectangle bounds;
	protected int x, y , count;
	protected boolean pickedUp = false;
	// khởi tạo
	public Item(BufferedImage texture, String name, int id)
	{
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
		items[id] = this;
		bounds = new Rectangle(x, y ,ITEM_WIDTH , ITEM_HEIGHT);
	}
	// tick + render
	public void tick()
	{
		// ticking cho viec pickupitem
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds))
		{
			pickedUp = true;
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
		}
	}
	public void render(Graphics g){
		if(handler == null) return ;
			render(g, (int) ( x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()));
			
	}
	public void render(Graphics g, int x, int y)
	{
		g.drawImage(texture, x, y, ITEM_WIDTH, ITEM_HEIGHT , null);
	}
	// add item
	public Item creatNew (int x, int y)
	{
		// tao item moi, ham chinh
		Item i = new Item(texture, name, id);
		i.setPosition(x, y);
		
		return i;
	}
	public Item creatNew (int count)
	{
		// neu item chua co thi tao mot item moi, ham kiem tra
		Item i = new Item(texture, name, id);
		setPickedUp(true);
		setCount(count);
		return i;
	}
	public void setPosition(int x, int y)
	{
		// set cho ca item va bounds cua no
		// muc dich thiet lap bounds la khi nv va cham vs bound -> item bien mat(nhat item)
		this.x = x;
		this.y = y;
		bounds.x = x ;
		bounds.y = y;
	}

	// get, set
	
	public Handler getHandler() {
		return handler;
	}
	public boolean isPickedUp() {
		return pickedUp;
	}
	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public BufferedImage getTexture() {
		return texture;
	}
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getId() {
		return id;
	}

}
