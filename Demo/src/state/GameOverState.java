package state;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.ui.ClickListener;
import tilegame.ui.UIImageButton;
import tilegame.ui.UIManager;

public class GameOverState extends State{
	// UI
	private UIManager uiManager;
	// khởi tạo 
	public GameOverState(Handler handler)
	{
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		uiManager.addObject(new UIImageButton(0, 0, 800, 600,Assets.lose, new ClickListener() {
			
			@Override
			// click chuyển về menu
			public void onClick() {
				State.setState(handler.getGame().scoreState);
			}
		}));
	}
	// tick + render
	@Override
	public void tick() {
		
		uiManager.tick();
		handler.getMouseManager().setUiManager(uiManager); 
		State.setState(handler.getGame().gameOverState);
	}

	@Override
	public void render(Graphics g) {
		
		g.drawString("GAME OVER!", 100, 100);
		uiManager.render(g);
	}
	
}
