package br.ufal.aracomp.loja.models;

public class ServicoTecnico implements Precificavel {
	private String id;
	private String nome;
	private double precoHora;
	private boolean ativo;
	private double tempoHoraServico;
	private String dataRealizacao; // mudar futuramente o tipo desse atributo kkk, fiquei com dúvida de como importa o tipo dele
	
	public ServicoTecnico(String id, String nome, double precoHora, double tempoHoraServico, String dataRealizacao) {
		this.id = id;
		this.nome = nome;
		this.precoHora = precoHora;
		this.tempoHoraServico = tempoHoraServico;
		this.dataRealizacao = dataRealizacao;
		this.ativo = true; // por padrão, ao instanciar um servico tecnico, ele hjá está ativo
	}
	
	public double getPreco() {
		double total = this.precoHora * this.tempoHoraServico;
		
		return total;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public double getTempoHorasServico() {
		return this.tempoHoraServico;
	}
	
	public boolean isDisponivel() {
		return this.ativo;
	}
	
	public void desativar() {
		this.ativo = false;
	}
	
	public void registrarVenda(int qtd) {} // pior que fique em dúvida aq kkk
	
}
