package tk.gvfs.automacaoresidencial.service;

import java.util.List;
import java.util.Optional;

import tk.gvfs.automacaoresidencial.model.entity.Luminaria;
import tk.gvfs.automacaoresidencial.model.enums.EstadoLuminaria;

public interface LuminariaService {
	
	List<Luminaria> buscarTodos();
	
	Luminaria salvar(Luminaria luminaria);
	
	Luminaria atualizar(Luminaria luminaria);
	
	void deletar(Luminaria luminaria);
	
	List<Luminaria> buscar(Luminaria luminariaFiltro);
	
	void atualizarEstado(Luminaria luminaria, EstadoLuminaria estado);
	
	void validar(Luminaria luminaria);
	
	Optional<Luminaria> obterPorId(Long id);
	

}
