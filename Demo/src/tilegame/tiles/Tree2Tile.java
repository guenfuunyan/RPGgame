package tilegame.tiles;

import tilegame.gfx.Assets;

public class Tree2Tile extends Tile {
	public Tree2Tile(int id) {
		super(Assets.tree2, id);
	}
	public boolean isSolid() {
		return true;
	}
}
