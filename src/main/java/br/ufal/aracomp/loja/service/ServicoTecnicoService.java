package br.ufal.aracomp.loja.service;

import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.models.ServicoTecnico;
import br.ufal.aracomp.loja.repository.ServicoTecnicoRepository;
import br.ufal.aracomp.loja.exception.*;


import java.util.List;

public class ServicoTecnicoService {
    private ServicoTecnicoRepository servicoTecnicoRepository;

    public ServicoTecnicoService(ServicoTecnicoRepository servicoTecnicoRepository) {
        this.servicoTecnicoRepository = servicoTecnicoRepository;
    }

    public ServicoTecnicoRepository getServicoTecnicoRepository() {
        return servicoTecnicoRepository;
    }

    public void cadastrarServicoTecnico(ServicoTecnico novoServico) throws RegistroCadastradoException {
        for (ServicoTecnico servico : servicoTecnicoRepository.listarServicoTecnico()) {
            if (servico.getId().equals(novoServico.getId())) {
                throw new RegistroCadastradoException("ID de serviço técnico já utilizado: " + novoServico.getId() + ".");
            }
        }

        servicoTecnicoRepository.adicionarServicoTecnico(novoServico);
    }

    public void removerServicoTecnico(String idServicoTecnico) throws RegistroNaoEncontradoException {
        buscarPorIdServicoTecnico(idServicoTecnico);
        servicoTecnicoRepository.removerServicoTecnico(idServicoTecnico);
    }

    public void desativarServicoTecnico(String idServicoTecnico)
            throws DadoEntradaException, RegistroNaoEncontradoException, ServicoIndisponivelException {
        if (idServicoTecnico == null || idServicoTecnico.trim().isEmpty()) {
            throw new DadoEntradaException("ID do serviço técnico não pode ser vazio.");
        }

        ServicoTecnico servico = buscarPorIdServicoTecnico(idServicoTecnico);
        if (!servico.isDisponivel()) {
            throw new ServicoIndisponivelException(
                    "Serviço técnico com ID " + idServicoTecnico + " já está desativado.");
        }

        servico.desativar();
        servicoTecnicoRepository.salvarAlteracoes();
    }

    public ServicoTecnico buscarPorIdServicoTecnico(String idServicoTecnico) throws RegistroNaoEncontradoException {
        for (ServicoTecnico servico : servicoTecnicoRepository.listarServicoTecnico()) {
            if (servico.getId().equals(idServicoTecnico)) {
                return servico;
            }
        }
        throw new RegistroNaoEncontradoException("Serviço técnico com ID " + idServicoTecnico + " não encontrado.");
    }

    public List<ServicoTecnico> listarServicoTecnico() {
        return servicoTecnicoRepository.listarServicoTecnico();
    }
}
