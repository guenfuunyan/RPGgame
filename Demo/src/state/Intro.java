package state;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.ui.ClickListener;
import tilegame.ui.UIImageButton;
import tilegame.ui.UIManager;

import java.awt.*;

public class Intro extends State  {
    private UIManager uiManager;

    public Intro(Handler handler){
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        uiManager.addObject(new UIImageButton(0, 0, 800, 600, Assets.intro, new ClickListener() {
            @Override
            // click thì sẽ chuyển sang gameStatemenuState
            public void onClick() {
                // TODO Auto-generated method stub
            handler.getGame().menuState = new MenuState(handler);
            		 State.setState(handler.getGame().menuState);
            	
            }
        }));
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        uiManager.tick();
        handler.getMouseManager().setUiManager(uiManager);
  
    }

    @Override
    public void render(Graphics g) {
        uiManager.render(g);
    }
}
