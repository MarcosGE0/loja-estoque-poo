package br.ufal.aracomp.loja.models;

public class SemDesconto implements DescontoStrategy {
	@Override
	public double calcularDesconto(Pedido pedido) {
		return 0.0;
	}

	@Override
	public String getNome() {
		return "Sem desconto";
	}

	@Override
	public String getDescricao() {
		return "Nenhum desconto aplicado.";
	}
}
