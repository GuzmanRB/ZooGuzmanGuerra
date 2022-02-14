package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clasesZoo.Animal;
import persistencia.PersistenciaHibernate;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaAnimales extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIDAnimal;
	private JTextField textFieldNombre;
	private JTextField textFieldDescripcion;
	private JTextField textFieldFecha;
	private PersistenciaHibernate per = new PersistenciaHibernate();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAnimales frame = new VentanaAnimales();
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
	public VentanaAnimales() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 519, 342);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIdAnimal = new JLabel("Id animal");
		lblIdAnimal.setBounds(10, 11, 57, 27);
		contentPane.add(lblIdAnimal);
		
		JLabel lblEspecie = new JLabel("Especie");
		lblEspecie.setBounds(10, 138, 102, 14);
		contentPane.add(lblEspecie);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 74, 46, 14);
		contentPane.add(lblNombre);
		
		JLabel lblFechaNac = new JLabel("Fecha Nac.");
		lblFechaNac.setBounds(10, 192, 102, 14);
		contentPane.add(lblFechaNac);
		
		textFieldIDAnimal = new JTextField();
		textFieldIDAnimal.setEditable(false);
		textFieldIDAnimal.setBounds(109, 14, 86, 20);
		contentPane.add(textFieldIDAnimal);
		textFieldIDAnimal.setColumns(10);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setText("");
		textFieldNombre.setBounds(109, 71, 154, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setBounds(109, 135, 154, 20);
		contentPane.add(textFieldDescripcion);
		textFieldDescripcion.setColumns(10);
		
		textFieldFecha = new JTextField();
		textFieldFecha.setEditable(false);
		textFieldFecha.setBounds(109, 189, 86, 20);
		contentPane.add(textFieldFecha);
		textFieldFecha.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		btnBuscar.setBounds(379, 13, 89, 23);
		contentPane.add(btnBuscar);
		
		JButton btnCancelarInicio = new JButton("Cancelar");
		btnCancelarInicio.setBounds(379, 188, 89, 23);
		contentPane.add(btnCancelarInicio);
		
		JLabel lblZona = new JLabel("Zona");
		lblZona.setBounds(10, 249, 46, 14);
		contentPane.add(lblZona);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(109, 246, 154, 20);
		contentPane.add(comboBox);
	}

	public List<Animal> buscar() {
		return null;
		
		
	}
}