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
import clasesZoo.Evento;
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

public class VentEventos extends JDialog {
	private JTable table;
	private DefaultTableModel dtm;
	private Persistencia per;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JButton btnNuevo;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private JButton btnBorrar;
	private JLabel lblDescripcion;
	private JTextField textFieldDescripcion;
	private JLabel lblPrecio;
	private JTextField textFieldPrecio;
	private JLabel lblHoraIni;
	private JTextField textFieldHoraIni;
	private JLabel lblFormatoHI;
	private JLabel lblFormatoHF;
	private JTextField textFieldHoraFin;
	private JLabel lblHoraFin;

	public VentEventos(Persistencia per) {

		this.per = per;

		setModal(true);
		setResizable(false);
		setTitle("EVENTOS");
		setBounds(100, 100, 532, 409);
		getContentPane().setLayout(null);

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
		btnBorrar.setBounds(283, 152, 108, 34);
		getContentPane().add(btnBorrar);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormEvento fe= new FormEvento(per);
				fe.setVisible(true);
			}
		});
		btnNuevo.setBounds(408, 62, 108, 34);
		getContentPane().add(btnNuevo);

		btnModificar = new JButton("Modificar");
		btnModificar.setVisible(false);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardar();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
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

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 197, 506, 172);
		getContentPane().add(scrollPane);
		dtm = new DefaultTableModel();

		dtm.addColumn("ID");
		dtm.addColumn("DESCRIPCIÓN");
		dtm.addColumn("PRECIO");
		dtm.addColumn("FECHA INICIO");
		dtm.addColumn("FECHA FIN");

		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		lblDescripcion = new JLabel("Descripci\u00F3n");
		lblDescripcion.setBounds(10, 20, 89, 14);
		getContentPane().add(lblDescripcion);
		
		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setColumns(10);
		textFieldDescripcion.setBounds(131, 17, 267, 20);
		getContentPane().add(textFieldDescripcion);
		
		lblPrecio = new JLabel("Precio");
		lblPrecio.setVisible(false);
		lblPrecio.setBounds(10, 48, 63, 14);
		getContentPane().add(lblPrecio);
		
		textFieldPrecio = new JTextField();
		textFieldPrecio.setVisible(false);
		textFieldPrecio.setColumns(10);
		textFieldPrecio.setBounds(131, 45, 63, 20);
		getContentPane().add(textFieldPrecio);
		
		lblHoraIni = new JLabel("Hora Inicio");
		lblHoraIni.setVisible(false);
		lblHoraIni.setBounds(10, 72, 89, 14);
		getContentPane().add(lblHoraIni);
		
		textFieldHoraIni = new JTextField();
		textFieldHoraIni.setVisible(false);
		textFieldHoraIni.setColumns(10);
		textFieldHoraIni.setBounds(131, 69, 63, 20);
		getContentPane().add(textFieldHoraIni);
		
		lblFormatoHI = new JLabel("(HH:MM)");
		lblFormatoHI.setVisible(false);
		lblFormatoHI.setBounds(251, 72, 89, 14);
		getContentPane().add(lblFormatoHI);
		
		lblFormatoHF = new JLabel("(HH:MM)");
		lblFormatoHF.setVisible(false);
		lblFormatoHF.setBounds(251, 100, 68, 14);
		getContentPane().add(lblFormatoHF);
		
		textFieldHoraFin = new JTextField();
		textFieldHoraFin.setVisible(false);
		textFieldHoraFin.setColumns(10);
		textFieldHoraFin.setBounds(131, 97, 63, 20);
		getContentPane().add(textFieldHoraFin);
		
		lblHoraFin = new JLabel("Hora Fin");
		lblHoraFin.setVisible(false);
		lblHoraFin.setBounds(10, 100, 46, 14);
		getContentPane().add(lblHoraFin);

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

	public void guardar() throws Exception {

		
		if (table.getSelectedRow() >= 0) {
			Evento e = per.consultarEventoID((Integer) dtm.getValueAt(table.getSelectedRow(), 0));
			if (e != null) {
				String desc = textFieldDescripcion.getText().trim();
				String precio = textFieldPrecio.getText().trim();
				String horaI = textFieldHoraIni.getText().trim();
				String horaF= textFieldHoraFin.getText().trim();
				SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
				sdf.setLenient(false);
				Date d;

				if (desc.equals("")) {
					JOptionPane.showMessageDialog(this, "Debe introducir una descripción", "INFORMACION",
							JOptionPane.INFORMATION_MESSAGE);
					textFieldDescripcion.grabFocus();
					return;
				}
				//VALIDACION DEL PRECIO
				try {
					Double p= Double.valueOf(precio);
					e.setPrecio(p);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this, "Debe introducir un precio válido", "INFORMACION",
							JOptionPane.INFORMATION_MESSAGE);
					textFieldPrecio.grabFocus();
					return;
				}
				//VALIDACION DE HORAS
				if (!horaI.equalsIgnoreCase("")) {
					try {
						d=sdf.parse(horaI);
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(this, "Debe introducir una hora de incio válida", "INFORMACION",
								JOptionPane.INFORMATION_MESSAGE);
						textFieldHoraIni.grabFocus();
						return;
					}
				}
				if (!horaF.equalsIgnoreCase("")) {
					try {
						d=sdf.parse(horaF);
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(this, "Debe introducir una hora de fin válida", "INFORMACION",
								JOptionPane.INFORMATION_MESSAGE);
						textFieldHoraFin.grabFocus();
						return;
					}
				}

				if (per.consultarEventoUnico(desc)!=null) {
					JOptionPane.showMessageDialog(this, "Ya existe un evento con esa descripción",
							"INFORMACION", JOptionPane.WARNING_MESSAGE);
					textFieldDescripcion.grabFocus();;
					return;
				}


				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				e.setDescripcion(desc.toUpperCase());
				if (per.guardar(e,"")) {
					JOptionPane.showMessageDialog(this, "Evento modificado correctamente", "CORRECTO",
							JOptionPane.PLAIN_MESSAGE);
					restablecerTodo();
					buscar();
				} else {
					JOptionPane.showMessageDialog(this, "Modificación fallida", "ERROR", JOptionPane.PLAIN_MESSAGE);
					return;
				}
			}

		}

		else {
			JOptionPane.showMessageDialog(this, "Debe selecionar un evento a modificar", "INFORMACION",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void buscar() throws Exception {

		List<Evento> eventos;

		String desc = textFieldDescripcion.getText().trim();
		eventos = per.consultarEventos(textFieldDescripcion.getText().trim());

		if (eventos.isEmpty()) {
			revelarCampos(false);
			JOptionPane.showMessageDialog(this, "No hay eventos", "Buscar",
					JOptionPane.INFORMATION_MESSAGE);
		}else {
			rellenarTabla(eventos);
		}

		
	}

	private void revelarCampos(boolean flag) {

		lblHoraIni.setVisible(flag);
		lblHoraFin.setVisible(flag);
		lblFormatoHI.setVisible(flag);
		lblFormatoHF.setVisible(flag);
		lblPrecio.setVisible(flag);
		textFieldPrecio.setVisible(flag);
		textFieldHoraIni.setVisible(flag);
		textFieldHoraFin.setVisible(flag);
		
		
		btnModificar.setVisible(flag);
		btnBorrar.setVisible(flag);

	}

	public void rellenarDatos() throws Exception {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		if (table.getSelectedRow() != -1) {
			revelarCampos(true);
			String Sid = String.valueOf(dtm.getValueAt(table.getSelectedRow(), 0));
			Integer id = Integer.parseInt(Sid);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			Evento e = per.consultarEventoID(id);
			textFieldDescripcion.setText(e.getDescripcion());
			textFieldPrecio.setText(String.valueOf(e.getPrecio()));
			if (e.getHoraInicio()!=null) {
				textFieldHoraIni.setText(sdf.format(e.getHoraInicio()));
			}
			if (e.getHoraFin()!=null) {
				textFieldHoraFin.setText(sdf.format(e.getHoraFin()));
			}
			
			
		}

	}

	public void rellenarTabla(List<Evento> eventos) {

		dtm.setRowCount(0);
		Object[] datos = new Object[5];
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		for (int i = 0; i < eventos.size(); i++) {
			datos[0] = eventos.get(i).getId();
			datos[1] = eventos.get(i).getDescripcion();
			datos[2] = eventos.get(i).getPrecio();
			if (eventos.get(i).getHoraInicio()!=null) {
				datos[3] = sdf.format(eventos.get(i).getHoraInicio());
			}else {
				datos[3] = "";
			}
			if (eventos.get(i).getHoraFin()!=null) {
				datos[4] = sdf.format(eventos.get(i).getHoraFin());
			}else {
				datos[4] = "";
			}
			
			dtm.addRow(datos);
		}

	}

	private void restablecerTodo() {
		// TODO Auto-generated method stub
		dtm.setRowCount(0);
		textFieldDescripcion.setText("");
		textFieldPrecio.setText("");
		textFieldHoraIni.setText("");
		textFieldHoraFin.setText("");
		revelarCampos(false);
		textFieldDescripcion.grabFocus();


	}
	private void borrar() throws Exception {
			
		if (table.getSelectedRow()>=0) {
			Evento e= per.consultarEventoID((Integer)dtm.getValueAt(table.getSelectedRow(), 0));
			if (!e.getEntradas().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El evento tiene entradas asociadas, no es posible eliminarlo", "INFORMACION",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			per.borrar(e,"");
			JOptionPane.showMessageDialog(this, "Evento borrador correctamente", "INFORMACION",
					JOptionPane.INFORMATION_MESSAGE);
			restablecerTodo();
			buscar();

		}else {
			JOptionPane.showMessageDialog(this, "Debe selecionar un evento a borrar", "INFORMACION",
					JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}
