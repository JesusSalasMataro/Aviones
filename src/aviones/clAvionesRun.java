package aviones;

import javax.swing.JTextArea;

public class clAvionesRun extends Thread {
	
	private clEspacioAereo EspacioAereo;
	private clLienzoEspacioAereo Lienzo;
	private clPanelInfoAviones PanelesInfo;
	private boolean bParar;
	
	public clAvionesRun( clPanelInfoAviones PanInfo ) {
		super();
		PanelesInfo = PanInfo;
		bParar = false;
	}
	
	public void run() {

		clAvion[] aviAviones;
		int i;

		if( (EspacioAereo != null) && (Lienzo != null) ) {
								
			aviAviones = new clAvion[EspacioAereo.getNumAviones()];
			aviAviones = EspacioAereo.getTodosAviones();
			
			bParar = false;
			while( !bParar ) {
				
				for( i=0; i<EspacioAereo.MAX_AVIONES; i++ ) {
					if( aviAviones[i] != null ) aviAviones[i].avanzar();
				}
				
				// El procedimiento 'mantenimiento' elimina del espacio a�reo los aviones en estado
				// inactivo y los que se han salido de los l�mites del espacio a�reo, devuelve el
				// n�mero de aviones eliminados
				if( EspacioAereo.mantenimiento(PanelesInfo) > 0 ) {
					// Comprovamos si quedan aviones y en ese caso establecemos el nuevo avi�n activo
					if( EspacioAereo.getNumAviones() > 0 ) 
						PanelesInfo.SeleccionarPanel(EspacioAereo.getAvionActivo());
				}
				PanelesInfo.ActualizarInfoAviones(EspacioAereo);
				
				Lienzo.pintar(EspacioAereo.getTodosAviones());
				Lienzo.repaint();

				try {
					sleep(500);
				} catch( InterruptedException e ) {
					bParar = true;
				}
				
			}
		}
		
	}
	
	public void CargarEspacioAereo( clEspacioAereo ea, clLienzoEspacioAereo l ) {
		
		EspacioAereo = ea;
		Lienzo = l;
		
	}
	
	public void detener() {
		bParar = true;
	}


}

