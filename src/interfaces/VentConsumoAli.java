package interfaces;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import clasesZoo.Animal;
import persistencia.Persistencia;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class VentConsumoAli extends JDialog {
	private JTable table;
	private JTable table_1;
	private JTextField textFieldnOMBRE;
	private JTextField textFieldEspecie;
	private JTextField textFieldDesc;
	private JTable table_2;
	private JTextField textFieldCant;
	private DefaultTableModel dtm;
	private DefaultTableModel dtm1;
	private DefaultTableModel dtm2;
	private Persistencia per;
	private JButton btnAadir;
	private JButton btnGuardar;
	private JButton btnNewButton;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	
	
	public VentConsumoAli(Persistencia per) {
		setResizable(false);
		
		
		this.per=per;
		
		setTitle("CONSUMO ALIMENTOS");
		setBounds(100, 100, 950, 577);
		getContentPane().setLayout(null);
		
		//CREACION DE TABLA ANIMALES
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 104, 418, 151);
		getContentPane().add(scrollPane);
		
		dtm= new DefaultTableModel();
		
		dtm.addColumn("ID");
		dtm.addColumn("NOMBRE");
		dtm.addColumn("ESPECIE");
		dtm.addColumn("FECHA NAC");
		dtm.addColumn("ZONA");
		
		table = new JTable(dtm);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		
		
		//CREACION DE TABLA ALIMENTOS
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(471, 104, 418, 151);
		getContentPane().add(scrollPane_1);
		
		dtm1= new DefaultTableModel();
		
		dtm1.addColumn("ID");
		dtm1.addColumn("DESCRIPCIÓN");
		dtm1.addColumn("COSTE");
		
		table_1 = new JTable(dtm1);
		table_1.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(table_1);
		
		
	
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 371, 533, 140);
		getContentPane().add(scrollPane_2);
		
		dtm2= new DefaultTableModel();
		
		dtm2.addColumn("ID");
		dtm2.addColumn("ANIMAL");
		dtm2.addColumn("ALIMENTO");
		dtm2.addColumn("CANTIDAD");
		
		table_2 = new JTable(dtm2);
		table_2.getTableHeader().setReorderingAllowed(false);
		scrollPane_2.setViewportView(table_2);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(10, 37, 92, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblEspecie = new JLabel("ESPECIE");
		lblEspecie.setBounds(10, 76, 92, 14);
		getContentPane().add(lblEspecie);
		
		textFieldnOMBRE = new JTextField();
		textFieldnOMBRE.setBounds(214, 35, 214, 17);
		getContentPane().add(textFieldnOMBRE);
		textFieldnOMBRE.setColumns(10);
		
		textFieldEspecie = new JTextField();
		textFieldEspecie.setText("");
		textFieldEspecie.setBounds(214, 73, 214, 20);
		getContentPane().add(textFieldEspecie);
		textFieldEspecie.setColumns(10);
		
		JLabel lblAnimal = new JLabel("ANIMAL");
		lblAnimal.setBounds(10, 12, 46, 14);
		getContentPane().add(lblAnimal);
		
		JLabel lblAlimento = new JLabel("ALIMENTO");
		lblAlimento.setBounds(471, 12, 126, 14);
		getContentPane().add(lblAlimento);
		
		textFieldDesc = new JTextField();
		textFieldDesc.setColumns(10);
		textFieldDesc.setBounds(620, 35, 269, 17);
		getContentPane().add(textFieldDesc);
		
		JLabel lblDescripcion = new JLabel("DESCRIPCI\u00D3N");
		lblDescripcion.setBounds(471, 37, 92, 14);
		getContentPane().add(lblDescripcion);
		
		btnNewButton = new JButton("SELECCIONAR");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(10, 275, 126, 34);
		getContentPane().add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 320, 934, 4);
		getContentPane().add(separator);
		
		JLabel lblConsumos = new JLabel("CONSUMOS");
		lblConsumos.setBounds(10, 335, 101, 14);
		getContentPane().add(lblConsumos);
		
		
		textFieldCant = new JTextField();
		textFieldCant.setEnabled(false);
		textFieldCant.setBounds(767, 369, 52, 20);
		getContentPane().add(textFieldCant);
		textFieldCant.setColumns(10);
		
		JLabel lblCant = new JLabel("Cant");
		lblCant.setBounds(711, 372, 46, 14);
		getContentPane().add(lblCant);
		
		btnAadir = new JButton("A\u00F1adir");
		btnAadir.setEnabled(false);
		btnAadir.setBounds(829, 368, 89, 23);
		getContentPane().add(btnAadir);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setEnabled(false);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGuardar.setBounds(793, 471, 125, 40);
		getContentPane().add(btnGuardar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(620, 471, 125, 40);
		getContentPane().add(btnEliminar);
		
		textFieldnOMBRE.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTablaAnimales();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTablaAnimales();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTablaAnimales();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		textFieldEspecie.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTablaAnimales();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTablaAnimales();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTablaAnimales();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		try {
			rellenarTablaAnimales();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public void rellenarTablaAnimales() throws Exception {
		String nombre= textFieldnOMBRE.getText().trim().toUpperCase();
		String especie= textFieldEspecie.getText().trim().toUpperCase();
		List<Animal> animales= per.consultarAnimales(nombre, especie);
		
		dtm.setRowCount(0);
		Object[] datos= new Object[5];
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		for (int i = 0; i < animales.size(); i++) {
				datos[0]= animales.get(i).getId();
				datos[1]= animales.get(i).getNombre();
				datos[2]= animales.get(i).getEspecie().getDescripcion();
				if (animales.get(i).getFechaNac()!=null) {
					datos[3]= sdf.format(animales.get(i).getFechaNac());
				}else {
					datos[3]= animales.get(i).getFechaNac();
				}
				datos[4]= animales.get(i).getZona().getDescripcion();
				dtm.addRow(datos);
		}
		
	}
}
