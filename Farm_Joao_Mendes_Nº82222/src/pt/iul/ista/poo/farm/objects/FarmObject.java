package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class FarmObject implements ImageTile {

	private Point2D position;

	public FarmObject(Point2D p) {
		this.position = p;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 0;
	}
	
	public void setPosition(Point2D p){
		this.position=p;
	}
	
	public abstract String toFile();
	
	public static FarmObject newFarmObject(String[] tokens){
		switch(tokens[0]){
		case "Farmer":
			return Farmer.newFarmer(tokens);
		case "Land":
			return Land.newLand(tokens);
		case "chicken":
			return Chicken.newChicken(tokens);
		case "Sheep":
			return Sheep.newSheep(tokens);
		case "egg":
			return Egg.newEgg(tokens);
		case "Tomato":
			return Tomato.newTomato(tokens);
		case "Cabbage":
			return Cabbage.newCabbage(tokens);
			default:
				throw new IllegalStateException();
		}
	}
	
	public int getPontos(){
		return 0;
	}
		
	

	
	
}
