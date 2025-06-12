package tilegame.tiles;

import tilegame.gfx.Assets;

public class Wall1Tile extends Tile {
	public Wall1Tile(int id) {
		super(Assets.wall1, id);
	}
	public boolean isSolid() {
		return true;
	}
}


