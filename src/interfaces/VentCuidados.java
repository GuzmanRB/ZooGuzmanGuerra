package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clasesZoo.Alimento;
import clasesZoo.Especie;
import clasesZoo.Tratamiento;
import clasesZoo.Zona;
import persistencia.PersistenciaHibernate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.text.Normalizer.Form;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentCuidados extends JDialog {
	private JTextField textFieldDesc;
	private JTable table;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private JLabel lblDescripcion;
	private  PersistenciaHibernate per;
	private DefaultTableModel dtm;
	private JLabel lblCoste;
	private JTextField textFieldCoste;
	private String tipoVentana;


	public VentCuidados( String tipoVentana, PersistenciaHibernate per) {
		
		this.tipoVentana=tipoVentana;
		setTitle(tipoVentana.toUpperCase());
		setResizable(false);
		
		this.per=per;
		setBounds(100, 100, 571, 415);
		getContentPane().setLayout(null);
		{
			lblDescripcion = new JLabel("Descripci\u00F3n");
			lblDescripcion.setBounds(10, 31, 90, 14);
			getContentPane().add(lblDescripcion);
		}
		{
			textFieldDesc = new JTextField();
			textFieldDesc.setBounds(88, 28, 246, 20);
			getContentPane().add(textFieldDesc);
			textFieldDesc.setColumns(10);
		}
		{
			btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscar();
				}
			});
			btnBuscar.setBounds(432, 21, 108, 34);
			getContentPane().add(btnBuscar);
		}
		{
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 145, 535, 156);
			getContentPane().add(scrollPane);
			
			table = new JTable();
			scrollPane.setViewportView(table);
		}
		{
			btnNuevo = new JButton("Nuevo");
			btnNuevo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FormCuidados fa= new FormCuidados(tipoVentana,per);
					fa.setVisible(true);
				}
			});
			btnNuevo.setVisible(false);
			btnNuevo.setBounds(10, 341, 108, 34);
			getContentPane().add(btnNuevo);
		}
		{
			btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					guardar();
				}
			});
			btnModificar.setVisible(false);
			btnModificar.setBounds(139, 341, 108, 34);
			getContentPane().add(btnModificar);
		}
		{
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					restablecerTodo();
				}
			});
			btnCancelar.setBounds(432, 341, 108, 34);
			getContentPane().add(btnCancelar);
		}
		{
			btnBorrar = new JButton("Borrar");
			btnBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrar();
				}
			});
			btnBorrar.setVisible(false);
			btnBorrar.setBounds(293, 341, 108, 34);
			getContentPane().add(btnBorrar);
		}
		dtm = new DefaultTableModel();

		dtm.addColumn("ID");
		dtm.addColumn("DESCRIPCIÓN");
		dtm.addColumn("COSTE");

		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		{
			lblCoste = new JLabel("Coste");
			lblCoste.setVisible(false);
			lblCoste.setBounds(10, 91, 46, 14);
			getContentPane().add(lblCoste);
		}
		{
			textFieldCoste = new JTextField();
			textFieldCoste.setVisible(false);
			textFieldCoste.setBounds(88, 88, 99, 20);
			getContentPane().add(textFieldCoste);
			textFieldCoste.setColumns(10);
		}
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



	private void borrar() {

		if (table.getSelectedRow()!=-1) {
			
			String desc = (String) dtm.getValueAt(table.getSelectedRow(), 1);
			
			Alimento a=(Alimento)per.consultarUnico("Alimento", desc);
			
			if (a.getConsumes().isEmpty()) {
				per.borrar(a);
				JOptionPane.showMessageDialog(this, tipoVentana+" borrado correctamente", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
				textFieldCoste.setText("");
				textFieldDesc.setText("");
				buscar();
			}else {
				JOptionPane.showMessageDialog(this, "Este "+tipoVentana+"tiene animales que lo consumen, no es posible eliminarlo.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
			}
			
		}else {
			JOptionPane.showMessageDialog(this, "Debe selecionar un "+tipoVentana+" de la tabla.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
		}
		
		
	}


	private void rellenarDatos() {
		if (table.getSelectedRow()!=-1) {
			textFieldDesc.setText((String)dtm.getValueAt(table.getSelectedRow(), 1));
			textFieldCoste.setText(String.valueOf(dtm.getValueAt(table.getSelectedRow(), 2)));
		}
		
	}
	private void buscar() {
		mostrarBotones(true);
		String desc = textFieldDesc.getText().trim();
		rellenarTabla((List)per.consultarPorDesc(tipoVentana, desc));

	}

	private void rellenarTabla(List<Object> cuidados) {
		dtm.setRowCount(0);
		Object[] datos = new Object[3];
		
		if (tipoVentana.equalsIgnoreCase("tratamiento")) {
			Tratamiento t;
			for (int i = 0; i < cuidados.size(); i++) {
				t=(Tratamiento)cuidados.get(i);
				datos[0] = t.getId();
				datos[1] = t.getDescripcion();
				datos[2] = t.getCoste();

				dtm.addRow(datos);
			}
		}else {
			Alimento a;
			for (int i = 0; i < cuidados.size(); i++) {
				a=(Alimento)cuidados.get(i);
				datos[0] =a.getId();
				datos[1] =a.getDescripcion();
				datos[2] =a.getCoste();

				dtm.addRow(datos);
			}
		}



	}
	private void mostrarBotones(boolean flag) {
		// TODO Auto-generated method stub
		lblCoste.setVisible(flag);
		textFieldCoste.setVisible(flag);
		btnBorrar.setVisible(flag);
		btnModificar.setVisible(flag);
		btnNuevo.setVisible(flag);
	}
	private void restablecerTodo() {
		// TODO Auto-generated method stub
		textFieldCoste.setText("");
		textFieldDesc.setText("");
		mostrarBotones(false);
		dtm.setRowCount(0);
	}
	private void guardar() {
		if (table.getSelectedRow()!=-1) {
			String desc= (String) dtm.getValueAt(table.getSelectedRow(), 1);
			Object o= per.consultarUnico(tipoVentana, desc);
			
			String nuevaDesc= textFieldDesc.getText().trim().toUpperCase();
			Double coste;
			if (nuevaDesc.equals("")) {
				JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
				textFieldDesc.grabFocus();
				return;
			}
			try {
				coste= Double.valueOf(textFieldCoste.getText().trim());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Solo puede introducir números en el campo coste", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
				textFieldCoste.setText("");
				textFieldCoste.grabFocus();
				return;
			}
			if (per.consultarUnico(tipoVentana, desc)!=null) {
				JOptionPane.showMessageDialog(this, "Ya existe un "+tipoVentana+" con esta descripción.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				if (tipoVentana.equalsIgnoreCase("tratamiento")) {
					Tratamiento t=(Tratamiento)o;
					t.setCoste(coste);
					t.setDescripcion(nuevaDesc);
					o=t;
					
				}else {
					Alimento a=(Alimento)o;
					a.setCoste(coste);
					a.setDescripcion(nuevaDesc);
					o=a;
				}

				if (per.guardar(o)) {
					JOptionPane.showMessageDialog(this, tipoVentana+" modificado correctamente.", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
					textFieldCoste.setText("");
					textFieldDesc.setText("");
					buscar();
				}else {
					JOptionPane.showMessageDialog(this, "No se ha podido modificar el "+tipoVentana, "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		}else {
			JOptionPane.showMessageDialog(this, "Debe selecionar un "+tipoVentana+" de la tabla.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
		}

	}
}
