package br.ufal.aracomp.loja.models;

public interface DescontoStrategy {
	double calcularDesconto(Pedido p);
}
