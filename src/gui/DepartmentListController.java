package gui;

import java.io.IOException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Service.DepartmentService;
import model.entities.Department;

public class DepartmentListController implements Initializable {
	
	private DepartmentService departmentServico;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private Button botaoNew;
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	@FXML
	private TableColumn<Department, String> tableColumnNome;
	
	private ObservableList<Department> obsList;
	
	
	//buscar a janela do formulario
	@FXML
	public void onButtonAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		createDialogForm("/gui/DepartmentForm.fxml", parentStage);
	}
	
	public void setDepartment(DepartmentService dpService) {
		this.departmentServico = dpService;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		InitializeNodes();
		
	}

	private void InitializeNodes() {
		// para "Instancia" as tabelas 
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
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
	private void createDialogForm(String absoluteName,Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			//carrega a janela do formulario para preencher
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados do departmento");
			
			dialogStage.setScene(new Scene(pane));
			
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch(IOException e) {
			Alerts.Aviso("IOException", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
		
	}

}
