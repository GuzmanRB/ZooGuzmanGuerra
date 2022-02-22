package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clasesZoo.Animal;
import clasesZoo.Empleado;
import clasesZoo.Especie;
import clasesZoo.Zona;
import persistencia.Persistencia;
import persistencia.PersistenciaHibernate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentEmpleados extends JDialog {
	private JTextField textFieldNombre;
	private JTextField textFieldDirec;
	private JTextField textFieldFecha;
	private JTable table;
	private DefaultTableModel dtm;
	private Persistencia per;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JButton btnNuevo;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private JLabel lblFechaNac;
	private JLabel lblFormato;
	private JLabel lblDireccin;
	private JLabel label;
	private JButton btnBorrar;

	public VentEmpleados(Persistencia per) {

		this.per = per;

		setModal(true);
		setResizable(false);
		setTitle("EMPLEADOS");
		setBounds(100, 100, 532, 409);
		getContentPane().setLayout(null);

		label = new JLabel("Nombre");
		label.setBounds(10, 11, 63, 34);
		getContentPane().add(label);

		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(103, 18, 267, 20);
		getContentPane().add(textFieldNombre);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buscar();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(408, 17, 108, 34);
		getContentPane().add(btnBuscar);

		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					borrar();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBorrar.setVisible(false);
		btnBorrar.setBounds(283, 152, 108, 34);
		getContentPane().add(btnBorrar);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormEmpleado fe = new FormEmpleado(per);
				fe.setVisible(true);
			}
		});
		btnNuevo.setBounds(408, 62, 108, 34);
		getContentPane().add(btnNuevo);

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardarEmpleado();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnModificar.setVisible(false);
		btnModificar.setBounds(408, 107, 108, 34);
		getContentPane().add(btnModificar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerTodo();
			}
		});
		btnCancelar.setBounds(408, 152, 108, 34);
		getContentPane().add(btnCancelar);

		lblDireccin = new JLabel("Direcci\u00F3n");
		lblDireccin.setBounds(10, 72, 80, 14);
		getContentPane().add(lblDireccin);

		textFieldDirec = new JTextField();
		textFieldDirec.setBounds(103, 69, 267, 20);
		getContentPane().add(textFieldDirec);
		textFieldDirec.setColumns(10);

		textFieldFecha = new JTextField();
		textFieldFecha.setVisible(false);
		textFieldFecha.setBounds(103, 114, 108, 20);
		getContentPane().add(textFieldFecha);
		textFieldFecha.setColumns(10);

		lblFechaNac = new JLabel("Fecha Nac");
		lblFechaNac.setVisible(false);
		lblFechaNac.setBounds(10, 117, 63, 14);
		getContentPane().add(lblFechaNac);

		lblFormato = new JLabel("(DD/MM/AAAA)");
		lblFormato.setVisible(false);
		lblFormato.setBounds(236, 117, 134, 14);
		getContentPane().add(lblFormato);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 197, 506, 172);
		getContentPane().add(scrollPane);
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
					rellenarDatos();
					if (table.getSelectedRow()>=0) {
						btnModificar.setVisible(true);
						btnBorrar.setVisible(true);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void guardarEmpleado() throws Exception {

		
		if (table.getSelectedRow() >= 0) {
			Empleado e = per.consultarEmpleadoID((Integer) dtm.getValueAt(table.getSelectedRow(), 0));
			if (e != null) {
				String nombre = textFieldNombre.getText().trim();
				String direccion = textFieldDirec.getText().trim();
				String fecha = textFieldFecha.getText().trim();

				if (nombre.equals("")) {
					JOptionPane.showMessageDialog(this, "Debe introducir un nombre", "INFORMACION",
							JOptionPane.INFORMATION_MESSAGE);
					textFieldNombre.grabFocus();
					return;
				}
				if (direccion.equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(this, "Debe introducir una dirección", "INFORMACION",
							JOptionPane.WARNING_MESSAGE);
					textFieldDirec.grabFocus();
					return;
				}

				if (per.consultarEmpleadoUnico(nombre, direccion) != null) {
					JOptionPane.showMessageDialog(this, "Ya existe un empleado con ese nombre y dirreción",
							"INFORMACION", JOptionPane.WARNING_MESSAGE);
					textFieldNombre.grabFocus();
					return;
				}

				// Validamos fecha
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.setLenient(false);
				try {
					Date d = sdf.parse(fecha);
					e.setFechaNac(d);
				} catch (ParseException p) {
					JOptionPane.showMessageDialog(this, "Formato de la fecha incorrecto o vacío", "INFORMACION",
							JOptionPane.WARNING_MESSAGE);
					textFieldFecha.grabFocus();
					return;
				}

				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				e.setNombre(nombre.toUpperCase());
				e.setDireccion(direccion);
				if (per.guardar(e,"")) {
					JOptionPane.showMessageDialog(this, "Empleado modificado correctamente", "CORRECTO",
							JOptionPane.PLAIN_MESSAGE);
					restablecerTodo();
					buscar();
				} else {
					JOptionPane.showMessageDialog(this, "Modificacion fallida", "ERROR", JOptionPane.PLAIN_MESSAGE);
					return;
				}
			}

		}

		else {
			JOptionPane.showMessageDialog(this, "Debe selecionar un empleado a modificar", "INFORMACION",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void buscar() throws Exception {

		List<Empleado> empleados;

		String direccion = textFieldDirec.getText();
		if (!direccion.equals("")) {

			empleados = per.consultarEmpleados(textFieldNombre.getText().trim(), textFieldDirec.getText().trim());
		} else {
			empleados = per.consultarEmpleados(textFieldNombre.getText().trim(), "");

		}

		if (empleados.isEmpty()) {
			revelarCampos(false);
			JOptionPane.showMessageDialog(this, "No hay empleados", "Buscar",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			revelarCampos(true);
		}

		rellenarTabla(empleados);
	}

	private void revelarCampos(boolean flag) {

		lblFechaNac.setVisible(flag);
		lblFormato.setVisible(flag);
		textFieldFecha.setVisible(flag);
		btnModificar.setVisible(flag);
		btnBorrar.setVisible(flag);

	}

	public void rellenarDatos() throws Exception {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		if (table.getSelectedRow() != -1) {
			String Sid = String.valueOf(dtm.getValueAt(table.getSelectedRow(), 0));
			Integer id = Integer.parseInt(Sid);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			btnModificar.setVisible(true);
			btnBorrar.setVisible(true);
			Empleado e = per.consultarEmpleadoID(id);
			textFieldNombre.setText(e.getNombre());
			textFieldDirec.setText(e.getDireccion());
			if (e.getFechaNac() != null) {
				textFieldFecha.setText(sdf.format(e.getFechaNac()));
			} else {
				textFieldFecha.setText("");
			}
		}

	}

	public void rellenarTabla(List<Empleado> empleados) {

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

	private void restablecerTodo() {
		// TODO Auto-generated method stub
		dtm.setRowCount(0);
		textFieldNombre.setText("");
		textFieldDirec.setText("");
		textFieldFecha.setText("");
		revelarCampos(false);
		textFieldNombre.grabFocus();
		btnModificar.setVisible(false);
		btnBorrar.setVisible(true);

	}
	private void borrar() throws Exception {
			
		if (table.getSelectedRow()>=0) {
			Empleado e= per.consultarEmpleadoID((Integer)dtm.getValueAt(table.getSelectedRow(), 0));
			if (!e.getTratamientos().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El empleado tiene tratamientos asociados, no es posible eliminarlo", "INFORMACION",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!e.getZonas().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El empleado tiene zonas asociados, no es posible eliminarlo", "INFORMACION",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!e.getNominas().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El empleado tiene nominas asociados, no es posible eliminarlo", "INFORMACION",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!e.getAnimaltratamientos().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El empleado tiene tratamientos en curso, no es posible eliminarlo", "INFORMACION",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			per.borrar(e,"");
			JOptionPane.showMessageDialog(this, "Empleado borrador correctamente", "INFORMACION",
					JOptionPane.INFORMATION_MESSAGE);
			restablecerTodo();
			buscar();

		}else {
			JOptionPane.showMessageDialog(this, "Debe selecionar un empleado a borrar", "INFORMACION",
					JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}
