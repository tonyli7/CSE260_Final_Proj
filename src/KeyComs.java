import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import java.util.LinkedList;
//import java.util.Deque;
public class KeyComs extends AnimationTimer{

    public static boolean right_pressed;
    public static boolean left_pressed;
    public static boolean up_pressed;
    public static boolean down_pressed;
    public static boolean z_pressed;
    public static boolean enter_pressed;
    public static boolean shift_pressed;
    
    private int attack_form;
    private Player player;
    private boolean attacking;
    private LinkedList<Integer> frontier; // Stores the order of movement commands

    public KeyComs(Player player){
	this.player = player;
	attack_form = 0;
	frontier = new LinkedList<Integer>();
	attacking = false;
    }
    

    @Override
    public void handle(long timestamp){
	if (!frontier.isEmpty()){ // if the Player is moving
	    frontier.remove((Integer)(getOppDir(frontier.peek())));
	    Movement.unitMove(player, frontier.getLast());
	    Movement.unitMove(player, frontier.getFirst());
	    
	}

	// Adding the move commands to the frontier
	if (right_pressed && !z_pressed && !frontier.contains(Sprite.RIGHT)){
	    frontier.add(Sprite.RIGHT);
	}
	if (left_pressed && !z_pressed && !frontier.contains(Sprite.LEFT)){
	    frontier.add(Sprite.LEFT);
	}
	if (up_pressed && !z_pressed && !frontier.contains(Sprite.UP)){
	    frontier.add(Sprite.UP);
	}
	if (down_pressed && !z_pressed && !frontier.contains(Sprite.DOWN)){
	    frontier.add(Sprite.DOWN);
	}

	// While the Player is moving, disable the ability to move in the opposite direction
	if (!right_pressed && frontier.contains(Sprite.RIGHT)){
	    frontier.remove((Integer)Sprite.RIGHT);
	}
	if (!left_pressed && frontier.contains(Sprite.LEFT)){
	    frontier.remove((Integer)Sprite.LEFT);
	}
	if (!up_pressed && frontier.contains(Sprite.UP)){
	    frontier.remove((Integer)Sprite.UP);
	}
	if (!down_pressed && frontier.contains(Sprite.DOWN)){
	    frontier.remove((Integer)Sprite.DOWN);
	}
	if (!right_pressed && !left_pressed && !up_pressed && !down_pressed && !attacking){
	    Movement.stop(player, 0);
	}

	// The Player can only attack and show the attack animation if the Player is not moving
	if ((z_pressed || attack_form > 0) && !attacking && frontier.isEmpty()){
	    
	    player.getWeapon().display(true);
	    Image[] attack_imgs = player.getAttackImgs(player.getDir());
	    if (attack_form >= attack_imgs.length - 1){
		attacking = true;
		Movement.attack(player, attack_imgs, attack_form % attack_imgs.length);
		attack_form = 0;
		player.getWeapon().display(false);
	    }else{
		
		Movement.attack(player, attack_imgs, attack_form % attack_imgs.length);
		attack_form++;
	    }	    
	}
	if (!z_pressed){
	    player.getWeapon().display(false);
	    
	    if (attacking){
		attacking = false;
	    }
	}
	
    }

    private int getOppDir(int dir){
	switch(dir){
	case Sprite.UP: return Sprite.DOWN;  
	case Sprite.RIGHT: return Sprite.LEFT;  
	case Sprite.DOWN: return Sprite.UP;  
	case Sprite.LEFT: return Sprite.RIGHT;
	default: return -1;
	    
	}
    }
    
}
