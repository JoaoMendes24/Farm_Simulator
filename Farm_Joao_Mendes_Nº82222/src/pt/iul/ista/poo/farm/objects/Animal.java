package pt.iul.ista.poo.farm.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Animal extends FarmObject implements Interactable, Updatable {
	
	private int ciclos=0;

	public Animal(Point2D p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void comer(Point2D p);

	
	public int getCiclos() {
		return ciclos;
	}

	public void setCiclos(int ciclos) {
		this.ciclos = ciclos;
	}

	public int getLayer() {
		return 2;
	}
	
	public Point2D randomPosition(){
		List<Point2D> positions=Direction.getNeighbourhoodPoints(getPosition());
		List<Point2D> aux=new ArrayList<>();
		for(Point2D p: positions){
			if(Farm.getInstance().isValidPosition(p))
				aux.add(p);
		}
		if(aux.isEmpty())
			return getPosition();
		else{
			Random r=new Random();
			int i=r.nextInt(aux.size());
			return aux.get(i);
		}	
	}

}
