package tilegame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import state.*;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.input.KeyManager;
import tilegame.input.MouseManager;

public class Game implements Runnable {
	private Display display;
	private int width, height;
	private Thread thread;
	public String title;
	private boolean running = false;
	private BufferStrategy bs; // how should we draw thing
	// buffer is like a hidden screen
	private Graphics g;
	// state
	public static State gameState; // to access easier
	public State gameState2;
	public State menuState;
	public State winState;
	public State gameOverState;
	public State Intro;
	public State levelState;
	public State scoreState;
	// input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	// camera
	private GameCamera gameCamera;
	// handler
	private Handler handler;

	// khoi tao
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}

	// initializing
	public void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager); // lang nghe chuyen dong cua chuot
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager); // muon cho chuot co the su dung ca tren frame va
																	// canvas
		Assets.init(); // khoi tao kho du lieu hinh anh, am thanh, ...
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		menuState = new MenuState(handler);
		winState = new WinState(handler);
		gameOverState = new GameOverState(handler);
		levelState = new LevelState(handler);
		scoreState = new ScoreState(handler);	
		Intro = new Intro(handler);
		State.setState(Intro);
	}

	// tick + render
	private void tick() {
		keyManager.tick();

		if (State.getState() != null) // important
			State.getState().tick(); // cap nhat giua cac state
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy(); // tạo một bộ đệm để lưu trữ hình ảnh, object(chưa in ra màn hình)
		if (bs == null) // if has no bufferstrategy
		{
			display.getCanvas().createBufferStrategy(3); // tao ra 3 trang thai
			return;
		}
		g = bs.getDrawGraphics();
		// Clear Screen
		g.clearRect(0, 0, width, height);
		// Draw Here
		if (State.getState() != null) // important
			State.getState().render(g);
		// End drawing
		bs.show(); // need // khi bộ đệm đã sẵn sàng, tiến hành in hình ảnh, dữ liệu ra màn hình.
		g.dispose(); // need // giải phóng graphics đã bị giữ lại trong bufferstrategy
	}

// run thread
	public void run() {
		init();
		int fps = 60; // 60 frame per second
		double timePerTick = 1000000000 / fps; // nanosecond // maximum time we allow
		double delta = 0;
		long now;
		long lastTime = System.nanoTime(); // return current time but in nanosecond;
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				delta--;
			}
			// tat ca chi de dam bao 1s se co 60 frame dc ticked
		}
		stop();
	}

	// synchronizing
	// đồng bộ hóa luồng: chỉ cho phép một luồng được chạy, giúp giảm sai sót khi display dữ liệu
	public synchronized void start() { // phuong thuc nay se duoc goi duy nhat trong class GameLauncher
		if (running)
			return; // neu game da run roi thi ko can start nua
		running = true;
		thread = new Thread(this); // this cuc ki quan trong
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return; // neu game da dung thi ko can stop nua
		running = false;
		try {
			thread.join(); // stop
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// get,set
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public void setMouseManager(MouseManager mouseManager) {
		this.mouseManager = mouseManager;
	}
}
