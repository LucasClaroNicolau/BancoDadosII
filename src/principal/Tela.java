
package principal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import banco.Banco;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JProgressBar;
import javax.swing.JInternalFrame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JList;
import javax.swing.JComboBox;

public class Tela {

	JTabbedPane tabbedPane = new JTabbedPane();
	JFrame frame;
	final  JProgressBar pr = new JProgressBar();
	DefaultTableModel modelConsulta;
	DefaultTableModel modelListagem;
	DefaultTableModel modelCampanha;
	DefaultTableModel modelInstituicao;
	private JTable tableListagem;
	private JTable tableConsulta;
	private JTable tableCampanhas;
	private JTable tableInstituicao;
	private JTextField txtInput;
	private JTextField txtNomeInst;
	private JTextField txtCNPJ;
	private JTextField textField;

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
	    tabbedPane.addTab("Institui��es", painelInstituicao());
	    tabbedPane.addTab("Campanhas", painelCampanha());
	    tabbedPane.setVisible(true);
	    tabbedPane.setSize(1054, 608);
	    frame.getContentPane().add(tabbedPane);
	    
		
	}
	
	public JComponent painelConsulta() {
        JPanel painel1 = new JPanel();
        pr.setBounds(422, 45, 146, 14);
		pr.setStringPainted(true);
		pr.setVisible(false);
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
			            pr.setVisible(true);
			            getSwingWorker().execute();
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
		modelConsulta = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
		modelConsulta.addColumn("Usu�rio");
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
        tableConsulta.getColumnModel().getColumn(1).setPreferredWidth(724);
        tableConsulta.getColumnModel().getColumn(2).setPreferredWidth(157);
		return painel1;
    }
	
	public JComponent painelListagem() {
        JPanel painel2 = new JPanel(); 
        tabbedPane.setLocation(10, 73);
        modelListagem = new DefaultTableModel(){

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
        modelListagem.addColumn("Usu�rio");
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
        tableListagem.getColumnModel().getColumn(1).setPreferredWidth(724);
        tableListagem.getColumnModel().getColumn(2).setPreferredWidth(157);
		return painel2;
    }
	
	public JComponent painelInstituicao() {
        JPanel painel3 = new JPanel(); 
        painel3.addComponentListener(new ComponentAdapter() {
        	@Override
        	public void componentShown(ComponentEvent arg0) {
        		setTabelaInstitiucoes();
        	}
        });
        tabbedPane.setLocation(10, 73);
        modelInstituicao = new DefaultTableModel(){

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
		modelInstituicao.addColumn("Código");
		modelInstituicao.addColumn("Nome");
		modelInstituicao.addColumn("CNPJ");
        painel3.setLayout(null);
        
        JInternalFrame internalFrame = new JInternalFrame("Adicionar Institui\u00E7\u00E3o");
        internalFrame.setBounds(294, 41, 500, 207);        
        painel3.add(internalFrame);
        internalFrame.getContentPane().setLayout(null);
        
        JLabel lblNome = new JLabel("Nome");
        lblNome.setBounds(10, 11, 46, 14);
        internalFrame.getContentPane().add(lblNome);
        
        txtNomeInst = new JTextField();
        txtNomeInst.setBounds(10, 25, 300, 20);
        internalFrame.getContentPane().add(txtNomeInst);
        txtNomeInst.setColumns(10);
        
        JLabel lblCnpj = new JLabel("CNPJ");
        lblCnpj.setBounds(10, 56, 46, 14);
        internalFrame.getContentPane().add(lblCnpj);
        
        txtCNPJ = new JTextField();
        txtCNPJ.setBounds(10, 81, 300, 20);
        internalFrame.getContentPane().add(txtCNPJ);
        txtCNPJ.setColumns(10);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Instituicao inst = new Instituicao();
        		inst.setNome(txtNomeInst.getText());
        		inst.setCnpj(txtCNPJ.getText());
        		try {
					Banco.insetInst(inst);
					txtNomeInst.setText("");
					txtCNPJ.setText("");
					internalFrame.setVisible(false);
	        		setTabelaInstitiucoes();	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        btnSalvar.setBounds(10, 125, 89, 23);
        internalFrame.getContentPane().add(btnSalvar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		internalFrame.setVisible(false);
        	}
        });
        btnCancelar.setBounds(109, 125, 89, 23);
        internalFrame.getContentPane().add(btnCancelar);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 70, 1029, 499);
        painel3.add(scrollPane);
        
        tableInstituicao = new JTable();
        scrollPane.setViewportView(tableInstituicao);
        tableInstituicao.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableInstituicao.setModel(modelInstituicao);
        
        JButton button = new JButton("Adicionar");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		internalFrame.setVisible(true);			
        	}
        });
        button.setBounds(10, 36, 100, 23);
        painel3.add(button);
        
        JLabel label = new JLabel("Institui��es");
        label.setBounds(10, 11, 100, 14);
        painel3.add(label);
        tableInstituicao.getColumnModel().getColumn(0).setPreferredWidth(800);
        tableInstituicao.getColumnModel().getColumn(1).setPreferredWidth(211);
		return painel3;
    }
	
	public JComponent painelCampanha() {
        JPanel painel4 = new JPanel(); 
        painel4.addComponentListener(new ComponentAdapter() {
        	@Override
        	public void componentShown(ComponentEvent arg0) {
        		setTabelaCampanhas();
        	}
        });
        tabbedPane.setLocation(10, 73);
        modelCampanha = new DefaultTableModel(){

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
		modelCampanha.addColumn("Institui��o");
		modelCampanha.addColumn("Palavras-Chave");
        painel4.setLayout(null);
        
        JInternalFrame internalFrame = new JInternalFrame("Adicionar Campanha");
        internalFrame.setBounds(292, 45, 501, 222);
        painel4.add(internalFrame);
        internalFrame.getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Institui\u00E7\u00E3o");
        lblNewLabel.setBounds(10, 11, 86, 14);
        internalFrame.getContentPane().add(lblNewLabel);
        
        JTextField intituicaoId = new JTextField();
		intituicaoId.setBounds(10, 36, 300, 20);
        internalFrame.getContentPane().add(intituicaoId);
        
        JLabel lblNewLabel_1 = new JLabel("Palavra-Chave");
        lblNewLabel_1.setBounds(10, 67, 86, 14);
        internalFrame.getContentPane().add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setText("");
        textField.setBounds(10, 92, 300, 20);
        internalFrame.getContentPane().add(textField);
        textField.setColumns(10);
        
        JButton btnSalvar_1 = new JButton("Salvar");
        btnSalvar_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Campanhas camp = new Campanhas();
        		//camp.setInstituicao(comboBox.getSelectedIndex());
        		camp.setPalavrasChave(textField.getText());
        		camp.setInstituicaoId(new Integer(intituicaoId.getText()));
        		try {
					Banco.insetCamp(camp);
					textField.setText("");
					internalFrame.setVisible(false);
	        		setTabelaCampanhas();	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        	}
        });
        btnSalvar_1.setBounds(10, 141, 89, 23);
        internalFrame.getContentPane().add(btnSalvar_1);
        
        JButton btnCancelar_1 = new JButton("Cancelar");
        btnCancelar_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		internalFrame.setVisible(false);
        	}
        });
        btnCancelar_1.setBounds(109, 141, 89, 23);
        internalFrame.getContentPane().add(btnCancelar_1);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 70, 1029, 499);
        painel4.add(scrollPane);
        
        tableCampanhas = new JTable();
        scrollPane.setViewportView(tableCampanhas);
        tableCampanhas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableCampanhas.setModel(modelCampanha);
        
        JButton button = new JButton("Adicionar");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		internalFrame.setVisible(true);	
        	}
        });
        button.setBounds(10, 36, 100, 23);
        painel4.add(button);
        
        JLabel label = new JLabel("Campanhas");
        label.setBounds(10, 11, 100, 14);
        painel4.add(label);
        tableCampanhas.getColumnModel().getColumn(0).setPreferredWidth(800);
        tableCampanhas.getColumnModel().getColumn(1).setPreferredWidth(211);
		return painel4;
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
	
	public void setTabelaCampanhas(){
		modelCampanha.setRowCount(0);
		List<Campanhas> camp = new ArrayList<Campanhas>();
		camp = Banco.selectCamp();
		for(Campanhas c : camp) {
			String[] add = {c.getInstituicaoId().toString(),c.getPalavrasChave()};
			modelCampanha.addRow(add);
		}
	}
	
	public void setTabelaInstitiucoes(){
		modelInstituicao.setRowCount(0);
		List<Instituicao> inst = new ArrayList<Instituicao>();
		inst = Banco.selectInst();
		for(Instituicao i :inst) {
			String[] add = {i.getId().toString(), i.getNome(),i.getCnpj()};
			modelInstituicao.addRow(add);
		}
	}
	
	private SwingWorker getSwingWorker(){
		return new SwingWorker() {
	        @Override
	        protected Object doInBackground() throws Exception {
	            Process process = Runtime.getRuntime().exec("app.bat");
	            
	            for (int i = 1; i < 101; i++) {
	                try {
	                    pr.setValue(i);
	                    pr.setString(i + "%");
	                    Thread.sleep(50);
	                } catch (InterruptedException ex) {
	                    ex.printStackTrace();
	                }
	            }
	            setTabela(txtInput.getText());
	            pr.setVisible(false);
	            pr.setValue(0);
	            pr.setString(0 + "%");
				return process;  
	        }
	    };
	}
}

