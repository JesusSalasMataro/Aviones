package ProyectoAviones;

import javax.swing.JTextArea;

public class clEspacioAereo {
	
	public static final int MAX_AVIONES = 5;
	
	public static final int LIMITE_INFERIOR_X = 0;
	public static final int LIMITE_SUPERIOR_X = 910;
	public static final int LIMITE_INFERIOR_Y = 0;
	public static final int LIMITE_SUPERIOR_Y = 500;
	
	public static final int DISTANCIA_PELIGRO_ACCIDENTE = 40;
	public static final int ALTITUD_PELIGRO_ACCIDENTE = 500;
	public static final int DISTANCIA_ACCIDENTE = 10;
	public static final int ALTITUD_ACCIDENTE = 100;
	
	private clAvion[] aviAviones;
	private int iAvionesVolando;
	private boolean bEstado;

	private int iAvionActivo = -1;  // Indica el número de avión activo al que se le aplicarán
                                    // los cambios al pulsar los botones de control de aviones

	
	public clEspacioAereo() {
		
		iAvionesVolando = 0;
		bEstado = true;
		aviAviones = new clAvion[MAX_AVIONES];
		
	}
	
	// Devuelve el índice del panel asignado si se ha podido incorporar el avión
	// al espacio aéreo, devuelve -1 en caso contrario
	public int incorporarAvion( clAvion aviAvionActual ) {
		
		boolean bIncorporado = false;
		int i = 0;
		
		if( posicionEstaLibre(aviAvionActual.getPosXAvion(), aviAvionActual.getPosYAvion())
			&& espacioAereoLibre() ) {
				
			while( !bIncorporado && (i<MAX_AVIONES) ) {
					
				if( aviAviones[i] == null ) {
					aviAviones[i] = aviAvionActual;
					bIncorporado = true;
					iAvionesVolando++;
				}
				i++;
			}
			
		}
		
		if( !bIncorporado ) i = -1;
		else i--;
		return i;
		
	}
	
	// Se eliminan los aviones en estado "Inactivo" y los que se hayan salido
	// de los límites del espacio aéreo
	public int mantenimiento( clPanelInfoAviones PanelesInfo ) {
		
		int i, iEliminados, iAccidentados;
		
		iEliminados = 0;
		i = 0;
		while( i < clEspacioAereo.MAX_AVIONES ) {
			
			if( aviAviones[i] != null ) {
				if( !dentroLimitesEspacioAereo(aviAviones[i]) ) {
					eliminarAvion(i);
					PanelesInfo.LimpiarCuadroInfoAvion(i);
					iEliminados++;
				}
			}
			i++;
			
		}
		iAccidentados = comprobarAccidentes();
		iEliminados = iEliminados + iAccidentados;
		
		return iEliminados;
		
	}
	
	public int comprobarAccidentes()  {
		
		int iNumAccidentados = 0, i, ii;
		int iDiferenciaAltitud, iDiferenciaDistanciaX, iDiferenciaDistanciaY;
		boolean bAccidente = false;
		
		for( i=0; i<clEspacioAereo.MAX_AVIONES; i++ ) {
			if( aviAviones[i] != null ) {
				for( ii=i; ii<clEspacioAereo.MAX_AVIONES; ii++ ) {
					if( (aviAviones[ii] != null) && (ii != i) && !bAccidente ) {
						
						iDiferenciaAltitud = Math.abs(aviAviones[i].getAltitud() - aviAviones[ii].getAltitud());
						iDiferenciaDistanciaX = Math.abs(aviAviones[i].getPosXAvion() - aviAviones[ii].getPosXAvion());
						iDiferenciaDistanciaY = Math.abs(aviAviones[i].getPosYAvion() - aviAviones[ii].getPosYAvion());
						
						// Comprovamos si hay 2 aviones que estén demasiado cerca, tanto en altura como en distancia entre ellos,
						// en ese caso se muestra un mensaje de advertencia
						if( (iDiferenciaAltitud < ALTITUD_PELIGRO_ACCIDENTE) 
							&& (iDiferenciaDistanciaX < DISTANCIA_PELIGRO_ACCIDENTE)
							&& (iDiferenciaDistanciaY < DISTANCIA_PELIGRO_ACCIDENTE) ) {
							
							clVentanaPrincipal.lblMensajes.append("PELIGRO de accidente entre aviones " + 
								Integer.toString(i) + " y " + Integer.toString(ii) + ".\n");
							clVentanaPrincipal.lblMensajes.setCaretPosition(clVentanaPrincipal.lblMensajes.getDocument().getLength());
						}
						
						// Comprovamos si se ha producido un accidente, en ese caso mostramos un mensaje y eliminamos del
						// espacio aéreo los aviones implicados
						if( (iDiferenciaAltitud < ALTITUD_ACCIDENTE) 
								&& (iDiferenciaDistanciaX < DISTANCIA_ACCIDENTE)
								&& (iDiferenciaDistanciaY < DISTANCIA_ACCIDENTE) ) {
								
								clVentanaPrincipal.lblMensajes.append("ACCIDENTE aéreo entre aviones " + 
									Integer.toString(i) + " y " + Integer.toString(ii) + ".\n");
								clVentanaPrincipal.lblMensajes.setCaretPosition(clVentanaPrincipal.lblMensajes.getDocument().getLength());
								eliminarAvion(i);
								eliminarAvion(ii);
								iNumAccidentados = iNumAccidentados + 2;
								bAccidente = true;
							}
						
					}
				}
				
				
			}
		}
				
		return iNumAccidentados;	
	}
	
	public void eliminarAvion( int iIndiceAvion ) {
		
		aviAviones[iIndiceAvion] = null;		
		iAvionesVolando--;
		setAvionActivo();
		
	}
	
	// Esta función se encarga de establecer el nuevo avión activo después de haber
	// eliminado un avión
	public void setAvionActivo() {
		
		boolean bEncontrado = false;
		int i = 0;
		
		while( !bEncontrado && (i < MAX_AVIONES) ) {
			if( aviAviones[i] != null ) bEncontrado = true;
			i++;
		}
		if( bEncontrado ) iAvionActivo = i - 1;
		else iAvionActivo = -1;
		
	}
	
	// Cambia de avión activo al siguiente al pulsar el botón "Cambiar avión"
	public void cambiarAvionActivo() {
		
		boolean bEncontrado;
		int iIndicePaneles, i;
		
		if( iAvionesVolando > 0 ) {
			if( iAvionActivo == (clEspacioAereo.MAX_AVIONES - 1) ) iIndicePaneles = 0;
			else iIndicePaneles = iAvionActivo + 1;
			i = 0;
			bEncontrado = false;
			while( !bEncontrado && (i < clEspacioAereo.MAX_AVIONES) ) {
				if( aviAviones[iIndicePaneles] != null ) {
					iAvionActivo = iIndicePaneles;
					bEncontrado = true;
				}				
				iIndicePaneles++;
				if( iIndicePaneles == clEspacioAereo.MAX_AVIONES ) iIndicePaneles = 0;				
				i++;
			}
		}		
		
	}
	
	public boolean dentroLimitesEspacioAereo( clAvion aviAvionActual ) {
		
		boolean bDentroLimites = true;
		
		if( (aviAvionActual.getPosXAvion() < LIMITE_INFERIOR_X) ||
			(aviAvionActual.getPosXAvion() > LIMITE_SUPERIOR_X) || 
			(aviAvionActual.getPosYAvion() < LIMITE_INFERIOR_Y) ||
			(aviAvionActual.getPosYAvion() > LIMITE_SUPERIOR_Y) ) {
			
			bDentroLimites = false;
			
		}
		return bDentroLimites;
		
	}
	
	// Dada una matrícula devuelve un avión en caso de que exista o nulo en
	// caso de no existir
	public clAvion getAvion( String sMatricula ) {
		
		clAvion aviAvionActual = null;
		int i;
		
		i = 0;
		while( (i < iAvionesVolando) && (aviAvionActual == null) ) {
			if( aviAviones[i] != null ) {
				if( aviAviones[i].getMatricula().equals(sMatricula) ) {
					aviAvionActual = new clAvion();
					aviAvionActual = aviAviones[i];
				}
			}
			i++;
		}
		return aviAvionActual;
		
	}
	
	public clAvion getAvion( int iNumAvion ) {
		
		return aviAviones[iNumAvion];
		
	}
	
	public void setAvion( clAvion A, int iNumAvion ) {
		
		aviAviones[iNumAvion] = A;
		
	}
	
	// Comprueba si una determinada posición de la pista está libre
	public boolean posicionEstaLibre( int iPX, int iPY ) {
		
		boolean bLibre = true;
		int i;
		
		for( i=0; i<iAvionesVolando; i++ ) {
			if( aviAviones[i] != null ) {
				if( (aviAviones[i].getPosXAvion() == iPX) 
					&& (aviAviones[i].getPosYAvion() == iPY) && (aviAviones[i].getAltitud() == 0)) {
					
						bLibre = false;
						
				}
					
			}
		}
		
		return bLibre;
		
	}
	
	public boolean espacioAereoLibre() {
		
		if( iAvionesVolando < MAX_AVIONES ) bEstado = true;
		else bEstado = false;
		
		return bEstado;
		
	}
	
	public clAvion[] getTodosAviones() {
		
		return aviAviones;
		
	}
	
	public int getNumAviones() {
		
		return iAvionesVolando;
		
	}
	
	public void setAvionActivo( int iNumAvion ) {
		
		iAvionActivo = iNumAvion;
		
	}
	
	public int getAvionActivo() {
		
		return iAvionActivo;
		
	}
			
}
