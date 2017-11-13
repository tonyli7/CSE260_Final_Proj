public class Monster extends Sprite{
    
    private int health;
    private String name;
    
    
    public Monster(String name, int health, String monster_dir, double start_x, double start_y){
	super(monster_dir, start_x, start_y);
	this.name = name;
	this.health = health;
    }
}
