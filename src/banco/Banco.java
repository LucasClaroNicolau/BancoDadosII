package banco;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import principal.Campanhas;
import principal.Instituicao;
import principal.Leads;

public class Banco{

    private static final String HOST = "localhost:3306";
    private static final String BANCO = "bigdata";
    private static final String USUARIO = "root";
    private static final String SENHA = "admin";
    private static final String URL = "jdbc:mysql://" + HOST + "/" + BANCO + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static Connection conexao = null;

    private Banco(){ }

    public static Connection iniciarConexao() {
        if(conexao == null){
            try{
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                JOptionPane.showMessageDialog(null, "======== CONEXÃO INICIADA ========");
            }catch (Exception e1){
            	JOptionPane.showMessageDialog(null, "Não foi possível se conectar a base de dados");
            	System.out.println(e1);
                
            }
        }

        return conexao;
    }

    public static Connection getConexao() {
        if(conexao == null){
            iniciarConexao();
        }

        return conexao;
    }

    public static void fecharConexao() {
        if(conexao != null){
            try{
                conexao.close();
                JOptionPane.showMessageDialog(null, "======== CONEXÃO FECHADA ========");
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null, "Não foi possível ao fechar a conexão com a base de dados");
                
            }
        }
    }
    
    public static List<Leads> select(){
    	List<Leads> leads = new ArrayList<>();
        String  sql = "SELECT * FROM leads;";

        try{
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
            	Leads lead = new Leads();
                lead.setId(rs.getInt("id"));
                lead.setUsername(rs.getString("username"));
                lead.setTweet(rs.getString("tweet"));
                lead.setDataAtual(rs.getString("date"));
                leads.add(lead);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
		return leads;
    }
    
    
    public static List selectEspesific(String palavra){
    	List<Leads> leads = new ArrayList<>();
        String  sql = "SELECT * FROM leads where tweet like '%"+palavra+"%';";

        try{
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
            	Leads lead = new Leads();
                lead.setId(rs.getInt("id"));
                lead.setUsername(rs.getString("username"));
                lead.setTweet(rs.getString("tweet"));
                lead.setDataAtual(rs.getString("date"));
                leads.add(lead);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
		return leads;
    }

    
    public static void createTableCamp(){
        String  sql = "CREATE TABLE IF NOT EXISTS campanhas (\n" + 
        		"id int UNSIGNED NOT NULL AUTO_INCREMENT,\n" + 
        		"instituicao varchar(200)  NULL,\n" + 
        		"palavrasChave varchar(200) NULL,\n" + 
        		"PRIMARY KEY (id));";

        try{
            Statement st = conexao.createStatement();
            st.execute(sql);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void insetCamp(Campanhas campanha) throws SQLException {
    	createTableCamp();
    	 String query = "INSERT INTO campanhas(instituicao,palavraschave) VALUES ("+campanha.getInstituicao()+","+campanha.getPalavrasChave()+")";
    	 PreparedStatement stmt = conexao.prepareStatement(query);
    }
    
    public static List selectCamp(){
    	List<Campanhas> camp = new ArrayList<>();
        String  sql = "SELECT * FROM campanhas;";

        try{
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
            	Campanhas cmp = new Campanhas();
                //cmp.setInstituicao(rs.getString("instituicao"));
                cmp.setPalavrasChave(rs.getString("palavrasChave"));
                camp.add(cmp);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
		return camp;
    }

    
    public static void createTableInst(){
        String  sql = "CREATE TABLE IF NOT EXISTS instituicao (\n" + 
        		"id int UNSIGNED NOT NULL AUTO_INCREMENT,\n" + 
        		"nome varchar(200)  NULL,\n" + 
        		"cnpj varchar(200) NULL,\n" + 
        		"PRIMARY KEY (id));";

        try{
            Statement st = conexao.createStatement();
            st.execute(sql);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void insetInst(Instituicao instituicao) throws SQLException {
    	createTableInst();
    	 String query = "INSERT INTO instituicao(nome,cnpj) VALUES (?,?)";
    	 PreparedStatement stmt = conexao.prepareStatement(query);
    	 
    	 stmt.setString(1, instituicao.getNome());  
         stmt.setString(2, instituicao.getCnpj());  
         stmt.execute();   
         stmt.close();  
    }
    
    public static List selectInst(){
    	List<Instituicao> inst = new ArrayList<>();
        String  sql = "SELECT * FROM instituicao;";

        try{
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
            	Instituicao insti = new Instituicao();
                insti.setNome(rs.getString("nome"));
                insti.setCnpj(rs.getString("cnpj"));
                inst.add(insti);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
		return inst;
    }
    
    

}