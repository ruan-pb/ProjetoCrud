package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
		System.out.println("Menu departamento");
	}
	@FXML
	public void onBtMenuSobre() {
		System.out.println("Menu Sobre");
	}
	
	
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
	}
	
	

}
