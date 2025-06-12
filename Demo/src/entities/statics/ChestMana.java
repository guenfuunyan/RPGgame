package entities.statics;

import java.awt.Graphics;

import entities.Entity;
import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.items.Item;

public class ChestMana extends Entity{
	public ChestMana(Handler handler, float x, float y)
	{
		super(handler, x, y, 40 , 40 );
		// 
		// chinh lai sau
//		bounds.x = 10;
//		bounds.y = (int) (height/1.5f);
//		bounds.width = width - 20;
//		bounds.height = (int) (height - height / 1.5f); 
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
	}

	// tick + render
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.chestmana, (int) (x - handler.getGameCamera().getxOffset()), (int) (y- handler.getGameCamera().getyOffset()), width , height, null);
		
	}
	public void die()
	{
		handler.getWorld().getItemManager().addItem(Item.manaItem.creatNew((int)x, (int)y));
	}
}

