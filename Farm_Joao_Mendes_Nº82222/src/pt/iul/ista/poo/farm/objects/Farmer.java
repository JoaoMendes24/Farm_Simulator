package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;

public class Farmer extends FarmObject {

	public Farmer(Point2D p) {
		super(p);
	}

	public void move(int key) {
		Vector2D v = Direction.directionFor(key).asVector();
		Point2D p = getPosition().plus(v);
		if (ImageMatrixGUI.getInstance().isWithinBounds(p) && Farm.getInstance().isValidPosition(p))
			setPosition(p);

	}
	
	public int getLayer() {
		return 2;
	}

	@Override
	public String toFile() {
		return "Farmer"+ " ("+getPosition().getX()+","+getPosition().getY()+") ";
	}
	
	public static Farmer newFarmer(String[] tokens){
		int comma=tokens[1].indexOf(",");
		Point2D p=new Point2D(Integer.valueOf(tokens[1].substring(1,comma)),Integer.valueOf(tokens[1].substring(comma+1, tokens[1].length()-1)));
		Farmer f=new Farmer(p);
		return f;
	}


}
