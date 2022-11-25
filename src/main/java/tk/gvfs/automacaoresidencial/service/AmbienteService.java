package tk.gvfs.automacaoresidencial.service;

import java.util.List;
import java.util.Optional;

import tk.gvfs.automacaoresidencial.model.entity.Ambiente;

public interface AmbienteService {
	
	List<Ambiente> buscarTodos();
	
	Ambiente salvar(Ambiente ambiente);
	
	Ambiente atualizar(Ambiente ambiente);
	
	void deletar(Ambiente ambiente);
	
	List<Ambiente> buscar(Ambiente ambienteFiltro);
	
	void validar(Ambiente ambiente);
	
	Optional<Ambiente> obterPorId(Long id);
	
	Ambiente findById(Long id);

}
