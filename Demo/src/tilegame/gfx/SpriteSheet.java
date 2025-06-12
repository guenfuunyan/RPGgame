package tilegame.gfx;

import java.awt.image.BufferedImage;
// lớp cắt ảnh, lấy ảnh
public class SpriteSheet {
	private BufferedImage sheet; 
	public SpriteSheet(BufferedImage sheet)
	{
		this.sheet = sheet;
	}
	// cắt ảnh từ ảnh gốc
	public BufferedImage crop(int x, int y, int width, int height)
	{
		return sheet.getSubimage(x, y, width, height);
	}
}
