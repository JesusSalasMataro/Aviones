package ProyectoAviones;

public class clAvion {
	
	public final static int ACTIVO = 0;
	public final static int INACTIVO = 1;
	public final static int DEMASIADA_ALTITUD = 2;
	public final static int DEMASIADA_VELOCIDAD = 3;
	
	public final static int ELEVACION_OK = 0;
	public final static int ELEVACION_ERROR_VELOCIDAD = 1;
	public final static int ELEVACION_ERROR_TRENAT = 2;
	public final static int ELEVACION_ERROR_MOTOR = 3;
	
	public final static int DESCENSO_OK = 0;
	public final static int DESCENSO_ERROR_ALTITUD = 1;
	public final static int DESCENSO_ERROR_MOTOR = 3;

	private String sModelo;
	private String sFabricante;
	private int iCapacidad;  // número máximo de pasajeros que permite
	private String sMatricula;
	
	private boolean bMotorEncendido;
	private int iVelocidad;
	private boolean bTrenAterrizajeBajado;
	private int iAltitud;
	private int iRumbo;  // entre 0 y 360 grados
	
	private int iPosXAvion;
	private int iPosYAvion;
	
	private int sEstado;
	
	public clAvion( ) {
		
	}
	
	public clAvion( String sMatricula, String sModelo, String sFabricante,
			int iCapacidad, int iPosXAvion, int iPosYAvion ) {
		
		setMatricula(sMatricula);
		setModelo(sModelo);
		setFabricante(sFabricante);
		setCapacidad(iCapacidad);
		bMotorEncendido = false;
		iVelocidad = 0;
		bTrenAterrizajeBajado = true;
		iAltitud = 0;
		iRumbo = 0;
		this.iPosXAvion = iPosXAvion;
		this.iPosYAvion = iPosYAvion;
		
	}
	
	public void avanzar() {
		
		double dRadianes;
		
		dRadianes = Math.toRadians(iRumbo);
		
		iPosXAvion = (int)(iPosXAvion + Math.round(iVelocidad/50 * Math.cos(dRadianes)));
		iPosYAvion = (int)(iPosYAvion - Math.round(iVelocidad/50 * Math.sin(dRadianes)));
		
	}
	
	public void encenderMotor() {
		bMotorEncendido = true;
	}
	
	public void apagarMotor() {
		if( (iVelocidad == 0) && (iAltitud == 0) )
			bMotorEncendido = false;
	}
	
	public boolean motorEncendido() {
		return bMotorEncendido;
	}
	
	public boolean bajarTrenAterrizaje() {
		
		if( !((getAltitud() > 500) || (getVelocidad() > 300)) ) {
			bTrenAterrizajeBajado = true;
		}
		
		return bTrenAterrizajeBajado;
	}
	
	public boolean subirTrenAterrizaje() {
		
		if( iAltitud > 0 ) {
			bTrenAterrizajeBajado = false;
		}
		return !bTrenAterrizajeBajado;  // Devuelve si se ha podido subir o no el tren de aterrizaje
	}
	
	public boolean trenAterrizajeBajado() {		
		return bTrenAterrizajeBajado;	
	}
	
	public int elevarAvion( int iMetros ) {
		
		int iElevacionOk = ELEVACION_OK;
		
		if( motorEncendido() ) {
			if( iAltitud == 0 ) {
				if( iVelocidad >= 180 )
					iAltitud = iAltitud + iMetros;
				else
					iElevacionOk = ELEVACION_ERROR_VELOCIDAD;
			}
			else
				if( ((iAltitud + iMetros) > 500) && trenAterrizajeBajado() )
					iElevacionOk = ELEVACION_ERROR_TRENAT;
				else 
					iAltitud = iAltitud + iMetros;
		}
		else
			iElevacionOk = ELEVACION_ERROR_MOTOR;
		
		return iElevacionOk;
	}	
	
	public int descenderAvion( int iMetros ) {
		
		int iDescensoOk = DESCENSO_OK;
		
		if( motorEncendido() ) 
			if( iMetros <= iAltitud )
				iAltitud = iAltitud - iMetros;
			else
				iDescensoOk = DESCENSO_ERROR_ALTITUD;
		else
			iDescensoOk = DESCENSO_ERROR_MOTOR;
		
		return iDescensoOk;
	}
	
	public int getAltitud() {	
		return iAltitud;	
	}
	
	public void acelerarAvion( int iKmh ) {	
		if( motorEncendido() )
			iVelocidad = iVelocidad + iKmh;			
	}
	
	public void frenarAvion( int iKmh ) {		
		if( motorEncendido() )
			iVelocidad = iVelocidad - iKmh;	
		if( iVelocidad < 0 ) iVelocidad = 0;
	}
	
	public int getVelocidad() {
		return iVelocidad;
	}
	
	public String getModelo() {
		return sModelo;
	}
	
	public void setModelo(String sModelo) {
		this.sModelo = sModelo;
	}
	
	public String getFabricante() {
		return sFabricante;
	}
	
	public void setFabricante(String sFabricante) {
		this.sFabricante = sFabricante;
	}
	
	public int getCapacidad() {
		return iCapacidad;
	}
	
	public void setCapacidad(int iCapacidad) {
		this.iCapacidad = iCapacidad;
	}
	
	public String getMatricula() {
		return sMatricula;
	}
	
	public void setMatricula(String sMatricula) {
		this.sMatricula = sMatricula;
	}
	
	public void setEstado( int sEstado ) {
		this.sEstado = sEstado;
	}
	
	public int getEstado() {
		return sEstado;
	}
	
	public void setPosXAvion( int iPosXAvion ) {
		this.iPosXAvion = iPosXAvion;
	}
	
	public int getPosXAvion() {
		return iPosXAvion;
	}
	
	public void setPosYAvion( int iPosYAvion ) {
		this.iPosYAvion = iPosYAvion;
	}
	
	public int getPosYAvion() {
		return iPosYAvion;
	}

	public boolean establecerRumbo( int iGrados ) {
		
		boolean bRumboOk = true;
		
		if( (iGrados >= 0) && (iGrados <= 360) )
			iRumbo = iGrados;
		else
			bRumboOk = false;
		
		return bRumboOk;		
	}
	
	public int getRumbo() {
		
		return iRumbo;
		
	}
	
		
}
