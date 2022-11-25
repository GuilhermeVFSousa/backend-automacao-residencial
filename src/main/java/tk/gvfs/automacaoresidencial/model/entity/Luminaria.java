package tk.gvfs.automacaoresidencial.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.gvfs.automacaoresidencial.model.enums.EstadoLuminaria;

@Entity
@Table(name = "LUMINARIA", schema = "AUT_RESIDENCIA")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Luminaria {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NOME")
	private String nome;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ID_AMBIENTE")
	private Ambiente ambiente;
	
	@Column(name = "ESTADO")
	@Enumerated(value = EnumType.STRING)
	private EstadoLuminaria estado;

}
