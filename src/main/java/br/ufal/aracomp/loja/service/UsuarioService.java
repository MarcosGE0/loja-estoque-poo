package br.ufal.aracomp.loja.service;
import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.models.Usuario;
import br.ufal.aracomp.loja.repository.UsuarioRepository;
import br.ufal.aracomp.loja.exception.*;
import java.util.List;

public class UsuarioService {
	private UsuarioRepository usuarioRepository;
	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository; // no construtor UsuarioRepository eu inicio um atributo ArrayList<Usuario>()
	}
	
	public void cadastrarUsuario(Usuario novoUsuario) throws RegistroCadastradoException {
		// regra de negócio: unicidade de e-mail, cpf ou cnpj nos dados, para isso temos que percorrer os registros
		
		for(Usuario usuario_cadastrado: usuarioRepository.listarUsuarios()) { // percorrer todos os registros de usuários cadastrados
			if(usuario_cadastrado.getEmail().equals(novoUsuario.getEmail())) { // e ver se tem algum email cadastrado
				// se for verdadeiro, lançar a desgraça da exceção kk
				throw new RegistroCadastradoException("E-mail já utilizado: " + novoUsuario.getEmail() + ".");
			}
			
			if(usuario_cadastrado.getId().equals(novoUsuario.getId())) {
				throw new RegistroCadastradoException("ID já utilizado: " + novoUsuario.getId() + ".");
			}
			
			// ainda falta inserir duas condições para validar se existe CPF ou CNJP`já cadastrados kk
		}
		
		this.usuarioRepository.adicionarUsuario(novoUsuario); // se passar por todas as regras
	}
	
	public Usuario buscarPorId(String idUsuario) throws RegistroNaoEncontradoException {
		for (Usuario usuario_cadastrado: usuarioRepository.listarUsuarios()) { // percorrer toda a lista de usuários cadastrados
			if(usuario_cadastrado.getId().equals(idUsuario)) {
				return usuario_cadastrado;
			}
		}
		throw new RegistroNaoEncontradoException("Usuário com ID: " + idUsuario + " não está cadastrado.");
		// tem que jgoar a exceção pq esse metodo tem que retornar o tipo Usuário
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
