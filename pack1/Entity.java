package pack1;

public class Entity {
    protected int id;
    protected String name;
    protected Vector2 position;
    protected Image sprite;
    protected boolean isActive;

    // Constructor
    public Entity(int id, String name, float x, float y) {
        this.id = id;
        this.name = name;
        this.position = new Vector2(x, y);
        this.sprite = new Image(); // Placeholder for sprite
        this.isActive = true;
    }

    // Update entity state (e.g., check status, reduce timers)
    public void update() {
        if (!isActive) {
            System.out.println(name + " is inactive and cannot be updated.");
            return;
        }
        System.out.println(name + " updated at " + position);
    }

    // Render entity (placeholder for graphics, outputs to console)
    public void render() {
        if (!isActive) {
            System.out.println(name + " is inactive and cannot be rendered.");
            return;
        }
        System.out.println(name + " rendered at " + position + " with sprite.");
    }

    // Move entity to a new position
    public void move(float x, float y) {
        if (!isActive) {
            System.out.println(name + " is inactive and cannot move.");
            return;
        }
        position = new Vector2(x, y);
        System.out.println(name + " moved to " + position);
    }

    // Destroy the entity (set inactive)
    public void destroy() {
        if (!isActive) {
            System.out.println(name + " is already destroyed.");
            return;
        }
        isActive = false;
        System.out.println(name + " has been destroyed.");
    }

    // Getters for testing
    public int getId() { return id; }
    public String getName() { return name; }
    public Vector2 getPosition() { return position; }
    public Image getSprite() { return sprite; }
    public boolean isActive() { return isActive; }
}