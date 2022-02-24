package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clasesZoo.Empleado;
import clasesZoo.Tratamiento;
import clasesZoo.Zona;
import persistencia.Persistencia;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormCapEmpleado extends JDialog {
	private JTextField textFieldDireccion;
	private JTextField textFieldNombre;
	private JTable table;
	private Persistencia per;
	private Tratamiento t;
	private DefaultTableModel dtm;

	public FormCapEmpleado(Persistencia per, Tratamiento t) {
		
		this.per=per;
		this.t=t;
		
		setTitle("A\u00D1ADIR EMPLEADO");
		setBounds(100, 100, 560, 312);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Nombre");
		label.setBounds(20, 0, 63, 34);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Direcci\u00F3n");
		label_1.setBounds(20, 61, 80, 14);
		getContentPane().add(label_1);
		
		textFieldDireccion = new JTextField();
		textFieldDireccion.setColumns(10);
		textFieldDireccion.setBounds(113, 58, 267, 20);
		getContentPane().add(textFieldDireccion);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(113, 7, 267, 20);
		getContentPane().add(textFieldNombre);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 86, 506, 172);
		getContentPane().add(scrollPane);
		
		
		JButton btnAadir = new JButton("A\u00F1adir");
		btnAadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					anniadir();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAadir.setBounds(418, 6, 108, 34);
		getContentPane().add(btnAadir);
		
		dtm = new DefaultTableModel();
		
		dtm.addColumn("ID");
		dtm.addColumn("NOMBRE");
		dtm.addColumn("DIRECCIÓN");
		dtm.addColumn("FECHA NAC");

		table = new JTable(dtm);
		scrollPane.setViewportView(table);

		ListSelectionModel listSelectionModel = table.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
					if (table.getSelectedRow()>=0) {
						btnAadir.setEnabled(true);
					}else {
						btnAadir.setEnabled(false);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		// EVENTO PARA DETECTAR CAMBIOS EN EL TEXTFIELD
		textFieldNombre.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					rellenarTabla();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTabla();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTabla();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		textFieldDireccion.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					rellenarTabla();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTabla();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTabla();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		try {
			rellenarTabla();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	private void anniadir() throws Exception {
		if (table.getSelectedRow()>=0) {
			
			Empleado e=per.consultarEmpleadoID((Integer)dtm.getValueAt(table.getSelectedRow(), 0));
			this.t.getEmpleados().add(e);
			per.guardarSinCommit(this.t);
			JOptionPane.showMessageDialog(this, "Empleado añadido correctamente","AVISO",JOptionPane.INFORMATION_MESSAGE);
//			per.refresh(e, "");
//			per.refresh(this.zona, "");
		}
		
	}


	public void rellenarTabla() throws Exception {
		String nombre =textFieldNombre.getText().trim().toUpperCase();
		String direccion=textFieldDireccion.getText().trim();
		List<Empleado> empleados=per.consultarEmpleados(nombre, direccion);
		
		dtm.setRowCount(0);
		Object[] datos = new Object[4];
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (int i = 0; i < empleados.size(); i++) {
			datos[0] = empleados.get(i).getId();
			datos[1] = empleados.get(i).getNombre();
			datos[2] = empleados.get(i).getDireccion();
			datos[3] = sdf.format(empleados.get(i).getFechaNac());
			dtm.addRow(datos);
		}

	}
	
	
}
