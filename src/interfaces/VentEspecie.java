package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clasesZoo.Especie;
import persistencia.PersistenciaHibernate;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class VentEspecie extends JDialog {
	private PersistenciaHibernate per;
	private JButton btnNueva;
	private JComboBox cbEspecie;
	private JButton btnBorrar;
	private JLabel lblEspecies;

	public VentEspecie(boolean modal, PersistenciaHibernate per) {
		setTitle("Especies");
		
		
		this.per=per;
		setModal(modal);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 450, 210);
		getContentPane().setLayout(null);
		
		lblEspecies = new JLabel("Especies");
		lblEspecies.setBounds(22, 22, 78, 27);
		getContentPane().add(lblEspecies);
		
		cbEspecie = new JComboBox();
		cbEspecie.setSelectedIndex(-1);
		cbEspecie.setBounds(84, 25, 322, 20);
		getContentPane().add(cbEspecie);
		
		btnNueva = new JButton("Nueva");
		btnNueva.setBounds(84, 89, 138, 41);
		getContentPane().add(btnNueva);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(268, 89, 138, 41);
		getContentPane().add(btnBorrar);
		rellenarEspecies();
	}
	private void rellenarEspecies() {
		// TODO Auto-generated method stub
		
		List<Especie> le= per.consultarEspecie();
		
		for (int i = 0; i < le.size(); i++) {
			cbEspecie.addItem(le.get(i).getDescripcion());
			
		}
		cbEspecie.setSelectedIndex(-1);
		
	}
}
