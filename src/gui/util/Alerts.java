package gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	
	public static void Aviso (String cabecalho,String titulo,String conteudo,AlertType informacao) {
		Alert alert = new Alert(informacao);
		alert.setContentText(conteudo);
		alert.setHeaderText(cabecalho);
		alert.setTitle(titulo);
		alert.show();
	}

}
