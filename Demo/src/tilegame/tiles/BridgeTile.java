package tilegame.tiles;
import tilegame.gfx.Assets;

public class BridgeTile extends Tile {
	public BridgeTile(int id) {
		super(Assets.bridge, id);
	}
	public boolean isSolid() {
		return false;
	}
}
