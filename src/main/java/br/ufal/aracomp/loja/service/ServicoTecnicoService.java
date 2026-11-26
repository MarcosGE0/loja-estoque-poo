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

    public void cadastrarServicoTecnico(ServicoTecnico novo_servico) throws RegistroCadastradoException {
        for (ServicoTecnico servico : servicoTecnicoRepository.listarServicoTecnico()) {
            if (servico.getId().equals(novo_servico.getId())) {
                throw new RegistroCadastradoException("ID já utilizado!");
            }
        }

        servicoTecnicoRepository.adicionarServicoTecnico(novo_servico);
    }

    public void removerServicoTecnico(String idServicoTecnico) throws RegistroNaoEncontradoException {
        buscarPorIdServicoTecnico(idServicoTecnico);
        servicoTecnicoRepository.removerServicoTecnico(idServicoTecnico);
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
