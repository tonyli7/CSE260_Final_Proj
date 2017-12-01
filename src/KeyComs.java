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
    private LinkedList<Integer> frontier;

    public KeyComs(Player player){
	this.player = player;
	attack_form = 0;
	frontier = new LinkedList<Integer>();

    }
    

    @Override
    public void handle(long timestamp){
	if (!frontier.isEmpty()){
	    frontier.remove((Integer)(getOppDir(frontier.peek())));
	    Movement.unitMove(player, frontier.getLast());
	    Movement.unitMove(player, frontier.getFirst());
	    
	}
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
	if (!right_pressed && !left_pressed && !up_pressed && !down_pressed){
	    Movement.stop(player, 3);
	}
	
	if (z_pressed){
	    
	    Image[] attack_imgs = player.getAttackImgs(player.getDir());
	    Movement.attack(player, attack_imgs, attack_form);
	    attack_form = (attack_form + 1) % attack_imgs.length;
	
	}else{
	    attack_form = 0;
	}
	
    }

    public int getOppDir(int dir){
	switch(dir){
	case Sprite.UP: return Sprite.DOWN;  
	case Sprite.RIGHT: return Sprite.LEFT;  
	case Sprite.DOWN: return Sprite.UP;  
	case Sprite.LEFT: return Sprite.RIGHT;
	default: return -1;
	    
	}
    }
    
}
