package interfaces;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clasesZoo.Entrada;
import clasesZoo.Evento;
import persistencia.Persistencia;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class VentEntradas extends JDialog {
	private JTextField textFieldCantidad;
	private JTextField textFieldImporte;
	private Persistencia per;
	private JComboBox cbEventos;
	private JLabel lblEvento;
	private JButton btnSeleccionar;
	private JLabel lblCantidad;
	private JLabel lblImporte;
	private JButton btnComprar;
	private JButton btnBuscar;
	private JButton btnEliminar;
	private JSeparator separator;
	private JSeparator separator_1;
	private JScrollPane scrollPane;
	private JButton btnCancelar;
	private DefaultTableModel dtm;
	private JTable table;

	public VentEntradas(Persistencia per) {
		
		this.per=per;
		setTitle("ENTRADAS");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 444, 465);
		getContentPane().setLayout(null);
		
		lblEvento = new JLabel("Evento");
		lblEvento.setBounds(10, 23, 61, 14);
		getContentPane().add(lblEvento);
		
		cbEventos = new JComboBox();
		cbEventos.setBounds(81, 20, 187, 20);
		getContentPane().add(cbEventos);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				campoCompra();
			}
		});
		btnSeleccionar.setBounds(324, 13, 108, 34);
		getContentPane().add(btnSeleccionar);
		
		separator = new JSeparator();
		separator.setBounds(0, 58, 462, 9);
		getContentPane().add(separator);
		
		lblCantidad = new JLabel("Cantidad");
		lblCantidad.setBounds(10, 90, 61, 14);
		getContentPane().add(lblCantidad);
		
		textFieldCantidad = new JTextField();
		textFieldCantidad.setEnabled(false);
		textFieldCantidad.setBounds(81, 87, 61, 20);
		getContentPane().add(textFieldCantidad);
		textFieldCantidad.setColumns(10);
		
		btnComprar = new JButton("Comprar");
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardarCompras();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnComprar.setEnabled(false);
		btnComprar.setBounds(324, 112, 108, 34);
		getContentPane().add(btnComprar);
		
		lblImporte = new JLabel("Importe");
		lblImporte.setBounds(10, 132, 46, 14);
		getContentPane().add(lblImporte);
		
		textFieldImporte = new JTextField();
		textFieldImporte.setEditable(false);
		textFieldImporte.setBounds(81, 129, 86, 20);
		getContentPane().add(textFieldImporte);
		textFieldImporte.setColumns(10);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(0, 157, 462, 9);
		getContentPane().add(separator_1);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setEnabled(false);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rellenarDatos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(324, 177, 108, 34);
		getContentPane().add(btnBuscar);
		
		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 228, 420, 149);
		getContentPane().add(scrollPane);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					borrarEntrada();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(10, 391, 108, 34);
		getContentPane().add(btnEliminar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerTodo();
			}
		});
		btnCancelar.setBounds(324, 391, 108, 34);
		getContentPane().add(btnCancelar);
		
		//Creacion de la tabla
		dtm= new DefaultTableModel();
		
		dtm.addColumn("FECHA / HORA");
		dtm.addColumn("ID");
		dtm.addColumn("EVENTO");
		
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);

		
		ListSelectionModel listSelectionModel= table.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new ListSelectionListener() {
	        public void valueChanged(ListSelectionEvent e) {
	        	if (table.getSelectedRow()>=0) {
					btnEliminar.setEnabled(true);
				}
	        }
	    });
		
		try {
			rellenarEventos();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
	
	protected void borrarEntrada() throws NumberFormatException, Exception {
		if (table.getSelectedRow()>=0) {
			Entrada ent= per.consultarEntradaID(Integer.parseInt(String.valueOf(dtm.getValueAt(table.getSelectedRow(), 1))));
			Evento evento= ent.getEvento();
			per.borrar(ent,"");
			JOptionPane.showMessageDialog(this, "Entrada borrada correctament", "AVISO", JOptionPane.INFORMATION_MESSAGE);
			per.refresh(evento,"");
			rellenarDatos();
		}
		
	}




	private void guardarCompras() throws Exception {
		Integer ent;
		String desc=(String)cbEventos.getSelectedItem();
		Evento evento= per.consultarEventoUnico(desc);
		try {
			ent= Integer.parseInt(textFieldCantidad.getText().trim());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "Solo se permiten números", "AVISO", JOptionPane.INFORMATION_MESSAGE);
			textFieldCantidad.grabFocus();
			return;
		}
		if (ent<1) {
			JOptionPane.showMessageDialog(this, "El número de entradas no puede ser inferior a 1", "AVISO", JOptionPane.INFORMATION_MESSAGE);
			textFieldCantidad.grabFocus();
			return;
		}

		for (int i = 0; i < ent; i++) {
			Entrada e= new Entrada();
			e.setEvento(evento);
			e.setFechaHoraVenta(new Date(System.currentTimeMillis()));
			per.guardar(e,"");
			
		}
		textFieldImporte.setText(String.valueOf(ent*evento.getPrecio()));
		inhabilitarCompra(false);
		
	}


	private void rellenarDatos() throws Exception {
		dtm.setRowCount(0);
		String desc=(String)cbEventos.getSelectedItem();
		Evento evento= per.consultarEventoUnico(desc);
		
		List<Entrada> ent=per.consultarEntradas(evento.getId());
		
		Object[] datos= new Object[3];
		
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/YYYY HH:mm");
		sdf.setLenient(false);
		
		for (int i = 0; i < ent.size(); i++) {
			datos[0]=sdf.format(ent.get(i).getFechaHoraVenta());
			datos[1]=ent.get(i).getId();
			datos[2]=ent.get(i).getEvento().getDescripcion();
			dtm.addRow(datos);
		}
		
	}




	private void campoCompra() {
		if (cbEventos.getSelectedIndex()!=-1) {
			inhabilitarSeleccion(false);
			inhabilitarCompra(true);
			inhabilitarBusqueda(true);
			
		}else {
			JOptionPane.showMessageDialog(this, "Debe selecionar un evento", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	private void restablecerTodo() {
		
		dtm.setRowCount(0);
		cbEventos.setSelectedIndex(-1);
		textFieldCantidad.setText("");
		textFieldImporte.setText("");
		inhabilitarCompra(false);
		inhabilitarBusqueda(false);
		inhabilitarSeleccion(true);
		btnEliminar.setEnabled(false);
		
	}

	private void inhabilitarSeleccion(boolean flag) {
		cbEventos.setEnabled(flag);
		btnSeleccionar.setEnabled(flag);
	}
	private void inhabilitarCompra(boolean flag) {
		textFieldCantidad.setEnabled(flag);
		btnComprar.setEnabled(flag);
	}
	private void inhabilitarBusqueda(boolean flag) {
		scrollPane.setEnabled(flag);
		btnBuscar.setEnabled(flag);
		
	}


	public void rellenarEventos() throws Exception {
		List<Evento> le=per.consultarEventos("");
		for (int i = 0; i < le.size(); i++) {
			cbEventos.addItem(le.get(i).getDescripcion());
		}
		cbEventos.setSelectedIndex(-1);
	}
}
