package state;

import java.awt.Color;
import java.awt.Graphics;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.ui.ClickListener;
import tilegame.ui.UIImageButton;
import tilegame.ui.UIManager;

public class LevelState extends State{
	private UIManager uiManager;
	// level demo
	public int currentLevel = 2;
	// timer
	private long timer = 0;
	// Khởi tạo
	public LevelState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		uiManager.addObject(new UIImageButton(300, 300, 64, 64, Assets.button, new ClickListener() {
			@Override
			// click thì sẽ chuyển sang gameState
			public void onClick() {
				// TODO Auto-generated method stub
				handler.getGame().gameState2 = new GameState2(handler);
				
					State.setState(handler.getGame().gameState2);

			}
		}));
		
	}

	// tick + render
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		uiManager.tick();
		handler.getMouseManager().setUiManager(uiManager);

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 800, 600);
		g.drawImage(Assets.topScoreImage, 0, 0, null);
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.YELLOW);
		g.setFont(g.getFont().deriveFont(30.0f));
		g.drawString("LEVEL : " + currentLevel, 300, 100);
		uiManager.render(g);
	}

}