package br.ufal.aracomp.loja.controller;
import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.models.Administrador;
import br.ufal.aracomp.loja.models.PessoaFisica;
import br.ufal.aracomp.loja.models.PessoaJuridica;
import br.ufal.aracomp.loja.models.Usuario;
import br.ufal.aracomp.loja.service.UsuarioService;
import br.ufal.aracomp.loja.service.*;
import br.ufal.aracomp.loja.models.*;
import br.ufal.aracomp.loja.exception.*;
import java.util.*;

public class UsuarioController {
	private UsuarioService usuarioservice;
	
	public UsuarioController(UsuarioService usuarioservice) {
		this.usuarioservice = usuarioservice;
	}
	
	public void cadastrarPessoaFisica(String id, String nome, String email, String endereco, String cpf)
			throws RegistroCadastradoException, DadoEntradaException, PersistenciaException {
		PessoaFisica nova_pf = new PessoaFisica(id, nome, email, endereco, cpf);
		this.usuarioservice.cadastrarUsuario(nova_pf);
	}
	
	public void cadastrarPessoaJuridica(String id, String nome, String email, String endereco, String cnpj)
			throws RegistroCadastradoException, DadoEntradaException, PersistenciaException {
		PessoaJuridica nova_pj = new PessoaJuridica(id, nome, email, endereco, cnpj);
		this.usuarioservice.cadastrarUsuario(nova_pj);
	}
	
	public void cadastrarAdministrador(String id, String nome, String email, String setor) throws RegistroCadastradoException, DadoEntradaException, PersistenciaException {
		Administrador adm = new Administrador(id, nome, email, setor);
		this.usuarioservice.cadastrarUsuario(adm);
	}
	
	public Usuario buscarPorId(String idUsuario) throws RegistroNaoEncontradoException {
		return this.usuarioservice.buscarPorId(idUsuario);
	}
	
	public List<Usuario> listarUsuarios() {
		return this.usuarioservice.listarUsuarios();
	}

	public void desativarUsuario(String idUsuario)
			throws DadoEntradaException, RegistroNaoEncontradoException, UsuarioInativoException, PersistenciaException {
		this.usuarioservice.desativarUsuario(idUsuario);
	}

	public void removerUsuario(String idUsuario)
			throws DadoEntradaException, RegistroNaoEncontradoException, PersistenciaException {
		this.usuarioservice.removerUsuario(idUsuario);
	}
}
