package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.EstoqueInsuficienteException;

public interface Precificavel {
	double getPreco();
	String getNome();
	void registrarVenda(int qtd) throws DadoEntradaException, EstoqueInsuficienteException;
}
