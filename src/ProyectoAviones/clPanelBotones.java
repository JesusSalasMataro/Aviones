package ProyectoAviones;

import java.awt.Graphics;
import javax.swing.JPanel;

public class clPanelBotones extends JPanel {
	
	public clPanelBotones() {
		
		super();
		
	}
	
	public void paintComponent( Graphics g ) {
		
		g.drawImage(clVentanaPrincipal.imgFondoPanelBotones, 0, 0, null);
		
	}

}
