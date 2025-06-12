package state;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.ui.ClickListener;
import tilegame.ui.UIImageButton;
import tilegame.ui.UIManager;

public class MenuState extends State {
	// UI
	private UIManager uiManager;

	// Khởi tạo
	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);

		//them anh menu
		uiManager.addObject(new UIImageButton(0, 0, 800, 600, Assets.menuImage, new ClickListener() {
			@Override
			// click thì sẽ chuyển sang gameState
			public void onClick() {
			}
		}));

		//them chu vao anh menu
		uiManager.addObject(new UIImageButton(400 - 80, 400, 200, 50, Assets.menuText, new ClickListener() {
			@Override
			// click thì sẽ chuyển sang gameState
			public void onClick() {
				System.out.print("B");
				// TODO Auto-generated method stub
				handler.getGame().gameState = new GameState(handler);
				State.setState(handler.getGame().gameState);
			}
		}));
		uiManager.addObject(new UIImageButton(400 - 80, 450, 200, 50, Assets.exitbutton, new ClickListener() {
			@Override
			// click thì sẽ chuyển sang end
			public void onClick() {
				// TODO Auto-generated method stub
				System.exit(0);
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
		uiManager.render(g);

	}
}
