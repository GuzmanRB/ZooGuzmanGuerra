package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import clasesZoo.Animal;
import clasesZoo.Especie;
import persistencia.PersistenciaHibernate;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaAnimales extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIDAnimal;
	private JTextField textFieldNombre;
	private JTextField textFieldFecha;
	private PersistenciaHibernate per = new PersistenciaHibernate();
	private JComboBox cbZona;
	private JComboBox cbEspecie;
	private JButton btnCancelarInicio;
	private JLabel lblZona;
	private JLabel lblFechaNac;
	private JLabel lblNombre;
	private JLabel lblEspecie;
	private JLabel lblIdAnimal;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel dtm;

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
		setBounds(100, 100, 519, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblIdAnimal = new JLabel("Id animal");
		lblIdAnimal.setBounds(10, 11, 57, 27);
		contentPane.add(lblIdAnimal);

		lblEspecie = new JLabel("Especie");
		lblEspecie.setBounds(10, 138, 102, 14);
		contentPane.add(lblEspecie);

		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 74, 46, 14);
		contentPane.add(lblNombre);

		lblFechaNac = new JLabel("Fecha Nac.");
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

		btnCancelarInicio = new JButton("Cancelar");
		btnCancelarInicio.setBounds(379, 245, 89, 23);
		contentPane.add(btnCancelarInicio);

		lblZona = new JLabel("Zona");
		lblZona.setBounds(10, 249, 46, 14);
		contentPane.add(lblZona);

		cbZona = new JComboBox();
		cbZona.setBounds(109, 246, 154, 20);
		contentPane.add(cbZona);

		cbEspecie = new JComboBox();
		cbEspecie.setBounds(109, 135, 154, 20);
		contentPane.add(cbEspecie);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 290, 462, 149);
		contentPane.add(scrollPane);
		
		dtm= new DefaultTableModel();
		
		dtm.addColumn("ID");
		dtm.addColumn("NOMBRE");
		dtm.addColumn("ESPECIE");
		dtm.addColumn("FECHA NAC");
		dtm.addColumn("ZONA");
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		rellenarEspecies();
	}

	private void rellenarEspecies() {
		// TODO Auto-generated method stub
		
		List<Especie> le= per.consultarEspecie();
		
		for (int i = 0; i < le.size(); i++) {
			cbEspecie.addItem(le.get(i).getDescripcion());
			
		}
		cbEspecie.setSelectedIndex(-1);
		
	}

	public List<Animal> buscar() {
		List<Animal> animales;
		
		String desc=(String)cbEspecie.getSelectedItem();
		Especie e= per.consultarEspecieUnica(desc);
		
		animales = per.consultarAnimal(textFieldNombre.getText().trim(),e.getId() );
		rellenarTabla(animales);
		return animales;
	}

	public void rellenarTabla(List<Animal> animales) {
		
		Object[] datos= new Object[5];
		
		for (int i = 0; i < animales.size(); i++) {
				datos[0]= animales.get(i).getId();
				datos[1]= animales.get(i).getNombre();
				datos[2]= animales.get(i).getEspecie();
				datos[3]= animales.get(i).getFechaNac();
				datos[4]= animales.get(i).getZona();
				dtm.addRow(datos);
		}
		
	}
}
