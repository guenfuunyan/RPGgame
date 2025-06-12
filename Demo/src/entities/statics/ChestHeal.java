
package entities.statics;


import java.awt.Graphics;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.items.Item;

public class ChestHeal extends StaticEntity{
	public ChestHeal(Handler handler , float x, float y)
	{
		super(handler, x, y, 40, 40);
		// chinh lai sau
//		bounds.x = 3;
//		bounds.y = (int) (height/2f);
//		bounds.height =(int) (height - height/2f);
//		bounds.width = width -6;
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
		g.drawImage(Assets.chestheal, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width , height, null );
	}
	// tao item ma nhan vat co the nhat duoc sau khi chet
	public void die()
	{
		handler.getWorld().getItemManager().addItem(Item.healItem.creatNew((int)x, (int)y));

	}
}