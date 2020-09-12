package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Vegetable extends FarmObject implements Interactable, Updatable {

	private boolean maduro = false;
	private boolean podre = false;
	private int ciclos = 0;

	public Vegetable(Point2D p) {
		super(p);
	}

	public int getLayer() {
		return 1;
	}

	public boolean isMaduro() {
		return maduro;
	}

	public void setMaduro(boolean maduro) {
		this.maduro = maduro;
	}

	public boolean isPodre() {
		return podre;
	}

	public void setPodre(boolean podre) {
		this.podre = podre;
	}

	public void setCiclos(int c) {
		this.ciclos = c;
	}

	public int getCiclos() {
		return ciclos;
	}
	
	public void interact(){
		cuidar();
		colher();
		limpar();
	}
	
	public abstract void cuidar();
	
	public void colher(){
		if(maduro){
			ImageMatrixGUI.getInstance().removeImage(this);
			Farm.getInstance().removeObject(this);
			remove();
			Farm.getInstance().somarPontos(getPontos());
		}
	}
	
	public void limpar(){
		if(podre){
			ImageMatrixGUI.getInstance().removeImage(this);
			Farm.getInstance().removeObject(this);
			remove();
		}
	}

	public void remove() {
		FarmObject o = Farm.getInstance().search(getPosition(), 0);
		((Land) o).setLavrada(false);
		((Land) o).setPlantada(false);

	}

	public String getEstado() {
		if (maduro)
			return " maduro";
		if (podre)
			return " podre";
		return " pequeno";

	}

}
