package br.ufal.aracomp.loja.controller;
import br.ufal.aracomp.loja.service.*;
import br.ufal.aracomp.loja.models.*;
import br.ufal.aracomp.loja.exception.*;
import java.util.*;

public class UsuarioController {
	private UsuarioService usuarioservice;
	
	public UsuarioController(UsuarioService usuarioservice) {
		this.usuarioservice = usuarioservice;
	}
	
	public void cadastrarPessoaFisica(String id, String nome, String email, String endereco, String cpf) throws RegistroCadastradoException {
		PessoaFisica nova_pf = new PessoaFisica(id, nome, email, endereco, cpf);
		this.usuarioservice.cadastrarUsuario(nova_pf);
	}
	
	public void cadastrarPessoaJuridica(String id, String nome, String email, String endereco, String cnpj) throws RegistroCadastradoException {
		PessoaJuridica nova_pj = new PessoaJuridica(id, nome, email, endereco, cnpj);
		this.usuarioservice.cadastrarUsuario(nova_pj);
	}
	
	public void cadastrarAdministrador(String id, String nome, String email, String senha, String setor) throws RegistroCadastradoException {
		Administrador adm = new Administrador(id, nome, email, senha, setor);
		this.usuarioservice.cadastrarUsuario(adm);
	}
	
	public Usuario buscarPorId(String id) throws RegistroNaoEncontradoException {
		return this.usuarioservice.buscarPorId(id);
	}
	
	public List<Usuario> listarUsuarios() {
		return this.usuarioservice.listarUsuarios();
	}
}
