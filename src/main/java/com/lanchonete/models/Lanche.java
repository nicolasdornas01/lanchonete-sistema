package com.lanchonete.models;

/**
 * Subclasse Lanche - herda de Produto.
 * Sobrescreve calcularPrecoFinal() aplicando POLIMORFISMO:
 * - Se tem bacon, acrescenta R$ 3,00
 */
public class Lanche extends Produto {
    private String ingredientes;
    private boolean temBacon;
    private String tipoCarne;

    public Lanche() {
        super();
    }

    public Lanche(String nome, double preco, String ingredientes,
                  boolean temBacon, String tipoCarne) {
        super(nome, preco);
        this.ingredientes = ingredientes;
        this.temBacon = temBacon;
        this.tipoCarne = tipoCarne;
    }

    public Lanche(int id, String nome, double preco, String ingredientes,
                  boolean temBacon, String tipoCarne) {
        super(id, nome, preco);
        this.ingredientes = ingredientes;
        this.temBacon = temBacon;
        this.tipoCarne = tipoCarne;
    }

    @Override
    public double calcularPrecoFinal() {
        double precoFinal = this.preco;
        if (this.temBacon) {
            precoFinal += 3.00; // adicional do bacon
        }
        return precoFinal;
    }

    @Override
    public String exibirInfo() {
        return String.format(
            "[LANCHE] #%d %s | Carne: %s | Ingredientes: %s | Bacon: %s | Preço final: R$ %.2f",
            id, nome, tipoCarne, ingredientes,
            (temBacon ? "Sim" : "Não"), calcularPrecoFinal()
        );
    }

    // Getters e Setters
    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public boolean isTemBacon() {
        return temBacon;
    }

    public void setTemBacon(boolean temBacon) {
        this.temBacon = temBacon;
    }

    public String getTipoCarne() {
        return tipoCarne;
    }

    public void setTipoCarne(String tipoCarne) {
        this.tipoCarne = tipoCarne;
    }
}
