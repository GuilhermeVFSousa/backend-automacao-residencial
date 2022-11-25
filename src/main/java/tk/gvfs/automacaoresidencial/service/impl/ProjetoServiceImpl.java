package tk.gvfs.automacaoresidencial.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.gvfs.automacaoresidencial.exception.RegraNegocioException;
import tk.gvfs.automacaoresidencial.exception.ResourceNotFoundException;
import tk.gvfs.automacaoresidencial.model.entity.Projeto;
import tk.gvfs.automacaoresidencial.model.repository.ProjetoRepository;
import tk.gvfs.automacaoresidencial.service.ProjetoService;

@Service
public class ProjetoServiceImpl implements ProjetoService{
	
	private ProjetoRepository repository;
	
	public ProjetoServiceImpl(ProjetoRepository repository) {
		this.repository = repository;
	}


	@Override
	@Transactional
	public Projeto salvar(Projeto projeto) {
		validar(projeto);
		return repository.save(projeto);
	}

	@Override
	@Transactional
	public void deletar(Projeto projeto) {
		Objects.requireNonNull(projeto.getId());
		repository.delete(projeto);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Projeto> buscar(Projeto projetoFiltro) {
		Example<Projeto> example = Example.of(projetoFiltro);
		return repository.findAll(example);
	}

	@Override
	public void validar(Projeto projeto) {
		
		if(projeto.getNome() == null || projeto.getNome().trim().equals("") || projeto.getNome().trim().equals(" ")) {
			throw new RegraNegocioException("Informe um Nome válido.");
		}
		
				
	}

	@Override
	public Optional<Projeto> obterPorId(Long id) {
		
		return repository.findById(id);
	}

	@Override
	public Projeto atualizar(Projeto projeto) {
		Objects.requireNonNull(projeto.getId());
		validar(projeto);
		return repository.save(projeto);
	}

	@Override
	public List<Projeto> buscarTodos() {
		return repository.findAll();

	}

	@Override
	public Projeto findById(Long id) {
		Optional<Projeto> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id)); //RESOURCE NOT FOUND EXCEPTION APLICAR
	}

}
