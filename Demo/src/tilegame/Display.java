package tilegame;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;


public class Display {
	private JFrame frame;
	private Canvas canvas;
	private String title;
	private int width, height;
	public Display(String title, int width , int height)
	{
		this.title = title;
		this.width = width ;
		this.height = height;
		createDisplay();
	}
	private void createDisplay()
	{
		frame = new JFrame(title);
		frame.setSize(this.width, this.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // không cho phép thay đổi size của frame
		frame.setLocationRelativeTo(null); // set cửa sổ hiển thị lên vị trí giữa màn hình
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height)); // chọn size cho canvas mặc định là dài, rộng theo default
		canvas.setMaximumSize(new Dimension(width, height)); // size lúc nào cũng là width , height.
		canvas.setMinimumSize(new Dimension(width, height));  
		
		canvas.setFocusable(false); // very important // không cho tập trung vào bất cứ object nào
		frame.add(canvas);
		frame.pack(); // can thiet // tự động chuyển đổi size của các object nếu framesize thay đổi.
	}
	public Canvas getCanvas() {
		return canvas;
	}
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	public JFrame getFrame()
	{
		return frame;
	}
}
