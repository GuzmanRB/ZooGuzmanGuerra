package interfaces;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clasesZoo.Empleado;
import clasesZoo.Nomina;
import clasesZoo.Zona;
import persistencia.Persistencia;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JSeparator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentTrabajadoresZona extends JDialog {
	private JTextField textFieldZona;
	private DefaultTableModel dtm, dtm1;
	private JTable table;
	private Persistencia per;
	private JTable table_1;
	private JScrollPane scrollPane_1;
	private JButton btnAnniadir;
	private JButton btnBorrar;
	private JButton btnInicio;
	private JButton btnSeleccionar;
	private JScrollPane scrollPane;
	private JButton btnGuardarTodo;
	private JTextField textFieldEmpleado;
	private JButton btnActualizar;
	

	public VentTrabajadoresZona(Persistencia per) {
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				try {
					per.transaccionRollback();	
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setTitle("TRABAJADORES POR ZONA");
		setResizable(false);
		this.per = per;
		setBounds(100, 100, 463, 598);
		getContentPane().setLayout(null);

		JLabel lblZona = new JLabel("ZONA");
		lblZona.setBounds(20, 11, 63, 34);
		getContentPane().add(lblZona);

		textFieldZona = new JTextField();
		textFieldZona.setColumns(10);
		textFieldZona.setBounds(109, 18, 224, 20);
		getContentPane().add(textFieldZona);

		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					seleccionarTratamiento();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSeleccionar.setEnabled(false);
		btnSeleccionar.setBounds(343, 11, 108, 34);
		getContentPane().add(btnSeleccionar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 56, 431, 172);
		getContentPane().add(scrollPane);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 239, 451, 10);
		getContentPane().add(separator);

		JLabel lblTrabajadores = new JLabel("EMPLEADOS");
		lblTrabajadores.setBounds(20, 260, 98, 14);
		getContentPane().add(lblTrabajadores);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 296, 431, 172);
		getContentPane().add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);

		btnAnniadir = new JButton("A\u00F1adir");
		btnAnniadir.setEnabled(false);
		btnAnniadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					anniadir();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnAnniadir.setBounds(20, 524, 108, 34);
		getContentPane().add(btnAnniadir);



		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					eliminar();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBorrar.setEnabled(false);
		btnBorrar.setBounds(225, 524, 108, 34);
		getContentPane().add(btnBorrar);

		btnInicio = new JButton("Inicio");
		btnInicio.setEnabled(false);
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					restablecerTodo();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnInicio.setBounds(343, 524, 108, 34);
		getContentPane().add(btnInicio);

		btnGuardarTodo = new JButton("Guardar");
		btnGuardarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardarTodo();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGuardarTodo.setEnabled(false);
		btnGuardarTodo.setBounds(225, 479, 222, 34);
		getContentPane().add(btnGuardarTodo);

		// CREANDO TABLA EMPLEADOS
		dtm = new DefaultTableModel();

		dtm.addColumn("ID");
		dtm.addColumn("DESCRIPCIÓN");

		table = new JTable(dtm);
//		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		
		ListSelectionModel listSelectionModel = table.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRow() >=0) {
					btnSeleccionar.setEnabled(true);
				}else {
					btnSeleccionar.setEnabled(false);
				}
			}
		});
		// CREANDO TABLA NOMINAS
		dtm1 = new DefaultTableModel();

		dtm1.addColumn("ID");
		dtm1.addColumn("NOMBRE");
		dtm1.addColumn("DIRECCIÓN");
		dtm1.addColumn("FECHA NAC");

		table_1 = new JTable(dtm1);
		table_1.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(table_1);
		
		textFieldEmpleado = new JTextField();
		textFieldEmpleado.setColumns(10);
		textFieldEmpleado.setBounds(109, 257, 224, 20);
		getContentPane().add(textFieldEmpleado);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rellenarTablaEmpleado();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnActualizar.setEnabled(false);
		btnActualizar.setBounds(343, 256, 108, 34);
		getContentPane().add(btnActualizar);

		ListSelectionModel listSelectionModel1 = table_1.getSelectionModel();
		listSelectionModel1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {

					if (table_1.getSelectedRow() >= 0) {
						btnBorrar.setEnabled(true);
					} else {
						
						btnBorrar.setEnabled(false);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// EVENTO PARA DETECTAR CAMBIOS EN EL TEXTFIELD
		textFieldZona.getDocument().addDocumentListener(new DocumentListener() {

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
		
		textFieldEmpleado.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					rellenarTablaEmpleado();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTablaEmpleado();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					rellenarTablaEmpleado();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	private void anniadir() throws Exception {
		// TODO Auto-generated method stub
		String desc= String.valueOf(dtm.getValueAt(table.getSelectedRow(), 1)).trim();
		Zona zona= (Zona) per.consultarUnico("Zona", desc);
		FormZonaEmpleado fze= new FormZonaEmpleado(per, zona);
		fze.setVisible(true);
		btnGuardarTodo.setEnabled(true);
		
	}

	private void eliminar() throws Exception {
		Empleado e = per.consultarEmpleadoID(((Integer)dtm1.getValueAt(table_1.getSelectedRow(), 0)));
		String desc= String.valueOf(dtm.getValueAt(table.getSelectedRow(), 1)).trim();
		Zona zona= (Zona) per.consultarUnico("Zona", desc);
		if (JOptionPane.showConfirmDialog(this, "Seguro que desea elminar el registro", "AVISO", JOptionPane.WARNING_MESSAGE)==0) {
			
			zona.getEmpleados().remove(e);
			per.guardarSinCommit(zona);
			per.refrescar(zona);
			JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente","AVISO",JOptionPane.INFORMATION_MESSAGE);
			btnGuardarTodo.setEnabled(true);
			rellenarTablaEmpleado();
		}

		
	}



	private void guardarTodo() throws Exception {
		
		per.transaccionCommit();
		JOptionPane.showMessageDialog(this, "Cambios guardados", "AVISO", JOptionPane.INFORMATION_MESSAGE);
		restablecerTodo();

	}




	private void restablecerTodo() throws Exception {
		// TODO Auto-generated method stub
		rellenarTabla();
		dtm1.setRowCount(0);
		habilitarEmpleados(false);
		habilitarBotones(false);
		inhabilitarEmpleado(true);
		btnSeleccionar.setEnabled(false);
		textFieldZona.setText("");
		textFieldZona.grabFocus();

	}


	private void habilitarBotones(boolean b) {

		btnAnniadir.setEnabled(b);
		btnInicio.setEnabled(b);
		btnGuardarTodo.setEnabled(b);
		btnActualizar.setEnabled(b);
		table_1.setEnabled(b);

	}



	private void seleccionarTratamiento() throws Exception {
		if (table.getSelectedRow() >= 0) {
			inhabilitarEmpleado(false);
			habilitarEmpleados(true);
			rellenarTablaEmpleado();
		} else {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado", "AVISO",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void rellenarTablaEmpleado() throws Exception {
		Zona z = (Zona) per
				.consultarUnico("Zona",(String.valueOf(dtm.getValueAt(table.getSelectedRow(), 1))));
		Set<Empleado> e = z.getEmpleados();

		dtm1.setRowCount(0);
		Object[] datos = new Object[5];
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Iterator<Empleado> it=e.iterator();
		
		while (it.hasNext()) {
			Empleado empleado = (Empleado) it.next();
			per.refrescar(empleado);
			datos[0] = empleado.getId();
			datos[1] = empleado.getNombre();
			datos[2] = empleado.getDireccion();
			datos[3] = sdf.format(empleado.getFechaNac());
			dtm1.addRow(datos);		
		}
		

	}



	private void habilitarEmpleados(boolean flag) {
		table_1.setEnabled(flag);
		btnInicio.setEnabled(flag);
		btnAnniadir.setEnabled(flag);
		btnActualizar.setEnabled(flag);

	}

	private void inhabilitarEmpleado(boolean flag) {
		// TODO Auto-generated method stub
		btnSeleccionar.setEnabled(flag);
		table.setEnabled(flag);
		textFieldZona.setEnabled(flag);

	}

	public void rellenarTabla() throws Exception {
		String desc = textFieldZona.getText().trim();
		List<Zona> zona =(List)per.consultarPorDesc("Zona", desc);

		dtm.setRowCount(0);
		Object[] datos = new Object[2];

		for (int i = 0; i < zona.size(); i++) {
			per.refrescar(zona.get(i));
			datos[0] = zona.get(i).getId();
			datos[1] = zona.get(i).getDescripcion();
			dtm.addRow(datos);
		}

	}
}
