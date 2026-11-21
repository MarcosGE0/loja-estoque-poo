package br.ufal.aracomp.loja.models;

public class Computador extends Produto {
	private String processador;
	private int memoriaRam;
	private String sistemaOperacional;
	
	public Computador(String id, String nome, double preco, int qtdEstoque, String processador, int memoriaRam, String sistemaOperacional) {
		super(id, nome, preco, qtdEstoque);
		this.processador = processador;
		this.memoriaRam = memoriaRam;
		this.sistemaOperacional = sistemaOperacional;
	}
	
	public String getProcessador() {
		return this.processador;
	}
	
	@Override
	public String toString() {
		return String.format("%s | %s | R$ %s | Qtd: %s | %s | %s | %s", this.getId(), this.getNome(), this.getPreco(), this.getQtdEstoque(), this.isDisponivel(), this.getProcessador(), this.memoriaRam);
	}
}
