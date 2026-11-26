package br.ufal.aracomp.loja.models;

public class DescontoPromocional implements DescontoStrategy {
	private double percentual;
	
	public DescontoPromocional(double percentual) {
		this.percentual = percentual;
	}
	
	@Override
	public double calcularDesconto(Pedido p) {
		double desconto = p.getValorBruto() * (this.percentual)/100;
		
		return desconto;
	}
}
