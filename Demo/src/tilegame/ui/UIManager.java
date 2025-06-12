package tilegame.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import tilegame.Handler;

public class UIManager {
	private Handler handler;
	private ArrayList<UIObject> objects; // 1 list các UI object
	public UIManager(Handler handler)
	{
		this.handler = handler;
		objects = new ArrayList<UIObject>();
	}
	// tick + render
	public void tick()
	{
		for(UIObject o: objects)
		{
			o.tick();
		}
	}
	public void render(Graphics g)
	{
		for(UIObject o: objects)
		{
			o.render(g);
		}
	}
	
	//mouse move đến button -> tạo  
	public void onMouseMove(MouseEvent e) //  (tạo thêm Interface, đang còn thiếu)
	{
		for(UIObject o: objects)
		{
			o.onMouseMove(e);
		}
	}
	// tạo Interface khi thả chuột 
	public void onMouseReleased(MouseEvent e) // (tạo thêm)
	{
		for(UIObject o: objects)
		{
			o.onMouseReleased(e);
		}
	}
	// get, set , add
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public ArrayList<UIObject> getObjects() {
		return objects;
	}
	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}
	public void addObject(UIObject o)
	{
		objects.add(o);
	}
	public void removeObject(UIObject o)
	{
		objects.remove(o);
	}
}
