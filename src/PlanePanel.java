import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.awt.*;
import java.util.TimerTask;

public class PlanePanel extends JPanel implements ActionListener {

	private JButton start;
	private int planeX = 0;
	private int planeY = 250;
	private double[] floorY;
	private int[] floorX;
	private FuzzyLogic fz;
	private Timer t;
	ArrayList<Integer> drawLineX, drawLineY;
	Graphics2D g2d;
	MouseListener ml;

	PlanePanel() {
		initComponents();
		this.add(start);
		ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				drawLineX.add(e.getX());
				drawLineY.add(e.getY());
				movement();
			}
		};
		this.addMouseListener(ml);
		fz = new FuzzyLogic(floorX, floorY, planeX, planeY);
	}

	private void initComponents() {
		start = new JButton("Start");
		start.addActionListener(this);

		drawLineX = new ArrayList<>();
		drawLineY = new ArrayList<>();
		drawLineX.add(0);
		drawLineY.add(300);
		t = new Timer();
	}

	private void drawPoint(Graphics2D g2d, int x, int y) {
		g2d.drawRect(x, y,5,5);
	}

	private void drawLines(Graphics2D g2d) {
		for (int i = 1; i < floorX.length; i++) {
			g2d.drawLine(floorX[i-1], (int) Math.round(floorY[i-1]), floorX[i], (int) Math.round(floorY[i]));
		}
	}

	private void fillFloorYParameters() {
		int x = drawLineX.get(drawLineX.size()-1)/10 + 1;
		floorY = new double[x];
		int jVal = 1;
		int limit = (drawLineX.get(1) - drawLineX.get(0))/10 + 1;
		floorY[0] = drawLineY.get(0);
		for (int i = 1; i < drawLineY.size(); i++) {
			double values = drawLineY.get(i) - drawLineY.get(i - 1);
			if (i > 1) {
				limit += Math.abs((drawLineX.get(i) - drawLineX.get(i-1)))/10 + 1;
				if (limit > x) {
					limit = x;
				}
			}
			double increment = values/limit;
			for (int j = jVal; j <= limit; j++) {
				if (limit == x) {
					limit--;
				}
				floorY[j] = floorY[j-1] + increment;
			}
			jVal = limit;
		}
	}

	private void fillFloorXParameters() {
		int limit = drawLineX.get(drawLineX.size()-1)/10 + 1;
		floorX = new int[limit];
		floorX[0] = 0;
		for (int i = 1; i < limit; i++) {
			floorX[i] = floorX[i-1] + 10;
		}
	}

	private void movement() {
		planeX = fz.getPlaneX();
		planeY = fz.getPlaneY();
		revalidate();
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		try {
			drawLines(g2d);
		} catch (Exception e) {
			System.out.print("");
		}
		g2d.drawRect(planeX, planeY,10,10);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(start)) {
			this.removeMouseListener(ml);
			fillFloorYParameters();
			fillFloorXParameters();
			fz = new FuzzyLogic(floorX, floorY, planeX, planeY);
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					fz.active();
					movement();
					if (planeX/10 == floorX.length-1) {
						t.cancel();
					}
				}
			}, 0, 1);
		}
	}
}