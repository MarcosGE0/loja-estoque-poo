package br.ufal.aracomp.loja.models;

import java.io.Serializable;

public interface DescontoStrategy extends Serializable {
	double calcularDesconto(Pedido pedido);
	String getNome();
	String getDescricao();
}
