package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Service.DepartmentService;
import model.entities.Department;

public class DepartmentListController implements Initializable, DataChangeListener {

	private DepartmentService departmentServico;

	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private Button botaoNew;
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	@FXML
	private TableColumn<Department, String> tableColumnNome;

	@FXML
	private TableColumn<Department, Department> tableColumnEdit;

	private ObservableList<Department> obsList;

	@FXML
	public void onBtNewAction(ActionEvent evento) {
		Stage stage = Utils.currentStage(evento);
		Department dp = new Department();
		createDialogForm(dp, "/gui/DepartmentForm.fxml", stage);
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
		Stage stage = (Stage) Main.pegarMinhaCena().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());

	}

	public void UpdateTableView() {
		if (departmentServico == null) {
			throw new IllegalStateException("Não pode ser valor nulo");
		}
		List<Department> lista = departmentServico.findAll();
		obsList = FXCollections.observableArrayList(lista);
		tableViewDepartment.setItems(obsList);
		initEditButtons();
	}

	private synchronized void createDialogForm(Department dp, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(absoluteName));

			Pane age = carregar.load();

			DepartmentFormController controller = carregar.getController();
			controller.setDepartmentService(new DepartmentService());
			controller.setDp(dp);
			// passando para o metodo a própria classe ou metodo, (não entendi direito)
			controller.subscribeDataChangelistener(this);
			controller.AtulizarFormulario();

			// carrega a janela do formulario para preencher
			Stage dialogStage = new Stage();

			dialogStage.setTitle("Entre com os dados do departmento");

			dialogStage.setScene(new Scene(age));
			// pode ou não ser redimencionada
			dialogStage.setResizable(false);

			// função para saber quem é o pai da janela
			dialogStage.initOwner(parentStage);
			// enquanto você não fechar essa janela você não consegue mexer na janela
			// anterios
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.Aviso("IOException", "Não foi possivel carregar a pagina", e.getMessage(), AlertType.ERROR);
		}

	}

	@Override
	// atualizar a tabela simultaneamente
	public void onDataChange() {
		this.UpdateTableView();

	}

	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Department, Department>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));
			}
		});
	}

}
