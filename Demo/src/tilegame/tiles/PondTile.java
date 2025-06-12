package tilegame.tiles;

import tilegame.gfx.Assets;

public class PondTile extends Tile {
	public PondTile(int id) {
		super(Assets.pond, id);
	}
	public boolean isSolid() {
		return true;
	}
}
