package tk.gvfs.automacaoresidencial.api.resource;

import java.util.List;

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
import tk.gvfs.automacaoresidencial.api.dto.ProjetoDTO;
import tk.gvfs.automacaoresidencial.exception.RegraNegocioException;
import tk.gvfs.automacaoresidencial.model.entity.Projeto;
import tk.gvfs.automacaoresidencial.service.ProjetoService;

@RestController
@RequestMapping("/api/v1/projetos")
@RequiredArgsConstructor
public class ProjetoResource {
	
	private final ProjetoService service;
	
	@GetMapping
	public ResponseEntity<List<Projeto>> buscarTodos() {
		List<Projeto> list = service.buscarTodos();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Projeto> obterProjetos(@PathVariable("id") Long id) {
		Projeto obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody ProjetoDTO dto) {
		Projeto projeto = Projeto.builder()
				.nome(dto.getNome()).build();
		
		try {
			Projeto projetoSalvo = service.salvar(projeto);
			return new ResponseEntity<>(projetoSalvo, HttpStatus.CREATED);
		}
		catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ProjetoDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				Projeto projeto = converter(dto);
				projeto.setId(entity.getId());
				service.atualizar(projeto);
				return ResponseEntity.ok(projeto);
			}
			catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>("Projeto não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entidade -> {
			service.deletar(entidade);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Projeto não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
		

	private Projeto converter(ProjetoDTO dto) {
		Projeto projeto = new Projeto();
		projeto.setId(dto.getId());
		projeto.setNome(dto.getNome());
		
		
	
		return projeto;
		
		}
	

}
