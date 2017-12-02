import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.File;

public class Player extends Sprite{
    private int health;
    private int rupees;
    private String name;
    private Image[] attack_right_imgs;
    private Image[] attack_left_imgs;
    private Image[] attack_up_imgs;
    private Image[] attack_down_imgs;
    private Weapon sword;
    
    public Player(String name, String player_dir, double start_x, double start_y){
	super(player_dir, start_x, start_y);
	this.name = name;
	health = 6;
	rupees = 0;
	File attack_right_f = new File("img/" + player_dir + "/SlashingRight/");
	File attack_left_f = new File("img/" + player_dir + "/SlashingLeft/");
	File attack_up_f = new File("img/" + player_dir + "/SlashingUp/");
	File attack_down_f = new File("img/" + player_dir + "/SlashingDown/");

	int num_attack_right_imgs = attack_right_f.list().length;
	int num_attack_left_imgs = attack_left_f.list().length;
	int num_attack_up_imgs = attack_up_f.list().length;
	int num_attack_down_imgs = attack_down_f.list().length;

	attack_right_imgs = new Image[num_attack_right_imgs];
	attack_left_imgs = new Image[num_attack_left_imgs];
	attack_up_imgs = new Image[num_attack_up_imgs];
	attack_down_imgs = new Image[num_attack_down_imgs];

	loadImgs(attack_right_imgs, num_attack_right_imgs, player_dir, "/SlashingRight/");
	loadImgs(attack_left_imgs, num_attack_left_imgs, player_dir, "/SlashingLeft/");
	loadImgs(attack_up_imgs, num_attack_up_imgs, player_dir, "/SlashingUp/");
	loadImgs(attack_down_imgs, num_attack_down_imgs, player_dir, "/SlashingDown/");

	sword = new Weapon();

	
    }

    public Image[] getAttackImgs(int dir){
	switch(dir){
	case UP: return attack_up_imgs;
	case RIGHT: return attack_right_imgs;
	case DOWN: return attack_down_imgs;
	case LEFT: return attack_left_imgs;
	default: return attack_down_imgs;
	    
	}
    }

    public Weapon getWeapon(){
	return sword;
    }

}
