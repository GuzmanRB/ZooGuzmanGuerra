package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentAnimales extends JDialog {
	private Persistencia per;
	private JTextField textFieldNombre;
	private JLabel lblNombre;
	private JLabel lblEspecie;
	private JComboBox cbEspecie;
	private JButton btnBuscar;
	private JButton btnCancelar;
	private JLabel lblFechaNac;
	private JTextField textFieldFecha;
	private JLabel lblFormto;
	private JLabel lblZonas;
	private JComboBox cbZona;
	private JTable table;
	private DefaultTableModel dtm;
	private JButton btnModificar;
	private JButton btnNuevo;
	private JButton btnBorrar;

	public VentAnimales(boolean modal, Persistencia per) {
		setResizable(false);
		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(modal);
		setTitle("ANIMALES");
		setBounds(100, 100, 524, 493);
		getContentPane().setLayout(null);
		{
			lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(10, 11, 63, 34);
			getContentPane().add(lblNombre);
		}
		
		//Iniciamos objeto persistencia
		this.per=per;
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(103, 18, 195, 20);
		getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		lblEspecie = new JLabel("Especie");
		lblEspecie.setBounds(10, 69, 46, 14);
		getContentPane().add(lblEspecie);
		
		cbEspecie = new JComboBox();
		cbEspecie.setBounds(103, 66, 195, 20);
		getContentPane().add(cbEspecie);
		
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
		btnBuscar.setBounds(377, 18, 108, 34);
		getContentPane().add(btnBuscar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerTodo();
			}
		});
		btnCancelar.setBounds(377, 196, 108, 34);
		getContentPane().add(btnCancelar);
		
		lblFechaNac = new JLabel("Fecha Nac");
		lblFechaNac.setBounds(10, 116, 63, 14);
		getContentPane().add(lblFechaNac);
		lblFechaNac.setVisible(false);
		
		textFieldFecha = new JTextField();
		textFieldFecha.setBounds(103, 113, 92, 20);
		getContentPane().add(textFieldFecha);
		textFieldFecha.setColumns(10);
		textFieldFecha.setVisible(false);
		
		lblFormto = new JLabel("(DD/MM/AAAA)");
		lblFormto.setBounds(200, 116, 98, 14);
		getContentPane().add(lblFormto);
		lblFormto.setVisible(false);
		
		lblZonas = new JLabel("Zonas");
		lblZonas.setBounds(10, 168, 46, 14);
		getContentPane().add(lblZonas);
		lblZonas.setVisible(false);
		
		cbZona = new JComboBox();
		cbZona.setBounds(103, 165, 195, 20);
		getContentPane().add(cbZona);
		cbZona.setVisible(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 241, 488, 211);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nuevoAnimal();
			}
		});
		btnNuevo.setBounds(377, 65, 108, 34);
		getContentPane().add(btnNuevo);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardarAnimal();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnModificar.setVisible(false);
		btnModificar.setBounds(377, 106, 108, 34);
		getContentPane().add(btnModificar);
		
		//Creacion de la tabla
		dtm= new DefaultTableModel();
		
		dtm.addColumn("ID");
		dtm.addColumn("NOMBRE");
		dtm.addColumn("ESPECIE");
		dtm.addColumn("FECHA NAC");
		dtm.addColumn("ZONA");
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
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
		btnBorrar.setBounds(377, 151, 108, 34);
		getContentPane().add(btnBorrar);
		
		ListSelectionModel listSelectionModel= table.getSelectionModel();
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
		
		
		
		try {
			rellenarEspecies();
			rellenarZonas();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	
	
	
	protected void nuevoAnimal() {
		// TODO Auto-generated method stub
		FormAnimal fa= new FormAnimal(per);
		fa.setVisible(true);
		
	}


	public void guardarAnimal() throws Exception {
		Animal a= per.consultarAnimalID((Integer)dtm.getValueAt(table.getSelectedRow(), 0));
		
		if (a!=null) {
			String nombre = textFieldNombre.getText().trim();
			String especie = (String)cbEspecie.getSelectedItem();
			String fecha= textFieldFecha.getText().trim();
			String zona=(String)cbZona.getSelectedItem();
			
			if (nombre.equals("")) {
				JOptionPane.showMessageDialog(this, "Debe introducir un nombre", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				textFieldNombre.grabFocus();
				return;
			}
			if (especie==null) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar una especie", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				cbEspecie.grabFocus();
				return;
			}
			Especie esp= (Especie)per.consultarUnico("Especie", especie);
			
			if (per.conultarAnimalUnico(nombre, esp.getId())!=null) {
				JOptionPane.showMessageDialog(this, "Ya existe un animal con ese nombre y especie", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				textFieldNombre.grabFocus();
				return;
			}
			if(zona==null) {
				JOptionPane.showMessageDialog(this, "Debe selecionar una zona", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				cbZona.grabFocus();
				return;
			}
			//Validamos fecha
			if (fecha.equals("")) {
				a.setFechaNac(null);
			}else {
				SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
				sdf.setLenient(false);
				try {
					Date d=sdf.parse(fecha);
					a.setFechaNac(d);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(this, "Formato de la fecha incorrecto", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
					textFieldFecha.grabFocus();
					return;
				}
			}
			
			
			Zona zon=(Zona)per.consultarUnico("Zona",zona);
			
			
			DefaultTableModel dtm= (DefaultTableModel) table.getModel();
			a.setNombre(nombre.toUpperCase());
			a.setEspecie(esp);
			a.setZona(zon);
			if (per.guardar(a)) {
				JOptionPane.showMessageDialog(this, "Animal modificado correctamente", "CORRECTO", JOptionPane.PLAIN_MESSAGE);
				restablecerTodo();
			}else {
				JOptionPane.showMessageDialog(this, "Modificacion fallida", "ERROR", JOptionPane.PLAIN_MESSAGE);
			}
		}

		
	}

	public List<Animal> buscar() throws Exception {
		
		List<Animal> animales;
		
		String desc=(String)cbEspecie.getSelectedItem();
		if (desc!=null) {
			Especie e= (Especie)per.consultarUnico("Especie", desc);
			animales = per.consultarAnimales(textFieldNombre.getText().trim(),e.getId() );
		}else {
			animales = per.consultarAnimales(textFieldNombre.getText().trim(),null );
			
		}
		
		rellenarTabla(animales);
		return animales;
	}
	
	private void revelarCampos(boolean flag) {
		
		
		lblFechaNac.setVisible(flag);
		lblFormto.setVisible(flag);
		textFieldFecha.setVisible(flag);
		cbZona.setVisible(flag);
		lblZonas.setVisible(flag);
		btnModificar.setVisible(flag);
		btnBorrar.setVisible(flag);
	}


	private void rellenarZonas() throws Exception {
		List<Zona> lz =(List)per.consultarPorDesc("Zona", "");
		
		for (int i = 0; i < lz.size(); i++) {
			cbZona.addItem(lz.get(i).getDescripcion());
		}
		cbZona.setSelectedIndex(-1);
		
	}

	private void rellenarEspecies() throws Exception {
		// TODO Auto-generated method stub
		
		List<Especie> le= (List)per.consultarPorDesc("Especie", "");
		
		for (int i = 0; i < le.size(); i++) {
			cbEspecie.addItem(le.get(i).getDescripcion());
			
		}
		cbEspecie.setSelectedIndex(-1);
		
	}
	public void rellenarDatos() throws Exception {
		DefaultTableModel dtm= (DefaultTableModel) table.getModel();
		if (table.getSelectedRow()!=-1) {
			String Sid =String.valueOf(dtm.getValueAt(table.getSelectedRow(), 0));
			Integer id= Integer.parseInt(Sid);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			revelarCampos(true);
			Animal a=per.consultarAnimalID(id);
			textFieldNombre.setText(a.getNombre());
			if (a.getFechaNac()!=null) {
				textFieldFecha.setText(sdf.format(a.getFechaNac()));
			}else {
				textFieldFecha.setText("");
			}
			
			
			cbEspecie.setSelectedItem(a.getEspecie().getDescripcion());
			cbZona.setSelectedItem(a.getZona().getDescripcion());
		}
		
		
	}
	public void rellenarTabla(List<Animal> animales) {
		
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
		btnBorrar.setVisible(false);
		
	}
	private void borrar() throws Exception {
		
		if (table.getSelectedRow()>=0) {
			Animal a= per.consultarAnimalID((Integer)dtm.getValueAt(table.getSelectedRow(), 0));
			if (!a.getAnimaltratamientos().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El animal tiene tratamientos asociados, no es posible eliminarlo", "INFORMACION",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!a.getConsumes().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El animal tiene alimentos asociados, no es posible eliminarlo", "INFORMACION",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			per.borrar(a);
			JOptionPane.showMessageDialog(this, "Animal borrador correctamente", "INFORMACION",
					JOptionPane.INFORMATION_MESSAGE);
			restablecerTodo();
			buscar();

		}else {
			JOptionPane.showMessageDialog(this, "Debe selecionar un animal a borrar", "INFORMACION",
					JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}
