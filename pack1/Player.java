package pack1;

public class Player extends Character {
    public Player(int id, String name, float x, float y, int maxHp, int attackPower, int defense, float speed) {
		super(id, name, x, y, maxHp, attackPower, defense, speed);
		// TODO Auto-generated constructor stub
	}
	private int level;
    private int experience;
    private int mana;
    private int gold;
    private List<Skill> skills;
    private int shieldDurability;
    private float parryWindow;

    public void levelUp() {}
    public void useSkill(Skill skill, Character target) {}
    public void equipItem(Item item) {}
    public void interact(Character entity) {}
    public void saveProgress() {}
    public boolean parry(int enemyAttackPower) { return false; }
    public void useItem(Item item) {}
	public int getGold() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getGold() {
		// TODO Auto-generated method stub
		return 0;
	}
}