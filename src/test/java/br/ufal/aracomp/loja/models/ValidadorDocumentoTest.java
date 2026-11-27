package br.ufal.aracomp.loja.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import org.junit.jupiter.api.Test;

public class ValidadorDocumentoTest {

    @Test
    public void aceitaCpfOuCnpjDesdeQueEstejaPreenchido() {
        assertTrue(ValidadorDocumento.documentoInformado("qualquer identificador"));
        assertTrue(ValidadorDocumento.documentoInformado("529.982.247-25"));
        assertFalse(ValidadorDocumento.documentoInformado("   "));
        assertFalse(ValidadorDocumento.documentoInformado(null));
    }

    @Test
    public void normalizaPontuacaoParaCompararDocumentosDuplicados() {
        assertEquals("52998224725", ValidadorDocumento.normalizar("529.982.247-25"));
        assertEquals("52998224725", ValidadorDocumento.normalizar("52998224725"));
        assertEquals("", ValidadorDocumento.normalizar(null));
    }

    @Test
    public void pessoaFisicaEJuridicaRejeitamDocumentoVazio() {
        assertThrows(DadoEntradaException.class,
                () -> new PessoaFisica("u1", "Ana", "ana@email.com", "Rua A", " "));
        assertThrows(DadoEntradaException.class,
                () -> new PessoaJuridica("u2", "Loja", "loja@email.com", "Rua B", ""));
    }
}
