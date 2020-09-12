package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Sheep extends Animal {

	private boolean alimentada = true;

	public Sheep(Point2D p) {
		super(p);
	}

	public void interact() {
		alimentada = true;
		setCiclos(0);

	}

	public void update() {
		setCiclos(getCiclos() + 1);
		if (alimentada)
			Farm.getInstance().somarPontos(getPontos());
		if (getCiclos() > 10 && getCiclos() <= 20) {
			Point2D p = randomPosition();
			if (Farm.getInstance().farmObjectInPosition(p, 1))
				comer(p);
			else
				setPosition(p);

		}
		if (getCiclos() > 20)
			alimentada = false;

	}

	public void comer(Point2D p) {
		FarmObject o = Farm.getInstance().search(p, 1);
		if (o instanceof Vegetable) {
			Vegetable v1 = (Vegetable) o;
			alimentada = true;
			setCiclos(0);
			ImageMatrixGUI.getInstance().removeImage(v1);
			Farm.getInstance().removeObject(v1);
			v1.remove();
		}
	}

	public String getName() {
		if (!alimentada)
			return "famished_sheep";
		else
			return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public String toFile() {
		return "Sheep" + " (" + getPosition().getX() + "," + getPosition().getY() + ") " + "Ciclos: " + getCiclos()
				+ getEstado();

	}

	public String getEstado() {
		if (alimentada)
			return " alimentada";
		else
			return " faminta";
	}

	public static Sheep newSheep(String[] tokens) {
		int comma = tokens[1].indexOf(",");
		Point2D p = new Point2D(Integer.valueOf(tokens[1].substring(1, comma)),
				Integer.valueOf(tokens[1].substring(comma + 1, tokens[1].length() - 1)));
		Sheep s = new Sheep(p);
		s.setCiclos(Integer.valueOf(tokens[3]));
		if (tokens[4].equals("alimentada"))
			s.setAlimentada(true);
		else
			s.setAlimentada(false);
		return s;
	}

	public void setAlimentada(boolean a) {
		alimentada = a;
	}

	public int getPontos() {
		return 1;
	}

}
