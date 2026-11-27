package br.ufal.aracomp.loja.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.ServicoIndisponivelException;
import br.ufal.aracomp.loja.models.ServicoTecnico;
import br.ufal.aracomp.loja.repository.ServicoTecnicoRepository;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ServicoTecnicoServiceTest {
    @TempDir
    Path diretorioTemporario;

    private ServicoTecnicoRepository repository;
    private ServicoTecnicoService service;

    @BeforeEach
    public void configurar() {
        repository = new ServicoTecnicoRepository(
                diretorioTemporario.resolve("servicos.dat").toString());
        service = new ServicoTecnicoService(repository);
    }

    @Test
    public void desativaServicoERejeitaDesativacaoRepetida() throws Exception {
        service.cadastrarServicoTecnico(new ServicoTecnico("s1", "Manutenção", 50.0, 2, "2026-11-26"));

        service.desativarServicoTecnico("s1");

        assertFalse(service.buscarPorIdServicoTecnico("s1").isDisponivel());
        assertThrows(ServicoIndisponivelException.class,
                () -> service.desativarServicoTecnico("s1"));
    }

    @Test
    public void rejeitaIdVazioAoDesativarServico() {
        assertThrows(DadoEntradaException.class, () -> service.desativarServicoTecnico(" "));
    }
}
