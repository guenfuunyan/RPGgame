package pack1;

import java.util.List;

public class NPC {
    protected int id;
    protected String name;
    protected Vector2 position;
    protected Image sprite;
    protected boolean isActive;
    protected List<String> dialogue;
    protected float interactionRange;

    public NPC(int id2, String name2, float x, float y, List<String> dialogue2, float interactionRange2) {
		// TODO Auto-generated constructor stub
	}
	public void update() {}
    public void render() {}
    public void move(float x, float y) {}
    public void destroy() {}
    public void talk() {}
    public void giveInformation() {}
}