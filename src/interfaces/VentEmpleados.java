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
import persistencia.PersistenciaHibernate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentEmpleados extends JDialog {
	private JTextField textFieldNombre;
	private JTextField textFieldDirec;
	private JTextField textFieldFecha;
	private JTable table;
	private DefaultTableModel dtm;
	private PersistenciaHibernate per;

	public VentEmpleados(PersistenciaHibernate per) {

		this.per = per;

		setModal(true);
		setResizable(false);
		setTitle("EMPLEADOS");
		setBounds(100, 100, 532, 409);
		getContentPane().setLayout(null);

		JLabel label = new JLabel("Nombre");
		label.setBounds(10, 11, 63, 34);
		getContentPane().add(label);

		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(103, 18, 267, 20);
		getContentPane().add(textFieldNombre);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(408, 17, 108, 34);
		getContentPane().add(btnBuscar);

		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(408, 62, 108, 34);
		getContentPane().add(btnNuevo);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(408, 107, 108, 34);
		getContentPane().add(btnModificar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(408, 152, 108, 34);
		getContentPane().add(btnCancelar);

		JLabel lblDireccin = new JLabel("Direcci\u00F3n");
		lblDireccin.setBounds(10, 72, 46, 14);
		getContentPane().add(lblDireccin);

		textFieldDirec = new JTextField();
		textFieldDirec.setBounds(103, 69, 267, 20);
		getContentPane().add(textFieldDirec);
		textFieldDirec.setColumns(10);

		textFieldFecha = new JTextField();
		textFieldFecha.setBounds(103, 114, 108, 20);
		getContentPane().add(textFieldFecha);
		textFieldFecha.setColumns(10);

		JLabel lblFechaNac = new JLabel("Fecha Nac");
		lblFechaNac.setBounds(10, 117, 63, 14);
		getContentPane().add(lblFechaNac);

		JLabel lblFormato = new JLabel("(DD/MM/AAAA)");
		lblFormato.setBounds(236, 117, 134, 14);
		getContentPane().add(lblFormato);

		JScrollPane scrollPane = new JScrollPane();
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
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void guardarEmpleado() {
		if ((Integer) dtm.getValueAt(table.getSelectedRow(), 0) != -1) {

		}
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
				JOptionPane.showMessageDialog(this, "Ya existe un empleado con ese nombre y dorreción", "INFORMACION",
						JOptionPane.WARNING_MESSAGE);
				textFieldNombre.grabFocus();
				return;
			}

			// Validamos fecha
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
			if (per.guardar(e)) {
				JOptionPane.showMessageDialog(this, "Empleado modificado correctamente", "CORRECTO",
						JOptionPane.PLAIN_MESSAGE);
				restablecerTodo();
			} else {
				JOptionPane.showMessageDialog(this, "Modificacion fallida", "ERROR", JOptionPane.PLAIN_MESSAGE);
			}
		}

	}

	public List<Empleado> buscar() {

		List<Empleado> empleados;

		String direccion = textFieldDirec.getText();
		if (!direccion.equals("")) {
			
			empleados = per.consultarEmpleados(textFieldNombre.getText().trim(), textFieldDirec.getText().trim());
		} else {
			empleados = per.consultarEmpleados(textFieldNombre.getText().trim(),"");

		}

		if (empleados.isEmpty()) {
			revelarCampos(false);
		} else {
			revelarCampos(true);
		}

		rellenarTabla(empleados);
		return empleados;
	}

	private void revelarCampos(boolean flag) {

		lblFechaNac.setVisible(flag);
		lblFormto.setVisible(flag);
		textFieldFecha.setVisible(flag);
		cbZona.setVisible(flag);
		lblZonas.setVisible(flag);

	}

	private void rellenarZonas() {
		List<Zona> lz = (List) per.consultarPorDesc("Zona", "");

		for (int i = 0; i < lz.size(); i++) {
			cbZona.addItem(lz.get(i).getDescripcion());
		}
		cbZona.setSelectedIndex(-1);

	}

	private void rellenarEspecies() {
		// TODO Auto-generated method stub

		List<Especie> le = (List) per.consultarPorDesc("Especie", "");

		for (int i = 0; i < le.size(); i++) {
			cbEspecie.addItem(le.get(i).getDescripcion());

		}
		cbEspecie.setSelectedIndex(-1);

	}

	public void rellenarDatos() {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		if (table.getSelectedRow() != -1) {
			String Sid = String.valueOf(dtm.getValueAt(table.getSelectedRow(), 0));
			Integer id = Integer.parseInt(Sid);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			btnModificar.setVisible(true);
			Animal a = per.consultarAnimalID(id);
			textFieldNombre.setText(a.getNombre());
			if (a.getFechaNac() != null) {
				textFieldFecha.setText(sdf.format(a.getFechaNac()));
			} else {
				textFieldFecha.setText("");
			}

			cbEspecie.setSelectedIndex(a.getEspecie().getId() - 1);
			cbZona.setSelectedIndex(a.getZona().getId() - 1);
		}

	}

	public void rellenarTabla(List<Animal> animales) {

		dtm.setRowCount(0);
		Object[] datos = new Object[5];
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (int i = 0; i < animales.size(); i++) {
			datos[0] = animales.get(i).getId();
			datos[1] = animales.get(i).getNombre();
			datos[2] = animales.get(i).getEspecie().getDescripcion();
			if (animales.get(i).getFechaNac() != null) {
				datos[3] = sdf.format(animales.get(i).getFechaNac());
			} else {
				datos[3] = animales.get(i).getFechaNac();
			}
			datos[4] = animales.get(i).getZona().getDescripcion();
			dtm.addRow(datos);
		}

	}

	private void restablecerTodo() {
		// TODO Auto-generated method stub
		dtm.setRowCount(0);
		textFieldNombre.setText("");
		textFieldFecha.setText("");
		cbEspecie.setSelectedIndex(-1);
		cbZona.setSelectedIndex(-1);
		revelarCampos(false);
		textFieldNombre.grabFocus();
		btnModificar.setVisible(false);

	}

}
