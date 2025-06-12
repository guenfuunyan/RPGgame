package entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import entities.creatures.Monsters;
import entities.creatures.Player;
import tilegame.Handler;

public class EntityManager {
	public int point = 0;
	private Player player;
	private Monsters monsters;
	private ArrayList<Entity> entities;
	private long timer = 0;
	// list so sánh các thực thể, chủ yếu để check entities nào sẽ đc load trc(nhân
	// vật sẽ ở sau hay trc các entities)
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity a, Entity b) {
			// TODO Auto-generated method stub
			// kiểm tra góc dưới phải entity <-> entity nào sẽ được vẽ trc
			if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1; // a trc b
			else
				return 1; // b trc a
		}
	};

	// khởi tạo
	public EntityManager(Handler handler, Player player, Monsters monsters) {
		this.player = player;
		entities = new ArrayList<Entity>();
		addEntity(player);
		this.monsters = monsters;
		addEntity(monsters);
	}

	// tick + render
	public void tick() {
		// dùng iterator sẽ an toàn hơn for. Do khi tick có thể sẽ gây thiếu sót khi kiểm tra đk trong vòng for.
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) // còn element thì còn duyệt
		{
			Entity e = it.next();
			e.tick();
			if (!e.isActive()) {
				it.remove(); // ko hoạt động thì remove
			}
		}
		entities.sort(renderSorter);
	}
	// đếm số monsters còn lại
	public int countMonstersLeft() {
		int count = 0;
		for (Entity e : entities) {
			if (e instanceof Monsters)
				count++;
		}
//		System.out.println(count);
		
		return count;
		
	}
	// (chưa sử dụng isBeingAttacked, cần thêm hoạt động)
	public void render(Graphics g) {
		for (Entity e : entities) {
			if (e instanceof Monsters && e.isBeingAttacked) {
				if (System.currentTimeMillis() - timer > 500) {
					e.render(g);
					timer = System.currentTimeMillis();
				}
				e.setBeingAttacked(false);
			}
			else
				e.render(g);
		}
		player.postRender(g); 
	}

	// Get, set, add
	public void addEntity(Entity e) {
		entities.add(e);
	}
	public Monsters getMonsters() {
		return monsters;
	}

	public void setMonsters(Monsters monsters) {
		this.monsters = monsters;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
}
