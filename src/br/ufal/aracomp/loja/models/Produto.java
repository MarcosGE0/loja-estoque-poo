package br.ufal.aracomp.loja.models;

public class Produto implements Precificavel {
	private String id;
	private String nome;
	private double preco;
	private boolean ativo; 
	private int qtdEstoque;
	
	public Produto(String id, String nome, double preco, int qtdEstoque) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.ativo = true; // decidi que por padrão, ao criar o produto e colocar em estoque ele já está disponível
		this.qtdEstoque = qtdEstoque;
	}
	
	public double getPreco() {
		return this.preco;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getId() {
		return this.id;
	}
	
	public boolean isDisponivel() {
		return this.ativo;
	}
	
	public String toString() {
		return String.format("%s | %s | R$ %s | Qtd: %s | %s", this.getId(), this.getNome(), this.getPreco(), this.getQtdEstoque(), this.isDisponivel());
	}
	
	public int getQtdEstoque() {
		return this.qtdEstoque;
	}
	
	public void desativar() { // estabelecemos que, mesmo se tiver estoque, e esse atributo
		this.ativo = false; // estiver desativado, ele não vende de forma alguma kkka
	}
	
	public void adicionarEstoque(int qtd) { // inserir tratamento de dados para não serr inserido uma qantidade negativa
		// inserir aqui a exceção de DadosEntradaException
		this.qtdEstoque += qtd;
	}
	
	public void removerEstoque(int qtd) { // inserir tratamento de dados para não serr inserido uma qantidade negativa
		// inserir aqui a exceção de DadosEntradaException
		this.qtdEstoque -= qtd;
	}
	
	public void registrarVenda(int qtd) {
		this.qtdEstoque -= qtd;
	}
	
}
