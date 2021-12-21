import javafx.geometry.Orientation;
import javafx.scene.control.Slider;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

	MainFrame() {
		this.setSize(1000,600);
		this.setLocation(300,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(new PlanePanel(), BorderLayout.CENTER);
	}
}
