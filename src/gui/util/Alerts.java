package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {

	public static void Aviso(String cabecalho, String titulo, String conteudo, AlertType informacao) {
		Alert alert = new Alert(informacao);
		alert.setContentText(conteudo);
		alert.setHeaderText(cabecalho);
		alert.setTitle(titulo);
		alert.show();
	}

	public static Optional<ButtonType> showConfirmation(String title, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		return alert.showAndWait();
	}

}
