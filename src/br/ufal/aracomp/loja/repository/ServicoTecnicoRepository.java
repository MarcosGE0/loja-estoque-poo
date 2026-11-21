package br.ufal.aracomp.loja.repository;

import br.ufal.aracomp.loja.models.*;
import java.util.*;

public class ServicoTecnicoRepository {
    private List<ServicoTecnico> servicoTecnico;

    public ServicoTecnicoRepository() {
        servicoTecnico = new ArrayList<>();
    }

    public void adicionarServicoTecnico(ServicoTecnico servico) {
        servicoTecnico.add(servico);
    }

    public void removerServicoTecnico(String idServicoTecnico) {
        for (int i = 0; i < servicoTecnico.size(); i++) {
            if (servicoTecnico.get(i).getId().equals(idServicoTecnico)) {
                servicoTecnico.remove(i);
                return;
            }
        }
    }

    public List<ServicoTecnico> listarServicoTecnico() {
        return new ArrayList<>(servicoTecnico);
    }
}
