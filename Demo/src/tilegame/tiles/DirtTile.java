package tilegame.tiles;

import tilegame.gfx.Assets;

public class DirtTile extends Tile {
	public DirtTile(int id) {
		super(Assets.tile, id);
	}

	public boolean isSolid() {
		return false;
	}
}
