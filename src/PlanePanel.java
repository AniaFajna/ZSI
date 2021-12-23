import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.awt.*;
import java.util.TimerTask;

public class PlanePanel extends JPanel implements ActionListener {

	private JPanel x;
	private JButton start, reset;
	private JSpinner js;
	private int planeX;
	private int planeY;
	private double[] floorY;
	private int[] floorX;
	private FuzzyLogic fz;
	private Timer t;
	ArrayList<Integer> drawLineX, drawLineY;
	Graphics2D g2d;
	MouseListener ml;
	int deltaH;

	PlanePanel() {
		initComponents();
		SpinnerModel model = new SpinnerNumberModel(50, // initial value
				20,  // min
				300, // max
				1);  // step
		js.setModel(model);
		this.add(x, BorderLayout.NORTH);
		ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				drawLineX.add(e.getX());
				drawLineY.add(e.getY());
				movement();
			}
		};
		this.addMouseListener(ml);
	}

	private void initComponents() {
		x = new JPanel();
		x.setLayout(new GridLayout(1,1, 15, 15));
		start = new JButton("Start");
		start.addActionListener(this);
		start.setEnabled(false);
		reset = new JButton("Reset");
		reset.addActionListener(this);
		js = new JSpinner();
		planeX = 0;
		planeY = 250;
		x.add(start);
		x.add(reset);
		x.add(new JLabel("Δh :", SwingConstants.RIGHT));
		x.add(js);

		drawLineX = new ArrayList<>();
		drawLineY = new ArrayList<>();
		drawLineX.add(0);
		drawLineY.add(300);
		t = new Timer();
		fz = new FuzzyLogic(floorX, floorY, planeX, planeY);
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
		start.setEnabled(true);
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
			deltaH = (int) js.getValue();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					reset.setEnabled(false);
					fz.active(deltaH);
					movement();
					if (planeX/10 == floorX.length-1) {
						t.cancel();
						reset.setEnabled(true);
					}
				}
			}, 0, 1);
		} else if (e.getSource().equals(reset)) {
			removeAll();
			int index = (int) js.getValue();
			initComponents();
			SpinnerModel model = new SpinnerNumberModel(index, // initial value
					20,  // min
					300, // max
					1);  // step
			js.setModel(model);
			this.add(x, BorderLayout.NORTH);
			planeX = 0;
			planeY = 300 - (int) js.getValue();
			floorY = null;
			floorX = null;
			ml = new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					drawLineX.add(e.getX());
					drawLineY.add(e.getY());
					movement();
				}
			};
			this.addMouseListener(ml);
			fz = new FuzzyLogic(floorX, floorY, planeX, planeY);
			this.repaint();
			this.validate();
		}
	}
}