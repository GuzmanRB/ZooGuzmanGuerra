package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clasesZoo.Zona;
import persistencia.PersistenciaHibernate;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class VentZonas extends JDialog {
	private PersistenciaHibernate per;
	private JComboBox cbZona;
	private JButton btnNueva;
	private JButton btnBorrar;

	public VentZonas(boolean modal, PersistenciaHibernate per) {
		setTitle("Zonas");
		
		
		
		this.per=per;
		setModal(modal);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 450, 215);
		getContentPane().setLayout(null);
		{
			JLabel lblZonas = new JLabel("Zonas");
			lblZonas.setBounds(20, 32, 78, 27);
			getContentPane().add(lblZonas);
		}
		{
			cbZona = new JComboBox();
			cbZona.setBounds(82, 35, 322, 20);
			getContentPane().add(cbZona);
		}
		{
			btnNueva = new JButton("Nueva");
			btnNueva.setBounds(82, 104, 138, 41);
			getContentPane().add(btnNueva);
		}
		{
			btnBorrar = new JButton("Borrar");
			btnBorrar.setBounds(266, 104, 138, 41);
			getContentPane().add(btnBorrar);
		}
		rellenarZonas();
	}
	private void rellenarZonas() {
		List<Zona> lz =per.consultarZonas();
		
		for (int i = 0; i < lz.size(); i++) {
			cbZona.addItem(lz.get(i).getDescripcion());
		}
		cbZona.setSelectedIndex(-1);
		
	}

}
