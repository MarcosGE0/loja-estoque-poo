package br.ufal.aracomp.loja.views;

import br.ufal.aracomp.loja.controller.*;
import br.ufal.aracomp.loja.exception.*;
import br.ufal.aracomp.loja.models.*;
import java.util.List;
import java.util.Scanner;

public class ServicoTecnicoView {
    private ServicoTecnicoController servicoTecnicoController;
    private Scanner scanner;

    public ServicoTecnicoView(ServicoTecnicoController servicoTecnicoController, Scanner scanner) {
        this.servicoTecnicoController = servicoTecnicoController;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== MENU DE SERVIÇOS TÉCNICOS ===");
            System.out.println("1 - Cadastrar serviço técnico");
            System.out.println("2 - Listar serviços técnicos");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Remover serviço técnico");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Selecione uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números!");
                continue;
            }

            if (opcao == 1) {
                telaCadastrarServicoTecnico();
            } else if (opcao == 2) {
                telaListarServicosTecnicos();
            } else if (opcao == 3) {
                telaBuscarPorId();
            } else if (opcao == 4) {
                telaRemoverServicoTecnico();
            } else if (opcao != 0) {
                System.out.println("Opção inválida!");
            }
        }
    }

    private void telaCadastrarServicoTecnico() {
        System.out.println("\n--- Cadastro de Serviço Técnico ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preço por hora: ");
        double precoHora;
        try {
            precoHora = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("O preço deve ser um número válido.");
            return;
        }

        System.out.print("Tempo estimado em horas: ");
        double tempoHoras;
        try {
            tempoHoras = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("O tempo deve ser um número válido.");
            return;
        }

        System.out.print("Data de realização: ");
        String dataRealizacao = scanner.nextLine();

        try {
            ServicoTecnico servico = new ServicoTecnico(id, nome, precoHora, tempoHoras, dataRealizacao);
            servicoTecnicoController.cadastrarServicoTecnico(servico);
            System.out.println("Serviço técnico cadastrado com sucesso!");
        } catch (RegistroCadastradoException e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        } catch (DadoEntradaException e) {
            System.out.println("Erro nos dados do serviço: " + e.getMessage());
        }
    }

    private void telaListarServicosTecnicos() {
        System.out.println("\n--- Serviços Técnicos Cadastrados ---");
        List<ServicoTecnico> servicos = servicoTecnicoController.listarServicosTecnico();
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço técnico cadastrado.");
            return;
        }

        for (ServicoTecnico servico : servicos) {
            exibirServico(servico);
        }
    }

    private void telaBuscarPorId() {
        System.out.println("\n--- Buscar Serviço Técnico ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();

        try {
            exibirServico(servicoTecnicoController.buscarPorId(id));
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void telaRemoverServicoTecnico() {
        System.out.println("\n--- Remover Serviço Técnico ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();

        try {
            servicoTecnicoController.removerServicoTecnico(id);
            System.out.println("Serviço técnico removido com sucesso!");
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void exibirServico(ServicoTecnico servico) {
        System.out.println("ID: " + servico.getId());
        System.out.println("Nome: " + servico.getNome());
        System.out.println("Preço total: R$ " + servico.getPreco());
        System.out.println("Tempo estimado: " + servico.getTempoHorasServico() + " horas");
        System.out.println("Disponível: " + servico.isDisponivel());
        System.out.println();
    }
}
