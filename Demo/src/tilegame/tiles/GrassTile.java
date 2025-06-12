package tilegame.tiles;

import tilegame.gfx.Assets;

public class GrassTile extends Tile {
	public GrassTile(int id) {
		super(Assets.tree, id);
	}
	public boolean isSolid() {
		return false;
	}
}
