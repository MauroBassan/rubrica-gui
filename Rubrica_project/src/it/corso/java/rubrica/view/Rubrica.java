package it.corso.java.rubrica.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import it.corso.java.rubrica.business.RubricaBusiness;
import it.corso.java.rubrica.model.Contatto;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;

public class Rubrica {

	private JFrame frame;
	private JTextField txtNome;
	private JTextField txtCognome;
	private JTextField txtTelefono;
	private JTable listaContatti;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rubrica window = new Rubrica();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Rubrica() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1108, 647);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 75, 1072, 522);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Inserisci contatto", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(23, 25, 46, 14);
		panel.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(108, 22, 86, 20);
		panel.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setBounds(23, 71, 46, 14);
		panel.add(lblCognome);
		
		txtCognome = new JTextField();
		txtCognome.setBounds(108, 68, 86, 20);
		panel.add(txtCognome);
		txtCognome.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(23, 115, 46, 14);
		panel.add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(108, 112, 86, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contatto nuovoContatto = new Contatto();
				nuovoContatto.setNome(txtNome.getText());
				nuovoContatto.setCognome(txtCognome.getText());
				nuovoContatto.setTelefono(txtTelefono.getText());
				
				try {
					int id = RubricaBusiness.getInstance().aggiungiContatto(nuovoContatto);
					
					if(id > 0) {
						JOptionPane.showMessageDialog(null, "contatto inserito correttamente!");
						txtNome.setText("");
						txtCognome.setText("");
						txtTelefono.setText("");					
					}
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}		
			}
		});
		
		btnAggiungi.setBounds(105, 183, 92, 23);
		panel.add(btnAggiungi);
		
		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.setBounds(105, 228, 92, 23);
		panel.add(btnAnnulla);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Ricerca contatto", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(22, 51, 1021, 432);
		panel_1.add(scrollPane);
		
		listaContatti = new JTable();
		listaContatti.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Cognome", "Telefono"
			}
			) {
				boolean[] columnEditables = new boolean[] {
						false, true, true, true
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
			}
			});
		
		listaContatti.getColumnModel().getColumn(0).setPreferredWidth(83);
		listaContatti.getColumnModel().getColumn(1).setPreferredWidth(111);
		listaContatti.getColumnModel().getColumn(2).setPreferredWidth(133);
		listaContatti.getColumnModel().getColumn(3).setPreferredWidth(126);
		
		DefaultTableModel dtm = (DefaultTableModel) listaContatti.getModel();
		
		List<Contatto> contatti;
		
		try {
			contatti = RubricaBusiness.getInstance().ricercaContatti();
			for (Contatto c : contatti) {
				//andiamo ad aggiungere al tablemodel un elemento:
				Vector rowData = new Vector();
				rowData.add(c.getId());
				rowData.add(c.getNome());
				rowData.add(c.getCognome());
				rowData.add(c.getTelefono());
				
				dtm.addRow(rowData);
				
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		scrollPane.setViewportView(listaContatti);
	}

}
