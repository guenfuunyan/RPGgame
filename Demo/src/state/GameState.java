package state;

import java.awt.Graphics;

import entities.creatures.Monsters;
import entities.statics.ChestHeal;
import entities.statics.ChestMana;
import tilegame.Handler;
import tilegame.worlds.World;

public class GameState extends State{
	// load world
	private World world;
	// khởi tạo
	public GameState(Handler handler) {
		// TODO Auto-generated constructor stub
		super(handler);
		world = new World(handler, "res/World/World1");
		handler.setWorld(world);
		world.getEntityManager().addEntity(new ChestMana(handler, 76, 592));
		world.getEntityManager().addEntity(new ChestHeal(handler, 524, 464));
		world.getEntityManager().addEntity(new ChestMana(handler, 844, 720));
		world.getEntityManager().addEntity(new ChestHeal(handler, 844, 336));

		world.getEntityManager().addEntity(new Monsters(handler, 92, 352));
		world.getEntityManager().addEntity(new Monsters(handler, 288, 736));
		world.getEntityManager().addEntity(new Monsters(handler, 608, 672));
		
		
	}

	// tick + render
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		world.tick();
		if (isNewGame) {

		} else if (world.getEntityManager().countMonstersLeft() > 0) {
			return; // còn quái vật thì return
		} else {
				State.setState(world.getHandler().getGame().levelState);
		
			
			// hết quái vật -> nhay level
			
			
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		world.render(g);
	}

}