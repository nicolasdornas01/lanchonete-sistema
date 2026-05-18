package com.lanchonete.models;

/**
 * Classe abstrata base da hierarquia.
 * Aplica ENCAPSULAMENTO (atributos protegidos + getters/setters)
 * e define o método polimórfico calcularPrecoFinal().
 */
public abstract class Produto {
    protected int id;
    protected String nome;
    protected double preco;

    public Produto() {
    }

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    // Método polimórfico - cada subclasse implementa do seu jeito
    public abstract double calcularPrecoFinal();

    // Método polimórfico - exibir info detalhada
    public abstract String exibirInfo();

    // Getters e Setters (encapsulamento)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        this.preco = preco;
    }
}
