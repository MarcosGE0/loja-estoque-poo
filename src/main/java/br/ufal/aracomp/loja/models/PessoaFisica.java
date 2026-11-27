package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

public class PessoaFisica extends Cliente {
	private String cpf;
	
	public PessoaFisica(String id, String nome, String email, String endereco, String cpf)
			throws DadoEntradaException {
		super(id, nome, email, endereco);
		if (!ValidadorDocumento.documentoInformado(cpf)) {
			throw new DadoEntradaException("CPF deve ser informado.");
		}
		this.cpf = cpf;
	}

	public String getCpf() {
		return this.cpf;
	}
	
	@Override
	public String getDocumentacao() {
		return String.format("Nome: %s \nCPF: %s", this.getNome(), this.cpf);
	}
	
	@Override
	// polimorfimos de sobrecrita salva aq pq eu sobrescrevo para aparecer no views o cpf ou cnpj, 
	// identificando se é pessoa física ou jurídica
	public String toString() {
		return String.format("%s | %s | %s | CPF: %s | %s", this.getId(), this.getNome(), this.getEmail(), this.cpf, this.isAtivo());
	}
}
