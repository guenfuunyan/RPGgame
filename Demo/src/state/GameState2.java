package state;

import java.awt.Graphics;

import javax.swing.JOptionPane;

import entities.creatures.Monsters;
import entities.statics.ChestHeal;
import entities.statics.ChestMana;
import tilegame.Handler;
import tilegame.util.User;
import tilegame.util.UserData;
import tilegame.worlds.World;

public class GameState2 extends State {
	// load world
	private World world2;
	// khởi tạo level cho state
	private int currentLevel = 2;
	// khởi tạo
	public GameState2(Handler handler) {
		// TODO Auto-generated constructor stub
		super(handler);
		world2 = new World(handler, "res/World/World2");
		handler.setWorld(world2);
		world2.getEntityManager().getMonsters().setHealth(100);
		world2.getEntityManager().getMonsters().setSpeed(100);
		world2.getEntityManager().getMonsters().hurt(10);
		world2.getEntityManager().getPlayer().setSpeed(3.0f);
		// add thêm monsters vào đây!
		world2.getEntityManager().addEntity(new Monsters(handler, 288, 288));
		world2.getEntityManager().addEntity(new Monsters(handler, 352, 672));
		world2.getEntityManager().addEntity(new Monsters(handler, 736, 480));
		
		world2.getEntityManager().addEntity(new ChestMana(handler, 76, 656));
		world2.getEntityManager().addEntity(new ChestHeal(handler, 268, 848));
		world2.getEntityManager().addEntity(new ChestMana(handler, 588, 656));
		world2.getEntityManager().addEntity(new ChestHeal(handler, 588, 336));
	}

	// tick + render
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		world2.tick();
		if (isNewGame) {

		} else if (world2.getEntityManager().countMonstersLeft() > 0) {
			return; // còn quái vật thì return
		} else {
			String name = JOptionPane.showInputDialog("Input your name: ");
			UserData.users.add(new User(name, String.valueOf(currentLevel)));
			State.setState(world2.getHandler().getGame().winState);
			// hết quái vật -> win
			
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		world2.render(g);

	}

}