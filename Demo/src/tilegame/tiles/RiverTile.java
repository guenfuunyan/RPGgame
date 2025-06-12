package tilegame.tiles;

import tilegame.gfx.Assets;

public class RiverTile extends Tile {
	public RiverTile(int id) {
		super(Assets.river, id);
	}
	public boolean isSolid() {
		return true;
	}
}

