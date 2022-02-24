package interfaces;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import persistencia.Persistencia;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JSeparator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentNominas extends JDialog {
	private JTextField textFieldNombre;
	private DefaultTableModel dtm, dtm1;
	private JTable table;
	private Persistencia per;
	private JTable table_1;
	private JScrollPane scrollPane_1;
	private JButton btnNueva;
	private JButton btnBorrar;
	private JButton btnInicio;
	private JButton btnSeleccionar;
	private JScrollPane scrollPane;
	private JButton btnGuardarTodo;
	private JLabel lblFecha;
	private JTextField textFieldFecha;
	private JLabel lblFormato;
	private JTextField textFieldImpBruto;
	private JLabel lblImporte;
	private JLabel lblIRPF;
	private JTextField textFieldIRPF;
	private JLabel lblSegS;
	private JTextField textFieldSegS;
	private JButton btnCrear;
	private JButton btnVolver;

	public VentNominas(Persistencia per) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

			}
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					per.transaccionRollback();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setTitle("N\u00D3MINAS");
		setResizable(false);
		this.per = per;
		setBounds(100, 100, 856, 590);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(20, 11, 63, 34);
		getContentPane().add(lblNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(109, 18, 224, 20);
		getContentPane().add(textFieldNombre);

		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					seleccionarEmpleado();
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
		separator.setBounds(0, 239, 850, 10);
		getContentPane().add(separator);

		JLabel lblNominas = new JLabel("N\u00D3MINAS");
		lblNominas.setBounds(20, 260, 98, 14);
		getContentPane().add(lblNominas);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 285, 431, 172);
		getContentPane().add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);

		btnNueva = new JButton("Nueva");
		btnNueva.setEnabled(false);
		btnNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nuevaNomina();
			}
		});
		btnNueva.setBounds(20, 513, 108, 34);
		getContentPane().add(btnNueva);



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
		btnBorrar.setBounds(229, 513, 108, 34);
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
		btnInicio.setBounds(343, 513, 108, 34);
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
		btnGuardarTodo.setBounds(229, 468, 222, 34);
		getContentPane().add(btnGuardarTodo);

		lblFecha = new JLabel("Fecha Emisi\u00F3n");
		lblFecha.setBounds(508, 288, 86, 14);
		getContentPane().add(lblFecha);

		textFieldFecha = new JTextField();
		textFieldFecha.setEnabled(false);
		textFieldFecha.setColumns(10);
		textFieldFecha.setBounds(646, 285, 86, 20);
		getContentPane().add(textFieldFecha);

		lblFormato = new JLabel("(DD/MM/AAAA)");
		lblFormato.setBounds(742, 286, 108, 14);
		getContentPane().add(lblFormato);

		textFieldImpBruto = new JTextField();
		textFieldImpBruto.setEnabled(false);
		textFieldImpBruto.setColumns(10);
		textFieldImpBruto.setBounds(646, 316, 86, 20);
		getContentPane().add(textFieldImpBruto);

		lblImporte = new JLabel("Imp. Bruto");
		lblImporte.setBounds(508, 319, 86, 14);
		getContentPane().add(lblImporte);

		lblIRPF = new JLabel("IRPF");
		lblIRPF.setBounds(508, 350, 59, 14);
		getContentPane().add(lblIRPF);

		textFieldIRPF = new JTextField();
		textFieldIRPF.setEnabled(false);
		textFieldIRPF.setColumns(10);
		textFieldIRPF.setBounds(646, 347, 86, 20);
		getContentPane().add(textFieldIRPF);

		lblSegS = new JLabel("Seg. Social");
		lblSegS.setBounds(508, 381, 59, 14);
		getContentPane().add(lblSegS);

		textFieldSegS = new JTextField();
		textFieldSegS.setEnabled(false);
		textFieldSegS.setColumns(10);
		textFieldSegS.setBounds(646, 378, 86, 20);
		getContentPane().add(textFieldSegS);

		btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (crear()) {
						volver();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnCrear.setEnabled(false);
		btnCrear.setBounds(744, 513, 96, 34);
		getContentPane().add(btnCrear);

		// CREANDO TABLA EMPLEADOS
		dtm = new DefaultTableModel();

		dtm.addColumn("ID");
		dtm.addColumn("NOMBRE");
		dtm.addColumn("DIRECCIÓN");
		dtm.addColumn("FECHA NAC");

		table = new JTable(dtm);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		

		ListSelectionModel listSelectionModel = table.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {

					if (table.getSelectedRow() >= 0) {
						btnSeleccionar.setEnabled(true);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// CREANDO TABLA NOMINAS
		dtm1 = new DefaultTableModel();

		dtm1.addColumn("FECHA EMISIÓN");
		dtm1.addColumn("ID");
		dtm1.addColumn("IMP BRUTO");
		dtm1.addColumn("IRPF");
		dtm1.addColumn("SEG.SOCIAL");

		table_1 = new JTable(dtm1);
		table_1.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(table_1);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volver();
			}
		});
		btnVolver.setEnabled(false);
		btnVolver.setBounds(508, 513, 108, 34);
		getContentPane().add(btnVolver);

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
		try {
			rellenarTabla();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void eliminar() throws Exception {
		Nomina n = per.consultarNominaID((Integer)dtm1.getValueAt(table_1.getSelectedRow(), 1));
		if (JOptionPane.showConfirmDialog(this, "Seguro que desea elminar el registro", "AVISO", JOptionPane.WARNING_MESSAGE)==0) {
			per.borrarSinCommit(n);
			btnGuardarTodo.setEnabled(true);
			rellenarTablaNomina();
		}

		
	}

	private void volver() {
		// TODO Auto-generated method stub
		habilitarCamposNomina(true);
		hablitarCamposNuevaNomina(false);
		restablecerCamposNuevoNomina();

	}

	private void guardarTodo() throws Exception {
		
		per.transaccionCommit();
		JOptionPane.showMessageDialog(this, "Cambios guardados", "AVISO", JOptionPane.INFORMATION_MESSAGE);
		restablecerTodo();

	}

	// METEDO PARA CREAR NOMINA
	private boolean crear() throws Exception {
		Nomina n;
		Integer id;
		Double importe, irpf, ss;
		Date d = null;
		

		if (validarCampos()) {
			String fecha = textFieldFecha.getText().trim();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			try {
				d = sdf.parse(fecha);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			importe = Double.valueOf(textFieldImpBruto.getText().trim());
			irpf = Double.valueOf(textFieldIRPF.getText().trim());
			ss = Double.valueOf(textFieldSegS.getText().trim());

				Empleado e = per.consultarEmpleadoID((Integer) dtm.getValueAt(table.getSelectedRow(), 0));
				n = new Nomina();
				n.setFechaEmision(d);
				n.setImporteBruto(importe);
				n.setIrpf(irpf);
				n.setSegSocial(ss);
				n.setEmpleado(e);
				per.guardarSinCommit(n);
				rellenarTablaNomina();
				return true;
			}else {
				return false;
			}

	}

	private boolean validarCampos() {
		String fecha = textFieldFecha.getText().trim();
		Double importe, irpf, ss;
		Date d;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		// VALIDANDO LA FECHA
		try {
			d = sdf.parse(fecha);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto o vacío", "AVISO",
					JOptionPane.INFORMATION_MESSAGE);
			textFieldFecha.grabFocus();
			return false;
		}

		// VALIDANDO EL IMPORTE

		try {
			importe = Double.valueOf(textFieldImpBruto.getText().trim());
		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(this, "Importe incorrecto o vacío.Solo se permiten números", "AVISO",
					JOptionPane.INFORMATION_MESSAGE);
			textFieldImpBruto.grabFocus();
			return false;
		}
		if (importe < 1) {
			JOptionPane.showMessageDialog(this, "El importe no puede ser inferior a cero", "AVISO",
					JOptionPane.INFORMATION_MESSAGE);
			textFieldImpBruto.grabFocus();
			return false;
		}
		try {
			irpf = Double.valueOf(textFieldIRPF.getText().trim());
		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(this, "IRPF incorrecto o vacío.Solo se permiten números", "AVISO",
					JOptionPane.INFORMATION_MESSAGE);
			textFieldIRPF.grabFocus();
			return false;
		}
		if (irpf < 1 || irpf > 100) {
			JOptionPane.showMessageDialog(this, "El IRPF no puede ser inferior a cero ni mayor a 100", "AVISO",
					JOptionPane.INFORMATION_MESSAGE);
			textFieldIRPF.grabFocus();
			return false;
		}
		try {
			ss = Double.valueOf(textFieldSegS.getText().trim());
		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(this, "Seg. Social incorrecta o vacía.Solo se permiten números", "AVISO",
					JOptionPane.INFORMATION_MESSAGE);
			textFieldSegS.grabFocus();
			return false;
		}
		if (ss < 1 || ss > 100) {
			JOptionPane.showMessageDialog(this, "La Seg. Social no puede ser inferior a cero ni mayor a 100", "AVISO",
					JOptionPane.INFORMATION_MESSAGE);
			textFieldSegS.grabFocus();
			return false;
		}
		if (ss + irpf > 100) {
			JOptionPane.showMessageDialog(this, "La suma de los campos IRPF y Seg. Social no puede ser mayor a 100",
					"AVISO", JOptionPane.INFORMATION_MESSAGE);
			textFieldIRPF.grabFocus();
			return false;
		}
		return true;
	}

	private void restablecerCamposNuevoNomina() {
		textFieldFecha.setText("");
		textFieldImpBruto.setText("");
		textFieldIRPF.setText("");
		textFieldSegS.setText("");

	}

	private void restablecerTodo() throws Exception {
		// TODO Auto-generated method stub
		rellenarTabla();
		dtm1.setRowCount(0);
		restablecerCamposNuevoNomina();
		hablitarCamposNuevaNomina(false);
		habilitarNominas(false);
		habilitarCamposNomina(false);
		inhabilitarEmpleado(true);
		btnSeleccionar.setEnabled(false);
		textFieldNombre.setText("");
		textFieldNombre.grabFocus();

	}

	private void nuevaNomina() {
		hablitarCamposNuevaNomina(true);
		textFieldFecha.grabFocus();
		habilitarCamposNomina(false);

	}

	private void habilitarCamposNomina(boolean b) {

		btnNueva.setEnabled(b);
		btnInicio.setEnabled(b);
		btnGuardarTodo.setEnabled(b);
		table_1.setEnabled(b);

	}

	private void hablitarCamposNuevaNomina(boolean b) {
		// TODO Auto-generated method stub

		textFieldFecha.setEnabled(b);
		textFieldImpBruto.setEnabled(b);
		textFieldIRPF.setEnabled(b);
		textFieldSegS.setEnabled(b);
		btnVolver.setEnabled(b);
		btnCrear.setEnabled(b);

	}

	private void seleccionarEmpleado() throws Exception {
		if (table.getSelectedRow() >= 0) {
			inhabilitarEmpleado(false);
			habilitarNominas(true);
			rellenarTablaNomina();
		} else {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado", "AVISO",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void rellenarTablaNomina() throws Exception {
		Empleado e = per
				.consultarEmpleadoID(Integer.valueOf(String.valueOf(dtm.getValueAt(table.getSelectedRow(), 0))));
		List<Nomina> n = per.consultarNominasPorEmpleado(e.getId());

		dtm1.setRowCount(0);
		Object[] datos = new Object[5];
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (int i = 0; i < n.size(); i++) {
			datos[0] = sdf.format(n.get(i).getFechaEmision());
			datos[1] = n.get(i).getId();
			datos[2] = n.get(i).getImporteBruto();
			datos[3] = n.get(i).getIrpf();
			datos[4] = n.get(i).getSegSocial();
			dtm1.addRow(datos);
		}

	}



	private void habilitarNominas(boolean flag) {
		table_1.setEnabled(flag);
		btnInicio.setEnabled(flag);
		btnNueva.setEnabled(flag);

	}

	private void inhabilitarEmpleado(boolean flag) {
		// TODO Auto-generated method stub
		btnSeleccionar.setEnabled(flag);
		table.setEnabled(flag);
		textFieldNombre.setEnabled(flag);

	}

	public void rellenarTabla() throws Exception {
		String nombre = textFieldNombre.getText().trim();
		List<Empleado> empleados = per.consultarEmpleados(nombre, "");

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
