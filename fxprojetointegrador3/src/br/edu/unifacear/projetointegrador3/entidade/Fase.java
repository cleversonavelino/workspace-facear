package br.edu.unifacear.projetointegrador3.entidade;

public class Fase {

	private Integer id;
	private String nome;
	
	public String toString() {
		return nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
