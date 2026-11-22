package br.ufal.aracomp.loja.views;

import br.ufal.aracomp.loja.controller.*;
import br.ufal.aracomp.loja.repository.*;
import br.ufal.aracomp.loja.service.*;
import java.util.Scanner;

public class MainView {
	private final Scanner scanner;
	private final UsuarioView usuarioView;
	private final ProdutoView produtoView;
	private final ServicoTecnicoView servicoTecnicoView;
	private final PedidoView pedidoView;

	public MainView() {
		scanner = new Scanner(System.in);

		UsuarioRepository usuarioRepository = new UsuarioRepository();
		ProdutoRepository produtoRepository = new ProdutoRepository();
		ServicoTecnicoRepository servicoTecnicoRepository = new ServicoTecnicoRepository();
		PedidoRepository pedidoRepository = new PedidoRepository();

		UsuarioService usuarioService = new UsuarioService(usuarioRepository);
		ProdutoService produtoService = new ProdutoService(produtoRepository);
		ServicoTecnicoService servicoTecnicoService = new ServicoTecnicoService(servicoTecnicoRepository);
		PedidoService pedidoService = new PedidoService(pedidoRepository, usuarioRepository,
				produtoRepository, servicoTecnicoService);

		UsuarioController usuarioController = new UsuarioController(usuarioService);
		ProdutoController produtoController = new ProdutoController(produtoService);
		ServicoTecnicoController servicoTecnicoController = new ServicoTecnicoController(servicoTecnicoService);
		PedidoController pedidoController = new PedidoController(pedidoService);

		usuarioView = new UsuarioView(usuarioController, scanner);
		produtoView = new ProdutoView(produtoController, scanner);
		servicoTecnicoView = new ServicoTecnicoView(servicoTecnicoController, scanner);
		pedidoView = new PedidoView(pedidoController, usuarioController, scanner);
	}

	public void exibirMenuPrincipal() {
		int opcao = -1;
		while (opcao != 0) {
			System.out.println("\n=== LOJA DE INFORMÁTICA ===");
			System.out.println("1 - Gerenciar Usuários");
			System.out.println("2 - Gerenciar Produtos");
			System.out.println("3 - Gerenciar Serviços Técnicos");
			System.out.println("4 - Gerenciar Pedidos");
			System.out.println("0 - Sair");
			System.out.print("Opção: ");

			try {
				opcao = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Opção inválida!");
				continue;
			}

			switch (opcao) {
				case 0:
					System.out.println("Encerrando sistema...");
					break;
				case 1:
					usuarioView.exibirMenu();
					break;
				case 2:
					produtoView.exibirMenu();
					break;
				case 3:
					servicoTecnicoView.exibirMenu();
					break;
				case 4:
					pedidoView.exibirMenu();
					break;
				default:
					System.out.println("Opção inválida!");
			}
		}

		scanner.close();
	}
}
