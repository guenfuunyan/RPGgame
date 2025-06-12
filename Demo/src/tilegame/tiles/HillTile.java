package tilegame.tiles;
import tilegame.gfx.Assets;

public class HillTile extends Tile {
	public HillTile(int id) {
		super(Assets.hill, id);
	}
	public boolean isSolid() {
		return true;
	}
}


