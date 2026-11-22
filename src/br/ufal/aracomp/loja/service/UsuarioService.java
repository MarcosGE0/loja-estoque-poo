package br.ufal.aracomp.loja.service;
import br.ufal.aracomp.loja.repository.*;
import br.ufal.aracomp.loja.models.*;
import br.ufal.aracomp.loja.exception.*;
import java.util.List;

public class UsuarioService {
	private UsuarioRepository usuariorepository;
	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuariorepository = usuarioRepository; // no construtor UsuarioRepository eu inicio um atributo ArrayList<Usuario>()
	}
	
	public void cadastrarUsuario(Usuario novo_usuario) throws RegistroCadastradoException {
		// regra de negócio: unicidade de e-mail, cpf ou cnpj nos dados, para isso temos que percorrer os registros
		
		for(Usuario usuario_cadastrado: usuariorepository.listarUsuarios()) { // percorrer todos os registros de usuários cadastrados 
			if(usuario_cadastrado.getEmail().equals(novo_usuario.getEmail())) { // e ver se tem algum email cadastrado
				// se for verdadeiro, lançar a desgraça da exceção kk
				throw new RegistroCadastradoException("E-mail já utilizado!"); // id poderá ser 
			}
			
			if(usuario_cadastrado.getId().equals(novo_usuario.getId())) {
				throw new RegistroCadastradoException("ID já utilizado!"); 
			}
			
			// ainda falta inserir duas condições para validar se existe CPF ou CNJP`já cadastrados kk
		}
		
		this.usuariorepository.adicionarUsuario(novo_usuario); // se passar por todas as regras
	}
	
	public Usuario buscarPorId(String id_usuario_buscado) throws RegistroNaoEncontradoException {
		for (Usuario usuario_cadastrado: usuariorepository.listarUsuarios()) { // percorrer toda a lista de usuários cadastrados
			if(usuario_cadastrado.getId().equals(id_usuario_buscado)) {
				return usuario_cadastrado;
			}
		}
		throw new RegistroNaoEncontradoException("Usuário com ID: " + id_usuario_buscado + " não está cadastrado.");
		// tem que jgoar a exceção pq esse metodo tem que retornar o tipo Usuário
	}
	
	public List<Usuario> listarUsuarios() {
		return usuariorepository.listarUsuarios();
	}
	
	// falta inserir os métodos de remover usuário e atualizar para desativar o usuário
}
