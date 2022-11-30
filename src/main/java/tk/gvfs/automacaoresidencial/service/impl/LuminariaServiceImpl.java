package tk.gvfs.automacaoresidencial.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.gvfs.automacaoresidencial.exception.DatabaseException;
import tk.gvfs.automacaoresidencial.exception.RegraNegocioException;
import tk.gvfs.automacaoresidencial.exception.ResourceNotFoundException;
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
		try {
			Objects.requireNonNull(luminaria.getId());
			validar(luminaria);
			return repository.save(luminaria);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(luminaria.getId());
		}
	}

	@Override
	@Transactional
	public void deletar(Luminaria luminaria) {
		try {
		Objects.requireNonNull(luminaria.getId());
		repository.delete(luminaria);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(luminaria.getId());
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
		catch (ConstraintViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	@Override
	public List<Luminaria> buscar(Luminaria luminariaFiltro) {
		Example<Luminaria> example = Example.of(luminariaFiltro);

		return repository.findAll(example);
	}

	@Override
	public void atualizarEstado(Luminaria luminaria, EstadoLuminaria estado) {
		luminaria.setEstado(estado);
		atualizar(luminaria);

	}

	@Override
	public void validar(Luminaria luminaria) {

		if (luminaria.getNome() == null || luminaria.getNome().trim().equals("")
				|| luminaria.getNome().trim().equals(" ")) {
			throw new RegraNegocioException("Informe um Nome válido para a Luminária.");
		}

		if (luminaria.getAmbiente() == null || luminaria.getAmbiente().getId() == null) {
			throw new RegraNegocioException("Informe um Ambiente.");
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

	@Override
	public Luminaria findById(Long id) {
		Optional<Luminaria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public void validar_(Luminaria luminaria) {

		if (luminaria.getNome() == null || luminaria.getNome().trim().equals("")
				|| luminaria.getNome().trim().equals(" ")) {
			throw new RegraNegocioException("Informe um Nome válido para a Luminária.");
		}

	}

	@Override
	public Luminaria atualizar_(Luminaria luminaria) {
		Objects.requireNonNull(luminaria.getId());
		validar_(luminaria);
		return repository.save(luminaria);
	}

}
