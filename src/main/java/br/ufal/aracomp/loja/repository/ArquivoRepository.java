package br.ufal.aracomp.loja.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ArquivoRepository<T> {

    private final String arquivo;

    protected ArquivoRepository(String arquivo) {
        this.arquivo = arquivo;
        criarDiretorio();
    }

    protected void salvar(List<T> dados) {
        try (ObjectOutputStream output =
                     new ObjectOutputStream(new FileOutputStream(arquivo))) {
            output.writeObject(dados);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados em " + arquivo, e);
        }
    }

    @SuppressWarnings("unchecked")
    protected List<T> carregar() {
        File arquivoDados = new File(arquivo);

        if (!arquivoDados.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<T>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar dados de " + arquivo, e);
        }
    }

    private void criarDiretorio() {
        File arquivoDados = new File(arquivo);

        File diretorio = arquivoDados.getParentFile();
        if (diretorio != null && !diretorio.exists()) {
            diretorio.mkdirs();
        }
    }
}