package tilegame.tiles;

import tilegame.gfx.Assets;

public class LandTile extends Tile {
	public LandTile(int id) {
		super(Assets.land, id);
	}
	public boolean isSolid() {
		return false;
	}
}

