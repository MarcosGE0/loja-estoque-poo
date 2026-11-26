package br.ufal.aracomp.loja.views;

import br.ufal.aracomp.loja.controller.PedidoController;
import br.ufal.aracomp.loja.controller.UsuarioController;
import br.ufal.aracomp.loja.exception.DominioException;
import br.ufal.aracomp.loja.models.Cliente;
import br.ufal.aracomp.loja.models.Pedido;
import br.ufal.aracomp.loja.models.Usuario;
import java.util.Scanner;

public class PedidoView {
	private PedidoController pedidoController;
	private UsuarioController usuarioController;
	private Scanner scanner;

	// Recebe os controllers e o scanner usados pelo menu de pedidos.
	public PedidoView(PedidoController pedidoController, UsuarioController usuarioController, Scanner scanner) {
		this.pedidoController = pedidoController;
		this.usuarioController = usuarioController;
		this.scanner = scanner;
	}

	// Exibe o menu principal das operações de pedido.
	public void exibirMenu() {
		int opcao = -1;
		while (opcao != 0) {
			System.out.println("\n=== MENU DE PEDIDOS ===");
			System.out.println("1 - Cadastrar Pedido");
			System.out.println("2 - Listar Pedidos");
			System.out.println("3 - Buscar Pedido por ID");
			System.out.println("4 - Adicionar Produto");
			System.out.println("5 - Adicionar Serviço Técnico");
			System.out.println("6 - Remover Item");
			System.out.println("7 - Finalizar Pedido");
			System.out.println("8 - Cancelar Pedido");
			System.out.println("9 - Remover Pedido");
			System.out.println("0 - Voltar ao Menu Principal");
			System.out.print("Selecione uma opção: ");

			try {
				opcao = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Digite apenas números!");
				continue;
			}

			if (opcao == 1) {
				telaCadastrarPedido();
			} else if (opcao == 2) {
				telaListarPedidos();
			} else if (opcao == 3) {
				telaBuscarPedido();
			} else if (opcao == 4) {
				telaAdicionarProduto();
			} else if (opcao == 5) {
				telaAdicionarServico();
			} else if (opcao == 6) {
				telaRemoverItem();
			} else if (opcao == 7) {
				telaFinalizarPedido();
			} else if (opcao == 8) {
				telaCancelarPedido();
			} else if (opcao == 9) {
				telaRemoverPedido();
			} else if (opcao != 0) {
				System.out.println("Opção inválida!");
			}
		}
	}

	// Solicita os dados e cadastra um novo pedido.
	private void telaCadastrarPedido() {
		System.out.println("\n--- Cadastro de Pedido ---");
		System.out.print("ID do pedido: ");
		String idPedido = scanner.nextLine();
		System.out.print("ID do cliente: ");
		String idCliente = scanner.nextLine();
		System.out.print("Data de criação: ");
		String dataCriacao = scanner.nextLine();

		try {
			Usuario usuario = usuarioController.buscarPorId(idCliente);
			if (!(usuario instanceof Cliente)) {
				System.out.println("Usuário não é um cliente.");
				return;
			}

			Pedido pedido = new Pedido(idPedido, (Cliente) usuario, dataCriacao);
			pedidoController.cadastrarPedido(pedido);
			System.out.println("Pedido cadastrado com sucesso!");
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Lista todos os pedidos cadastrados.
	private void telaListarPedidos() {
		System.out.println("\n--- Pedidos Cadastrados ---");
		if (pedidoController.listarPedidos().isEmpty()) {
			System.out.println("Nenhum pedido cadastrado.");
			return;
		}

		for (Pedido pedido : pedidoController.listarPedidos()) {
			System.out.println(pedido);
		}
	}

	// Busca e exibe um pedido pelo ID.
	private void telaBuscarPedido() {
		System.out.print("ID do pedido: ");
		String idPedido = scanner.nextLine();

		try {
			System.out.println(pedidoController.buscarPorId(idPedido));
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Solicita os dados e adiciona um produto ao pedido.
	private void telaAdicionarProduto() {
		System.out.print("ID do pedido: ");
		String idPedido = scanner.nextLine();
		System.out.print("ID do produto: ");
		String idProduto = scanner.nextLine();
		System.out.print("Quantidade: ");

		try {
			int quantidade = Integer.parseInt(scanner.nextLine());
			pedidoController.adicionarProdutoPedido(idPedido, idProduto, quantidade);
			System.out.println("Produto adicionado ao pedido!");
		} catch (NumberFormatException e) {
			System.out.println("Quantidade inválida!");
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Solicita os dados e adiciona um serviço técnico ao pedido.
	private void telaAdicionarServico() {
		System.out.print("ID do pedido: ");
		String idPedido = scanner.nextLine();
		System.out.print("ID do serviço técnico: ");
		String idServico = scanner.nextLine();
		System.out.print("Quantidade: ");

		try {
			int quantidade = Integer.parseInt(scanner.nextLine());
			pedidoController.adicionarServicoPedido(idPedido, idServico, quantidade);
			System.out.println("Serviço técnico adicionado ao pedido!");
		} catch (NumberFormatException e) {
			System.out.println("Quantidade inválida!");
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Solicita os dados e remove uma quantidade de um item.
	private void telaRemoverItem() {
		System.out.print("ID do pedido: ");
		String idPedido = scanner.nextLine();
		System.out.print("ID do item: ");
		String idItem = scanner.nextLine();
		System.out.print("Quantidade: ");

		try {
			int quantidade = Integer.parseInt(scanner.nextLine());
			pedidoController.removerItemPedido(idPedido, idItem, quantidade);
			System.out.println("Item removido do pedido!");
		} catch (NumberFormatException e) {
			System.out.println("Quantidade inválida!");
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Solicita o ID e finaliza um pedido.
	private void telaFinalizarPedido() {
		System.out.print("ID do pedido: ");
		String idPedido = scanner.nextLine();

		try {
			pedidoController.finalizarPedido(idPedido);
			System.out.println("Pedido finalizado com sucesso!");
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Solicita o ID e cancela um pedido.
	private void telaCancelarPedido() {
		System.out.print("ID do pedido: ");
		String idPedido = scanner.nextLine();

		try {
			pedidoController.cancelarPedido(idPedido);
			System.out.println("Pedido cancelado com sucesso!");
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Solicita o ID e remove um pedido.
	private void telaRemoverPedido() {
		System.out.print("ID do pedido: ");
		String idPedido = scanner.nextLine();

		try {
			pedidoController.removerPedido(idPedido);
			System.out.println("Pedido removido com sucesso!");
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
