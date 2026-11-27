package br.ufal.aracomp.loja.repository;

import br.ufal.aracomp.loja.models.ServicoTecnico;
import java.util.ArrayList;
import java.util.List;

public class ServicoTecnicoRepository extends ArquivoRepository<ServicoTecnico> {
    private final List<ServicoTecnico> servicoTecnico;

    public ServicoTecnicoRepository() {
        this("dados/servicos.dat");
    }

    public ServicoTecnicoRepository(String arquivo) {
        super(arquivo);
        servicoTecnico = carregar();
    }

    public void adicionarServicoTecnico(ServicoTecnico servico) {
        servicoTecnico.add(servico);
        salvar(servicoTecnico);
    }

    public void salvarAlteracoes() {
        salvar(servicoTecnico);
    }

    public void removerServicoTecnico(String idServicoTecnico) {
        for (int i = 0; i < servicoTecnico.size(); i++) {
            if (servicoTecnico.get(i).getId().equals(idServicoTecnico)) {
                servicoTecnico.remove(i);
                salvar(servicoTecnico);
                return;
            }
        }
    }

    public List<ServicoTecnico> listarServicoTecnico() {
        return new ArrayList<>(servicoTecnico);
    }
}
