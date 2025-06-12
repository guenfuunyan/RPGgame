package tilegame.tiles;
import tilegame.gfx.Assets;

public class HouseTile extends Tile {
	public HouseTile(int id) {
		super(Assets.house, id);
	}
	public boolean isSolid() {
		return true;
	}
}



