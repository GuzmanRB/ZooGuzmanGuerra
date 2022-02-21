package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clasesZoo.Animal;
import clasesZoo.Empleado;
import clasesZoo.Especie;
import clasesZoo.Zona;
import persistencia.Persistencia;
import persistencia.PersistenciaHibernate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormEmpleado extends JDialog {
	private JTextField textFieldNombre;
	private JTextField textFieldFecha;
	private JButton btnGuardar;
	private JLabel lblNombre;
	private JLabel lblDireccion;
	private JLabel lblFecha;
	private JLabel lblFormato;
	private JButton btnLimpiar;
	private Persistencia per;
	private JTextField textFieldDireccion;

	public FormEmpleado(Persistencia per) {
		
		this.per=per;
		setResizable(false);
		setModal(true);
		setTitle("NUEVO EMPLEADO");
		setBounds(100, 100, 569, 201);
		getContentPane().setLayout(null);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardarEmpleado();
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGuardar.setBounds(435, 20, 108, 34);
		getContentPane().add(btnGuardar);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(20, 30, 46, 14);
		getContentPane().add(lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(80, 27, 246, 20);
		getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		lblDireccion = new JLabel("Direcci\u00F3n");
		lblDireccion.setBounds(20, 76, 46, 14);
		getContentPane().add(lblDireccion);
		
		lblFecha = new JLabel("Fecha Nac");
		lblFecha.setBounds(20, 125, 61, 14);
		getContentPane().add(lblFecha);
		
		textFieldFecha = new JTextField();
		textFieldFecha.setBounds(80, 122, 91, 20);
		getContentPane().add(textFieldFecha);
		textFieldFecha.setColumns(10);
		
		lblFormato = new JLabel("(DD/MM/AAAA)");
		lblFormato.setHorizontalAlignment(SwingConstants.LEFT);
		lblFormato.setBounds(193, 125, 133, 14);
		getContentPane().add(lblFormato);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerTodo();
			}
		});
		btnLimpiar.setBounds(435, 115, 108, 34);
		getContentPane().add(btnLimpiar);
		
		textFieldDireccion = new JTextField();
		textFieldDireccion.setBounds(80, 73, 246, 20);
		getContentPane().add(textFieldDireccion);
		textFieldDireccion.setColumns(10);
		textFieldNombre.grabFocus();
	}
	public void guardarEmpleado() throws HeadlessException, Exception {
		Empleado e= new Empleado();
		String nombre = textFieldNombre.getText().trim();
		String direccion = textFieldDireccion.getText().trim();
		String fecha= textFieldFecha.getText().trim();
		
		if (nombre.equals("")) {
			JOptionPane.showMessageDialog(this, "Debe introducir un nombre", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
			textFieldNombre.grabFocus();
			return;
		}
		if (direccion.equals("")) {
			JOptionPane.showMessageDialog(this, "Debe introducir una dirección", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
			textFieldDireccion.grabFocus();
			return;
		}
		
		if (per.consultarEmpleadoUnico(nombre.toUpperCase(), direccion)!=null) {
			JOptionPane.showMessageDialog(this, "Ya existe un empleado con ese nombre y dirección", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
			textFieldNombre.grabFocus();
			return;
		}

		//Validamos fecha
			SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			try {
				Date d=sdf.parse(fecha);
				e.setFechaNac(d);
			} catch (ParseException p) {
				JOptionPane.showMessageDialog(this, "Formato de la fecha incorrecto o vacío", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				textFieldFecha.grabFocus();
				return;
			}
		
		
		
		e.setNombre(nombre.toUpperCase());
		e.setDireccion(direccion);
		per.guardar(e);
		JOptionPane.showMessageDialog(this, "Empleado guardado correctamente", "CORRECTO", JOptionPane.PLAIN_MESSAGE);
		restablecerTodo();
	}
	private void restablecerTodo() {
		// TODO Auto-generated method stub
		
		textFieldNombre.setText("");
		textFieldDireccion.setText("");
		textFieldFecha.setText("");
		textFieldNombre.grabFocus();
		
	}


}
