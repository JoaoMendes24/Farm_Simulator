package pt.iul.ista.poo.farm;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Scanner;

import pt.iul.ista.poo.farm.objects.Animal;
import pt.iul.ista.poo.farm.objects.Chicken;
import pt.iul.ista.poo.farm.objects.FarmObject;
import pt.iul.ista.poo.farm.objects.Farmer;
import pt.iul.ista.poo.farm.objects.Interactable;
import pt.iul.ista.poo.farm.objects.Land;
import pt.iul.ista.poo.farm.objects.Sheep;
import pt.iul.ista.poo.farm.objects.Updatable;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Farm implements Observer {

	// TODO
	private int pontos = 0;
	private boolean space;
	private List<FarmObject> objetos = new ArrayList<>();
	private static final String SAVE_FNAME = "config/savedGame";

	private static final int MIN_X = 5;
	private static final int MIN_Y = 5;

	private static Farm INSTANCE;
	private Farmer farmer;

	private int max_x;
	private int max_y;

	private Farm(int max_x, int max_y) {
		if (max_x < MIN_X || max_y < MIN_Y)
			throw new IllegalArgumentException();

		this.max_x = max_x;
		this.max_y = max_y;

		INSTANCE = this;

		ImageMatrixGUI.setSize(max_x, max_y);

		// Não usar ImageMatrixGUI antes de inicializar o tamanho
		// TODO

		loadScenario();

	}

	private void registerAll() {
		// TODO
		List<ImageTile> images = new ArrayList<ImageTile>();

		for (int x = 0; x != max_x; x++)
			for (int y = 0; y != max_y; y++) {
				Land l = new Land(new Point2D(x, y));
				objetos.add(l);
			}
		images.add(farmer);

		Animal chicken1 = new Chicken(randomPosition());
		objetos.add(chicken1);
		Animal chicken2 = new Chicken(randomPosition());
		objetos.add(chicken2);
		Animal sheep1 = new Sheep(randomPosition());
		objetos.add(sheep1);
		Animal sheep2 = new Sheep(randomPosition());
		objetos.add(sheep2);
		images.addAll(objetos);

		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
	}

	private void loadScenario() {
		// TODO
		farmer = new Farmer(new Point2D(0, 0));
		registerAll();

	}

	public FarmObject search(Point2D p, int layer) {
		for (FarmObject o : objetos) {
			if (p.equals(o.getPosition()) && layer == o.getLayer()) {
				return o;
			}
		}
		return null;

	}

	public boolean farmObjectInPosition(Point2D p, int layer) {
		for (FarmObject o : objetos) {
			if (p.equals(o.getPosition()) && layer == o.getLayer())
				return true;
		}
		return false;
	}

	public boolean isValidPosition(Point2D p) {
		for (FarmObject o : objetos)
			if (o.getPosition().equals(p) && o.getLayer() == farmer.getLayer() || p.equals(farmer.getPosition())
					|| !ImageMatrixGUI.getInstance().isWithinBounds(p))
				return false;
		return true;

	}

	public List<Point2D> validPositions() {
		List<Point2D> positions = new ArrayList<>();
		for (FarmObject o : objetos)
			if (isValidPosition(o.getPosition()))
				positions.add(o.getPosition());
		return positions;

	}

	public Point2D randomPosition() {
		List<Point2D> positions = validPositions();
		Random r = new Random();
		int i = r.nextInt(positions.size());
		return positions.get(i);
	}

	public boolean canMove(Point2D p) {
		List<Point2D> posicoesVizinhas = Direction.getNeighbourhoodPoints(p);
		for (Point2D p1 : posicoesVizinhas) {
			if (isValidPosition(p1))
				return true;
		}
		return false;

	}

	@Override
	public void update(Observable gui, Object a) {
		System.out.println("Update sent " + a);
		// TODO
		if (a instanceof Integer) {
			int key = (Integer) a;
			interactAll(key);
			save(key);
			load(key);
			if (Direction.isDirection(key)) {
				updateAll();
				System.out.println("Update is a Direction " + Direction.directionFor(key));
			}

		}

		ImageMatrixGUI.getInstance().setStatusMessage("Points: " + pontos);
		ImageMatrixGUI.getInstance().update();

	}

	private void updateAll() {
		List<Animal> animais = new ArrayList<>();
		for (FarmObject o : objetos) {
			if (o instanceof Updatable && !(o instanceof Animal)) {
				Updatable u = (Updatable) o;
				u.update();
			}
			if (o instanceof Animal) {
				Animal a = (Animal) o;
				animais.add(a);
			}
		}
		for (Animal a : animais)
			a.update();

	}

	private void interactAll(int key) {
		if (key == KeyEvent.VK_SPACE) {
			space = true;
		} else {
			if (space) {
				if (Direction.isDirection(key)) {
					Point2D d = farmer.getPosition().plus(Direction.directionFor(key).asVector());
					if (ImageMatrixGUI.getInstance().isWithinBounds(d)) {
						FarmObject o = search(d, higherLayer(d));
						if (o instanceof Interactable) {
							Interactable i = (Interactable) o;
							i.interact();
						}
					}

					space = false;
				}
			} else if (Direction.isDirection(key))
				farmer.move(key);

		}

	}

	public void addObject(FarmObject o) {
		objetos.add(o);
	}

	public void removeObject(FarmObject p) {
		List<FarmObject> aux = new ArrayList<>();
		for (FarmObject o : objetos)
			if (o.equals(p))
				aux.add(o);
		objetos.removeAll(aux);

	}

	public void somarPontos(int p) {
		pontos += p;
	}

	public void save(int key) {
		if (key == KeyEvent.VK_S) {
			try (PrintWriter file = new PrintWriter(new File("Farm.txt"));) {
				file.println(max_x + " " + max_y);
				file.println(pontos);
				file.println(farmer.toFile());
				for (FarmObject o : objetos)
					file.println(o.toFile());
			} catch (FileNotFoundException e) {
				System.out.println("Não consegui abrir o ficheiro");
			}
		}
	}

	public void load(int key) {
		if (key == KeyEvent.VK_L) {
			List<FarmObject> objetos2 = new ArrayList<FarmObject>();
			List<ImageTile> images = new ArrayList<ImageTile>();
			try (Scanner s = new Scanner(new File("Farm.txt"))) {
				if (!validDimension(s))
					throw new IllegalArgumentException();
				else {
					String secondLine = s.nextLine();
					pontos = Integer.valueOf(secondLine);
					while (s.hasNextLine()) {
						String line = s.nextLine();
						String[] tokens = line.split(" ");
						FarmObject o = FarmObject.newFarmObject(tokens);
						if (o instanceof Farmer) {
							Farmer f = (Farmer) o;
							farmer = f;
							images.add(farmer);
						} else {
							objetos2.add(o);
						}
					}
					ImageMatrixGUI.getInstance().clearImages();
					objetos = objetos2;
					images.addAll(objetos);
					ImageMatrixGUI.getInstance().addImages(images);
				}

			} catch (FileNotFoundException e) {
				System.out.println("Ficheiro nao encontrado");
			} catch (IllegalArgumentException e) {
				System.out.println("Dimensao invalida");
			}

		}
	}

	public boolean validDimension(Scanner n) {
		String line = n.nextLine();
		String[] tokens = line.split(" ");
		if (Integer.valueOf(tokens[0]) != max_x || Integer.valueOf(tokens[1]) != max_y)
			return false;
		return true;

	}

	public int higherLayer(Point2D p) {
		if (farmObjectInPosition(p, 2))
			return 2;
		else if (farmObjectInPosition(p, 1))
			return 1;
		else
			return 0;

	}

	// Não precisa de alterar nada a partir deste ponto
	private void play() {
		ImageMatrixGUI.getInstance().addObserver(this);
		ImageMatrixGUI.getInstance().go();
	}

	public static Farm getInstance() {
		assert (INSTANCE != null);
		return INSTANCE;
	}

	public static void main(String[] args) {
		try (Scanner s = new Scanner(new File("config/Dimensao"));) {
			String line = s.nextLine();
			String[] tokens = line.split(" ");
			Farm f = new Farm(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]));
			f.play();
		} catch (IllegalArgumentException e) {
			System.out.println("Dimensoes invalidas");

		} catch (FileNotFoundException e) {
			System.out.println("Ficheiro nao encontrado");
		}
	}

}
