package br.ufal.aracomp.loja.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.models.Administrador;
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
    public void configurar() throws Exception {
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
    public void alteraDadosDoUsuarioEPersisteAlteracao() throws Exception {
        service.cadastrarUsuario(pessoa("u1", "ana@email.com", "CPF 123"));

        service.alterarDadosUsuario("u1", "ana.nova@email.com", "Rua B");

        PessoaFisica usuarioAtualizado = (PessoaFisica) service.buscarPorId("u1");
        assertEquals("ana.nova@email.com", usuarioAtualizado.getEmail());
        assertEquals("Rua B", usuarioAtualizado.getEndereco());

        UsuarioRepository repositoryReaberto =
                new UsuarioRepository(diretorioTemporario.resolve("usuarios.dat").toString());
        PessoaFisica usuarioPersistido = (PessoaFisica) repositoryReaberto.listarUsuarios().get(0);
        assertEquals("ana.nova@email.com", usuarioPersistido.getEmail());
        assertEquals("Rua B", usuarioPersistido.getEndereco());
    }

    @Test
    public void rejeitaEmailDuplicadoAoAlterarUsuario() throws Exception {
        service.cadastrarUsuario(pessoa("u1", "ana@email.com", "CPF 123"));
        service.cadastrarUsuario(pessoa("u2", "bia@email.com", "CPF 456"));

        assertThrows(RegistroCadastradoException.class,
                () -> service.alterarDadosUsuario("u2", "ana@email.com", "Rua B"));
        assertEquals("bia@email.com", service.buscarPorId("u2").getEmail());
    }

    @Test
    public void permiteManterProprioEmailAoAlterarEndereco() throws Exception {
        service.cadastrarUsuario(pessoa("u1", "ana@email.com", "CPF 123"));

        service.alterarDadosUsuario("u1", "ana@email.com", "Rua B");

        PessoaFisica usuario = (PessoaFisica) service.buscarPorId("u1");
        assertEquals("ana@email.com", usuario.getEmail());
        assertEquals("Rua B", usuario.getEndereco());
    }

    @Test
    public void rejeitaEnderecoVazioAoAlterarSemAtualizarParcialmente() throws Exception {
        service.cadastrarUsuario(pessoa("u1", "ana@email.com", "CPF 123"));

        assertThrows(DadoEntradaException.class,
                () -> service.alterarDadosUsuario("u1", "ana.nova@email.com", ""));
        assertThrows(DadoEntradaException.class,
                () -> service.alterarDadosUsuario("u1", "ana.nova@email.com", "   "));
        assertThrows(DadoEntradaException.class,
                () -> service.alterarDadosUsuario("u1", "ana.nova@email.com", null));

        PessoaFisica usuario = (PessoaFisica) service.buscarPorId("u1");
        assertEquals("ana@email.com", usuario.getEmail());
        assertEquals("Rua A", usuario.getEndereco());
    }

    @Test
    public void exibeEnderecoDeClienteESetorDeAdministrador() throws Exception {
        PessoaFisica cliente = pessoa("u1", "ana@email.com", "CPF 123");
        Administrador administrador = new Administrador("a1", "Admin", "admin@email.com", "TI");

        assertTrue(cliente.toString().contains("Endereço: Rua A"));
        assertTrue(administrador.toString().contains("Setor: TI"));
    }

    @Test
    public void removerUsuarioInexistenteRetornaErro() {
        assertThrows(RegistroNaoEncontradoException.class, () -> service.removerUsuario("ausente"));
    }

    private PessoaFisica pessoa(String id, String email, String cpf) throws Exception {
        return new PessoaFisica(id, "Cliente", email, "Rua A", cpf);
    }
}
