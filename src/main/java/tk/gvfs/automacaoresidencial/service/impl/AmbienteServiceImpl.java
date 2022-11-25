package tk.gvfs.automacaoresidencial.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.gvfs.automacaoresidencial.exception.RegraNegocioException;
import tk.gvfs.automacaoresidencial.exception.ResourceNotFoundException;
import tk.gvfs.automacaoresidencial.model.entity.Ambiente;
import tk.gvfs.automacaoresidencial.model.repository.AmbienteRepository;
import tk.gvfs.automacaoresidencial.service.AmbienteService;

@Service
public class AmbienteServiceImpl implements AmbienteService {
	
	private AmbienteRepository repository;
	
	public AmbienteServiceImpl(AmbienteRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Ambiente salvar(Ambiente ambiente) {
		validar(ambiente);
		return repository.save(ambiente);
	}

	@Override
	@Transactional
	public Ambiente atualizar(Ambiente ambiente) {
		Objects.requireNonNull(ambiente.getId());
		validar(ambiente);
		return repository.save(ambiente);
	}

	@Override
	@Transactional
	public void deletar(Ambiente ambiente) {
		Objects.requireNonNull(ambiente.getId());
		repository.delete(ambiente);
		
	}

	@Override
	public List<Ambiente> buscar(Ambiente ambienteFiltro) {
		Example<Ambiente> example = Example.of(ambienteFiltro);
		return repository.findAll(example);
	}
	

	@Override
	public void validar(Ambiente ambiente) {
		
		if(ambiente.getNome() == null || ambiente.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe um Nome válido.");
		}
		
		if(ambiente.getProjeto() == null || ambiente.getProjeto().getId() == null) {
			throw new RegraNegocioException("Informe um Projeto.");
		}
		
	}

	@Override
	public Optional<Ambiente> obterPorId(Long id) {
		return repository.findById(id);
	}


	@Override
	public List<Ambiente> buscarTodos() {
		return repository.findAll();
	}

	@Override
	public Ambiente findById(Long id) {
		Optional<Ambiente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

}
