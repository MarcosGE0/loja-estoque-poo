package br.ufal.aracomp.loja.models;

public interface Precificavel {
	double getPreco();
	String getNome();
	void registrarVenda(int qtd);
}
