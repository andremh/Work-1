package myveryOwnproject.src.airplane;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Plane extends Thread {

	private Cell cell;
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image plane;
	private AirSpace asp;
	private int cellNumber;
	int rowNumber;
	int colunmNumber;
	private int nextCell;
	private int destinationCell;
	private boolean clicked = false;
	private int fuel = 100;
	private boolean hasDestination = false;
	private Image planeBack;
	private Image planeUp;
	private Image planeDown;
	private Image planeForward;
	private Image planeRed;
	private int sinalMove;
	private boolean onAir = true;

	/**
	 * @param asp
	 * @param cell
	 * @see Plane
	 * 
	 */
	public Plane(AirSpace asp, Cell cell) {

		this.nextCell = cell.getCelula();

		plane = toolkit.createImage("plane.png");
		planeBack = toolkit.createImage("planeBack.png");
		planeUp = toolkit.createImage("planeUp.png");
		planeDown = toolkit.createImage("planeDown.png");
		planeForward = toolkit.createImage("planeForward.png");
		planeRed = toolkit.createImage("planeRed.png");

		this.asp = asp;
		this.cell = cell;
		destinationCell = cell.getCelula();
	}

	public Plane(AirSpace asp, int cellNumber) {
		plane = toolkit.createImage("plane.png");
		this.asp = asp;
		this.cellNumber = cellNumber;
		this.nextCell = cell.getCelula();
	}

	public Plane(AirSpace asp, int rowNumber, int colunmNumber) {
		plane = toolkit.createImage("plane.png");
		this.asp = asp;
		this.rowNumber = rowNumber;
		this.colunmNumber = colunmNumber;
	}

	/**
	 * @see run
	 * @param nextCell
	 */
	@Override
	public void run() {
		/*
		 * //1)cell != destino //2)se n„o houver avioes //3)quando
		 * proxima!=destino ent„o vÍ se destino=aeroporto //bug se a proxima n„o
		 * tiver nada para antes // ver.5)alterado //System.out.println("cell: "
		 * + onAir);
		 * 
		 * //nao deixa andar fora dos aeroportos
		 * //(((nextCell!=destinationCell)&& asp.isMyAirport(nextCell)) //anda
		 * sempre excepto quando o aviao È clicado para uma posiÁ„o sem ser
		 * aeroporto e se sencontr aeroporto p·ra.
		 * //(((nextCell!=destinationCell)&& !asp.isMyAirport(nextCell)) ||
		 * asp.isMyAirport(destinationCell)) //nao passa nos outros aeroportos
		 * excepto o dele //if ((cell.getCelula() != destinationCell) &&
		 * asp.isEmpty(move()) && ((
		 * (!asp.isMyAirport(nextCell)||destinationCell==nextCell)) ) ) {
		 * System.out.println("next==dest: " + (nextCell == destinationCell));
		 * System.out.println("2)my airp: " +
		 * (asp.isMyAirport(destinationCell)));
		 */
		
		//ver.5)alterado
		while (true) {
			try {
				timeGoesBy();
				sleep(500);
				sinalMove = 0;
				if ((cell.getCelula() != destinationCell)
						&& asp.isEmpty(move())
						&& (((!asp.isMyAirport(nextCell) || destinationCell == nextCell)))) {

					sinalMove = 1;
					move();
				} else {
					moveInCir();
					System.out.println("out");
				}
				asp.getRwl().repaint();
			} catch (InterruptedException e) {
				System.out.println("EXCEPCAOOO");
				e.printStackTrace();
			}
		}
	}

	public boolean isOnAir() {
		return onAir;
	}

	public void setOnAir(boolean onAir) {
		this.onAir = onAir;
	}

	private void moveInCir() {
		// System.out.println("Parado");
		try {

			sleep(500);
			plane = planeDown;
			asp.getRwl().repaint();
			sleep(500);
			plane = planeBack;
			asp.getRwl().repaint();
			sleep(500);
			plane = planeUp;
			asp.getRwl().repaint();
			sleep(500);
			plane = planeForward;
			asp.getRwl().repaint();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Cell getPlaneCell() {
		return cell;
	}

	public void setPositionByCellNumber(int newCellNumber) {
		this.cellNumber = newCellNumber;
	}

	public int getCellColumn(Cell cell2) {
		// d· coluna da celulaX
		return (cell2.getCelula() % cell2.getNumColumns());
	}

	public int getCellLine(Cell cell2) {
		// d· linha da celulaY
		return (cell2.getCelula() / cell2.getNumColumns());
	}

	public int move() {

		int dx = (destinationCell % cell.getNumColumns()) - getCellColumn(cell);
		int dy = (destinationCell / cell.getNumColumns()) - getCellLine(cell);
		// int totalX = (destinationCell % cell.getNumColumns());
		// int totalY = (destinationCell / cell.getNumColumns());
		nextCell = cell.getCelula();

		// System.out.println("1) dx: " + Math.abs(dx) + " dy " + Math.abs(dy)
		// + ", getCellX: " + totalX);
		if (Math.abs(dx) > Math.abs(dy)) {
			// if (totalY > totalX && (dy != 0)) {
			// || dx !=0 ou && dy==0
			// System.out.println("1) dx>dy: " + dx + ">" + dy);
			if (dx > 0) {
				if (sinalMove == 1) {
					cell.advanceCells(1);
					plane = planeForward;
				} else
					nextCell = cell.getCelula() + 1;
			} else {
				if (sinalMove == 1) {
					cell.backWardCell();
					plane = planeBack;
				} else
					nextCell = cell.getCelula() - 1;
			}
		} else {
			if (dy > 0) {

				if (sinalMove == 1) {
					cell.advanceCells(cell.getNumColumns());
					plane = planeDown;
				} else
					nextCell = cell.getCelula() + cell.getNumColumns();
			} else {
				if (sinalMove == 1) {
					cell.advanceCells(-cell.getNumColumns());
					plane = planeUp;
				} else
					nextCell = cell.getCelula() - cell.getNumColumns();
			}
		}
		return nextCell;

	}

	public Image getImage() {
		return plane;
	}

	public int getDestinationCell() {
		return destinationCell;
	}

	public void setDestination(int destination) {
		destinationCell = destination;
	}

	public boolean airplaneIsSelected(Point point) {
		int col = (int) (point.getX() / (asp.getRwl().getWidth() / asp
				.getNumColumns()));// divide coord x pla largura cada celula->
		// indica qual a coluna onde estou
		int lin = (int) (point.getY() / (asp.getRwl().getHeight() / asp
				.getNumRows()));// ->indica qual a linha onde estou a contar
		// cima, =0
		int selected = col + lin * asp.getNumColumns();
		System.out.println("AIRPLANE SELECTED ->" + " selected : " + selected
				+ " cell.getcelula :" + cell.getCelula());
		if (cell.getCelula() == selected) {
			System.out
					.println("DENTRO DA CLASSE PLANE -> o aviao est√° na celula que indic√°mos!!");
			clicked = true;
			return true;

		}
		clicked = false;
		return false;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setNotClicked() {
		clicked = false;

	}

	public void timeGoesBy() {

		Timer time = new Timer(30000, new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				fuel--;

				System.out.println("You are out of fuel!");

				// ImageFilter filter = new GrayFilter(true, 50);
				//
				//
				// ImageProducer producer = new FilteredImageSource
				// (plane.getSource(), filter);
				// Image mage = toolkit.createImage(producer);;
				//
				//
				// plane=mage;
				// POSSO APROVEITAR ESTE FILTRO PARA QD EM MOVIMENTO FICAR COM
				// MENOS COR
				plane = planeRed;// NOVO
				asp.setScreen("The plane is out of fuel!");// NOVO

			}

		});
		time.start();
	}

	public int getFuelLevel() {
		return fuel;

	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public int getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(int cellNumber) {
		this.cellNumber = cellNumber;
	}

	public void setImage(Image newImage) {
		plane = newImage;

	}
}
