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
		
		UsuarioService usuarioService = new UsuarioService(usuarioRepository);
		
		UsuarioController usuarioController = new UsuarioController(usuarioService);
		
		UsuarioView usuarioView = new UsuarioView(usuarioController, scanner);
		
		int opcao = -1;
		while(opcao != 0) {
			System.out.println("\n=== LOJA DE INFORMÁTICA ===");
            System.out.println("1 - Gerenciar Usuários");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida!");
                continue;
            }
            
    		if(opcao == 1) {
    			usuarioView.exibirMenu();
    		}
    		else if (opcao == 0) {
    			System.out.println("Encerrando sistema...");
    		}
    		
		}
		

		scanner.close();
		
	}
}

// PAREI AQUI POIS ESTOU VENDO A UTILIDADE DO SERVICE ANTES DE CONTINUAR....
