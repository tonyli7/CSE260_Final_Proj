import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import java.util.LinkedList;
import javafx.application.Platform;
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

	if (Demo.GAME_STATE == Demo.GAME){

	    if (player.getHealth() == 0){
		Demo.GAME_STATE = Demo.OVER;
		Demo.scene.setRoot(Demo.over_screen.getPane());
		
	    }
	    
	    for (Cors c: Demo.curr_location.getLinks().keySet()){
		if (Math.abs(player.getX() - c.getX()) < 10 &&
		    Math.abs(player.getY() - c.getY()) < 10){

		    if (c.getX() == 768){
			player.setX(Math.IEEEremainder(-1 * c.getX(), 800));
		    }
		    else if (c.getX() == 0){
			player.setX(c.getX() - 40 + 800);
			System.out.println(player.getX());
		    }
		    else if(c.getY() == 576){
			player.setY(Math.IEEEremainder(-1 * c.getY(), 608));
		    }
		    else if(c.getY() == 0){
			player.setY(c.getY() - 40 + 608);
		    }

		    Demo.loadMap(Demo.curr_location.getLinks().get(c), Demo.pane);
		    player.update();
		}
	    }
	
	    if (!frontier.isEmpty()){ // if the Player is moving
		frontier.remove((Integer)(getOppDir(frontier.peek())));
		Movement.unitMove(player, player, frontier.getLast());
		Movement.unitMove(player, player, frontier.getFirst());
	    
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

	    if (enter_pressed){
		Demo.scene.setRoot(Demo.menu_screen.getPane());
		Demo.GAME_STATE = Demo.MENU;
		enter_pressed = false;
	    }
	}else{
	    MyScreen temp_screen = Demo.getScreen(Demo.GAME_STATE);
	    MyButton[] btns = temp_screen.getButtons();
	    if (up_pressed){
		temp_screen.setHovered((temp_screen.hovered() + btns.length - 1) % btns.length);
		up_pressed = false;
	    }
	    if (down_pressed){
		temp_screen.setHovered((temp_screen.hovered() + 1) % btns.length);
		down_pressed = false;
	    }
	    if (enter_pressed){
		enter_pressed = false;
		int screen = btns[temp_screen.hovered()].getLink();
		if (screen < 0){
		    Platform.exit();
		}
		if (screen == Demo.GAME){
		    if (Demo.GAME_STATE == Demo.SPLASH){
			player = new Player("Link", "Link", 400, 300);
			Demo.newGame(player);
			
		    }
		    Demo.scene.setRoot(Demo.pane);
		}
		if (screen == Demo.MENU){
		    Demo.scene.setRoot(Demo.menu_screen.getPane());
		    
		}
		if (screen == Demo.SPLASH){
		    Demo.scene.setRoot(Demo.splash_screen.getPane());
		}
		if (screen == Demo.SAVES){
		   
		    if (Demo.GAME_STATE == Demo.OVER || Demo.GAME_STATE == Demo.SPLASH){
			 Demo.newGame(player);
			 Saver.loadGame(player, "saves/save0");
			 Demo.scene.setRoot(Demo.pane);
			 screen = Demo.GAME;
		    }
		    if (Demo.GAME_STATE == Demo.MENU){
			Saver.saveGame(player, "saves/save0");
			Demo.scene.setRoot(Demo.pane);
			screen = Demo.GAME;
		    }
		    
		}
		Demo.GAME_STATE = screen;
	    }

	}
	
    }

    public static int getOppDir(int dir){
	switch(dir){
	case Sprite.UP: return Sprite.DOWN;  
	case Sprite.RIGHT: return Sprite.LEFT;  
	case Sprite.DOWN: return Sprite.UP;  
	case Sprite.LEFT: return Sprite.RIGHT;
	default: return -1;
	    
	}
    }

  
    
}
