public class Player extends Sprite{
    private int health;
    private String name;

    public Player(String name, String player_dir, double start_x, double start_y){
	super(player_dir, start_x, start_y);
	this.name = name;
	health = 6;
	
    }
}
