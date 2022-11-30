package tk.gvfs.automacaoresidencial.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "AMBIENTE", schema = "AUT_RESIDENCIA")
@NoArgsConstructor
public class Ambiente {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NOME")
	private String nome;

	@ManyToOne
	@JoinColumn(name = "ID_PROJETO")
	private Projeto projeto;
	
	@OneToMany(mappedBy = "ambiente")
	private final List<Luminaria> luminarias = new ArrayList<>();

	public Ambiente(Long id, String nome, Projeto projeto) {
		super();
		this.id = id;
		this.nome = nome;
		this.projeto = projeto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Luminaria> getLuminarias() {
		return luminarias;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, luminarias, nome, projeto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ambiente other = (Ambiente) obj;
		return Objects.equals(id, other.id) && Objects.equals(luminarias, other.luminarias)
				&& Objects.equals(nome, other.nome) && Objects.equals(projeto, other.projeto);
	}

	
	
	
}
