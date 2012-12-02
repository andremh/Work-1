package myveryOwnproject.src.airplane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

public class RunAwayLanding extends JComponent {

	private static final int n_avioes = 20;
	private static final int n_airports = 20;
	private int nLines;
	private int nCol;
	private static int selected = -1;// apenas um sera selecionado de cada
	// vez!!!
	private AirSpace asp;
	Plane plane;

	private Image aa;
	private boolean free;

	// ATENCAO QUE A CELULA RECEBE UMA COLUNA, UMA LINHA E NUMERO COLUNAS
	// TOTAL!!!
	private int selectedDestination = -1;
	private List<Plane> aircraftFleet = new ArrayList<Plane>();

	private int selectedWithOneClick = -1;
	private List<Airport> airportFleet = new ArrayList<Airport>();
	private Airport airp;

	public RunAwayLanding(int nLines, int nCol, AirSpace asp) {
		this.nLines = nLines;
		this.nCol = nCol;
		this.asp = asp;

		insertAirportsRandomLy();

		insertPlanesRandomLy();

	}

	private void insertAirportsRandomLy() {
		for (int i = 0; i != n_airports; i++) {
			Random r = new Random();
			int r1 = (r.nextInt(nCol));
			int r2 = (r.nextInt(nLines));
			Cell cell = new Cell(r2, r1, asp.getNumColumns());

			airp = new Airport(asp, cell);
			airportFleet.add(airp);
		}
	}

	public void insertPlanesRandomLy() {
		for (int i = 0; i != n_avioes; i++) {
			Random r = new Random();
			int r1 = (r.nextInt(nCol));
			int r2 = (r.nextInt(nLines));
			Cell cell = new Cell(r2, r1, asp.getNumColumns());

			plane = new Plane(asp, cell);
			aircraftFleet.add(plane);
			plane.start();

			int airp_dest = (r.nextInt(airportFleet.size()));

			plane.setDestination(airportFleet.get(airp_dest).getAirportCell()
					.getCelula());// vai tra�ar o destino para 1 dos aeroportos
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int dx = getWidth() / (nCol);// largura cada celula
		int dy = getHeight() / (nLines);// altura cada celula

		// DRAW AIRPORTS

		for (Airport airports : airportFleet) {
			int x1 = (int) ((airports.getAirportCell().getCelula() % nCol) * dx);
			int y1 = (int) ((airports.getAirportCell().getCelula() / nCol) * dy);
			g.drawImage(airports.getImage(), x1, y1, dx, dy, this);
		}
		// DRAW PLANES

		for (Plane airplanes : aircraftFleet) {
			int x1 = (int) ((airplanes.getPlaneCell().getCelula() % nCol) * dx);
			int y1 = (int) ((airplanes.getPlaneCell().getCelula() / nCol) * dy);
			g.drawImage(airplanes.getImage(), x1, y1, dx, dy, this);
		}

		// LINHAS
		for (int i = 1; i < nLines; i++) {
			g.drawLine(0, i * dy, getWidth(), i * dy);
		}

		// COLUNAS
		for (int i = 1; i < nCol; i++) {
			g.drawLine(i * dx, 0, i * dx, getHeight());
		}
		/*
		 * Graphics2D g2d=(Graphics2D)g; g2d.translate(170, 0);
		 * g2d.rotate(Math.PI);
		 * 
		 * g2d.drawImage(plane.getImage(), (int)
		 * ((plane.getPlaneCell().getCelula() % nCol) * dx),//desenhar 1 aviao a
		 * andar (int) ((plane.getPlaneCell().getCelula() / nCol) * dy), dx, dy,
		 * this);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * http://beginwithjava.blogspot.pt/2009/02/rotating-image-with-java.html
		 */

		// exemplo de como rodar a imagem sobre o proprio eixo
		// public void paintComponent(Graphics g){
		// Graphics2D g2d=(Graphics2D)g; // Create a Java2D version of g.
		// g2d.translate(170, 0); // Translate the center of our coordinates.
		// g2d.rotate(1); // Rotate the image by 1 radian.
		// g2d.drawImage(image, 0, 0, 200, 200, this);
		// }
		//			

		// selected=plane.getPlaneCell().getCelula(); ambos representam o numero
		// da celula , o ultimo nao é o seleccionado

		// g.drawImage(plane.getImage(), (int)
		// ((plane.getPlaneCell().getCelula() % nCol) * dx),//desenhar 1 aviao a
		// andar
		// (int) ((plane.getPlaneCell().getCelula() / nCol) * dy), dx, dy,
		// this);
		//
		//
		// g.drawImage(plane1.getImage(), (int)
		// ((plane1.getPlaneCell().getCelula() % nCol) * dx),//desenhar 1 aviao
		// a andar
		// (int) ((plane1.getPlaneCell().getCelula() / nCol) * dy), dx, dy,
		// this);

		// for(int i=0;i<2;i++){
		// Plane p1= insertPlanesRandomLy(i);
		// //p1.start();
		// // g.drawImage(p1.getImage(), (int) ((p1.getPlaneCell().getCelula() %
		// nCol) * dx),//desenhar 1 aviao a andar
		// // (int) ((p1.getPlaneCell().getCelula() / nCol) * dy), dx, dy,
		// this);
		//
		// //System.out.println((int) ((p1.getPlaneCell().getCelula() % nCol) *
		// dx)+":" + (int) ((p1.getPlaneCell().getCelula() % nCol) * dy));
		// }

		// System.out.println("Numero da celula que dei ao plane->"+(plane.getPlaneCell().getCelula()+"")+"coordenada x "+(int)
		// ((plane.getPlaneCell().getCelula() % nCol) * dx)
		// +"coordenada y "+(int) ((plane.getPlaneCell().getCelula() % nCol) *
		// dy));

		// BufferedImage off_Image =
		// new BufferedImage(100, 50,
		// BufferedImage.TYPE_INT_ARGB);
		//
		// Graphics2D g2 = off_Image.createGraphics();
		// BufferedImage bi = getMyImage();
		// File outputfile = new File("bandeira.png");
		// ImageIO.write(bi, "png", outputfile);
		// g.drawImage(off_Image, ((5 % nCol) * dx), ((5 / nCol) * dy), dx, dy,
		// null);

		// for (int i = 0; i < nCol * nLines; i++) {//esta condiçao inclui cada
		// single quadrado!
		// }

		// if (selected >= 0) {//Area selecionada com o click
		// g.setColor(Color.green);
		// g.fillRect((int) ((selected % nCol) * dx),
		// (int) ((selected / nCol) * dy), dx, dy);
		// // g.drawImage(plane.getImage(), (int) ((selected % nCol) *
		// dx),//desenhar 1 aviao
		// // (int) ((selected / nCol) * dy), dx, dy, this);
		// }
		// }
		/*
		 * if (selectedDestination >= 0) {//Area selecionada com o click //
		 * g.setColor(Color.WHITE); // g.fillRect((int) ((selectedDestination %
		 * nCol) * dx), // (int) ((selectedDestination / nCol) * dy), dx, dy);
		 * // g.drawImage(plane.getImage(), (int) ((selected % nCol) *
		 * dx),//desenhar 1 aviao // (int) ((selected / nCol) * dy), dx, dy,
		 * this); }
		 */}

	public boolean isEmpty(int i) {
		//ver.5)alterado
		for (Plane airplane : aircraftFleet) {
			if (i == airplane.getPlaneCell().getCelula()) {
				free = false;
				return free;
			}
			// ver.4) alterado
			// System.out.println("plane airport " +
			// isLandedIn(airplanes.getDestinationCell()).getId());
			// ver destinationcell
//			if (!(isMyAirport(airplane.getDestinationCell()))) {
//
//				System.out.println("plane ocupied");
//				free = false;
//				return free;
//			}

		}

		return true;
	}

	public boolean isAnAirport(int i) {
		// aqui
		for (Airport airports : airportFleet) {
			if (i == airports.getAirportCell().getCelula()) {
				return true;
			}
		}
		return false;
	}

	public boolean isMyAirport(int i) {
		// devolve airport para onde vai
		for (Airport airports : airportFleet) {
			if (i == airports.getAirportCell().getCelula()) {

				// airportFleet.get(i);
				return true;
			}
		}
		return false;
	}

	public void setSelected(Point point) {// este metodo mete o selected
		int col = (int) (point.getX() / (getWidth() / nCol));// divide coord x
		// pla largura cada celula-> indica qual a coluna onde estou
		int lin = (int) (point.getY() / (getHeight() / nLines));// ->indica qual
		// a linha onde estou a contar cima, =0
		selectedWithOneClick = col + lin * nCol;// ->selected da me o numero de
		// cada celula seleciona que vai de 0 ao nr de linhas dado ao
		// inicio ao quadrado (linhasi*linhasi)
		System.out.println("selecionei a celula + " + selected
				+ ": que esta na coluna= " + col + ", e na linha= " + lin);
		repaint();
	}

	public void setDestination(Point point) {
		int col = (int) (point.getX() / (getWidth() / nCol));// divide coord x
		// pla largura cada celula-> indica qual a coluna onde estou
		int lin = (int) (point.getY() / (getHeight() / nLines));// ->indica qual
		// a linha onde estou a contar cima, =0
		selectedDestination = col + lin * nCol;// ->selected da me o numero de
		// cada celula seleciona que vai de 0 ao nr de linhas dado ao
		// inicio ao quadrado (linhasi*linhasi)
		System.out
				.println("selecionei a celula PARA DESTINO DENTRO DO METODO SET DESTINATION "
						+ selectedDestination
						+ ": que esta na coluna= "
						+ col
						+ ", e na linha= " + lin);

		for (Plane airplane : aircraftFleet) {

			// System.out.println("FOR EACH DO SET DESTINATION");
			// System.out.println("celula do aviao: "
			// + airplane.getPlaneCell().getCelula()
			// + " selectedwithoneclick: " + selectedWithOneClick);
			// System.out.println("aiplane"+airplane + planeSelected(point));

			if (airplane.isClicked()) {

				airplane.setDestination(selectedDestination);

				System.out
						.println("NO METODO SET DESTINATION FOI ATRIBUIDO DESTINO AO AVIAO CLICADO");
			}
			airplane.setNotClicked();

		}
		repaint();
	}

	public boolean isPlaneSelected(Point point) {
		int col = (int) (point.getX() / (getWidth() / nCol));// divide coord x
		// pla largura cada celula-> indica qual a coluna onde estou
		int lin = (int) (point.getY() / (getHeight() / nLines));// ->indica qual
		// a linha onde estou a contar cima, =0
		selectedWithOneClick = col + lin * nCol;// ->selected da me o numero de
		// cada celula seleciona que vai de 0 ao nr de linhas dado ao
		// inicio ao quadrado (linhasi*linhasi)

		for (Plane airplanes : aircraftFleet) {
			if (selectedWithOneClick == airplanes.getPlaneCell().getCelula()) {// necessário
				// fazer com todos os avioes
				airplanes.airplaneIsSelected(point);// diz ao aviao que esta
				// clicado!!
				System.out
						.println("dentro do IS SELECTED:"
								+ "o selected imprime:"
								+ selected
								+ "devolve true pq a selecionada é a mesma onde está o aviao");
				return true;
			}
		}

		return false;
	}

	public Plane planeSelected(Point point) {
		int col = (int) (point.getX() / (getWidth() / nCol));// divide coord x
		// pla largura cada celula-> indica qual a coluna onde estou
		int lin = (int) (point.getY() / (getHeight() / nLines));// ->indica qual
		// a linha onde estou a contar cima, =0
		selectedWithOneClick = col + lin * nCol;// ->selected da me o numero de
		// cada celula seleciona que vai de 0 ao nr de linhas dado ao
		// inicio ao quadrado (linhasi*linhasi)
		System.out.println();
		for (Plane airplane : aircraftFleet) {

			if (selectedWithOneClick == airplane.getPlaneCell().getCelula()) {// necessário
				// fazer com todos os avioes
				return airplane;

			}

		}
		return null;
	}

	// ver alterado se pode ser chamado no plane
	public void addPlane(Plane plane2) {
		airp.addPlane(plane2);
	}

	// ver.5)alterado
	public Plane getPlane(int i) {
		// vai buscar o aviao na celula

		for (Plane airplane : aircraftFleet) {

			if (i == airplane.getPlaneCell().getCelula()) {

				return airplane;

			}

		}
		return null;
	}

}

// public void goThroughFleet(){
// for(Plane airplanes:aircraftFleet){
// airplanes.
// }
// }

/*
 * import java.awt.Color; import java.awt.Graphics; import java.awt.Point;
 * import javax.swing.JComponent; public class Board extends JComponent {
 * private int nLines; private int nCol; private int selected = -1; public
 * Board(int nLines, int nCol) { super(); this.nLines = nLines; this.nCol =
 * nCol; }
 * 
 * @Override protected void paintComponent(Graphics g) {
 * super.paintComponent(g); int dx = getWidth() / (nCol); int dy = getHeight() /
 * (nLines); for (int i = 1; i < nLines; i++) { g.drawLine(0, i * dy,
 * getWidth(), i * dy); } for (int i = 1; i < nCol; i++) { g.drawLine(i * dx, 0,
 * i * dx, getHeight()); } for (int i = 0; i < nCol * nLines; i++) { String
 * label = "" + i; int w = g.getFontMetrics().stringWidth(label) / 2; int h =
 * g.getFontMetrics().getDescent(); int x = (int) ((i % nCol + .5) * dx - w);
 * int y = (int) ((i / nCol + .5) * dy + h); g.drawString(label, x, y); } if
 * (selected >= 0) { g.setColor(Color.DARK_GRAY); g.fillRect((int) ((selected %
 * nCol) * dx), (int) ((selected / nCol) * dy), dx, dy); g.setColor(Color.red);
 * String label = selected + ""; int w = g.getFontMetrics().stringWidth(label) /
 * 2; int h = g.getFontMetrics().getDescent(); int x = (int) ((selected % nCol +
 * .5) * dx - w); int y = (int) ((selected / nCol + .5) * dy + h);
 * g.drawString(label, x, y); g.setColor(Color.black); } } public void
 * setSelected(Point point) { int col = (int) (point.getX() / (getWidth() /
 * nCol)); int lin = (int) (point.getY() / (getHeight() / nLines)); selected =
 * col + lin * nCol; System.out.println(selected + ": col= " + col + ", lin= " +
 * lin); repaint(); } }
 */