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
import clasesZoo.Evento;
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

public class FormEvento extends JDialog {
	private JTextField textFieldDesc;
	private JTextField textFieldHoraI;
	private JButton btnGuardar;
	private JLabel lblDesc;
	private JLabel lblPrecio;
	private JLabel lblHoraIni;
	private JLabel lblFormatoHI;
	private JButton btnLimpiar;
	private Persistencia per;
	private JTextField textFieldPrecio;
	private JLabel lblHoraFin;
	private JTextField textFieldHoraF;
	private JLabel lblFormatoHF;

	public FormEvento(Persistencia per) {
		
		this.per=per;
		setResizable(false);
		setModal(true);
		setTitle("NUEVO EVENTO");
		setBounds(100, 100, 569, 263);
		getContentPane().setLayout(null);
		
		btnGuardar = new JButton("Guardar");
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
		btnGuardar.setBounds(435, 20, 108, 34);
		getContentPane().add(btnGuardar);
		
		lblDesc = new JLabel("Descripci\u00F3n");
		lblDesc.setBounds(20, 30, 91, 14);
		getContentPane().add(lblDesc);
		
		textFieldDesc = new JTextField();
		textFieldDesc.setBounds(115, 27, 246, 20);
		getContentPane().add(textFieldDesc);
		textFieldDesc.setColumns(10);
		
		lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(20, 76, 46, 14);
		getContentPane().add(lblPrecio);
		
		lblHoraIni = new JLabel("Hora Inicio");
		lblHoraIni.setBounds(20, 125, 61, 14);
		getContentPane().add(lblHoraIni);
		
		textFieldHoraI = new JTextField();
		textFieldHoraI.setBounds(115, 122, 91, 20);
		getContentPane().add(textFieldHoraI);
		textFieldHoraI.setColumns(10);
		
		lblFormatoHI = new JLabel("(HH:MM)");
		lblFormatoHI.setHorizontalAlignment(SwingConstants.LEFT);
		lblFormatoHI.setBounds(247, 125, 70, 14);
		getContentPane().add(lblFormatoHI);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerTodo();
			}
		});
		btnLimpiar.setBounds(435, 151, 108, 34);
		getContentPane().add(btnLimpiar);
		
		textFieldPrecio = new JTextField();
		textFieldPrecio.setBounds(115, 73, 99, 20);
		getContentPane().add(textFieldPrecio);
		textFieldPrecio.setColumns(10);
		
		lblHoraFin = new JLabel("Hora Fin");
		lblHoraFin.setBounds(20, 171, 61, 14);
		getContentPane().add(lblHoraFin);
		
		textFieldHoraF = new JTextField();
		textFieldHoraF.setColumns(10);
		textFieldHoraF.setBounds(115, 168, 91, 20);
		getContentPane().add(textFieldHoraF);
		
		lblFormatoHF = new JLabel("(HH:MM)");
		lblFormatoHF.setHorizontalAlignment(SwingConstants.LEFT);
		lblFormatoHF.setBounds(247, 171, 70, 14);
		getContentPane().add(lblFormatoHF);
		textFieldDesc.grabFocus();
	}
	public void guardar() throws HeadlessException, Exception {
		Evento e= new Evento();
		String desc = textFieldDesc.getText().trim();
		String precio = textFieldPrecio.getText().trim();
		String horaF= textFieldHoraF.getText().trim();
		String horaI= textFieldHoraI.getText().trim();
		
		if (desc.equals("")) {
			JOptionPane.showMessageDialog(this, "Debe introducir una descripción", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
			textFieldDesc.grabFocus();
			return;
		}
		if (precio.equals("")) {
			JOptionPane.showMessageDialog(this, "Debe introducir un precio", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
			textFieldPrecio.grabFocus();
			return;
		}
		
		if (per.consultarEventoUnico(desc.toUpperCase())!=null) {
			JOptionPane.showMessageDialog(this, "Ya existe un evento con esa descripción", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
			textFieldDesc.grabFocus();
			return;
		}
		//VALIDAMOS PRECIO
		try {
			Double prec=Double.valueOf(precio);
			e.setPrecio(prec);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "Precio incorrecto ", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
			textFieldPrecio.grabFocus();
			return;
		}
		
		//VALIDAMOS HORAS
		SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
		sdf.setLenient(false);
		if (!horaI.equalsIgnoreCase("")) {
			try {
				Date d=sdf.parse(horaI);
				e.setHoraInicio(d);
				
			} catch (ParseException p) {
				JOptionPane.showMessageDialog(this, "Formato de la hora de inicio incorrecto", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				textFieldHoraI.grabFocus();
				return;
			}
		}
		if (!horaF.equalsIgnoreCase("")) {
			try {
				Date d=sdf.parse(horaF);
				e.setHoraFin(d);
				
			} catch (ParseException p) {
				JOptionPane.showMessageDialog(this, "Formato de la hora de fin incorrecto", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				textFieldHoraF.grabFocus();
				return;
			}
		}
		
		e.setDescripcion(desc.toUpperCase());
		per.guardar(e,"");
		JOptionPane.showMessageDialog(this, "Evento guardado correctamente", "CORRECTO", JOptionPane.PLAIN_MESSAGE);
		restablecerTodo();
	}
	private void restablecerTodo() {
		// TODO Auto-generated method stub
		
		textFieldDesc.setText("");
		textFieldPrecio.setText("");
		textFieldHoraI.setText("");
		textFieldHoraF.setText("");
		textFieldDesc.grabFocus();
		
		
	}


}
