package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import clasesZoo.Especie;
import clasesZoo.Zona;
import persistencia.Persistencia;
import persistencia.PersistenciaHibernate;

public class FormEspecies extends JDialog {

	private JTextField textFieldDesc;
	private Persistencia per;
	
	
	
	public FormEspecies(Persistencia per) {
		setResizable(false);
		
		setModal(true);
		this.per=per;
		setTitle("NUEVA ESPECIE");
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
	private void guardar() throws HeadlessException, Exception {
		Especie e=  new Especie();
		String nuevaDesc= textFieldDesc.getText().trim().toUpperCase();
		if (nuevaDesc.equals("")) {
			JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía.", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
			textFieldDesc.grabFocus();
			return;
		}
		if (per.consultarUnico("Especie", nuevaDesc)!=null) {
			JOptionPane.showMessageDialog(this, "Ya existe una especie con esta descripción", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
			return;
		}else {
			e.setDescripcion(nuevaDesc.toUpperCase());
			if (per.guardar(e)) {
				JOptionPane.showMessageDialog(this, "Especie guardada correctamente", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
				textFieldDesc.setText("");
				textFieldDesc.grabFocus();
			}else {
				JOptionPane.showMessageDialog(this, "No se ha podido crear la especie", "INFORMACIÓN", JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}

}
