package br.ufal.aracomp.loja.service;
import br.ufal.aracomp.loja.repository.*;
import br.ufal.aracomp.loja.models.*;
import br.ufal.aracomp.loja.exception.*;

public class ProdutoService {
	private ProdutoRepository produtoRepository;
	
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	public void cadastrarProduto(Produto novo_produto) throws RegistroCadastradoException, DadoEntradaException  {
		
		for(Produto produto_cadastrado: produtoRepository.listarProdutos()) {
			if(produto_cadastrado.getId().equals(novo_produto.getId())) {
				throw new RegistroCadastradoException("Produto ID:" + novo_produto.getId() + " já cadastrado!");
			}
		}
		
		if (novo_produto.getPreco() <= 0) {
			throw new DadoEntradaException("Valor inválido!"); // validar se o valor do produto é negativo
		}
		
		if (novo_produto.getQtdEstoque() <= 0) { // regra de negócio
			throw new DadoEntradaException("Valor inválido!"); // validar se o estoque é negativo
		}
		
		// validar preenchimento dos demais atributos herdeiros de Produto para que não sejam cadastrados nulos, exemplo: cadastrar
		// um produto computador com os atributos processador, memória ram e sistemaoperacional nulos.
		
		this.produtoRepository.adicionarProduto(novo_produto);
	}
	
}
