package principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import banco.Banco;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JProgressBar;

public class Tela {

	JTabbedPane tabbedPane = new JTabbedPane();
	JFrame frame;
	final  JProgressBar pr = new JProgressBar();
	DefaultTableModel modelConsulta;
	DefaultTableModel modelListagem;
	private JTable tableListagem;
	private JTable tableConsulta;
	private JTextField txtInput;

	public Tela() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false); 
		frame.setBounds(100, 100, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		JLabel lblGeradorDeLeads = new JLabel("Gerador de Leads");
		lblGeradorDeLeads.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		lblGeradorDeLeads.setBounds(413, 12, 255, 64);
		frame.getContentPane().add(lblGeradorDeLeads);
		
		tabbedPane.addTab("Consulta", painelConsulta());
	    tabbedPane.addTab("Listagem", painelListagem());
	    tabbedPane.setVisible(true);
	    tabbedPane.setSize(1054, 608);
	    frame.getContentPane().add(tabbedPane);
	    
		
		
		
	}
	
	public JComponent painelConsulta() {
        JPanel painel1 = new JPanel();
        pr.setBounds(422, 45, 146, 14);
		pr.setStringPainted(true);
        painel1.add(pr);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(10, 36, 100, 23);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtInput.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Campo Vazio","Erro", JOptionPane.ERROR_MESSAGE);
				}
				else {
					File file = new File("crawler/parametro.txt");
		            if (!file.exists()) {
		                try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
		            }
		            FileWriter fw;
					try {
						fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);	
			            bw.write(txtInput.getText());
			            bw.close();
			            w.execute();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		txtInput = new JTextField();
		txtInput.setBounds(100, 10, 939, 20);
		txtInput.setColumns(10);
		
		JLabel lblPalavrachave = new JLabel("Palavra-chave");
		lblPalavrachave.setBounds(10, 11, 100, 14);
		tabbedPane.setLocation(10, 73);
		modelConsulta = new DefaultTableModel();
		modelConsulta.addColumn("Usuário");
		modelConsulta.addColumn("Tweet");	
		modelConsulta.addColumn("Data");
        painel1.setLayout(null);
		
        painel1.add(btnBuscar);
        painel1.add(txtInput);
        painel1.add(lblPalavrachave);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 70, 1029, 499);
        painel1.add(scrollPane);
        
        tableConsulta = new JTable();
        scrollPane.setViewportView(tableConsulta);
        tableConsulta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableConsulta.setModel(modelConsulta);
        
        
        tableConsulta.getColumnModel().getColumn(0).setPreferredWidth(130);
        tableConsulta.getColumnModel().getColumn(1).setPreferredWidth(826);
        tableConsulta.getColumnModel().getColumn(2).setPreferredWidth(70);
		return painel1;
    }
	
	public JComponent painelListagem() {
        JPanel painel2 = new JPanel(); 
        tabbedPane.setLocation(10, 73);
        modelListagem = new DefaultTableModel();
        modelListagem.addColumn("Usuário");
        modelListagem.addColumn("Tweet");	
        modelListagem.addColumn("Data");
        painel2.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 70, 1029, 499);
        painel2.add(scrollPane);
        
        tableListagem = new JTable();
        scrollPane.setViewportView(tableListagem);
        tableListagem.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableListagem.setModel(modelListagem);
        
        JButton button = new JButton("Listar");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
			        setTabela();				
        	}
        });
        button.setBounds(10, 36, 100, 23);
        painel2.add(button);
        
        JLabel label = new JLabel("Listagem");
        label.setBounds(10, 11, 100, 14);
        painel2.add(label);
        tableListagem.getColumnModel().getColumn(0).setPreferredWidth(130);
        tableListagem.getColumnModel().getColumn(1).setPreferredWidth(824);
        tableListagem.getColumnModel().getColumn(2).setPreferredWidth(70);
		return painel2;
    }
	
	public void setTabela(String palavra){
		modelConsulta.setRowCount(0);
		List<Leads> leads = new ArrayList<Leads>();
		leads = Banco.selectEspesific(palavra);
		for(Leads l : leads) {
			String[] add = {l.getUsername(),l.getTweet(),""+l.getDataAtual()};
			modelConsulta.addRow(add);
		}
	}
	
	public void setTabela(){
		modelListagem.setRowCount(0);
		List<Leads> leads = new ArrayList<Leads>();
		leads = Banco.select();
		for(Leads l : leads) {
			String[] add = {l.getUsername(),l.getTweet(),""+l.getDataAtual()};
			modelListagem.addRow(add);
		}
	}
	
	final SwingWorker w = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            Process process = Runtime.getRuntime().exec("app.bat");
            for (int i = 1; i <= 100; i++) {
                try {
                    pr.setValue(i);
                    pr.setString(i + "%");
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            setTabela(txtInput.getText());
            return 0;
        }
    };
}

