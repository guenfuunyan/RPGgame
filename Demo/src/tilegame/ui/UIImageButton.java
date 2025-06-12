package tilegame.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject{
	private BufferedImage[] images;
	private ClickListener clicker;
	
	// khởi tạo 
	public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		this.images = images;
		this.clicker = clicker;
		
	}
 // tick + render
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		// chuyển đổi giữa hai trạng thái button khi có hovering.
		if(hovering && images.length > 1)
		{
			g.drawImage(images[1] , (int) x, (int) y , width , height, null);
		}
		else 
			g.drawImage(images[0] , (int) x, (int) y , width , height, null);
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		clicker.onClick();
	}

}
