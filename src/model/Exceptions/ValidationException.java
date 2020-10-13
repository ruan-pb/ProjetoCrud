package model.Exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	// mapeia qual tipo de erro
	private Map<String,String> erros = new HashMap<>();
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	// pegar o erro que foi lançado
	public Map<String,String> getErros(){
		return erros;
	}
	
	// adicionar o tipo de error cada sintuação
	public void addError(String fieldName,String ErrorMessage) {
		erros.put(fieldName, ErrorMessage);
		
	}
	

}
