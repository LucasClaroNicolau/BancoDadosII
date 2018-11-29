package principal;
import principal.Tela;

import java.io.IOException;

import banco.Banco;

public class Main {
	public static void main(String[] args) throws IOException {
		
		Banco.iniciarConexao();
		//Process process = Runtime.getRuntime().exec("main.bat");
		Tela window = new Tela();
		window.frame.setVisible(true);
		
	}
}
