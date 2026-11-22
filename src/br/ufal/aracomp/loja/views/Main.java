package br.ufal.aracomp.loja.views;
import br.ufal.aracomp.loja.models.*;
import br.ufal.aracomp.loja.repository.*;
import br.ufal.aracomp.loja.service.*;
import br.ufal.aracomp.loja.controller.*;
import java.util.*;
import br.ufal.aracomp.loja.views.*;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		UsuarioRepository usuarioRepository = new UsuarioRepository();
		ProdutoRepository produtoRepository = new ProdutoRepository();
		ServicoTecnicoRepository servicoTecnicoRepository = new ServicoTecnicoRepository();
		
		UsuarioService usuarioService = new UsuarioService(usuarioRepository);
		ProdutoService produtoService = new ProdutoService(produtoRepository);
		ServicoTecnicoService servicoTecnicoService = new ServicoTecnicoService(servicoTecnicoRepository);
		
		UsuarioController usuarioController = new UsuarioController(usuarioService);
		ProdutoController produtoController = new ProdutoController(produtoService);
		ServicoTecnicoController servicoTecnicoController = new ServicoTecnicoController(servicoTecnicoService);
		
		UsuarioView usuarioView = new UsuarioView(usuarioController, scanner);
		ProdutoView produtoView = new ProdutoView(produtoController, scanner);
		ServicoTecnicoView servicoTecnicoView = new ServicoTecnicoView( servicoTecnicoController, scanner);
		
		int opcao = -1;
		while(opcao != 0) {
			System.out.println("\n=== LOJA DE INFORMÁTICA ===");
            System.out.println("1 - Gerenciar Usuários");
            System.out.println("2 - Gerenciar Produtos");
            System.out.println("3 - Gerenciar Serviços Técnicos");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida!");
                continue;
            }
            
            switch(opcao) {
            
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
            }
		}
		

		scanner.close();
		
	}
}