package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clasesZoo.Zona;
import persistencia.PersistenciaHibernate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormZonas extends JDialog {
	private JTextField textFieldDesc;
	private PersistenciaHibernate per;
	
	
	
	public FormZonas(PersistenciaHibernate per) {
		setResizable(false);
		
		setModal(true);
		this.per=per;
		setTitle("NUEVA ZONA");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 450, 157);
		getContentPane().setLayout(null);
		{
			JLabel lblDesc = new JLabel("Descripci\u00F3n");
			lblDesc.setBounds(10, 24, 103, 22);
			getContentPane().add(lblDesc);
		}
		{
			textFieldDesc = new JTextField();
			textFieldDesc.setBounds(90, 25, 206, 20);
			getContentPane().add(textFieldDesc);
			textFieldDesc.setColumns(10);
		}
		{
			JButton btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					guardar();
				}
			});
			btnGuardar.setBounds(316, 18, 108, 34);
			getContentPane().add(btnGuardar);
		}
		{
			JButton btnLimpiar = new JButton("Limpiar");
			btnLimpiar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textFieldDesc.setText("");
					textFieldDesc.grabFocus();
				}
			});
			btnLimpiar.setBounds(316, 73, 108, 34);
			getContentPane().add(btnLimpiar);
		}
	}
	private void guardar() {
		Zona z=  new Zona();
		String nuevaDesc= textFieldDesc.getText().trim();
		if (nuevaDesc.equals("")) {
			JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
			textFieldDesc.grabFocus();
			return;
		}
		if (per.consultarUnico("Zona", nuevaDesc)!=null) {
			JOptionPane.showMessageDialog(this, "Ya existe una zona con esta descripción", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
			return;
		}else {
			z.setDescripcion(nuevaDesc.toUpperCase());
			if (per.guardar(z)) {
				JOptionPane.showMessageDialog(this, "Zona guardada correctamente", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
				textFieldDesc.setText("");
				textFieldDesc.grabFocus();
			}else {
				JOptionPane.showMessageDialog(this, "No se ha crear guardar la zona", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		

	}

}
