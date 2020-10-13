package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Service.SellerService;
import model.entities.Seller;

public class SellerListController implements Initializable, DataChangeListener {

	private SellerService SellerServico;

	@FXML
	private TableView<Seller> tableViewSeller;
	@FXML
	private Button botaoNew;
	@FXML
	private TableColumn<Seller, Integer> tableColumnId;
	@FXML
	private TableColumn<Seller, String> tableColumnNome;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEdit;

	@FXML
	private TableColumn<Seller, Seller> tableColumnRemove;

	private ObservableList<Seller> obsList;

	@FXML
	public void onBtNewAction(ActionEvent evento) {
		Stage stage = Utils.currentStage(evento);
		Seller dp = new Seller();
		createDialogForm(dp, "/gui/SellerForm.fxml", stage);
	}

	public void setSeller(SellerService dpService) {
		this.SellerServico = dpService;
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
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());

	}

	public void UpdateTableView() {
		if (SellerServico == null) {
			throw new IllegalStateException("Não pode ser valor nulo");
		}
		List<Seller> lista = SellerServico.findAll();
		obsList = FXCollections.observableArrayList(lista);
		tableViewSeller.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private synchronized void createDialogForm(Seller dp, String absoluteName, Stage parentStage) {
		/*try {
			
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(absoluteName));

			Pane age = carregar.load();

			SellerFormController controller = carregar.getController();
			controller.setSellerService(new SellerService());
			controller.setDp(dp);
			// passando para o metodo a própria classe ou metodo, (não entendi direito)
			controller.subscribeDataChangelistener(this);
			controller.AtulizarFormulario();

			// carrega a janela do formulario para preencher
			Stage dialogStage = new Stage();

			dialogStage.setTitle("Entre com os dados do Sellero");

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
*/
	}

	@Override
	// atualizar a tabela simultaneamente
	public void onDataChange() {
		this.UpdateTableView();

	}

	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem ceteza que deseja deletar?");

		if (result.get() == ButtonType.OK) {
			if (this.SellerServico == null) {
				throw new IllegalStateException("Serviço está null");
			}
			try {
				SellerServico.remove(obj);
				UpdateTableView();
			} catch (DbIntegrityException e) {
				Alerts.Aviso("Error ao remover", null, e.getMessage(), AlertType.ERROR);
			}

		}
	}

}
