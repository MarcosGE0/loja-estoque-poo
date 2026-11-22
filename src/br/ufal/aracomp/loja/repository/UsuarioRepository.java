package br.ufal.aracomp.loja.repository;
import br.ufal.aracomp.loja.models.*;
import java.util.*;

public class UsuarioRepository {
	private List<Usuario> usuarios;
	
	public UsuarioRepository() {
		usuarios = new ArrayList<Usuario>();
	}
	
	public void adicionarUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
	}

	public List<Usuario> listarUsuarios() {
		return this.usuarios;
	}
	
	public void removerUsuario(String id) {
		// implementar remoção do usuário 
	}
}

