package br.ufal.aracomp.loja.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.models.PessoaFisica;
import br.ufal.aracomp.loja.models.PessoaJuridica;
import br.ufal.aracomp.loja.repository.UsuarioRepository;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class UsuarioServiceTest {
    @TempDir
    Path diretorioTemporario;

    private UsuarioRepository repository;
    private UsuarioService service;

    @BeforeEach
    public void configurar() {
        repository = new UsuarioRepository(diretorioTemporario.resolve("usuarios.dat").toString());
        service = new UsuarioService(repository);
    }

    @Test
    public void cadastraUsuarioERejeitaEmailDuplicado() throws Exception {
        service.cadastrarUsuario(pessoa("u1", "ana@email.com", "CPF 123"));

        assertThrows(RegistroCadastradoException.class,
                () -> service.cadastrarUsuario(pessoa("u2", "ana@email.com", "CPF 456")));
        assertEquals(1, service.listarUsuarios().size());
    }

    @Test
    public void rejeitaCpfDuplicadoMesmoComFormatacaoDiferente() throws Exception {
        service.cadastrarUsuario(pessoa("u1", "ana@email.com", "123.456-7"));

        assertThrows(RegistroCadastradoException.class,
                () -> service.cadastrarUsuario(pessoa("u2", "bia@email.com", "1234567")));
    }

    @Test
    public void rejeitaCnpjDuplicadoMesmoComFormatacaoDiferente() throws Exception {
        service.cadastrarUsuario(new PessoaJuridica(
                "u1", "Loja A", "a@loja.com", "Rua A", "12.345/ABC"));

        assertThrows(RegistroCadastradoException.class,
                () -> service.cadastrarUsuario(new PessoaJuridica(
                        "u2", "Loja B", "b@loja.com", "Rua B", "12345ABC")));
    }

    @Test
    public void desativaUsuarioEPersisteAlteracao() throws Exception {
        service.cadastrarUsuario(pessoa("u1", "ana@email.com", "CPF 123"));

        service.desativarUsuario("u1");

        assertFalse(service.buscarPorId("u1").isAtivo());
        UsuarioRepository repositoryReaberto =
                new UsuarioRepository(diretorioTemporario.resolve("usuarios.dat").toString());
        assertFalse(repositoryReaberto.listarUsuarios().get(0).isAtivo());
    }

    @Test
    public void removerUsuarioInexistenteRetornaErro() {
        assertThrows(RegistroNaoEncontradoException.class, () -> service.removerUsuario("ausente"));
    }

    private PessoaFisica pessoa(String id, String email, String cpf) throws Exception {
        return new PessoaFisica(id, "Cliente", email, "Rua A", cpf);
    }
}
