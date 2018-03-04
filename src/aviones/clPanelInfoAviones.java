package aviones;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class clPanelInfoAviones extends JPanel {
	
	public static final int LABEL_NUMAVION_MATRICULA = 0;
	public static final int LABEL_CAPACIDAD = 1;
	public static final int LABEL_FABRICANTE_MODELO = 2;
	public static final int LABEL_VELOCIDAD = 3;
	public static final int LABEL_ALTURA_RUMBO = 4;
	public static final int LABEL_MOTOR_TRENATERRIZAJE = 5;
	public static final int LABEL_PX_PY = 6;

	//private Color colColores[] = { Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.ORANGE };

	private clPanelInfo[] pnlInfoAvion;
	private JLabel[][] lblEtiqueta;
	
	public clPanelInfoAviones() {
		
		InicializarPaneles();
		
	}
	
	public void InicializarPaneles() {
		
		int i;

		pnlInfoAvion= new clPanelInfo[clEspacioAereo.MAX_AVIONES];
		lblEtiqueta = new JLabel[7][clEspacioAereo.MAX_AVIONES];

		for( i=0; i<clEspacioAereo.MAX_AVIONES; i++ ) {
			
			pnlInfoAvion[i] = new clPanelInfo();
			pnlInfoAvion[i].setBorder(new LineBorder(new Color(0, 0, 0), 3));
			pnlInfoAvion[i].setMinimumSize(new Dimension(270, 130));
			pnlInfoAvion[i].setMaximumSize(new Dimension(270, 130));
			pnlInfoAvion[i].setPreferredSize(new Dimension(270, 130));
			pnlInfoAvion[i].setLayout(new BoxLayout(pnlInfoAvion[i], BoxLayout.Y_AXIS));
			pnlInfoAvion[i].setBackground(new Color(80, 100, 0));
			
			lblEtiqueta[LABEL_NUMAVION_MATRICULA][i] = new JLabel();
			lblEtiqueta[LABEL_CAPACIDAD][i] = new JLabel();
			lblEtiqueta[LABEL_FABRICANTE_MODELO][i] = new JLabel();
			lblEtiqueta[LABEL_VELOCIDAD][i] = new JLabel();
			lblEtiqueta[LABEL_ALTURA_RUMBO][i] = new JLabel();
			lblEtiqueta[LABEL_MOTOR_TRENATERRIZAJE][i] = new JLabel();
			lblEtiqueta[LABEL_PX_PY][i] = new JLabel();
			
			lblEtiqueta[LABEL_NUMAVION_MATRICULA][i].setForeground(new Color(255, 255, 255));
			lblEtiqueta[LABEL_CAPACIDAD][i].setForeground(new Color(255, 255, 255));
			lblEtiqueta[LABEL_FABRICANTE_MODELO][i].setForeground(new Color(255, 255, 255));
			lblEtiqueta[LABEL_VELOCIDAD][i].setForeground(new Color(255, 255, 255));
			lblEtiqueta[LABEL_ALTURA_RUMBO][i].setForeground(new Color(255, 255, 255));
			lblEtiqueta[LABEL_MOTOR_TRENATERRIZAJE][i].setForeground(new Color(255, 255, 255));
			lblEtiqueta[LABEL_PX_PY][i].setForeground(new Color(255, 255, 255));
			
			for( int ii=0; ii<7; ii++ ) pnlInfoAvion[i].add(lblEtiqueta[ii][i]);
			
			this.add(pnlInfoAvion[i]);
			
		}
		
	}
	
	public void CrearPanel( int iPosPanel, String strMatricula, String strFabricante,
			String strModelo, int iCapacidad, int iPosX, int iPosY ) {
		
		//lblEtiqueta[LABEL_NUMAVION_MATRICULA][iPosPanel].setForeground(colColores[iPosPanel]);
		lblEtiqueta[LABEL_NUMAVION_MATRICULA][iPosPanel].setText(" Avi�n " + Integer.toString(iPosPanel+1) + "   Matr�cula: " + strMatricula);
		lblEtiqueta[LABEL_CAPACIDAD][iPosPanel].setText(" Capacidad: " + Integer.toString(iCapacidad));
		lblEtiqueta[LABEL_FABRICANTE_MODELO][iPosPanel].setText(" Fabricante: " + strFabricante + "   Modelo: " + strModelo);
		lblEtiqueta[LABEL_VELOCIDAD][iPosPanel].setText(" Velocidad: 0 km/h");
		lblEtiqueta[LABEL_ALTURA_RUMBO][iPosPanel].setText(" Altura: 0 m   Rumbo: 0�");
		lblEtiqueta[LABEL_MOTOR_TRENATERRIZAJE][iPosPanel].setText(" Motor: Off   Tren at.: On");
		lblEtiqueta[LABEL_PX_PY][iPosPanel].setText(" PosX: " + Integer.toString(iPosX) + "   PosY: " + Integer.toString(iPosY));
		
		pnlInfoAvion[iPosPanel].setBorder(new LineBorder(new Color(255, 0, 0), 3));
		
	}
	
	// Se encarga de resaltar en rojo el panel correspondiente al �ndice iPosPanel
	public void SeleccionarPanel( int iPosPanel ) {
		
		int i;
		
		for( i=0; i<clEspacioAereo.MAX_AVIONES; i++ ) {
			if( i == iPosPanel ) pnlInfoAvion[i].setBorder(new LineBorder(new Color(255, 0, 0), 3));
			else pnlInfoAvion[i].setBorder(new LineBorder(new Color(0, 0, 0), 3));
		}
		
	}
	
	// Actualiza la informaci�n de los paneles de datos de los aviones
	public void ActualizarInfoAviones( clEspacioAereo EspacioAereo ) {
		
		int i, ii;
		
		for( i=0; i<EspacioAereo.MAX_AVIONES; i++ ) {
			
			if( EspacioAereo.getAvion(i) != null ) {
				ModificarCuadroInfoAvion(LABEL_VELOCIDAD, EspacioAereo.getAvion(i), i, EspacioAereo.getAvionActivo());
				ModificarCuadroInfoAvion(LABEL_ALTURA_RUMBO, EspacioAereo.getAvion(i), i, EspacioAereo.getAvionActivo());
				ModificarCuadroInfoAvion(LABEL_MOTOR_TRENATERRIZAJE, EspacioAereo.getAvion(i), i, EspacioAereo.getAvionActivo());
				ModificarCuadroInfoAvion(LABEL_PX_PY, EspacioAereo.getAvion(i), i, EspacioAereo.getAvionActivo());
			}
			else 
				LimpiarCuadroInfoAvion(i);
			
		}
		
	}
	
	// Modifica un campo concreto del panel de informaci�n de un avi�n
	// iNumEtiqueta:  indica el campo a modificar
	// aviAvion:      el avi�n al cual se le va a modificar la informaci�n
	// iPosPanel:     �ndice del panel que pertenece a ese avi�n
	// iAvionActivo:  avi�n activo en ese momento     
	public void ModificarCuadroInfoAvion( int iNumEtiqueta, clAvion aviAvion, int iPosPanel, int iAvionActivo ) {
		
		String strTempMotor, strTempTrenAt;
		
		switch( iNumEtiqueta ) {

			case LABEL_VELOCIDAD:
				lblEtiqueta[LABEL_VELOCIDAD][iPosPanel].setText(" Velocidad: " + aviAvion.getVelocidad() + " km/h");
				break;
				
			case LABEL_ALTURA_RUMBO:
				lblEtiqueta[LABEL_ALTURA_RUMBO][iPosPanel].setText(" Altura: " + Integer.toString(aviAvion.getAltitud()) + " m   " + 
					"Rumbo: " + Integer.toString(aviAvion.getRumbo()) + "�");
				break;
				
			case LABEL_MOTOR_TRENATERRIZAJE:
				if(  aviAvion.motorEncendido() ) strTempMotor = "On";
				else strTempMotor = "Off";
				if( aviAvion.trenAterrizajeBajado() ) strTempTrenAt = "On";
				else strTempTrenAt = "Off";
				lblEtiqueta[LABEL_MOTOR_TRENATERRIZAJE][iPosPanel].setText(" Motor: " + strTempMotor + "   Tren at.: " + strTempTrenAt);
				break;
				
			case LABEL_PX_PY:
				lblEtiqueta[LABEL_PX_PY][iPosPanel].setText(" PosX: " + Integer.toString(aviAvion.getPosXAvion()) + 
						"   PosY: " + Integer.toString(aviAvion.getPosYAvion()));
				break;
				
		}
	
		if( iPosPanel == iAvionActivo )
			pnlInfoAvion[iPosPanel].setBorder(new LineBorder(new Color(255, 0, 0), 3));
		else
			pnlInfoAvion[iPosPanel].setBorder(new LineBorder(new Color(0, 0, 0), 3));
		
	}
	
	// Vac�a un cuadro de informaci�n de un avi�n que haya sido eliminado
	public void LimpiarCuadroInfoAvion( int iNumCuadroInfo ) {
		
		int i;
		
		for( i=0; i<7; i++ ) {
			lblEtiqueta[i][iNumCuadroInfo].setText("");
		}
		pnlInfoAvion[iNumCuadroInfo].setBorder(new LineBorder(new Color(0, 0, 0), 3));
		
	}

}
