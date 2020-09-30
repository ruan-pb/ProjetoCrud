package gui;

import java.net.URL;
import java.util.ResourceBundle;

import Entidades.Department;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DepartmentListController implements Initializable {
	
	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private Button botaoNew;
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	@FXML
	private TableColumn<Department, String> tableColumnNome;
	
	@FXML
	public void onButtonAction() {
		System.out.println("Botao New em Ação");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		InitializeNodes();
		
	}

	private void InitializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		
		Stage stage = (Stage)Main.pegarMinhaCena().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
		
	}

}
