package tk.gvfs.automacaoresidencial.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmbienteDTO {
	
	private Long id;
	private String nome;
	private Long projeto;

}
