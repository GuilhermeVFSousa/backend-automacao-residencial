package tk.gvfs.automacaoresidencial.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.gvfs.automacaoresidencial.exception.RegraNegocioException;
import tk.gvfs.automacaoresidencial.model.entity.Luminaria;
import tk.gvfs.automacaoresidencial.model.enums.EstadoLuminaria;
import tk.gvfs.automacaoresidencial.model.repository.LuminariaRepository;
import tk.gvfs.automacaoresidencial.service.LuminariaService;

@Service
public class LuminariaServiceImpl implements LuminariaService {
	
	private LuminariaRepository repository;
	
	public LuminariaServiceImpl(LuminariaRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Luminaria salvar(Luminaria luminaria) {
		validar(luminaria);
		luminaria.setEstado(EstadoLuminaria.DESLIGADO);
		return repository.save(luminaria);
	}

	@Override
	@Transactional
	public Luminaria atualizar(Luminaria luminaria) {
		Objects.requireNonNull(luminaria.getId());
		validar(luminaria);
		return repository.save(luminaria);
	}

	@Override
	@Transactional
	public void deletar(Luminaria luminaria) {
		Objects.requireNonNull(luminaria.getId());
		repository.delete(luminaria);
		
	}

	@Override
	public List<Luminaria> buscar(Luminaria luminariaFiltro) {
			Example<Luminaria> example = Example.of( luminariaFiltro);
			
			return repository.findAll(example);
	}

	@Override
	public void atualizarEstado(Luminaria luminaria, EstadoLuminaria estado) {
		luminaria.setEstado(estado);;
		atualizar(luminaria);
		
	}

	@Override
	public void validar(Luminaria luminaria) {
		
		if(luminaria.getNome() == null || luminaria.getNome().trim().equals("") || luminaria.getNome().trim().equals(" ") ) {
			throw new RegraNegocioException("Informe um Nome válido para a Luminária.");
		}
		
		if(luminaria.getAmbiente() == null || luminaria.getAmbiente().getId() == null) {
			throw new RegraNegocioException("Informe um Ambiente.");
		}
		
		if(luminaria.getEstado() == null) {
			throw new RegraNegocioException("O estado deve ser 'LIGADO' ou 'DESLIGADO'");
		}
	}

	@Override
	public Optional<Luminaria> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Luminaria> buscarTodos() {
		return repository.findAll();
	}

}
