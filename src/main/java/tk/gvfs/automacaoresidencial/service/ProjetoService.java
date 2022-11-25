package tk.gvfs.automacaoresidencial.service;

import java.util.List;
import java.util.Optional;

import tk.gvfs.automacaoresidencial.model.entity.Projeto;

public interface ProjetoService {
	
	List<Projeto> buscarTodos();
	
	Projeto salvar(Projeto projeto);
	
	Projeto atualizar(Projeto projeto);
	
	void deletar(Projeto projeto);
	
	List<Projeto> buscar(Projeto projetoFiltro);
	
	void validar(Projeto projeto);
	
	Optional<Projeto> obterPorId(Long id);
	
	Projeto findById(Long id);


}
