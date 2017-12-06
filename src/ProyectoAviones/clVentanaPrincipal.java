package ProyectoAviones;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;


public class clVentanaPrincipal extends JFrame {
	
	public final int ANCHO_ESPACIO_AEREO = 900;
	public final int ALTO_ESPACIO_AEREO = 495;
	public final int PANEL_MENSAJES_LINEAS = 8;
	public final int PANEL_MENSAJES_ANCHO = 42;
	
	public final int CANCELAR = -9999;
	
	private clEspacioAereo EspacioAereo;
	private clAvionesRun arRun;
	private JPanel contentPane;
	private clPanelInfoAviones ContenedorPanelesInfoAviones;
	private clLienzoEspacioAereo pnlEspacioAereo;
	private JPanel pnlMensajes;
	
	private boolean bSimuladorDetenido;

	public static JTextArea lblMensajes;  
	private JButton btnIniciarSimulador;
	
	public static Image imgFondoEspacioAereo;
	public static Image imgFondoPanelesInfo;
	public static Image imgFondoPanelBotones;
	public static Image imgAviones[];
	
	public clVentanaPrincipal() {
		
		super("Simulador Aéreo");
		setMinimumSize(new Dimension(1200, 700));
		setPreferredSize(new Dimension(1200, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		contentPane.setLayout(gbl_contentPane);

		// Imágenes del programa: fondos, iconos de los aviones, etc.
		imgFondoEspacioAereo = (new ImageIcon(getClass().getResource("../images/radar01_900x495.jpg")).getImage());
		imgFondoPanelesInfo = (new ImageIcon(getClass().getResource("../images/fondo_paneles_info.jpg")).getImage());
		imgFondoPanelBotones = (new ImageIcon(getClass().getResource("../images/fondo_panel_botones.jpg")).getImage());
		imgAviones = new Image[clEspacioAereo.MAX_AVIONES];
		imgAviones[0] = (new ImageIcon(getClass().getResource("../images/avion1.jpg")).getImage());
		imgAviones[1] = (new ImageIcon(getClass().getResource("../images/avion2.jpg")).getImage());
		imgAviones[2] = (new ImageIcon(getClass().getResource("../images/avion3.jpg")).getImage());
		imgAviones[3] = (new ImageIcon(getClass().getResource("../images/avion4.jpg")).getImage());
		imgAviones[4] = (new ImageIcon(getClass().getResource("../images/avion5.jpg")).getImage());

		
		// Panel que representa el espacio aéreo en la pantalla, es el espacio más
		// grande de la ventana
		pnlEspacioAereo = new clLienzoEspacioAereo();
		pnlEspacioAereo.setMaximumSize(new Dimension(ANCHO_ESPACIO_AEREO, ALTO_ESPACIO_AEREO));
		pnlEspacioAereo.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		pnlEspacioAereo.setPreferredSize(new Dimension(ANCHO_ESPACIO_AEREO, ALTO_ESPACIO_AEREO));
		pnlEspacioAereo.setMinimumSize(new Dimension(ANCHO_ESPACIO_AEREO, ALTO_ESPACIO_AEREO));
		GridBagConstraints gbc_pnlEspacioAereo = new GridBagConstraints();
		gbc_pnlEspacioAereo.gridheight = 1;
		gbc_pnlEspacioAereo.gridwidth = 3;
		gbc_pnlEspacioAereo.insets = new Insets(5, 0, 5, 5);
		gbc_pnlEspacioAereo.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnlEspacioAereo.gridx = 0;
		gbc_pnlEspacioAereo.gridy = 0;
		contentPane.add(pnlEspacioAereo, gbc_pnlEspacioAereo);
		
		
		// EspacioAereo contendrá los aviones hasta un máximo de 5
		EspacioAereo = new clEspacioAereo();
		bSimuladorDetenido = true;
		
		
		// Panel contenedor de los paneles de información de los aviones en la parte
		// derecha de la pantalla
		ContenedorPanelesInfoAviones = new clPanelInfoAviones();
		ContenedorPanelesInfoAviones.setMaximumSize(new Dimension(290, 650));
		ContenedorPanelesInfoAviones.setPreferredSize(new Dimension(290, 650));
		ContenedorPanelesInfoAviones.setMinimumSize(new Dimension(290, 650));
		GridBagConstraints gbc_ContenedorPanelesInfoAviones = new GridBagConstraints();
		gbc_ContenedorPanelesInfoAviones.insets = new Insets(5, 0, 5, 0);
		gbc_ContenedorPanelesInfoAviones.gridheight = 2;
		gbc_ContenedorPanelesInfoAviones.fill = GridBagConstraints.HORIZONTAL;
		gbc_ContenedorPanelesInfoAviones.anchor = GridBagConstraints.NORTH;
		gbc_ContenedorPanelesInfoAviones.gridx = 3;
		gbc_ContenedorPanelesInfoAviones.gridy = 0;
		contentPane.add(ContenedorPanelesInfoAviones, gbc_ContenedorPanelesInfoAviones);
		ContenedorPanelesInfoAviones.setLayout(new BoxLayout(ContenedorPanelesInfoAviones, BoxLayout.Y_AXIS));
		
		
		// Panel que contiene los botones de control del programa
		clPanelBotones pnlBotones = new clPanelBotones();
		pnlBotones.setMinimumSize(new Dimension(400, 150));
		pnlBotones.setPreferredSize(new Dimension(400, 150));
		pnlBotones.setMaximumSize(new Dimension(400, 150));
		pnlBotones.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		GridBagConstraints gbc_pnlBotones = new GridBagConstraints();
		gbc_pnlBotones.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnlBotones.insets = new Insets(0, 0, 5, 5);
		gbc_pnlBotones.gridx = 0;
		gbc_pnlBotones.gridy = 1;
		contentPane.add(pnlBotones, gbc_pnlBotones);
		pnlBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlBotones.setBackground(new Color(80, 100, 0));
				
		
		JButton btnNuevoAvion = new JButton("Nuevo avión");
		btnNuevoAvion.setBackground(new Color(0, 0, 0));
		btnNuevoAvion.setForeground(new Color(255, 255, 255));
		btnNuevoAvion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String strFabricante = "", strModelo = "", strMatricula = "";
				int iCapacidad, iPosX, iPosY, iNumPanelAvion;
				clAvion aviAvionActual;
				boolean bDatosCorrectos = true;
				boolean bSalir = false;
				
				if( EspacioAereo.espacioAereoLibre() ) {
					
					strFabricante = JOptionPane.showInputDialog(contentPane, "Fabricante:", "Nuevo avión", JOptionPane.QUESTION_MESSAGE);				
					if( strFabricante != null ) {
						
						strModelo = JOptionPane.showInputDialog(contentPane, "Modelo:", "Nuevo avión", JOptionPane.QUESTION_MESSAGE);
						
						if( strModelo != null ) {
													
							iCapacidad = 0;
							iPosX = -1; iPosY = -1;
							// La matrícula es obligatoria para dar de alta un nuevo avión
							try {
								while( strMatricula.equals("") )
									strMatricula = JOptionPane.showInputDialog(contentPane, "Matrícula:", "Nuevo avión", JOptionPane.QUESTION_MESSAGE);
							} catch( Exception e ) {  }  // La matrícula es nula
							
							if( strMatricula != null ) {
								
								try {
									iCapacidad = Integer.parseInt(JOptionPane.showInputDialog(contentPane, "Capacidad:", "Nuevo avión", JOptionPane.QUESTION_MESSAGE));
									while( (iPosX < 0) || (iPosX > ANCHO_ESPACIO_AEREO) ) {
										iPosX = Integer.parseInt(JOptionPane.showInputDialog(contentPane, "Posición eje X:", "Nuevo avión", JOptionPane.QUESTION_MESSAGE));
									}
									while( (iPosY < 0) || (iPosY > ALTO_ESPACIO_AEREO) ) {						
										iPosY = Integer.parseInt(JOptionPane.showInputDialog(contentPane, "Posición eje Y:", "Nuevo avión", JOptionPane.QUESTION_MESSAGE));
									}
								} catch( Exception e ) {
									bDatosCorrectos = false;
									lblMensajes.append("No se ha creado el avión. Datos no válidos.\n");
								}
							
				
								if( bDatosCorrectos ) {
									aviAvionActual = new clAvion(strMatricula, strModelo, strFabricante, iCapacidad, iPosX, iPosY);
									iNumPanelAvion = EspacioAereo.incorporarAvion(aviAvionActual);
									
									ContenedorPanelesInfoAviones.CrearPanel(iNumPanelAvion, strMatricula, strFabricante, strModelo, iCapacidad, iPosX, iPosY);
									ContenedorPanelesInfoAviones.SeleccionarPanel(iNumPanelAvion);
									
									EspacioAereo.setAvionActivo(iNumPanelAvion);
																			
									pnlEspacioAereo.pintar(EspacioAereo.getTodosAviones());		
									pnlEspacioAereo.repaint();
								}
								
							}
						
						}
						
					}
												
				}
					
				else {
						
					lblMensajes.append("Espacio aéreo lleno.\n");
						
				}
				
			}
		});
		pnlBotones.add(btnNuevoAvion);
		
		
		JButton btnCambiarAvion = new JButton("Cambiar avi\u00F3n");
		btnCambiarAvion.setBackground(new Color(0, 0, 0));
		btnCambiarAvion.setForeground(new Color(255, 255, 255));
		btnCambiarAvion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
											
					EspacioAereo.cambiarAvionActivo();
					ContenedorPanelesInfoAviones.SeleccionarPanel(EspacioAereo.getAvionActivo());

			}
		});
		pnlBotones.add(btnCambiarAvion);
		
		
		JButton btnEliminarAvion = new JButton("Eliminar avión");
		btnEliminarAvion.setBackground(new Color(0, 0, 0));
		btnEliminarAvion.setForeground(new Color(255, 255, 255));
		btnEliminarAvion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( EspacioAereo.getAvionActivo() >= 0 ) {
					EspacioAereo.eliminarAvion(EspacioAereo.getAvionActivo());
					ContenedorPanelesInfoAviones.ActualizarInfoAviones(EspacioAereo);
				}
			}
		});
		pnlBotones.add(btnEliminarAvion);
		
						
		JButton btnAcelerar = new JButton("Acelerar");
		btnAcelerar.setBackground(new Color(0, 0, 0));
		btnAcelerar.setForeground(new Color(255, 255, 255));
		btnAcelerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				clAvion aviAvionActual = new clAvion();
				aviAvionActual = EspacioAereo.getAvion(EspacioAereo.getAvionActivo());
				if( !aviAvionActual.motorEncendido() ) { 
					lblMensajes.append("El avión no tiene el motor encendido.\n");
				}
				else {
					aviAvionActual.acelerarAvion(50);
					EspacioAereo.setAvion(aviAvionActual, EspacioAereo.getAvionActivo());
					ContenedorPanelesInfoAviones.ModificarCuadroInfoAvion(ContenedorPanelesInfoAviones.LABEL_VELOCIDAD, 
							aviAvionActual, EspacioAereo.getAvionActivo(), EspacioAereo.getAvionActivo());
				}
				
			}
		});
		pnlBotones.add(btnAcelerar);
		
		
		JButton btnFrenar = new JButton("Frenar");
		btnFrenar.setBackground(new Color(0, 0, 0));
		btnFrenar.setForeground(new Color(255, 255, 255));
		btnFrenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				clAvion aviAvionActual = new clAvion();
				aviAvionActual = EspacioAereo.getAvion(EspacioAereo.getAvionActivo());
				if( aviAvionActual.getVelocidad() == 0  ) lblMensajes.append("El avión está parado.\n");
				else { 
					aviAvionActual.frenarAvion(50);
					EspacioAereo.setAvion(aviAvionActual, EspacioAereo.getAvionActivo());
					ContenedorPanelesInfoAviones.ModificarCuadroInfoAvion(ContenedorPanelesInfoAviones.LABEL_VELOCIDAD, 
							aviAvionActual, EspacioAereo.getAvionActivo(), EspacioAereo.getAvionActivo());
				}
				
			}
		});
		pnlBotones.add(btnFrenar);
		
		
		JButton btnAscender = new JButton("Ascender");
		btnAscender.setBackground(new Color(0, 0, 0));
		btnAscender.setForeground(new Color(255, 255, 255));
		btnAscender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int iElevar;
							
				clAvion aviAvionActual = new clAvion();
				aviAvionActual = EspacioAereo.getAvion(EspacioAereo.getAvionActivo());
				iElevar = aviAvionActual.elevarAvion(100);
				
				if( iElevar == aviAvionActual.ELEVACION_OK ) {
					EspacioAereo.setAvion(aviAvionActual, EspacioAereo.getAvionActivo());
					ContenedorPanelesInfoAviones.ModificarCuadroInfoAvion(ContenedorPanelesInfoAviones.LABEL_ALTURA_RUMBO, 
							aviAvionActual, EspacioAereo.getAvionActivo(), EspacioAereo.getAvionActivo());
				}				
				else if( iElevar == aviAvionActual.ELEVACION_ERROR_VELOCIDAD )
					lblMensajes.append("Velocidad insuficiente.\n");
				else if( iElevar == aviAvionActual.ELEVACION_ERROR_TRENAT )
					lblMensajes.append("Es necesario subir el tren de aterrizaje.\n");
				else if( iElevar == aviAvionActual.ELEVACION_ERROR_MOTOR )
					lblMensajes.append("El motor está apagado.\n");			
				
			}
		});
		pnlBotones.add(btnAscender);
		
		
		JButton btnDescender = new JButton("Descender");
		btnDescender.setBackground(new Color(0, 0, 0));
		btnDescender.setForeground(new Color(255, 255, 255));
		btnDescender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int iDescender;
							
				clAvion aviAvionActual = new clAvion();
				aviAvionActual = EspacioAereo.getAvion(EspacioAereo.getAvionActivo());
				iDescender = aviAvionActual.descenderAvion(100);
				
				if( iDescender == aviAvionActual.DESCENSO_OK ) {
					EspacioAereo.setAvion(aviAvionActual, EspacioAereo.getAvionActivo());
					ContenedorPanelesInfoAviones.ModificarCuadroInfoAvion(ContenedorPanelesInfoAviones.LABEL_ALTURA_RUMBO, 
						aviAvionActual, EspacioAereo.getAvionActivo(), EspacioAereo.getAvionActivo());
				}
				else if( iDescender == aviAvionActual.DESCENSO_ERROR_ALTITUD )
					lblMensajes.append("El avión ya se encuentra a altitud 0.\n");			
				else if( iDescender == aviAvionActual.DESCENSO_ERROR_MOTOR )
					lblMensajes.append("El motor está apagado.\n");							

			}
		});
		pnlBotones.add(btnDescender);
		
		
		JButton btnTrenAterrizaje = new JButton("Tren aterrizaje On/Off");
		btnTrenAterrizaje.setBackground(new Color(0, 0, 0));
		btnTrenAterrizaje.setForeground(new Color(255, 255, 255));
		btnTrenAterrizaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				clAvion aviAvionActual = new clAvion();
				aviAvionActual = EspacioAereo.getAvion(EspacioAereo.getAvionActivo());
				if( aviAvionActual.trenAterrizajeBajado() ) {
					if( !aviAvionActual.subirTrenAterrizaje() )
						lblMensajes.append("No es posible subir el tren de aterrizaje: compruebe altura y velocidad.\n");
				}
				else {
					aviAvionActual.bajarTrenAterrizaje();
					lblMensajes.append("No es posible bajar el tren de aterrizaje: compruebe altura y velocidad\n");
				}
				EspacioAereo.setAvion(aviAvionActual, EspacioAereo.getAvionActivo());
				
				ContenedorPanelesInfoAviones.ModificarCuadroInfoAvion(ContenedorPanelesInfoAviones.LABEL_MOTOR_TRENATERRIZAJE, 
						aviAvionActual, EspacioAereo.getAvionActivo(), EspacioAereo.getAvionActivo());
				
			}
		});
		pnlBotones.add(btnTrenAterrizaje);
				
		
		JButton btnMotor = new JButton("Motor On/Off");
		btnMotor.setBackground(new Color(0, 0, 0));
		btnMotor.setForeground(new Color(255, 255, 255));
		btnMotor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				clAvion aviAvionActual = new clAvion();
				aviAvionActual = EspacioAereo.getAvion(EspacioAereo.getAvionActivo());
				if( !aviAvionActual.motorEncendido() ) aviAvionActual.encenderMotor();
				else aviAvionActual.apagarMotor();
				EspacioAereo.setAvion(aviAvionActual, EspacioAereo.getAvionActivo());
				
				ContenedorPanelesInfoAviones.ModificarCuadroInfoAvion(ContenedorPanelesInfoAviones.LABEL_MOTOR_TRENATERRIZAJE, 
						aviAvionActual, EspacioAereo.getAvionActivo(), EspacioAereo.getAvionActivo());
				
			}
		});
		pnlBotones.add(btnMotor);

		
		JButton btnCambiarRumbo = new JButton("Rumbo");
		btnCambiarRumbo.setBackground(new Color(0, 0, 0));
		btnCambiarRumbo.setForeground(new Color(255, 255, 255));
		btnCambiarRumbo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String strRumbo = "";
				int iRumbo = -1;
				
				clAvion aviAvionActual = new clAvion();
				aviAvionActual = EspacioAereo.getAvion(EspacioAereo.getAvionActivo());

				// Rumbo ha de ser entre 0º y 360º
				while( ((iRumbo < 0) || (iRumbo > 360)) && (strRumbo != null) ) {
					
					strRumbo = JOptionPane.showInputDialog(contentPane, "Rumbo: ", 
							"Cambiar rumbo", JOptionPane.QUESTION_MESSAGE);
					
					if( strRumbo != null ) {
						try {
							iRumbo = Integer.parseInt(strRumbo);
						} catch ( Exception e ) {
							iRumbo = -1; }
					
						if( (iRumbo < 0) || (iRumbo > 360) )
							lblMensajes.append("Valor no válido, ha de ser entre 0 y 360 grados.\n");
					}
					
				}
			
				if( strRumbo != null ) {
					if( iRumbo != JOptionPane.CANCEL_OPTION ) {
						aviAvionActual.establecerRumbo(iRumbo);
						EspacioAereo.setAvion(aviAvionActual, EspacioAereo.getAvionActivo());
				
						ContenedorPanelesInfoAviones.ModificarCuadroInfoAvion(ContenedorPanelesInfoAviones.LABEL_ALTURA_RUMBO, 
								aviAvionActual, EspacioAereo.getAvionActivo(), EspacioAereo.getAvionActivo());
					}
				}

			}
		});
		pnlBotones.add(btnCambiarRumbo);
		

		btnIniciarSimulador = new JButton("Simulador On");
		btnIniciarSimulador.setBackground(new Color(0, 0, 0));
		btnIniciarSimulador.setForeground(new Color(255, 255, 255));
		btnIniciarSimulador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if( bSimuladorDetenido ) {
					btnIniciarSimulador.setText("Simulador Off");
					lblMensajes.append("Simulador activado.\n");
					arRun = new clAvionesRun(ContenedorPanelesInfoAviones);
					arRun.CargarEspacioAereo(EspacioAereo, pnlEspacioAereo);
					arRun.start();
					bSimuladorDetenido = false;
				}
				else {
					btnIniciarSimulador.setText("Simulador On");
					lblMensajes.append("Simulador detenido.\n");
					arRun.detener();
					bSimuladorDetenido = true;
				}
				
			} 
		});
		pnlBotones.add(btnIniciarSimulador);
		
		
		// Panel de visualización de los mensajes que emite el programa para comunicarse con el usuario,
		// se encuentra en la parte inferior central de la ventana
		pnlMensajes = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlMensajes.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnlMensajes.setPreferredSize(new Dimension(494, 150));
		pnlMensajes.setMaximumSize(new Dimension(494, 150));
		pnlMensajes.setMinimumSize(new Dimension(494, 150));
		pnlMensajes.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		GridBagConstraints gbc_pnlMensajes = new GridBagConstraints();
		gbc_pnlMensajes.anchor = GridBagConstraints.NORTH;
		gbc_pnlMensajes.insets = new Insets(0, 0, 5, 5);
		gbc_pnlMensajes.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlMensajes.gridx = 1;
		gbc_pnlMensajes.gridy = 1;
		pnlMensajes.setBackground(new Color(80, 100, 0));
		contentPane.add(pnlMensajes, gbc_pnlMensajes);
		
		lblMensajes = new JTextArea(PANEL_MENSAJES_LINEAS, PANEL_MENSAJES_ANCHO);
		lblMensajes.setEditable(false);
		lblMensajes.setBackground(new Color(0, 0, 0));
		lblMensajes.setForeground(new Color(255, 255, 255));
		
		JScrollPane scrScroll = new JScrollPane(lblMensajes);
		scrScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
		pnlMensajes.add(scrScroll);
			
		setLocation(100 ,10);
	}
	
	
}
