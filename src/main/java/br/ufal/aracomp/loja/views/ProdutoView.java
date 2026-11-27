package br.ufal.aracomp.loja.views;
import br.ufal.aracomp.loja.controller.ProdutoController;
import br.ufal.aracomp.loja.controller.*;
import br.ufal.aracomp.loja.exception.DominioException;
import br.ufal.aracomp.loja.models.Produto;

import java.util.Scanner;

public class ProdutoView {
	private ProdutoController produtoController;
	private Scanner scanner;
	
	public ProdutoView(ProdutoController produtoController, Scanner scanner) {
		this.produtoController = produtoController;
		this.scanner = scanner;
	}
	
	public void exibirMenu() {
		int opcao = -1;
		while(opcao != 0) {
			System.out.println("\n=== MENU DE PRODUTOS===");
			System.out.println("1 - Cadastrar Periférico");
			System.out.println("2 - Cadastrar Peça Hardware");
			System.out.println("3 - Cadastrar Computador");
			System.out.println("4 - Cadastrar Software");
			System.out.println("5 - Listar Produtos Cadastrados");
			System.out.println("6 - Buscar Produto por ID");
			System.out.println("7 - Desativar Produto por ID");
			System.out.println("8 - Remover Produto da B.D por ID");
			System.out.println("9 - Adicionar Estoque do Produto por ID");
			System.out.println("10 - Alterar Preço do Produto");
			System.out.println("0 - Voltar ao Menu Principal");
			
			System.out.println("Selecione uma opção: ");
			
			try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números!");
                continue;
            }
			
			
			switch(opcao) {
				case 0:
					break;
					
				case 1:
					telaCadastrarPeriferico();
					break;
					
				case 2:
					telaCadastrarPecaHardware();
					break;
					
				case 3:
					telaCadastrarComputador();
					break;
					
				case 4:
					telaCadastrarSoftware();
					break;
					
				case 5:
					telaListarProdutos();
					break;
					
				case 6:
					telaBuscarProduto();
					break;
					
				case 7:
					telaDesativarProduto();
					break;
				
				case 8:
					telaRemoverProduto();
					break;
					
				case 9:
					telaAdicionarEstoque();
					break;

				case 10:
					telaAlterarPreco();
					break;
			}
		}
	}
	
	private void telaCadastrarPeriferico() {
		System.out.println("\n--- Cadastro de Periférico ---");
		System.out.print("ID: ");
		String id = scanner.nextLine();
		
		System.out.print("Nome: ");
		String nome = scanner.nextLine();
		
		System.out.print("Preço R$: ");
		double preco = scanner.nextDouble();
		
		System.out.print("Quantidade Estoque (uni): ");
		int qtd_estoque = scanner.nextInt();
		scanner.nextLine();
		
		System.out.print("Conexão: ");
		String conexao = scanner.nextLine();
		
		try {
			produtoController.cadastrarPeriferico(id, nome, preco, qtd_estoque, conexao);
			System.out.println("Periférico Cadastrado com Sucesso!");
		}
		catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	private void telaCadastrarPecaHardware() {
		System.out.println("\n--- Cadastro de Peça Hardware ---");
		System.out.print("ID: ");
		String id = scanner.nextLine();
		
		System.out.print("Nome: ");
		String nome = scanner.nextLine();
		
		System.out.print("Preço R$: ");
		double preco = scanner.nextDouble();
		
		System.out.print("Quantidade Estoque (uni): ");
		int qtd_estoque = scanner.nextInt();
		scanner.nextLine(); 
		
		System.out.print("Compatibilidade: ");
		String compatibilidade = scanner.nextLine();
		
		try {
			produtoController.cadastrarPecaHardware(id, nome, preco, qtd_estoque, compatibilidade);
			System.out.println("Peça de Hardware Cadastrado com Sucesso!");
		}
		catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	private void telaCadastrarComputador() {
		System.out.println("\n--- Cadastro de Computador ---");
		System.out.print("ID: ");
		String id = scanner.nextLine();
		
		System.out.print("Nome: ");
		String nome = scanner.nextLine();
		
		System.out.print("Preço R$: ");
		double preco = scanner.nextDouble();
		
		System.out.print("Quantidade Estoque (uni): ");
		int qtd_estoque = scanner.nextInt();
		scanner.nextLine(); 
		
		System.out.print("Processador: ");
		String processador = scanner.nextLine();
		
		System.out.print("Memória RAM (GB): ");
		int memoriaRam = scanner.nextInt();
		scanner.nextLine(); 
		
		System.out.print("Sistema Operacional: ");
		String sistemaOperacional = scanner.nextLine();
		
		try {
			produtoController.cadastrarComputador(id, nome, preco, qtd_estoque, processador, memoriaRam, sistemaOperacional);
			System.out.println("Computador Cadastrado com Sucesso!");
		}
		catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	private void telaCadastrarSoftware() {
		System.out.println("\n--- Cadastro de Software ---");
		System.out.print("ID: ");
		String id = scanner.nextLine();
		
		System.out.print("Nome: ");
		String nome = scanner.nextLine();
		
		System.out.print("Preço R$: ");
		double preco = scanner.nextDouble();
		
		System.out.print("Quantidade Estoque (uni): ");
		int qtd_estoque = scanner.nextInt();
		scanner.nextLine(); 
		
		System.out.print("Chave Ativação: ");
		String chave = scanner.nextLine();
		
		System.out.print("Plataforma: ");
		String plataforma = scanner.nextLine();
		
		try {
			produtoController.cadastrarSoftware(id, nome, preco, qtd_estoque, chave, plataforma);
			System.out.println("Software Cadastrado com Sucesso!");
		}
		catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	private void telaListarProdutos() {
		System.out.println("\n--- Catálogo de Produtos da Loja Cadastrados ---");
		System.out.println("ID | NOME | PREÇO (R$) | EM ESTOQUE (Uni) | DISPONÍVEL | OBSERVAÇÕES");
		
		for(Produto produto_cadastrado: produtoController.listarProdutos()) {
			System.out.println(produto_cadastrado.toString());
		}
	}
	
	private void telaBuscarProduto() {
		System.out.println("\n--- Buscar Produto por ID ---");
		System.out.println("ID | NOME | PREÇO (R$) | EM ESTOQUE (Uni) | DISPONÍVEL | OBSERVAÇÕES");
		
		System.out.print("ID do Produto: ");
		String id = scanner.nextLine();
		
		try {
			Produto produto = produtoController.buscarPorId(id);
			System.out.println(produto.toString());
			
		}
	
		catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	private void telaDesativarProduto() {
		System.out.println("\n--- Desativar Produto por ID ---");
		
		System.out.print("ID do Produto: ");
		String id = scanner.nextLine();
		
		
		try {
			produtoController.desativarProduto(id);
		}
	
		catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	private void telaRemoverProduto() {
		System.out.println("\n--- Remover Produto por ID ---");
		
		System.out.print("ID do Produto: ");
		String id = scanner.nextLine();
		
		
		try {
			produtoController.removerProduto(id);
			System.out.println("Produto removido com êxito!");
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	private void telaAdicionarEstoque() {
		System.out.println("\n--- Adicionar Estoque de Produto por ID ---");
		
		System.out.print("ID do Produto: ");
		String id = scanner.nextLine();
		
		try {
			Produto produto = produtoController.buscarPorId(id);
			
			System.out.print("Quantidade Estoque (uni): ");
			int qtd_estoque = scanner.nextInt();
			
			produtoController.adicionarEstoque(id, qtd_estoque);
			
			System.out.println("Estoque do produto " + produto.getNome() + " atualizado com sucesso! (uni): " + produto.getQtdEstoque());
			
		}
		catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
	}

	private void telaAlterarPreco() {
		System.out.println("\n--- Alterar Preço do Produto ---");
		System.out.print("ID do Produto: ");
		String id = scanner.nextLine();
		System.out.print("Novo preço R$: ");

		try {
			double novoPreco = Double.parseDouble(scanner.nextLine());
			produtoController.alterarPreco(id, novoPreco);
			System.out.println("Preço do produto atualizado com sucesso!");
		} catch (NumberFormatException e) {
			System.out.println("O preço deve ser um número válido.");
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
}
