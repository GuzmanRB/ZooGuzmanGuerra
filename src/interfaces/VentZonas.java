package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clasesZoo.Especie;
import clasesZoo.Zona;
import persistencia.Persistencia;
import persistencia.PersistenciaHibernate;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentZonas extends JDialog {
	private Persistencia per;
	private JButton btnNueva;
	private JButton btnBorrar;
	private JTextField textFieldDesc;
	private JButton btnBuscar;
	private JTable table;
	private DefaultTableModel dtm;
	private JScrollPane scrollPane;
	private JButton btnCancelar;
	private JButton btnModifcar;

	public VentZonas(boolean modal, Persistencia per) {
		setResizable(false);
		setTitle("ZONAS");

		this.per = per;
		setModal(modal);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setBounds(100, 100, 563, 377);
		getContentPane().setLayout(null);
		{
			JLabel lblDescripcion = new JLabel("Descripci\u00F3n");
			lblDescripcion.setBounds(20, 32, 78, 27);
			getContentPane().add(lblDescripcion);
		}

		btnNueva = new JButton("Nueva");
		btnNueva.setVisible(false);
		btnNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormZonas fz = new FormZonas(per);
				fz.setVisible(true);
			}
		});
		btnNueva.setBounds(20, 303, 108, 34);
		getContentPane().add(btnNueva);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setVisible(false);
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
		btnBorrar.setBounds(256, 303, 108, 34);
		getContentPane().add(btnBorrar);

		{
			textFieldDesc = new JTextField();
			textFieldDesc.setBounds(95, 35, 295, 20);
			getContentPane().add(textFieldDesc);
			textFieldDesc.setColumns(10);
		}
		{
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
			btnBuscar.setBounds(439, 28, 108, 34);
			getContentPane().add(btnBuscar);
		}

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 70, 527, 222);
		getContentPane().add(scrollPane);

		// Creacion de la tabla
		dtm = new DefaultTableModel();

		dtm.addColumn("ID");
		dtm.addColumn("DESCRIPCIÓN");

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

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerTodo();
			}
		});
		btnCancelar.setBounds(439, 303, 108, 34);
		getContentPane().add(btnCancelar);

		btnModifcar = new JButton("Modificar");
		btnModifcar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardar();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnModifcar.setVisible(false);
		btnModifcar.setBounds(138, 303, 108, 34);
		getContentPane().add(btnModifcar);

	}

	protected void rellenarDatos() {
		if (table.getSelectedRow() != -1) {
			String desc = (String) dtm.getValueAt(table.getSelectedRow(), 1);
			textFieldDesc.setText(desc);
		}

	}

	private void restablecerTodo() {
		// TODO Auto-generated method stub
		textFieldDesc.setText("");
		mostrarBotones(false);
		dtm.setRowCount(0);
	}

	private void buscar() throws Exception {
		mostrarBotones(true);
		String desc = textFieldDesc.getText().trim();
		rellenarTabla((List)per.consultarPorDesc("Zona", desc));

	}

	private void mostrarBotones(boolean flag) {
		// TODO Auto-generated method stub
		btnBorrar.setVisible(flag);
		btnModifcar.setVisible(flag);
		btnNueva.setVisible(flag);
	}

	private void rellenarTabla(List<Zona> zonas) {
		dtm.setRowCount(0);
		Object[] datos = new Object[2];

		for (int i = 0; i < zonas.size(); i++) {
			datos[0] = zonas.get(i).getId();
			datos[1] = zonas.get(i).getDescripcion();

			dtm.addRow(datos);
		}

	}
	private void borrar() throws Exception {
		if (table.getSelectedRow()!=-1) {
			String desc = (String) dtm.getValueAt(table.getSelectedRow(), 1);
			Zona z=(Zona)per.consultarUnico("Zona", desc);
			if (z.getAnimals().isEmpty()) {
				if (z.getEmpleados().isEmpty()) {
					per.borrar(z);
					JOptionPane.showMessageDialog(this, "Zona borrada correctamente", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
					textFieldDesc.setText("");
					buscar();
				}else {
					JOptionPane.showMessageDialog(this, "Esta zona tiene empleados asociados, debe eliminar primero los empleados.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
				}

			}else {
				JOptionPane.showMessageDialog(this, "Esta zona tiene animales asociados, debe eliminar primero los animales.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
			}
			
		}else {
			JOptionPane.showMessageDialog(this, "Debe selecionar una zona de la tabla.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
		}
		
		
	}
	private void guardar() throws Exception {
		if (table.getSelectedRow()!=-1) {
			String desc= (String) dtm.getValueAt(table.getSelectedRow(), 1);
			Zona z= (Zona)per.consultarUnico("Zona", desc);
			String nuevaDesc= textFieldDesc.getText().trim().toUpperCase();
			if (nuevaDesc.equals("")) {
				JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
				textFieldDesc.grabFocus();
				return;
			}
			if (per.consultarUnico("Zona", nuevaDesc)!=null) {
				JOptionPane.showMessageDialog(this, "Ya existe una zona con esta descripción.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				z.setDescripcion(nuevaDesc);
				if (per.guardar(z)) {
					JOptionPane.showMessageDialog(this, "Zona modificada correctamente.", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
					textFieldDesc.setText("");
					buscar();
				}else {
					JOptionPane.showMessageDialog(this, "No se ha podido guardar la zona.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		}else {
			JOptionPane.showMessageDialog(this, "Debe selecionar una zona de la tabla.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
		}

		

	}

}
