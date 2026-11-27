package br.ufal.aracomp.loja.controller;
import br.ufal.aracomp.loja.models.*;
import br.ufal.aracomp.loja.service.ProdutoService;
import br.ufal.aracomp.loja.service.*;
import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.PersistenciaException;
import br.ufal.aracomp.loja.exception.ProdutoIndisponivelException;
import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.models.*;
import java.util.*;

public class ProdutoController {
	private ProdutoService produtoService;
	
	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}
	
	public void cadastrarPeriferico(String id, String nome, double preco, int qtdEstoque, String conexao) throws RegistroCadastradoException, DadoEntradaException, PersistenciaException {
		Periferico novo_periferico = new Periferico(id, nome, preco, qtdEstoque, conexao);
		produtoService.cadastrarProduto(novo_periferico);
	}
	
	public void cadastrarPecaHardware(String id, String nome, double preco, int qtdEstoque, String compatibilidade) throws RegistroCadastradoException, DadoEntradaException, PersistenciaException {
		PecaHardware novo_peca = new PecaHardware(id, nome, preco, qtdEstoque, compatibilidade);
		produtoService.cadastrarProduto(novo_peca);
	}
	
	public void cadastrarComputador(String id, String nome, double preco, int qtdEstoque, String processador, int memoriaRam, String sistemaOperacional) throws RegistroCadastradoException, DadoEntradaException, PersistenciaException {
		Computador novo_computador = new Computador(id, nome, preco, qtdEstoque, processador, memoriaRam, sistemaOperacional);
		produtoService.cadastrarProduto(novo_computador);
	}
	
	public void cadastrarSoftware(String id, String nome, double preco, int qtdEstoque, String chaveAtivacao, String plataforma) throws RegistroCadastradoException, DadoEntradaException, PersistenciaException {
		Software novo_software = new Software(id, nome, preco, qtdEstoque, chaveAtivacao, plataforma);
		produtoService.cadastrarProduto(novo_software);
	}
	
	public Produto buscarPorId(String idProduto) throws RegistroNaoEncontradoException {
		return produtoService.buscarPorId(idProduto);
	}
	
	public List<Produto> listarProdutos() {
		return produtoService.listarProdutos();
	}

	public void alterarPreco(String idProduto, double novoPreco)
			throws RegistroNaoEncontradoException, DadoEntradaException, PersistenciaException {
		produtoService.alterarPreco(idProduto, novoPreco);
	}
	
	public void desativarProduto(String idProduto) throws ProdutoIndisponivelException, RegistroNaoEncontradoException, PersistenciaException {
		produtoService.desativarProduto(idProduto);
	}
	
	public void removerProduto(String idProduto) throws PersistenciaException {
		produtoService.removerProduto(idProduto);
	}
	
	public void adicionarEstoque(String idProduto, int quantidade) throws RegistroNaoEncontradoException, DadoEntradaException, PersistenciaException {
		produtoService.adicionarEstoque(idProduto, quantidade);
	}
	
}
