package br.ufal.aracomp.loja.models;

public class DescontoPorQuantidade implements DescontoStrategy {
	private int quantidadeMinima;
	private double percentual;
	
	public DescontoPorQuantidade(int quantidadeMinima, double percentual) {
		this.quantidadeMinima = quantidadeMinima;
		this.percentual = percentual;
	}

	@Override
	public double calcularDesconto(Pedido pedido) {
		int totalItensPedido = 0;
		
		for(ItemPedido itemCarrinho: pedido.listarItens()) { // obter quantos itens no total o pedido teve, aqui necessitou que eu criasse o método getQuantide em ItemPedido
			totalItensPedido += itemCarrinho.getQuantidade();
		}
		
		if(totalItensPedido > this.quantidadeMinima) {
			double desconto = pedido.getValorBruto() * (this.percentual) / 100;
			
			return desconto;
		}
		return 0.0;
	}
	
	
	
}
