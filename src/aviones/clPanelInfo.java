package aviones;

import java.awt.Graphics;
import javax.swing.JPanel;

public class clPanelInfo extends JPanel {
	
	public clPanelInfo() {
		
		super();
		
	}
	
	public void paintComponent( Graphics g ) {
		
		g.drawImage(clVentanaPrincipal.imgFondoPanelesInfo, 0, 0, null);
		
	}

}
