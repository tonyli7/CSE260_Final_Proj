import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import java.util.LinkedList;
import java.util.Random;


public class MonsterComs extends AnimationTimer{

    private LinkedList<Monster> mons;
    public MonsterComs(LinkedList<Monster> mons){
	this.mons = mons;
	
    }

    @Override
    public void handle(long timestamp){

	//System.out.println(mons.get(0).getSteps());
	for (int i = 0; i < mons.size(); i++){
	    
	    Monster m = mons.get(i);
	    
	    Movement.unitMove(m, m.getDir(), 20);
	}
	
    }

    
    
    
    
}
