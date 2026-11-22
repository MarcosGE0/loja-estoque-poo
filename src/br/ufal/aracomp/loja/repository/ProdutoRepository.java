package br.ufal.aracomp.loja.repository;
import java.util.*;
import br.ufal.aracomp.loja.models.Produto;

public class ProdutoRepository {
	private List<Produto> produtos;
	
	public ProdutoRepository() {
		this.produtos = new ArrayList<Produto>();
	}
	
	public void adicionarProduto(Produto novo_produto) {
		this.produtos.add(novo_produto);
	}
	
	public List<Produto> listarProdutos() {
		return this.produtos;
	}
	
	public void removerProduto(String id_produto) {
		// IMPLEMENTAR SISTEMA DE REMOÇÃO POR ÍNDICE
		// Revisar essa implementação
		int posicao_produto = -1;
		int contador = 0;
		
		for(Produto produto_cadastrado: this.listarProdutos()) {
			if(produto_cadastrado.getId().equals(id_produto)) {
				posicao_produto = contador;
			}
			contador++;
		}
		
		if(posicao_produto != -1) {
			this.produtos.remove(posicao_produto);
		}
		
	}
}
