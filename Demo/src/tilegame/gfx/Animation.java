package tilegame.gfx;

import java.awt.image.BufferedImage;

public class Animation {
	private int speed, index; // tốc độ 1 frame thay đổi(milisecond) + chỉ số của frame
	private BufferedImage[] frames; // mảng các frame
	long lastTime, timer;
	// khởi tạo animation
	public Animation(int speed, BufferedImage[] frames)
	{
		this.speed = speed;
		this.frames = frames;
		index = 0;
		lastTime = System.currentTimeMillis(); // đặt cho lasttime = thời gian thực
	}
	// tick 
	public void tick()
	{
		timer+= System.currentTimeMillis() - lastTime; // timer = thời gian thực  - thời gian thực frame trc
		lastTime = System.currentTimeMillis();
		if(timer > speed) //cứ sau một khoảng speed thì thay đổi frame
		{
			index++;
			timer = 0;
			if(index >= frames.length) // nếu frame vượt quá tối đa thì trở lại frame đầu tiên(tạo sự thông suốt)
			{
				index = 0;
			}
		}
	}
	public BufferedImage getCurrentFrame() // lấy frame hiện tại
	{
		return frames[index];
	}
}
