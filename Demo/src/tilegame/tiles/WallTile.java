package tilegame.tiles;

import tilegame.gfx.Assets;

public class WallTile extends Tile {
	public WallTile(int id) {
		super(Assets.wall, id);
	}
	public boolean isSolid() {
		return true;
	}
}


