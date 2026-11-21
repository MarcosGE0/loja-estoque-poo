package br.ufal.aracomp.loja.models;

public class SemDesconto implements DescontoStrategy {
	@Override
	public double calcularDesconto(Pedido p) {
		return 0.0;
	}
}
