package pt.iul.ista.poo.farm.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Egg extends Chicken {
	
	public Egg(Point2D p) {
		super(p);
	}
	
	public int getLayer() {
		return 2;
	}
	
	public void update(){
		setCiclos(getCiclos()+1);
		if(getCiclos()==20 && Farm.getInstance().canMove(getPosition())){
			List<Point2D> positions=Direction.getNeighbourhoodPoints(getPosition());
			List<Point2D> validPositions=new ArrayList<>();
			for(Point2D p: positions){
				if(Farm.getInstance().isValidPosition(p))
					validPositions.add(p);
			}
			Random random = new Random();
			int i = random.nextInt(validPositions.size());
			ImageMatrixGUI.getInstance().removeImage(this);
			Farm.getInstance().removeObject(this);
			Chicken c=new Chicken(validPositions.get(i));
			ImageMatrixGUI.getInstance().addImage(c);
			Farm.getInstance().addObject(c);
			
		}
		
	}
	
	public void interact(){
		ImageMatrixGUI.getInstance().removeImage(this);
		Farm.getInstance().removeObject(this);
		Farm.getInstance().somarPontos(getPontos());
		
	}
	public static Egg newEgg(String[] tokens){
		int comma=tokens[1].indexOf(",");
		Point2D p=new Point2D(Integer.valueOf(tokens[1].substring(1,comma)),Integer.valueOf(tokens[1].substring(comma+1, tokens[1].length()-1)));
		Egg e=new Egg(p);
		e.setCiclos(Integer.valueOf(tokens[3]));
		return e;
	}
	
	public int getPontos(){
		return 1;
	}
	
	

}
