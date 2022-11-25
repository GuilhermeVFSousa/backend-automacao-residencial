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
import tk.gvfs.automacaoresidencial.api.dto.AmbienteDTO;
import tk.gvfs.automacaoresidencial.exception.RegraNegocioException;
import tk.gvfs.automacaoresidencial.model.entity.Ambiente;
import tk.gvfs.automacaoresidencial.model.entity.Projeto;
import tk.gvfs.automacaoresidencial.service.AmbienteService;
import tk.gvfs.automacaoresidencial.service.ProjetoService;

@RestController
@RequestMapping("/api/v1/ambientes")
@RequiredArgsConstructor
public class AmbienteResource {
	
	private final AmbienteService service;
	
	private final ProjetoService projetoService;
	
	@GetMapping
	public ResponseEntity<List<Ambiente>> buscarTodos() {
		List<Ambiente> list = service.buscarTodos();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> obterAmbientesPorId(@PathVariable("id") Long id) {
		return service.obterPorId(id)
				.map(ambiente -> new ResponseEntity<>(converterDTO(ambiente), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
			
	}
	
	@GetMapping("todos/{id}")
	public ResponseEntity<?> buscarTodosPorIdProjeto(@PathVariable("id") Long id) {
		Ambiente ambienteFiltro = new Ambiente();
		
		Optional<Projeto> projeto = projetoService.obterPorId(id);
		if(!projeto.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Projeto não encontrado para o Id informado.");
		}
		else {
			ambienteFiltro.setProjeto(projeto.get());
		}
		
		List<Ambiente> ambientes = service.buscar(ambienteFiltro);
		return ResponseEntity.ok(ambientes);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody AmbienteDTO dto) {
		try {
			Ambiente entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity<Object>(entidade, HttpStatus.CREATED);
		}
		catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AmbienteDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				Ambiente ambiente = converter(dto);
				ambiente.setId(entity.getId());
				service.atualizar(ambiente);
				return ResponseEntity.ok(ambiente);
			}
			catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<Object>("Ambiente não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entidade -> {
			service.deletar(entidade);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Ambiente não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	private AmbienteDTO converterDTO(Ambiente ambiente) {
		return AmbienteDTO.builder()
				.id(ambiente.getId())
				.nome(ambiente.getNome())
				.projeto(ambiente.getProjeto().getId())
				.build();
	}
	
	private Ambiente converter(AmbienteDTO dto) {
		Ambiente ambiente = new Ambiente();
		ambiente.setId(dto.getId());
		ambiente.setNome(dto.getNome());
		
		Projeto projeto = projetoService
				.obterPorId(dto.getProjeto())
				.orElseThrow(() -> new RegraNegocioException("Projeto não encontrado para o ID fornecido."));
		
		ambiente.setProjeto(projeto);
		
		return ambiente;
	}

}
