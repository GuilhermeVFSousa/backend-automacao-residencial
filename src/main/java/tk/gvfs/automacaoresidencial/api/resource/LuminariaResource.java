package tk.gvfs.automacaoresidencial.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tk.gvfs.automacaoresidencial.api.dto.LuminariaDTO;
import tk.gvfs.automacaoresidencial.exception.RegraNegocioException;
import tk.gvfs.automacaoresidencial.model.entity.Ambiente;
import tk.gvfs.automacaoresidencial.model.entity.Luminaria;
import tk.gvfs.automacaoresidencial.model.enums.EstadoLuminaria;
import tk.gvfs.automacaoresidencial.service.AmbienteService;
import tk.gvfs.automacaoresidencial.service.LuminariaService;

@RestController
@RequestMapping("/api/v1/luminarias")
@RequiredArgsConstructor
public class LuminariaResource {
	
	private final LuminariaService service;
	
	private final AmbienteService ambienteService;
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> buscarTodosPorIdAmbiente(@PathVariable("id") Long idAmbiente) {
		Luminaria luminariaFiltro = new Luminaria();
		
		Optional<Ambiente> ambiente = ambienteService.obterPorId(idAmbiente);
		if(!ambiente.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Ambiente não encontrado para o Id informado.");
		}
		else {
			luminariaFiltro.setAmbiente(ambiente.get());
		}
		List<Luminaria> luminarias = service.buscar(luminariaFiltro);
		return ResponseEntity.ok(luminarias);
	}
	
	@GetMapping("/todos")
	public ResponseEntity<List<Luminaria>> buscarTodos() {
		
		List<Luminaria> list = service.buscarTodos();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Luminaria> obterLuminarias(@PathVariable("id") Long id) {
		Luminaria obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody LuminariaDTO dto) {
		try {
			Luminaria entidade = converter(dto);
			entidade =service.salvar(entidade);
			return new ResponseEntity<>(entidade, HttpStatus.CREATED);
		}
		catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody LuminariaDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				Luminaria luminaria = converter(dto);
				luminaria.setId(entity.getId());
				service.atualizar(luminaria);
				return ResponseEntity.ok(luminaria);
			}
			catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>("Luminária não encontrada na base de dados.", HttpStatus.BAD_REQUEST));
		
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entidade -> {
			service.deletar(entidade);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Projeto não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	@PutMapping("{id}/atualiza-status-on")
	public ResponseEntity<?> atualizarEstadoOn (@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entity -> {
			EstadoLuminaria estadoSelecionado = EstadoLuminaria.LIGADO;
			
			if(estadoSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar o estado da luminária, envie um estado válido");
			}
			try {
				entity.setEstado(estadoSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			}
			catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> new ResponseEntity<>("Luminária não encontrada na base de Dados.", HttpStatus.BAD_REQUEST));
	}
	
	@PutMapping("{id}/atualiza-status-off")
	public ResponseEntity<?> atualizarEstadoOff (@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entity -> {
			EstadoLuminaria estadoSelecionado = EstadoLuminaria.DESLIGADO;
			
			if(estadoSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar o estado da luminária, envie um estado válido");
			}
			try {
				entity.setEstado(estadoSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			}
			catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> new ResponseEntity<>("Luminária não encontrada na base de Dados.", HttpStatus.BAD_REQUEST));
	}
	
	private Luminaria converter(LuminariaDTO dto) {
		Luminaria luminaria = new Luminaria();
		luminaria.setId(dto.getId());
		luminaria.setNome(dto.getNome());
		
		Ambiente ambiente = ambienteService
				.obterPorId(dto.getAmbiente())
				.orElseThrow(() -> new RegraNegocioException("Ambiente não encontrado para o ID informado."));
		
		luminaria.setAmbiente(ambiente);
		
		if(dto.getEstado() != null) {
			luminaria.setEstado(EstadoLuminaria.valueOf(dto.getEstado()));
		}
		
		return luminaria;
	}

}
