package gui;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constrains;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Exceptions.ValidationException;
import model.Service.DepartmentService;
import model.entities.Department;

public class DepartmentFormController implements Initializable{
	
	@FXML
	private TextField textNome;
	@FXML
	private TextField id;
	
	
	private Department entidade;
	
	private DepartmentService departmentService;
	
	private List<DataChangeListener> dataChangeListener = new ArrayList<>();
	
	@FXML
	private Label nomeError;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancel;
	
	

	
	@FXML
	public void btSalvar(ActionEvent event) {
		if(entidade == null) {
			throw new IllegalStateException("Entidade está vazia");
		}
		if(departmentService == null) {
			throw new IllegalStateException("Department Service está vazio");
		}
		try {
			entidade = getFormatDate();
			departmentService.salvarAtualizacao(entidade);
			notifyDataChangeListener();
			Utils.currentStage(event).close();
		}
		catch(DbException e) {
			Alerts.Aviso("Erro ao adicionar elemento", null,e.getMessage(), AlertType.ERROR);
		}
		catch(ValidationException e){
			setErrorMessages(e.getErros());
		}
	}
	// metodo de notificar a tabela que foi acrecentado um novo elemento
	private void notifyDataChangeListener() {
		for(DataChangeListener listener :this.dataChangeListener) {
			listener.onDataChange();
		}
		
	}
	private Department getFormatDate() {
		Department dp = new Department();
		ValidationException exception = new ValidationException("Erro na validação");
		
		dp.setId(Utils.tryParseInt(id.getText()));
		if(textNome.getText() == null || textNome.getText().trim().equals("")) {
			exception.addError("name", "O campo não pode ser vazio");
		}
		dp.setName(textNome.getText());
		if(exception.getErros().size() > 0) {
			throw exception;
		}
		return dp;
	}
	@FXML
	public void btCancelar(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	
	public void setDp(Department dp) {
		this.entidade =dp; 
	}
	public void setDepartmentService(DepartmentService dps) {
		this.departmentService = dps;
	}
	
	public void subscribeDataChangelistener(DataChangeListener listener) {
		this.dataChangeListener.add(listener);
	}

	
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	public void initializeNodes() {
		Constrains.setTextFieldInteger(id);
		Constrains.setTextFieldMaxLength(textNome, 30);
	}
	public void AtulizarFormulario() {
		if(entidade == null){
			throw new IllegalStateException("Enntidade está vazia");
		}
		id.setText(String.valueOf(this.entidade.getId()));
		textNome.setText(this.entidade.getName());
	}
	
	private void setErrorMessages(Map<String,String> erros) {
		Set<String> fields = erros.keySet();
		
		if(fields.contains("name")) {
			this.nomeError.setText(erros.get("name"));
		}
		
		
	}

}
