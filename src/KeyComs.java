import javafx.animation.AnimationTimer;
public class KeyComs extends AnimationTimer{

    public static boolean right_pressed;
    public static boolean left_pressed;
    public static boolean up_pressed;
    public static boolean down_pressed;
    public static boolean z_pressed;
    public static boolean enter_pressed;
    public static boolean shift_pressed;
    private Player player;

    public KeyComs(Player player){
	this.player = player;
    }
    

    @Override
    public void handle(long timestamp){
	if (right_pressed){
	    Movement.unitMoveX(player, Sprite.RIGHT);
	}
	if (left_pressed){
	    Movement.unitMoveX(player, Sprite.LEFT);
	}
	if (up_pressed){
	    Movement.unitMoveY(player, Sprite.UP);
	}
	if (down_pressed){
	    Movement.unitMoveY(player, Sprite.DOWN);
	}
	if (!right_pressed && !left_pressed && !up_pressed && !down_pressed){
	    Movement.stop(player, 3);
	}
	
    }
    
    
    
}
