import java.util.Scanner;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Saver{

    public static void loadGame(Player player, String save){
	File save_f = new File(save);
	try{
	    Scanner scn = new Scanner(save_f);
	    String[] toks = scn.nextLine().split(" ");
	    Demo.loadMap(Demo.getLocation(toks[0]), Demo.pane);
	    player.setX(Double.parseDouble(toks[1]));
	    player.setY(Double.parseDouble(toks[2]));
	    player.setHealth(Integer.parseInt(toks[3]));
	    player.updateHealth();
	    player.update();
	    
	}catch (FileNotFoundException ex){
	    System.out.println(ex.getMessage());
	}
    }

    public static void saveGame(Player player, String save){
	String loc = Demo.curr_location.getName();
	String x = player.getX() + "";
	String y = player.getY() + "";
	String health = player.getHealth() + "";

	try{
	    File save_f = new File(save);
	    PrintWriter writer = new PrintWriter(save_f);
	    writer.print("");
	    writer.flush();
	    writer.close();

	    FileWriter fileWriter = new FileWriter(save_f);
	    fileWriter.write(loc + " " + x + " " + y + " " + health);
	    fileWriter.flush();
	    fileWriter.close();
	}catch (IOException ex){
	    System.out.println(ex.getMessage());
	}
    }
    

    
}
