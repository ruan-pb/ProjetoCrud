package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entidades.Department;
import Entidades.DepartmenteService;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DepartmentListController implements Initializable {
	
	private DepartmenteService departmentServico;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private Button botaoNew;
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	@FXML
	private TableColumn<Department, String> tableColumnNome;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onButtonAction() {
		System.out.println("Botao New em Ação");
	}
	
	public void setDepartment(DepartmenteService dpService) {
		this.departmentServico = dpService;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		InitializeNodes();
		
	}

	private void InitializeNodes() {
		// para "Instancia" as tabelas 
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		
		// para a tableview fica do tamanho da tela
		Stage stage = (Stage)Main.pegarMinhaCena().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
		
	}
	public void UpdateTableView() {
		if(departmentServico == null) {
			throw new IllegalStateException("Não pode ser valor nulo");
		}
		List<Department>lista = departmentServico.findAll();
		obsList = FXCollections.observableArrayList(lista);
		tableViewDepartment.setItems(obsList);
	}

}
