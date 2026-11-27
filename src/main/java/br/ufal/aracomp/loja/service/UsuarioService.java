package br.ufal.aracomp.loja.service;
import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.models.PessoaFisica;
import br.ufal.aracomp.loja.models.PessoaJuridica;
import br.ufal.aracomp.loja.models.Usuario;
import br.ufal.aracomp.loja.models.ValidadorDocumento;
import br.ufal.aracomp.loja.repository.UsuarioRepository;
import br.ufal.aracomp.loja.exception.*;
import java.util.List;

public class UsuarioService {
	private UsuarioRepository usuarioRepository;
	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public void cadastrarUsuario(Usuario novoUsuario) throws RegistroCadastradoException {
		
		for(Usuario usuario_cadastrado: usuarioRepository.listarUsuarios()) {
			if(usuario_cadastrado.getEmail().equals(novoUsuario.getEmail())) {
				throw new RegistroCadastradoException("E-mail já utilizado: " + novoUsuario.getEmail() + ".");
			}
			
			if(usuario_cadastrado.getId().equals(novoUsuario.getId())) {
				throw new RegistroCadastradoException("ID já utilizado: " + novoUsuario.getId() + ".");
			}
			if (novoUsuario instanceof PessoaFisica && usuario_cadastrado instanceof PessoaFisica) {
				String cpfExistente = ValidadorDocumento.normalizar(
						((PessoaFisica) usuario_cadastrado).getCpf());
				String cpfNovo = ValidadorDocumento.normalizar(((PessoaFisica) novoUsuario).getCpf());
				if (cpfNovo.equals(cpfExistente)) {
					throw new RegistroCadastradoException("CPF já cadastrado.");
				}
			}

			if (novoUsuario instanceof PessoaJuridica && usuario_cadastrado instanceof PessoaJuridica) {
				String cnpjExistente = ValidadorDocumento.normalizar(
						((PessoaJuridica) usuario_cadastrado).getCnpj());
				String cnpjNovo = ValidadorDocumento.normalizar(((PessoaJuridica) novoUsuario).getCnpj());
				if (cnpjNovo.equals(cnpjExistente)) {
					throw new RegistroCadastradoException("CNPJ já cadastrado.");
				}
			}
		}
		
		this.usuarioRepository.adicionarUsuario(novoUsuario);
	}
	
	public Usuario buscarPorId(String idUsuario) throws RegistroNaoEncontradoException {
		for (Usuario usuario_cadastrado: usuarioRepository.listarUsuarios()) {
			if(usuario_cadastrado.getId().equals(idUsuario)) {
				return usuario_cadastrado;
			}
		}
		throw new RegistroNaoEncontradoException("Usuário com ID: " + idUsuario + " não está cadastrado.");
	}
	
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.listarUsuarios();
	}

	public void desativarUsuario(String idUsuario)
			throws DadoEntradaException, RegistroNaoEncontradoException, UsuarioInativoException {
		if (idUsuario == null || idUsuario.equals("")) {
			throw new DadoEntradaException("ID do usuário não pode ser vazio.");
		}

		Usuario usuario = buscarPorId(idUsuario);

		if (!usuario.isAtivo()) {
			throw new UsuarioInativoException("Usuário com ID " + idUsuario + " já está desativado.");
		}

		usuario.desativar();
		usuarioRepository.salvarAlteracoes();
	}

	public void removerUsuario(String idUsuario)
			throws DadoEntradaException, RegistroNaoEncontradoException {
		if (idUsuario == null || idUsuario.equals("")) {
			throw new DadoEntradaException("ID do usuário não pode ser vazio.");
		}

		buscarPorId(idUsuario);
		usuarioRepository.removerUsuario(idUsuario);
	}
}
