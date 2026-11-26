package br.ufal.aracomp.loja;

import br.ufal.aracomp.loja.controller.PedidoController;
import br.ufal.aracomp.loja.controller.ProdutoController;
import br.ufal.aracomp.loja.controller.ServicoTecnicoController;
import br.ufal.aracomp.loja.controller.UsuarioController;
import br.ufal.aracomp.loja.repository.PedidoRepository;
import br.ufal.aracomp.loja.repository.ProdutoRepository;
import br.ufal.aracomp.loja.repository.ServicoTecnicoRepository;
import br.ufal.aracomp.loja.repository.UsuarioRepository;
import br.ufal.aracomp.loja.service.PedidoService;
import br.ufal.aracomp.loja.service.ProdutoService;
import br.ufal.aracomp.loja.service.ServicoTecnicoService;
import br.ufal.aracomp.loja.service.UsuarioService;
import br.ufal.aracomp.loja.views.MainView;

public class Main {
	public static void main(String[] args) {
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

		MainView mainView = new MainView(usuarioController, produtoController,
				servicoTecnicoController, pedidoController);
		mainView.exibirMenuPrincipal();
	}
}
