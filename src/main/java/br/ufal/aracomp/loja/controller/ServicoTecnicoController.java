package br.ufal.aracomp.loja.controller;

import br.ufal.aracomp.loja.models.ServicoTecnico;
import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.ServicoIndisponivelException;
import br.ufal.aracomp.loja.service.ServicoTecnicoService;
import java.util.List;

public class ServicoTecnicoController {
    private ServicoTecnicoService servicoTecnicoService;

    public ServicoTecnicoController(ServicoTecnicoService servicoTecnicoService) {
        this.servicoTecnicoService = servicoTecnicoService;
    }

    public void cadastrarServicoTecnico (ServicoTecnico servico) throws RegistroCadastradoException {
        servicoTecnicoService.cadastrarServicoTecnico(servico);
    }

    public void removerServicoTecnico(String idServicoTecnico) throws RegistroNaoEncontradoException {
        servicoTecnicoService.removerServicoTecnico(idServicoTecnico);
    }

    public void desativarServicoTecnico(String idServicoTecnico)
            throws DadoEntradaException, RegistroNaoEncontradoException, ServicoIndisponivelException {
        servicoTecnicoService.desativarServicoTecnico(idServicoTecnico);
    }

    public ServicoTecnico buscarPorId(String idServicoTecnico) throws RegistroNaoEncontradoException {
        return servicoTecnicoService.buscarPorIdServicoTecnico(idServicoTecnico);
    }

    public List<ServicoTecnico> listarServicoTecnico() {
        return servicoTecnicoService.listarServicoTecnico();
    }
}
