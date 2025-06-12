package tilegame.worlds;

import java.awt.Graphics;

import entities.Entity;
import entities.EntityManager;
import entities.creatures.Monsters;
import entities.creatures.Player;

import tilegame.Handler;
import tilegame.items.ItemManager;
import tilegame.tiles.Tile;
import tilegame.util.Utils;

// class World chứa tất cả dữ liệu liên quan để tạo ra nhân vật, bản đồ, item, ...
public class World {
	// handler
	private Handler handler;
	// entity
	private EntityManager entityManager;
	private Entity entity;
	// item
	private ItemManager itemManager;
	// map
	private int width, height; //độ rộng, độ dài map
	private int spawnX, spawnY; // vị trí đầu tiên mà ta spawn người chơi
	private int[][] tiles; // mảng 2 chiều chứa các dữ liệu liên quan đến tiles(các mảnh ghép để tạo map) 
	
	// khởi tạo
	public World(Handler handler, String path) {
		this.handler = handler;
		
		entityManager = new EntityManager(handler, new Player(handler, 100, 100),
				new Monsters(handler, 736, 160));
		//Da them trong GameState - Duc
//		entityManager.addEntity(new ChestMana(handler, 76, 592));
//		entityManager.addEntity(new ChestHeal(handler, 524, 464));
//		entityManager.addEntity(new ChestMana(handler, 844, 720));
//		entityManager.addEntity(new ChestHeal(handler, 844, 336));
//
//		entityManager.addEntity(new Monsters(handler, 350, 144));
//		entityManager.addEntity(new Monsters(handler, 200, 200));
//		entityManager.addEntity(new Monsters(handler, 400, 400));
//		entityManager.addEntity(new Monsters(handler, 600, 600));
//		
		itemManager = new ItemManager(handler);
		// load mảng dữ liệu 
		loadWorld(path);
		// vị trí thả rơi đầu tiên của nhân vật
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}

	// tick + render
	public void tick() {
		// update toàn bộ thực thể và các item đi kèm
		itemManager.tick();
		entityManager.tick();
	}

	public void render(Graphics g) {
		//offset lớn hơn độ rộng 1 khung hình thi mới tiến hành render lại 1 khung mới
		// sẽ chỉ load những tiles trong 1 frame, còn lại sẽ chờ để đc load.
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);// lấy max của 0, vs offset/ tilewidth
		// nếu lớn hơn 0 tức là đã vượt quá 1 frame hình -> xStart =1 
		
		int xEnd = (int) Math.min(width,
				(handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1); // xEnd sẽ lớn hơn xStart số tiles quy định trong 1 frame
		// tg tự
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
		int yEnd = (int) Math.min(height,
				(handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);
		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				// offset = 0 thì render player ra giữa màn hình
				// offset != 0 thì điều chỉnh offset đi 1 khoảng lệch để đảm bảo vị trí tg đối giữa tiles và player
				// offset có thể dg, âm or = 0
				getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
						(int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		// item
		itemManager.render(g);
		// entity
		entityManager.render(g);
	}

	// trả ra tile tại hàng ,cột x, y
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x > width || y > height)
			return Tile.grassTile; // mặc định nếu vượt quá default thì trả ra grassTile
		Tile t = Tile.tiles[tiles[x][y]];
		if (t == null)
			return Tile.dirtTile; //nếu ko có tile nào cả thì default = dirtTile.
		return t;
	}

	// loadworld dùng để truyền vào data của cấu trúc dữ liệu map đc lấy từ 1 file txt có sẵn
	// chuyển đổi từ txt sang String rồi add vào các biến (gán gtri cho từng tile)
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path); // tạo một string để chứa dữ liệu từ file txt.
		String[] tokens = file.split("\\s+"); // \+s :split by space
		width = Utils.parseInt(tokens[0]); // lấy độ rộng từ file txt
		height = Utils.parseInt(tokens[1]); // tg tự
		spawnX = Utils.parseInt(tokens[2]); // 
		spawnY = Utils.parseInt(tokens[3]); // 
		tiles = new int[width][height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				tiles[j][i] = Utils.parseInt(tokens[(j + i * width) + 4]); 
			// parse theo chiều dọc, từ token 5(4 token trc đã lấy)										
			}
		}
	}

	// get, set
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

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

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
}
