package tilegame.items;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import tilegame.Handler;

public class ItemManager {
	private Handler handler;
	private ArrayList<Item> items;
	public ItemManager(Handler handler)
	{
		this.handler = handler;
		items = new ArrayList<Item>();
	}
	// tick + render
	public void tick()
	{
		// giong voi cac entity, de tranh mat mat khi goi vong lap, ta nen su dung iterator
		Iterator<Item> it = items.iterator();
		while(it.hasNext())
		{
			Item i = it.next();
			i.tick();
			if(i.isPickedUp()) // neu pickup -> xoa item khoi man hinh
			{
				it.remove();
			}
		}
	}
	public void render(Graphics g)
	{
		for(Item i : items)
		{
			i.render(g);
		}
	}
	// add
	public void addItem(Item i)
	{
		i.setHandler(handler);
		items.add(i);
	}
	// get, set 
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
}
