package state;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.ui.ClickListener;
import tilegame.ui.UIImageButton;
import tilegame.ui.UIManager;
import tilegame.util.User;
import tilegame.util.UserData;

public class ScoreState extends State {
	private UIManager uiManager; // cần có UI để tác động vs onclick
	// khởi tạo
	private int currentLevel = 1;
	public UserData ud;
	public ScoreState(Handler handler) {
		super(handler);
		ud = new UserData();
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		uiManager.addObject(new UIImageButton(0, 0, 800, 600, Assets.win, new ClickListener() {

			@Override
			// click chuột tự động chuyển sang menu
			public void onClick() {
				// TODO Auto-generated method stub

				State.setState(handler.getGame().menuState);

			}
		}));
	}

	// tick + render
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		uiManager.tick();
		handler.getMouseManager().setUiManager(uiManager);
		// set handler có UIManager là : uiManager
		// nếu để null thì sẽ ko tác động đc lên UI Object(nút button ko đổi, ko vào đc
		// game)
//		State.setState(handler.getGame().menuState); // nếu để là gameState thì sẽ vào game luôn.

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		g.drawImage(Assets.topScoreImage, 0, 0, null);
		g.setColor(Color.YELLOW);
		g.setFont(g.getFont().deriveFont(28.0f));
		g.drawString("LEVEL : " + currentLevel, 150, 120);
		g.setColor(Color.RED);
		for (int i = 0; i < UserData.users.size(); i++) {
			g.drawString(UserData.users.get(i).toString(), 350, i * 50 + 200);
		}
	}

}
