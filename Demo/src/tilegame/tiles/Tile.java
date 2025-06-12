package tilegame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	// những stuff tĩnh sẽ đc khởi tạo
	public static Tile[] tiles = new Tile[400]; // max = 400 tiles
	public static Tile grassTile = new GrassTile(0); // grassTile kí hiệu là 0
	public static Tile dirtTile = new DirtTile(1);
	public static Tile rockTile = new RockTile(2);
	public static Tile houseTile = new HouseTile(3);
	public static Tile tree1Tile = new Tree1Tile(4);
	public static Tile bridgeTile = new BridgeTile(5);
	public static Tile tree2Tile = new Tree2Tile(6);
	public static Tile hillTile = new HillTile(7);
	public static Tile wallTile = new WallTile(8);
	public static Tile wall1Tile = new Wall1Tile(9);
	public static Tile pondTile = new PondTile(10);
	public static Tile riverTile = new RiverTile(11);
	public static Tile landTile = new LandTile(12);
	public static Tile house1Tile = new House1Tile(13);
	protected BufferedImage texture;

	protected final int id;

	public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

	// khởi tạo cho các Tile
	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		tiles[id] = this;

	}

	// tick + render
	public void tick() {
	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null); // vừa draw vừa scale.
	}

	public int getId() {
		return id;
	}

	public boolean isSolid() {
		return false;
	}

}
