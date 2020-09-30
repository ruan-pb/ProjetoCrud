package Entidades;

import java.util.ArrayList;
import java.util.List;

public class DepartmenteService {
	
	
	
	public List<Department> findAll(){
		List<Department>lista = new ArrayList<>();
		lista.add(new Department(1, "Automoveis"));
		lista.add(new Department(2, "Livros"));
		lista.add(new Department(3, "Móveis"));
		lista.add(new Department(4, "Varejo"));
		lista.add(new Department(5, "Sport"));
		return lista;
	}

}
