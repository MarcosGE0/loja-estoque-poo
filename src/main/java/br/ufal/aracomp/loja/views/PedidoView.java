package br.ufal.aracomp.loja.views;

import br.ufal.aracomp.loja.controller.PedidoController;
import br.ufal.aracomp.loja.controller.UsuarioController;
import br.ufal.aracomp.loja.exception.DominioException;
import br.ufal.aracomp.loja.models.Cliente;
import br.ufal.aracomp.loja.models.Pedido;
import br.ufal.aracomp.loja.models.Usuario;
import br.ufal.aracomp.loja.models.DescontoPromocional;
import br.ufal.aracomp.loja.models.DescontoPorQuantidade;
import br.ufal.aracomp.loja.models.SemDesconto;
import java.util.Scanner;

public class PedidoView {
	private PedidoController pedidoController;
	private UsuarioController usuarioController;
	private Scanner scanner;

	public PedidoView(PedidoController pedidoController, UsuarioController usuarioController, Scanner scanner) {
		this.pedidoController = pedidoController;
		this.usuarioController = usuarioController;
		this.scanner = scanner;
	}

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
			System.out.println("10 - Aplicar Desconto");
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
			} else if (opcao == 10) {
				telaAplicarDesconto();
			} else if (opcao != 0) {
				System.out.println("Opção inválida!");
			}
		}
	}

	private void telaAplicarDesconto() {
		System.out.print("ID do pedido: ");
		String idPedido = scanner.nextLine();
		System.out.println("1 - Sem desconto");
		System.out.println("2 - Desconto promocional");
		System.out.println("3 - Desconto por quantidade");
		System.out.print("Tipo de desconto: ");

		try {
			int tipo = Integer.parseInt(scanner.nextLine());

			if (tipo == 1) {
				pedidoController.aplicarDesconto(idPedido, new SemDesconto());
			} else if (tipo == 2) {
				System.out.print("Percentual do desconto: ");
				double percentual = Double.parseDouble(scanner.nextLine());
				pedidoController.aplicarDesconto(idPedido, new DescontoPromocional(percentual));
			} else if (tipo == 3) {
				System.out.print("Quantidade mínima: ");
				int quantidadeMinima = Integer.parseInt(scanner.nextLine());
				System.out.print("Percentual do desconto: ");
				double percentual = Double.parseDouble(scanner.nextLine());
				pedidoController.aplicarDesconto(idPedido,
						new DescontoPorQuantidade(quantidadeMinima, percentual));
			} else {
				System.out.println("Tipo de desconto inválido.");
				return;
			}

			Pedido pedido = pedidoController.buscarPorId(idPedido);
			System.out.println("Desconto aplicado!");
			System.out.println("Tipo: " + pedido.getNomeDesconto());
			System.out.println("Detalhe: " + pedido.getDescricaoDesconto());
			System.out.println("Valor final: R$ " + pedido.getValorFinal());
		} catch (NumberFormatException e) {
			System.out.println("Valor numérico inválido!");
		} catch (br.ufal.aracomp.loja.exception.DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

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

	private void telaBuscarPedido() {
		System.out.print("ID do pedido: ");
		String idPedido = scanner.nextLine();

		try {
			System.out.println(pedidoController.buscarPorId(idPedido));
		} catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

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
