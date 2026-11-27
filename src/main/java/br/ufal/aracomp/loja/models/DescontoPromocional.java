package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

public class DescontoPromocional implements DescontoStrategy {
	private double percentual;
	
	public DescontoPromocional(double percentual) throws DadoEntradaException {
		if (percentual < 0.0 || percentual > 100.0) {
			throw new DadoEntradaException("Percentual do desconto deve estar entre 0 e 100.");
		}
		this.percentual = percentual;
	}
	
	@Override
	public double calcularDesconto(Pedido pedido) {
		double desconto = pedido.getValorBruto() * (this.percentual)/100;
		
		return desconto;
	}

	@Override
	public String getNome() {
		return "Desconto promocional";
	}

	@Override
	public String getDescricao() {
		return this.percentual + "% sobre o valor bruto.";
	}
}
