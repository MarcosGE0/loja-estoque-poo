package br.ufal.aracomp.loja.service;
import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.ProdutoIndisponivelException;
import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.models.Produto;
import br.ufal.aracomp.loja.repository.ProdutoRepository;
import br.ufal.aracomp.loja.repository.*;
import br.ufal.aracomp.loja.models.*;
import br.ufal.aracomp.loja.exception.*;
import java.util.*;

public class ProdutoService {
	private ProdutoRepository produtoRepository;
	
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	public void cadastrarProduto(Produto novoProduto) throws RegistroCadastradoException, DadoEntradaException {
		
		for(Produto produto_cadastrado: produtoRepository.listarProdutos()) {
			if(produto_cadastrado.getId().equals(novoProduto.getId())) {
				throw new RegistroCadastradoException("Produto ID:" + novoProduto.getId() + " já cadastrado!");
			}
		}
		
		if (novoProduto.getPreco() <= 0) {
			throw new DadoEntradaException("Preço deve ser maior que zero (recebido: " + novoProduto.getPreco() + ").");
		}
		
		if (novoProduto.getQtdEstoque() <= 0) {
			throw new DadoEntradaException("Quantidade em estoque deve ser maior que zero (recebida: " + novoProduto.getQtdEstoque() + ").");
		}
		
		// validar preenchimento dos demais atributos herdeiros de Produto para que não sejam cadastrados nulos, exemplo: cadastrar
		// um produto computador com os atributos processador, memória ram e sistemaoperacional nulos.
		
		this.produtoRepository.adicionarProduto(novoProduto);
	}
	
	public Produto buscarPorId(String idProduto) throws RegistroNaoEncontradoException {
		
		for(Produto produto_cadastrado: produtoRepository.listarProdutos()) {
			if(produto_cadastrado.getId().equals(idProduto)) {
				return produto_cadastrado;
			}
		}
		
		throw new RegistroNaoEncontradoException("Produto com ID: " + idProduto + " não está cadastrado.");
		
	}
	
	public List<Produto> listarProdutos() {
		// poderiamos pensar em insewrir uma exceção para ver a quantidade de itens cadastrados, se não tiver nenhum, ele retorna um exception
		return produtoRepository.listarProdutos();
	}
	
	
	public void desativarProduto(String idProduto) throws RegistroNaoEncontradoException, ProdutoIndisponivelException {
		// primeiro buscar na base de dados
		// dps verificar o status
		// se o status tiver ativado, desativa
		
		Produto produto = buscarPorId(idProduto);
		
		if(produto.isDisponivel()) {
			produto.desativar(); // mesmo se tiver em estoque, não permitir vender
			produtoRepository.salvarAlteracoes();
		}
		
		else {
			throw new ProdutoIndisponivelException("O produto ID: " + idProduto + " já está desativado!");
		}
		
	}
	
	public void removerProduto(String idProduto) {
		produtoRepository.removerProduto(idProduto); // atenção aqui, tem que ver depois se é necessário inserir exception para validar antes se existe
	}
	
	
	public void adicionarEstoque(String idProduto, int quantidade) throws RegistroNaoEncontradoException, DadoEntradaException {
		if(quantidade <= 0) {
			throw new DadoEntradaException("Quantidade de estoque a adicionar deve ser maior que zero (recebida: " + quantidade + ").");
		}
		
		Produto produto = buscarPorId(idProduto);
		produto.adicionarEstoque(quantidade);
		produtoRepository.salvarAlteracoes();
	}
}
