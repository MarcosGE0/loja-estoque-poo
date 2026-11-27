package br.ufal.aracomp.loja.repository;
import java.util.ArrayList;
import java.util.List;
import br.ufal.aracomp.loja.models.Produto;

public class ProdutoRepository extends ArquivoRepository<Produto> {
	private final List<Produto> produtos;

	public ProdutoRepository() {
		this("dados/produtos.dat");
	}

	public ProdutoRepository(String arquivo) {
		super(arquivo);
		this.produtos = carregar();
	}

	public void adicionarProduto(Produto novoProduto) {
		this.produtos.add(novoProduto);
		salvar(produtos);
	}

	public void salvarAlteracoes() {
		salvar(produtos);
	}

	public List<Produto> listarProdutos() {
		return new ArrayList<>(this.produtos);
	}

	public void removerProduto(String idProduto) {
		int posicao_produto = -1;
		int contador = 0;

		for (Produto produto_cadastrado : this.produtos) {
			if (produto_cadastrado.getId().equals(idProduto)) {
				posicao_produto = contador;
				break;
			}
			contador++;
		}

		if (posicao_produto != -1) {
			this.produtos.remove(posicao_produto);
			salvar(produtos);
		}
	}
}
