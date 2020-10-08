package gui;


import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
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
import model.Service.DepartmentService;
import model.entities.Department;

public class DepartmentFormController implements Initializable{
	
	@FXML
	private TextField textNome;
	@FXML
	private TextField id;
	
	
	private Department entidade;
	
	private DepartmentService departmentService;
	
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
			Utils.currentStage(event).close();
		}
		catch(DbException e) {
			Alerts.Aviso("Erro ao adicionar elemento", null,e.getMessage(), AlertType.ERROR);
		}
	}
	private Department getFormatDate() {
		Department dp = new Department();
		dp.setId(Utils.tryParseInt(id.getText()));
		dp.setName(textNome.getText());
		return dp;
	}
	@FXML
	public void btCancelar() {
		System.out.println("cancelado");
	}
	
	
	public void setDp(Department dp) {
		this.entidade =dp; 
	}
	public void setDepartmentService(DepartmentService dps) {
		this.departmentService = dps;
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

}
