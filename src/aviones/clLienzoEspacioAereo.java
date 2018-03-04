package aviones;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.color.*;

public class clLienzoEspacioAereo extends JPanel {
	
	private clAvion[] aviAviones;
	
	public clLienzoEspacioAereo( ) {
	
		super();
		aviAviones = new clAvion[clEspacioAereo.MAX_AVIONES];
		
	}
	
	public void paintComponent( Graphics g ) {
		
		int i, iAncho, iAlto;
		
		iAncho = getSize().width;
		iAlto = getSize().height;
			
		g.drawImage(clVentanaPrincipal.imgFondoEspacioAereo, 0, 0, iAncho, iAlto, null);
			
		for( i=0; i<clEspacioAereo.MAX_AVIONES; i++ ) {
			if( aviAviones[i] != null ) {				
				g.drawImage(clVentanaPrincipal.imgAviones[i], aviAviones[i].getPosXAvion(), 
					aviAviones[i].getPosYAvion(), 20, 20, null);
				//g.setColor(colColores[i]);
				//g.fillOval(aviAviones[i].getPosXAvion(), aviAviones[i].getPosYAvion(), 10, 10);
			}
		}	
					
	}
	
	public void pintar( clAvion[] aviAviones ) {
		
		int i;
		
		for( i=0; i<clEspacioAereo.MAX_AVIONES; i++ ) {
			this.aviAviones[i] = aviAviones[i];
		}
		
	}
	
}
