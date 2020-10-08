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
import javafx.scene.layout.AnchorPane;
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
	
	@FXML
	public void onBtNewAction(ActionEvent evento) {
		Stage stage = Utils.currentStage(evento);
		createDialogForm("/gui/DepartmentForm.fxml", stage);
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
			throw new IllegalStateException("N�o pode ser valor nulo");
		}
		List<Department>lista = departmentServico.findAll();
		obsList = FXCollections.observableArrayList(lista);
		tableViewDepartment.setItems(obsList);
	}
	private synchronized void createDialogForm(String absoluteName,Stage parentStage) {
		try {
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(absoluteName));
			System.out.println("OK01");
			Pane age = carregar.load();
			System.out.println("OK02");
			//carrega a janela do formulario para preencher
			Stage dialogStage = new Stage();
			System.out.println("OK03");
			dialogStage.setTitle("Entre com os dados do departmento");
			
			dialogStage.setScene(new Scene(age));
			//pode ou n�o ser redimencionada
			dialogStage.setResizable(false);
			
			//fun��o para saber quem � o pai da janela
			dialogStage.initOwner(parentStage);
			//enquanto voc� n�o fechar essa janela voc� n�o consegue mexer na janela anterios
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch(IOException e) {
			Alerts.Aviso("IOException", "N�o foi possivel carregar a pagina", e.getMessage(),AlertType.ERROR);
		}
		
	}

}
