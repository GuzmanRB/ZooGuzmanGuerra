package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import persistencia.PersistenciaHibernate;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private JPanel contentPane;
	private PersistenciaHibernate per= new PersistenciaHibernate();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 595, 439);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mZoologico = new JMenu("Zoologico");
		menuBar.add(mZoologico);
		
		JMenuItem mIAnimales = new JMenuItem("Animales");
		mIAnimales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentAnimales va= new VentAnimales(true, per);
				va.setVisible(true);
			}
		});
		mZoologico.add(mIAnimales);
		
		JMenuItem mIZonas = new JMenuItem("Zonas");
		mIZonas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentZonas vz= new VentZonas(true, per);
				vz.setVisible(true);
			}
		});
		mZoologico.add(mIZonas);
		
		JMenuItem mIEspecies = new JMenuItem("Especies");
		mIEspecies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentEspecie ve=new VentEspecie(true, per);
				ve.setVisible(true);
				
			}
		});
		mZoologico.add(mIEspecies);
		
		JMenu mTrabajadores = new JMenu("Trabajadores");
		menuBar.add(mTrabajadores);
		
		JMenuItem mIEmpleados = new JMenuItem("Empleados");
		mTrabajadores.add(mIEmpleados);
		
		JMenuItem mINominas = new JMenuItem("Nominas");
		mTrabajadores.add(mINominas);
		
		JMenu mAlmacen = new JMenu("Almacen");
		menuBar.add(mAlmacen);
		
		JMenuItem mIAlimentos = new JMenuItem("Alimentos");
		mAlmacen.add(mIAlimentos);
		
		JMenu mVentas = new JMenu("Ventas");
		menuBar.add(mVentas);
		
		JMenuItem mIEntradas = new JMenuItem("Entredas");
		mVentas.add(mIEntradas);
		
		JMenuItem mIEventos = new JMenuItem("Eventos");
		mVentas.add(mIEventos);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
