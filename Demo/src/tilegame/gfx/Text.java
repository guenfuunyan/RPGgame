package tilegame.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Text {
	// set font chữ , vị trí đặt chữ.
	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color c, Font font)
	{
		g.setColor(c);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
	// set chữ ra trung tâm
		if(center)
		{
			FontMetrics fm = g.getFontMetrics(font);
			x = xPos - fm.stringWidth(text) / 2; // căn giữa cho text theo chiều ngang
			y = (yPos - fm.getHeight()/2 ) + fm.getAscent(); // căn giữa cho text theo chiều dọc
		}
		g.drawString(text, x, y);
	}
}
