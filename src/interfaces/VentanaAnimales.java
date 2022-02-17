package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clasesZoo.Animal;
import clasesZoo.Especie;
import clasesZoo.Zona;
import persistencia.PersistenciaHibernate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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
		btnBuscar.setBounds(379, 13, 89, 27);
		contentPane.add(btnBuscar);

		btnCancelarInicio = new JButton("Cancelar");
		btnCancelarInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerTodo();
			}
		});
		btnCancelarInicio.setBounds(379, 245, 89, 23);
		contentPane.add(btnCancelarInicio);

		lblZona = new JLabel("Zona");
		lblZona.setBounds(10, 249, 46, 14);
		contentPane.add(lblZona);

		cbZona = new JComboBox();
		cbZona.setEnabled(false);
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
		
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarAnimal();
			}
		});
		btnNuevo.setBounds(379, 70, 89, 23);
		contentPane.add(btnNuevo);
		
		ListSelectionModel listSelectionModel= table.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new ListSelectionListener() {
	        public void valueChanged(ListSelectionEvent e) {
	            try {
	            	rellenarDatos();
	            } catch (Exception e1) {
	                // TODO Auto-generated catch block
	                e1.printStackTrace();
	            }
	        }
	    });
	    
		rellenarEspecies();
		rellenarZonas();
	}

	protected void guardarAnimal() {
		// TODO Auto-generated method stub
		Animal a;
		String nombre="", especie="";
		
		
		
		
		if (!textFieldNombre.getText().trim().equalsIgnoreCase("")) {
			nombre=textFieldNombre.getText().trim();
		}
		if (!cbEspecie.getSelectedItem().toString().trim().equalsIgnoreCase("")) {
			especie=cbEspecie.getSelectedItem().toString();
		}
		
		Especie e= per.consultarEspecieUnica(especie);
		
		if (per.consultarAnimal(nombre, e.getId())==null) {
			
			
			
		}else {
			
			JOptionPane.showMessageDialog(this, "Ya existe un animal con este nombre y especie", "Animal ya existente", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}

	protected void restablecerTodo() {
		// TODO Auto-generated method stub
		dtm.setRowCount(0);
		textFieldIDAnimal.setText("");
		textFieldNombre.setText("");
		textFieldFecha.setText("");
		cbEspecie.setSelectedIndex(-1);
		cbZona.setSelectedIndex(-1);
		
	}

	private void rellenarEspecies() {
		// TODO Auto-generated method stub
		
		List<Especie> le= per.consultarEspecie();
		
		for (int i = 0; i < le.size(); i++) {
			cbEspecie.addItem(le.get(i).getDescripcion());
			
		}
		cbEspecie.setSelectedIndex(-1);
		
	}
	private void rellenarZonas() {
		List<Zona> lz =per.consultarZonas();
		
		for (int i = 0; i < lz.size(); i++) {
			cbZona.addItem(lz.get(i).getDescripcion());
		}
		cbZona.setSelectedIndex(-1);
		
	}

	public List<Animal> buscar() {
		List<Animal> animales;
		
		String desc=(String)cbEspecie.getSelectedItem();
		if (desc!=null) {
			Especie e= per.consultarEspecieUnica(desc);
			animales = per.consultarAnimal(textFieldNombre.getText().trim(),e.getId() );
		}else {
			animales = per.consultarAnimal(textFieldNombre.getText().trim(),null );
		}
		
		rellenarTabla(animales);
		return animales;
	}

	public void rellenarTabla(List<Animal> animales) {
		
		dtm.setRowCount(0);
		Object[] datos= new Object[5];
		
		for (int i = 0; i < animales.size(); i++) {
				datos[0]= animales.get(i).getId();
				datos[1]= animales.get(i).getNombre();
				datos[2]= animales.get(i).getEspecie().getId();
				datos[3]= animales.get(i).getFechaNac();
				datos[4]= animales.get(i).getZona().getId();
				dtm.addRow(datos);
		}
		
	}
	
	public void rellenarDatos() {
		DefaultTableModel dtm= (DefaultTableModel) table.getModel();
		if (table.getSelectedRow()!=-1) {
			String Sid =String.valueOf(dtm.getValueAt(table.getSelectedRow(), 0));
			Integer id= Integer.parseInt(Sid);
			
			Animal a=per.consultarAnimalID(id);
			textFieldIDAnimal.setText(String.valueOf(a.getId()));
			textFieldNombre.setText(a.getNombre());
			textFieldFecha.setText(String.valueOf(a.getFechaNac()));
			cbEspecie.setSelectedIndex(a.getEspecie().getId()-1);
			cbZona.setSelectedIndex(a.getZona().getId()-1);
		}
		
		
	}
}
