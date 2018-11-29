package banco;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
    
    public static List select(){
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
                lead.setDataAtual(rs.getString("data"));
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
                lead.setDataAtual(rs.getString("data"));
                leads.add(lead);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
		return leads;
    }

}