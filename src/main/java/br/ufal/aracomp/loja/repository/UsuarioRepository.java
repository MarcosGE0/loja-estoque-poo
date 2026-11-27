package br.ufal.aracomp.loja.repository;
import br.ufal.aracomp.loja.models.Usuario;
import java.util.List;
import java.util.ArrayList;

public class UsuarioRepository extends ArquivoRepository<Usuario> {
	private final List<Usuario> usuarios;

	public UsuarioRepository() {
		super("dados/usuarios.dat");
		this.usuarios = carregar();
	}

	public void adicionarUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
		salvar(usuarios);
	}

	public void salvarAlteracoes() {
		salvar(usuarios);
	}

	public List<Usuario> listarUsuarios() {
		return new ArrayList<>(this.usuarios);
	}

	public void removerUsuario(String idUsuario) {
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getId().equals(idUsuario)) {
				usuarios.remove(i);
				salvar(usuarios);
				return;
			}
		}
	}
}
