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
	
	public void cadastrarProduto(Produto novoProduto) throws RegistroCadastradoException, DadoEntradaException, PersistenciaException {
		if (novoProduto == null) {
			throw new DadoEntradaException("Produto não pode ser nulo.");
		}

		for(Produto produto_cadastrado: produtoRepository.listarProdutos()) {
			if(produto_cadastrado.getId().equals(novoProduto.getId())) {
				throw new RegistroCadastradoException("Produto ID:" + novoProduto.getId() + " já cadastrado!");
			}
		}

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
		return produtoRepository.listarProdutos();
	}

	public void alterarPreco(String idProduto, double novoPreco)
			throws RegistroNaoEncontradoException, DadoEntradaException, PersistenciaException {
		Produto produto = buscarPorId(idProduto);
		produto.alterarPreco(novoPreco);
		produtoRepository.salvarAlteracoes();
	}
	
	
	public void desativarProduto(String idProduto) throws RegistroNaoEncontradoException, ProdutoIndisponivelException, PersistenciaException {
		
		Produto produto = buscarPorId(idProduto);
		
		if(produto.isDisponivel()) {
			produto.desativar();
			produtoRepository.salvarAlteracoes();
		}
		
		else {
			throw new ProdutoIndisponivelException("O produto ID: " + idProduto + " já está desativado!");
		}
		
	}
	
	public void removerProduto(String idProduto) throws PersistenciaException {
		produtoRepository.removerProduto(idProduto);
	}
	
	
	public void adicionarEstoque(String idProduto, int quantidade) throws RegistroNaoEncontradoException, DadoEntradaException, PersistenciaException {
		Produto produto = buscarPorId(idProduto);
		produto.adicionarEstoque(quantidade);
		produtoRepository.salvarAlteracoes();
	}
}
