package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

/** Represents a Cabbage in class FarmObject
 * 
 * @author Joao Mendes
 *
 */

public class Cabbage extends Vegetable {
	
	/**
	 * Number of cycles needed to change Cabbage state to ripe
	 */
	static final int MADURO = 10;
	
	/**
	 * Number of cycles needed to change Cabbage state to rotten
	 */
	static final int PODRE = 30;
	
	/** Creates new Cabbage
	 * 
	 * @param p Cabbage position
	 */

	public Cabbage(Point2D p) {
		super(p);
	}
	
	/** Changes name depending on cabbage state
	 * @return "small_cabbage" if not rotten nor ripe, "bad_cabbage" if rotten, class name if ripe
	 * 
	 */

	public String getName() {
		if (!isMaduro() && !isPodre())
			return "small_cabbage";
		if (isPodre())
			return "bad_cabbage";
		return getClass().getSimpleName().toLowerCase();
	}
	
	/** Speeds up cabbage growth
	 * 
	 */
	public void cuidar(){
		if (!isMaduro())
			setCiclos(getCiclos() + 1);
	}
	/**
	 * @return number of points gained when cabbage is harvested
	 */

	public int getPontos(){
		return 2;
	}
	
	/** Creates new Cabbage depending on tokens value
	 * 
	 * @param tokens Array with information(in each position) to create new Cabbage
	 * @return new Cabbage either small, ripe or rotten
	 */
	
	public static Cabbage newCabbage(String[] tokens){
		int comma=tokens[1].indexOf(",");
		Point2D p=new Point2D(Integer.valueOf(tokens[1].substring(1,comma)),Integer.valueOf(tokens[1].substring(comma+1, tokens[1].length()-1)));		
		Cabbage c=new Cabbage(p);
		c.setCiclos(Integer.valueOf(tokens[3]));
		if(tokens[4].equals("maduro"))
			c.setMaduro(true);
		if(tokens[4].equals("podre")){
			c.setPodre(true);
			c.setMaduro(false);
		}
		else{
			c.setPodre(false);
			c.setMaduro(false);
		}
		return c;
	}
	
	/** Updates Cabbage state
	 * 
	 */

	public void update() {
		setCiclos(getCiclos() + 1);
		if (getCiclos() >= MADURO)
			setMaduro(true);
		if (getCiclos() >= PODRE) {
			setPodre(true);
			setMaduro(false);
		}

	}
	
	/**
	 * @return a string following the format "Cabbage (x,y) Ciclos: ciclos estado(small, rotten or ripe)"
	 */

	@Override
	public String toFile() {
		return "Cabbage" + " (" + getPosition().getX() + "," + getPosition().getY() + ") " + "Ciclos: " + getCiclos()
				+ getEstado();
	}

}
