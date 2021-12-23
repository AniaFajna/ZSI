import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

	MainFrame() {
		this.setSize(1000,600);
		this.setLocation(300,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(new PlanePanel(), BorderLayout.CENTER);
	}
}
