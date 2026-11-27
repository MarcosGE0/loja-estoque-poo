package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

public class DescontoPorQuantidade implements DescontoStrategy {
	private int quantidadeMinima;
	private double percentual;
	
	public DescontoPorQuantidade(int quantidadeMinima, double percentual) throws DadoEntradaException {
		if (quantidadeMinima <= 0) {
			throw new DadoEntradaException("Quantidade mínima deve ser maior que zero.");
		}
		if (percentual < 0.0 || percentual > 100.0) {
			throw new DadoEntradaException("Percentual do desconto deve estar entre 0 e 100.");
		}
		this.quantidadeMinima = quantidadeMinima;
		this.percentual = percentual;
	}

	@Override
	public double calcularDesconto(Pedido pedido) {
		int totalItensPedido = 0;
		
		for(ItemPedido itemCarrinho: pedido.listarItens()) {
			totalItensPedido += itemCarrinho.getQuantidade();
		}
		
		if(totalItensPedido >= this.quantidadeMinima) {
			double desconto = pedido.getValorBruto() * (this.percentual) / 100;
			
			return desconto;
		}
		return 0.0;
	}

	public int getQuantidadeMinima() {
		return this.quantidadeMinima;
	}

	@Override
	public String getNome() {
		return "Desconto por quantidade";
	}

	@Override
	public String getDescricao() {
		return this.percentual + "% quando o pedido tiver pelo menos "
				+ this.quantidadeMinima + " itens.";
	}
	
	
	
}
