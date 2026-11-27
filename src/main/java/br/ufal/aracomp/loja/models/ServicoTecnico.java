package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.*;

import java.io.Serializable;

public class ServicoTecnico implements Precificavel, Serializable {
	private String id;
	private String nome;
	private double precoHora;
	private boolean ativo;
	private double tempoHoraServico;
	private String dataRealizacao;
	
	public ServicoTecnico(String id, String nome, double precoHora, double tempoHoraServico, String dataRealizacao)
			throws DadoEntradaException {
		if (precoHora < 0) {
			throw new DadoEntradaException("O preço por hora não pode ser negativo (recebido: " + precoHora + ").");
		}
		if (tempoHoraServico < 0) {
			throw new DadoEntradaException("O tempo do serviço não pode ser negativo (recebido: " + tempoHoraServico + ").");
		}

		this.id = id;
		this.nome = nome;
		this.precoHora = precoHora;
		this.tempoHoraServico = tempoHoraServico;
		this.dataRealizacao = dataRealizacao;
		this.ativo = true;
	}
	
	public double getPreco() {
		double total = this.precoHora * this.tempoHoraServico;
		
		return total;
	}

	public String getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public double getTempoHorasServico() {
		return this.tempoHoraServico;
	}
	
	public boolean isDisponivel() {
		return this.ativo;
	}
	
	public void desativar() {
		this.ativo = false;
	}
	
	public void registrarVenda(int qtd) {}
	
}
