package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Chicken extends Animal {

	public Chicken(Point2D p) {
		super(p);
	}

	public void interact() {
		ImageMatrixGUI.getInstance().removeImage(this);
		Farm.getInstance().removeObject(this);
		Farm.getInstance().somarPontos(getPontos());
	}

	public void update() {
		setCiclos(getCiclos() + 1);
		Point2D p = randomPosition();
		FarmObject o2 = Farm.getInstance().search(p, 1);
		if(o2 instanceof Tomato)
			comer(p);
		else
			move(p);			
		porOvo();

	}
	
	public void comer(Point2D p){
		FarmObject o2 = Farm.getInstance().search(p, 1);
		if (o2 instanceof Tomato) {
			Tomato t = (Tomato) o2;
			ImageMatrixGUI.getInstance().removeImage(t);
			Farm.getInstance().removeObject(t);
			t.remove();
		}
	}

	public void porOvo() {
		if (getCiclos()%10==0 && Farm.getInstance().canMove(getPosition())) {
			setCiclos(0);
			Egg g = new Egg(randomPosition());
			ImageMatrixGUI.getInstance().addImage(g);
			Farm.getInstance().addObject(g);
		}
	}

	public void move(Point2D p) {
		if (getCiclos() % 2 == 0) {
			setPosition(p);
		}

	}
	
	public static Chicken newChicken(String[] tokens){
		int comma=tokens[1].indexOf(",");
		Point2D p=new Point2D(Integer.valueOf(tokens[1].substring(1,comma)),Integer.valueOf(tokens[1].substring(comma+1, tokens[1].length()-1)));		
		Chicken c=new Chicken(p);
		c.setCiclos(Integer.valueOf(tokens[3]));
		return c;
	}

	@Override
	public String toFile() {
		return getName()+ " ("+getPosition().getX()+","+getPosition().getY()+") " + "Ciclos: " + getCiclos();
	}
	
	public int getPontos(){
		return 2;
	}

}
