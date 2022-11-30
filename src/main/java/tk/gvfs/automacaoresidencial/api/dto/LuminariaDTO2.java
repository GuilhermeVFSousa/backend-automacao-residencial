package tk.gvfs.automacaoresidencial.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuminariaDTO2 {
	
	private Long id;
	private String nome;
	private String estado;

}
