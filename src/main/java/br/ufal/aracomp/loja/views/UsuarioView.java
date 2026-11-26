package br.ufal.aracomp.loja.views;
import br.ufal.aracomp.loja.controller.UsuarioController;
import br.ufal.aracomp.loja.exception.DominioException;
import br.ufal.aracomp.loja.models.Usuario;
import br.ufal.aracomp.loja.controller.*;
import br.ufal.aracomp.loja.exception.*;
import br.ufal.aracomp.loja.models.*;
import java.util.Scanner;

public class UsuarioView {
	private UsuarioController usuarioController;
	private Scanner scanner;
	
	
	public UsuarioView(UsuarioController usuarioController, Scanner scanner) {
		this.usuarioController = usuarioController;
		this.scanner = scanner;
	}
	
	public void exibirMenu() {
		int opcao = -1;
		while(opcao != 0) {
			System.out.println("\n=== MENU DE USUÁRIOS===");
			System.out.println("1 - Cadastrar Pessoa Física");
			System.out.println("2 - Cadastrar Pessoa Jurídica");
			System.out.println("3 - Cadastrar Administrador");
			System.out.println("4 - Listar Usuarios");
			System.out.println("5 - Buscar Por ID");
			System.out.println("0 - Voltar ao Menu Principal");
			
			System.out.println("Selecione uma opção: ");
			
			try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números!");
                continue;
            }
			
			if(opcao == 0) {
				break;
			}
			
			else if(opcao == 1) {
				telaCadastrarPessoaFisica();
			}
			
			else if(opcao == 2) {
				telaCadastrarPessoaJuridica();
			}
			
			else if(opcao == 3) {
				telaCadastrarAdministrador();
			}
			
			else if(opcao == 4) {
				telaListarUsuarios();
			}

			else if(opcao == 5) {
				telaBuscarPorId();
			}
		}
	}
	
	private void telaCadastrarPessoaFisica() {
		System.out.println("\n--- Cadastro de Pessoa Física ---");
		System.out.print("ID: ");
		String id = scanner.nextLine();
		
		System.out.print("Nome: ");
		String nome = scanner.nextLine();
		
		System.out.print("E-mail: ");
		String email = scanner.nextLine();
		
		System.out.print("Endereco: ");
		String endereco = scanner.nextLine();
		
		System.out.print("CPF: ");
		String cpf = scanner.nextLine();
		
		try {
			usuarioController.cadastrarPessoaFisica(id, nome, email, endereco, cpf);
			System.out.println("Pessoa Física cadastrada com sucesso!");
		} catch (DominioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
	}
	
	private void telaCadastrarPessoaJuridica() {
		System.out.println("\n--- Cadastro de Pessoa Jurídica ---");
		
		System.out.print("ID: ");
		String id = scanner.nextLine();
		
		System.out.print("Nome: ");
		String nome = scanner.nextLine();
		
		System.out.print("E-mail: ");
		String email = scanner.nextLine();
		
		System.out.print("Endereco: ");
		String endereco = scanner.nextLine();
		
		System.out.print("CNPJ: ");
		String cnpj = scanner.nextLine(); // possivelmente inserir tratamento do CNPJ
		
		try {
			usuarioController.cadastrarPessoaJuridica(id, nome, email, endereco, cnpj);
			System.out.println("Pessoa Jurídica cadastrada com sucesso!");
		}
		catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	private void telaCadastrarAdministrador() {
		System.out.println("\n--- Cadastro de Pessoa Administrador ---");
		
		System.out.print("ID: ");
		String id = scanner.nextLine();
		
		System.out.print("Nome: ");
		String nome = scanner.nextLine();
		
		System.out.print("E-mail: ");
		String email = scanner.nextLine();
		
		System.out.print("Endereco: ");
		String endereco = scanner.nextLine();
		
		System.out.print("Senha: ");
		String senha = scanner.nextLine(); 
		

		try {
			usuarioController.cadastrarAdministrador(id, nome, email, endereco, senha);
			System.out.println("Administrador cadastrado com sucesso!");
		}
		catch (DominioException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	
	private void telaListarUsuarios() {
		System.out.println("\n--- Usuários Cadastrados no Sistema ---");
		System.out.println("ID | NOME | EMAIL | CPF/CNPJ | ATIVO");
		for(Usuario usuario_cadastrado: usuarioController.listarUsuarios()) {
			System.out.println(usuario_cadastrado.toString());
		}
	}
	
	private void telaBuscarPorId() {
		System.out.println("\n--- Buscar por Usuário Cadastrado ---");
		System.out.print("ID: ");
		String id = scanner.nextLine();
		
		try {
			Usuario usuario_buscado = usuarioController.buscarPorId(id);
			System.out.printf(usuario_buscado.toString());
			
		} catch( DominioException e ) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
