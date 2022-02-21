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

import clasesZoo.Animal;
import clasesZoo.Especie;
import clasesZoo.Zona;
import persistencia.PersistenciaHibernate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormAnimal extends JDialog {
	private JTextField textFieldNombre;
	private JTextField textFieldFecha;
	private JButton btnGuardar;
	private JLabel lblNombre;
	private JLabel lblEspecie;
	private JComboBox cbEspecie;
	private JLabel lblFecha;
	private JLabel lblFormato;
	private JLabel lblZona;
	private JComboBox cbZona;
	private JButton btnLimpiar;
	private PersistenciaHibernate per;

	public FormAnimal(PersistenciaHibernate per) {
		
		this.per=per;
		setResizable(false);
		setModal(true);
		setTitle("NUEVO ANIMAL");
		setBounds(100, 100, 569, 280);
		getContentPane().setLayout(null);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarAnimal();
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
		
		lblEspecie = new JLabel("Especie");
		lblEspecie.setBounds(20, 76, 46, 14);
		getContentPane().add(lblEspecie);
		
		cbEspecie = new JComboBox();
		cbEspecie.setBounds(80, 73, 246, 20);
		getContentPane().add(cbEspecie);
		
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
		
		lblZona = new JLabel("Zona");
		lblZona.setBounds(20, 181, 46, 14);
		getContentPane().add(lblZona);
		
		cbZona = new JComboBox();
		cbZona.setBounds(80, 178, 246, 20);
		getContentPane().add(cbZona);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerTodo();
			}
		});
		btnLimpiar.setBounds(435, 171, 108, 34);
		getContentPane().add(btnLimpiar);
		rellenarEspecies();
		rellenarZonas();
		textFieldNombre.grabFocus();
	}
	public void guardarAnimal() {
		Animal a= new Animal();
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
			try {
				Date d=sdf.parse(fecha);
				a.setFechaNac(d);
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(this, "Formato de la fecha incorrecto", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				textFieldFecha.grabFocus();
				return;
			}
		}
		
		
		Zona zon=(Zona)per.consultarUnico("Zona", zona);
		
		a.setNombre(nombre.toUpperCase());
		a.setEspecie(esp);
		a.setZona(zon);
		per.guardar(a);
		JOptionPane.showMessageDialog(this, "Animal guardado correctamente", "CORRECTO", JOptionPane.PLAIN_MESSAGE);
		restablecerTodo();
	}
	private void restablecerTodo() {
		// TODO Auto-generated method stub
		
		textFieldNombre.setText("");
		textFieldFecha.setText("");
		cbEspecie.setSelectedIndex(-1);
		cbZona.setSelectedIndex(-1);
		textFieldNombre.grabFocus();
		
	}
	private void rellenarZonas() {
		List<Zona> lz =(List)per.consultarPorDesc("Zona", "");
		
		for (int i = 0; i < lz.size(); i++) {
			cbZona.addItem(lz.get(i).getDescripcion());
		}
		cbZona.setSelectedIndex(-1);
		
	}

	private void rellenarEspecies() {
		// TODO Auto-generated method stub
		
		List<Especie> le= (List)per.consultarPorDesc("Especie", "");
		
		for (int i = 0; i < le.size(); i++) {
			cbEspecie.addItem(le.get(i).getDescripcion());
			
		}
		cbEspecie.setSelectedIndex(-1);
		
	}
}
