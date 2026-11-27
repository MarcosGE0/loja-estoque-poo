package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

public class PessoaJuridica extends Cliente {
	private String cnpj;
	
	public PessoaJuridica(String id, String nome, String email, String endereco, String cnpj)
			throws DadoEntradaException {
		super(id, nome, email, endereco);
		if (!ValidadorDocumento.documentoInformado(cnpj)) {
			throw new DadoEntradaException("CNPJ deve ser informado.");
		}
		this.cnpj = cnpj;
	}

	public String getCnpj() {
		return this.cnpj;
	}
	
	@Override
	public String toString() {
		return String.format("%s | %s | %s | CNPJ: %s | Endereço: %s | %s",
				this.getId(), this.getNome(), this.getEmail(), this.cnpj, this.getEndereco(), this.isAtivo());
	}

}
