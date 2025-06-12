package tilegame.tiles;

import tilegame.gfx.Assets;

public class House1Tile extends Tile {
	public House1Tile(int id) {
		super(Assets.house1, id);
	}
	public boolean isSolid() {
		return true;
	}
}