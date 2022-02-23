package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import persistencia.Persistencia;
import persistencia.PersistenciaHibernate;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private JPanel contentPane;
	private Persistencia per= new PersistenciaHibernate();

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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 595, 439);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mZoologico = new JMenu("Zool\u00F3gico");
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
		
		JMenu mPersonal = new JMenu("Personal");
		menuBar.add(mPersonal);
		
		JMenuItem mIEmpleados = new JMenuItem("Empleados");
		mIEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentEmpleados ve= new VentEmpleados(per);
				ve.setVisible(true);
			}
		});
		mPersonal.add(mIEmpleados);
		
		JMenuItem mINominas = new JMenuItem("Nominas");
		mINominas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentNominas vn= new VentNominas(per);
				vn.setVisible(true);
			}
		});
		mPersonal.add(mINominas);
		
		JMenu mCuidados = new JMenu("Cuidados");
		menuBar.add(mCuidados);
		
		JMenuItem mIAlimentos = new JMenuItem("Alimentos");
		mIAlimentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCuidados va= new VentCuidados("Alimento", per);
				va.setVisible(true);
			}
		});
		mCuidados.add(mIAlimentos);
		
		JMenuItem mITratamientos = new JMenuItem("Tratamientos");
		mITratamientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCuidados vc= new VentCuidados("Tratamiento", per);
				vc.setVisible(true);
			}
		});
		mCuidados.add(mITratamientos);
		
		JMenu mVentas = new JMenu("Ventas");
		menuBar.add(mVentas);
		
		JMenuItem mIEntradas = new JMenuItem("Entradas");
		mIEntradas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentEntradas ve= new VentEntradas(per);
				ve.setVisible(true);
			}
		});
		mVentas.add(mIEntradas);
		
		JMenuItem mIEventos = new JMenuItem("Eventos");
		mIEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentEventos ve= new VentEventos(per);
				ve.setVisible(true);
			}
		});
		mVentas.add(mIEventos);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
