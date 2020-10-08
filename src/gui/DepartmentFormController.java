package gui;


import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constrains;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{
	
	@FXML
	private TextField textNome;
	@FXML
	private TextField id;
	
	
	private Department entidade;
	
	@FXML
	private Label nomeError;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancel;
	
	

	
	@FXML
	public void btSalvar() {
		System.out.println("Salvado");
	}
	@FXML
	public void btCancelar() {
		System.out.println("cancelado");
	}
	
	
	public void setDp(Department dp) {
		this.entidade =dp; 
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
