package br.ufal.aracomp.loja.controller;
import br.ufal.aracomp.loja.service.*;
import br.ufal.aracomp.loja.exception.DadoEntradaException;
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
	
	public void cadastrarPeriferico(String id, String nome, double preco, int qtdEstoque, String conexao) throws RegistroCadastradoException, DadoEntradaException {
		Periferico novo_periferico = new Periferico(id, nome, preco, qtdEstoque, conexao);
		produtoService.cadastrarProduto(novo_periferico);
	}
	
	public void cadastrarPecaHardware(String id, String nome, double preco, int qtdEstoque, String compatibilidade) throws RegistroCadastradoException, DadoEntradaException {
		PecaHardware novo_peca = new PecaHardware(id, nome, preco, qtdEstoque, compatibilidade);
		produtoService.cadastrarProduto(novo_peca);
	}
	
	public void cadastrarComputador(String id, String nome, double preco, int qtdEstoque, String processador, int memoriaRam, String sistemaOperacional) throws RegistroCadastradoException, DadoEntradaException {
		Computador novo_computador = new Computador(id, nome, preco, qtdEstoque, processador, memoriaRam, sistemaOperacional);
		produtoService.cadastrarProduto(novo_computador);
	}
	
	public void cadastrarSoftware(String id, String nome, double preco, int qtdEstoque, String chaveAtivacao, String plataforma) throws RegistroCadastradoException, DadoEntradaException {
		Software novo_software = new Software(id, nome, preco, qtdEstoque, chaveAtivacao, plataforma);
		produtoService.cadastrarProduto(novo_software);
	}
	
	public Produto buscarPorId(String id_produto_buscado) throws RegistroNaoEncontradoException {
		return produtoService.buscarPorId(id_produto_buscado);
	}
	
	public List<Produto> listarProdutos() {
		return produtoService.listarProdutos();
	}
	
	public void desativarProduto(String id_produto_desativar) throws ProdutoIndisponivelException, RegistroNaoEncontradoException {
		produtoService.desativarProduto(id_produto_desativar);
	}
	
	public void removerProduto(String id_produto_remover) {
		produtoService.removerProduto(id_produto_remover);
	}
	
	public void adicionarEstoque(String id_produto, int qtd) throws RegistroNaoEncontradoException, DadoEntradaException {
		produtoService.adicionarEstoque(id_produto, qtd);
	}
	
}
