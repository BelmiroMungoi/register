package com.bbm.register.model.enums;

public enum Cargo {

	DESENVOLVEDOR("Desenvolvedor"), ADMINSTRADOR("Adminstrador"), SECRETARIO("Secretário"), GERENTE("Gerente"),
	ESTAGIARIO("Estagiário"), CHEFE("Chefe");

	private String nome;

	private Cargo(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return this.name();
	}
}
