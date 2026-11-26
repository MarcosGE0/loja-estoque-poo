package br.ufal.aracomp.loja.service;
import br.ufal.aracomp.loja.repository.*;
import br.ufal.aracomp.loja.models.*;
import br.ufal.aracomp.loja.exception.*;
import java.util.*;

public class ProdutoService {
	private ProdutoRepository produtoRepository;
	
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	public void cadastrarProduto(Produto novoProduto) throws RegistroCadastradoException, DadoEntradaException  {
		
		for(Produto produto_cadastrado: produtoRepository.listarProdutos()) {
			if(produto_cadastrado.getId().equals(novoProduto.getId())) {
				throw new RegistroCadastradoException("Produto ID:" + novoProduto.getId() + " já cadastrado!");
			}
		}
		
		if (novoProduto.getPreco() <= 0) {
			throw new DadoEntradaException("Valor inválido!"); // validar se o valor do produto é negativo
		}
		
		if (novoProduto.getQtdEstoque() <= 0) { // regra de negócio
			throw new DadoEntradaException("Valor inválido!"); // validar se o estoque é negativo
		}
		
		// validar preenchimento dos demais atributos herdeiros de Produto para que não sejam cadastrados nulos, exemplo: cadastrar
		// um produto computador com os atributos processador, memória ram e sistemaoperacional nulos.
		
		this.produtoRepository.adicionarProduto(novoProduto);
	}
	
	public Produto buscarPorId(String id_produto_buscado) throws RegistroNaoEncontradoException {
		
		for(Produto produto_cadastrado: produtoRepository.listarProdutos()) {
			if(produto_cadastrado.getId().equals(id_produto_buscado)) {
				return produto_cadastrado;
			}
		}
		
		throw new RegistroNaoEncontradoException("Produto com ID: " + id_produto_buscado + " não está cadastrado.");
		
	}
	
	public List<Produto> listarProdutos() {
		// poderiamos pensar em insewrir uma exceção para ver a quantidade de itens cadastrados, se não tiver nenhum, ele retorna um exception
		return produtoRepository.listarProdutos();
	}
	
	
	public void desativarProduto(String id_produto_desativar) throws RegistroNaoEncontradoException, ProdutoIndisponivelException {
		// primeiro buscar na base de dados
		// dps verificar o status
		// se o status tiver ativado, desativa
		
		Produto produto = buscarPorId(id_produto_desativar);
		
		if(produto.isDisponivel()) {
			produto.desativar(); // mesmo se tiver em estoque, não permitir vender
			produtoRepository.salvarAlteracoes();
		}
		
		else {
			throw new ProdutoIndisponivelException("O produto ID: " + id_produto_desativar + " já está desativado!");
		}
		
	}
	
	public void removerProduto(String id_produto_remover) {
		produtoRepository.removerProduto(id_produto_remover); // atenção aqui, tem que ver depois se é necessário inserir exception para validar antes se existe
	}
	
	
	public void adicionarEstoque(String id_produto, int qtd) throws RegistroNaoEncontradoException, DadoEntradaException {
		if(qtd <= 0) {
			throw new DadoEntradaException("Valor de estoque inválido!");
		}
		
		Produto produto = buscarPorId(id_produto);
		produto.adicionarEstoque(qtd);
		produtoRepository.salvarAlteracoes();
	}
}
