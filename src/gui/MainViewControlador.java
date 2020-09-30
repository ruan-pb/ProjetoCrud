package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewControlador implements Initializable {
	@FXML
	private MenuItem menuItemVendedor;
	@FXML
	private MenuItem menuItemDepartamento;
	@FXML
	private MenuItem menuItemSobre;

	@FXML
	public void onBtMenuVendedor() {
		System.out.println("Menu Vendedor");
	}

	@FXML
	public void onBtMenuDepartamento() {
		loadView("/gui/DepartmentList.fxml");
	}

	@FXML
	public void onBtMenuSobre() {
		loadView("/gui/AboutView.fxml");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox vBox = loader.load();
			Scene cena = Main.pegarMinhaCena();
			VBox MainVbox = (VBox)((ScrollPane)cena.getRoot()).getContent();
			Node mainMenu = MainVbox.getChildren().get(0);
			MainVbox.getChildren().clear();
			MainVbox.getChildren().add(mainMenu);
			MainVbox.getChildren().addAll(vBox.getChildren());
		}
		catch(IOException e) {
			Alerts.Aviso("IOException", "Falha ao carregar pagina", e.getMessage(), AlertType.ERROR);
		}
	}

}
