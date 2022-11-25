package tk.gvfs.automacaoresidencial.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuminariaDTO {
	
	private Long id;
	private String nome;
	private Long ambiente;
	private String estado;

}
