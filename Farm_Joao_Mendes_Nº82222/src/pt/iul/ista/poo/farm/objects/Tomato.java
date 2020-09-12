package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Tomato extends Vegetable {
	static final int MADURO = 15;
	static final int PODRE = 25;

	private boolean primeiraInteracao = false;

	public Tomato(Point2D p) {
		super(p);
	}

	public String getName() {
		if (!isMaduro() && !isPodre())
			return "small_tomato";
		if (isPodre())
			return "bad_tomato";
		return getClass().getSimpleName().toLowerCase();
	}

	public void update() {
		setCiclos(getCiclos() + 1);
		if (getCiclos() >= MADURO && primeiraInteracao)
			setMaduro(true);
		if (getCiclos() >= PODRE){
			setPodre(true);
			setMaduro(false);
		}
		

	}
	
	public void cuidar(){
		primeiraInteracao = true;
	}
	
	public int getPontos(){
		return 3;
	}

	
	public static Tomato newTomato(String[] tokens){
		int comma=tokens[1].indexOf(",");
		Point2D p=new Point2D(Integer.valueOf(tokens[1].substring(1,comma)),Integer.valueOf(tokens[1].substring(comma+1, tokens[1].length()-1)));
		Tomato t=new Tomato(p);
		t.setCiclos(Integer.valueOf(tokens[3]));
		if(tokens[4].equals("maduro"))
			t.setMaduro(true);
		if(tokens[4].equals("podre")){
			t.setPodre(true);
			t.setMaduro(false);
		}
		else{
			t.setPodre(false);
			t.setMaduro(false);
		}
		return t;
	}

	@Override
	public String toFile() {
		return "Tomato" + " (" + getPosition().getX() + "," + getPosition().getY() + ") " + "Ciclos: " + getCiclos()
		+ getEstado();
	}

}
