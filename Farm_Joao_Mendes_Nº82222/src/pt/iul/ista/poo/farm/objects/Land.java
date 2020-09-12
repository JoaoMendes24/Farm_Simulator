package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Land extends FarmObject implements Interactable {

	private boolean lavrada = false;
	private boolean plantada = false;
	private boolean rock;

	public Land(Point2D p) {
		super(p);
		if(Math.random()<0.1){
			rock=true;
		}
	}

	@Override
	public void interact() {
		if (!lavrada && !rock) {
			lavrada=true;
		} else {
			if (!plantada && lavrada) {
				if (Math.random() < 0.5) {
					Cabbage c = new Cabbage(getPosition());
					ImageMatrixGUI.getInstance().addImage(c);
					Farm.getInstance().addObject(c);
				} else {
					Tomato t = new Tomato(getPosition());
					ImageMatrixGUI.getInstance().addImage(t);
					Farm.getInstance().addObject(t);

				}
				plantada=true;
			}
		}

	}

	public String getName() {
		if (lavrada)
			return "plowed";
		if(rock){
			return "rock";
		}
		return super.getName();
	}

	
	public void setPlantada(boolean plantada){
		this.plantada=plantada;
	}
	
	public void setLavrada(boolean lavrada){
		this.lavrada=lavrada;
	}
	
	public String getEstado(){
		if(plantada)
			return "plantada";
		if(lavrada)
			return "lavrada";
		if(rock)
			return "rock";
		return "normal";
	}
	
	public static Land newLand(String[] tokens){
		int comma=tokens[1].indexOf(",");
		Point2D p=new Point2D(Integer.valueOf(tokens[1].substring(1,comma)),Integer.valueOf(tokens[1].substring(comma+1, tokens[1].length()-1)));
		Land l=new Land(p);
		if(tokens[2].equals("rock"))
			l.setRock(true);
		if(tokens[2].equals("lavrada")){
			l.setLavrada(true);
			l.setRock(false);
		}
		if(tokens[2].equals("plantada")){
			l.setPlantada(true);
			l.setLavrada(true);
			l.setRock(false);
		}
		if(tokens[2].equals("normal")){
			l.setLavrada(false);
			l.setRock(false);
		}
		
		return l;
		
		
		
	}

	private void setRock(boolean b) {
		rock=b;
		
	}

	@Override
	public String toFile() {
		return "Land" +" ("+getPosition().getX()+","+getPosition().getY()+") "+ getEstado();
	}
	


}
