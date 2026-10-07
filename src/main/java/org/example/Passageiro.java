package org.example;

public class Passageiro {

    private String nome;
    private String documento;
    private CategoriaCliente categoria;

    public Passageiro(String nome, String documento, CategoriaCliente categoria) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }

        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("Documento não pode ser nulo ou vazio.");
        }

        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não pode ser nula.");
        }

        this.nome = nome;
        this.documento = documento;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public CategoriaCliente getCategoria() {
        return categoria;
    }
}
