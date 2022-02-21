package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clasesZoo.Alimento;
import clasesZoo.Tratamiento;
import clasesZoo.Zona;
import persistencia.Persistencia;
import persistencia.PersistenciaHibernate;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormCuidados extends JDialog {
	private Persistencia per;
	private JTextField textFieldDesc;
	private JTextField textFieldCoste;
	private String tipoVentana;


	public FormCuidados(String tipoVentana, Persistencia per) {
		
		this.per=per;
		this.tipoVentana=tipoVentana;
		
		setTitle(tipoVentana.toUpperCase());
		setResizable(false);
		setModal(true);
		
		
		
		setBounds(100, 100, 450, 171);
		getContentPane().setLayout(null);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setBounds(10, 33, 95, 14);
		getContentPane().add(lblDescripcin);
		
		textFieldDesc = new JTextField();
		textFieldDesc.setBounds(78, 30, 229, 20);
		getContentPane().add(textFieldDesc);
		textFieldDesc.setColumns(10);
		
		JLabel lblCoste = new JLabel("Coste");
		lblCoste.setBounds(10, 90, 46, 14);
		getContentPane().add(lblCoste);
		
		textFieldCoste = new JTextField();
		textFieldCoste.setBounds(78, 87, 86, 20);
		getContentPane().add(textFieldCoste);
		textFieldCoste.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardar();
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGuardar.setBounds(326, 23, 108, 34);
		getContentPane().add(btnGuardar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		btnLimpiar.setBounds(326, 86, 108, 34);
		getContentPane().add(btnLimpiar);
	}

	private void limpiar() {
		textFieldDesc.setText("");
		textFieldCoste.setText("");
		textFieldDesc.grabFocus();
		
	}

	private void guardar() throws HeadlessException, Exception {
		Alimento a=  new Alimento();
		Tratamiento t= new Tratamiento();
		Object o= new Object();
		String nuevaDesc= textFieldDesc.getText().trim();
		Double coste=null;

		
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
		if (per.consultarUnico(tipoVentana, nuevaDesc)!=null) {
			JOptionPane.showMessageDialog(this, "Ya existe un "+tipoVentana+" con esta descripción", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
			return;
		}else {
			if (tipoVentana.equalsIgnoreCase("tratamiento")) {
				t.setDescripcion(nuevaDesc.toUpperCase());
				t.setCoste(coste);
				o=t;
			}else {
				a.setDescripcion(nuevaDesc.toUpperCase());
				a.setCoste(coste);
				o=a;
			}

			if (per.guardar(o)) {
				JOptionPane.showMessageDialog(this, tipoVentana+" guardado correctamente", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
				limpiar();
			}else {
				JOptionPane.showMessageDialog(this, "No se ha podido guardar el "+tipoVentana, "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		

	}

}
