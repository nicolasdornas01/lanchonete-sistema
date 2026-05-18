package com.lanchonete.models;

/**
 * Subclasse Sobremesa - herda de Produto.
 * Sobrescreve calcularPrecoFinal() aplicando POLIMORFISMO:
 * - Se calorias > 500, aplica desconto de 20% (promo "saudável")
 */
public class Sobremesa extends Produto {
    private boolean temLactose;
    private int calorias;

    public Sobremesa() {
        super();
    }

    public Sobremesa(String nome, double preco, boolean temLactose, int calorias) {
        super(nome, preco);
        this.temLactose = temLactose;
        this.calorias = calorias;
    }

    public Sobremesa(int id, String nome, double preco, boolean temLactose, int calorias) {
        super(id, nome, preco);
        this.temLactose = temLactose;
        this.calorias = calorias;
    }

    @Override
    public double calcularPrecoFinal() {
        double precoFinal = this.preco;
        if (this.calorias > 500) {
            precoFinal *= 0.80; // 20% off em sobremesas muito calóricas
        }
        return precoFinal;
    }

    @Override
    public String exibirInfo() {
        return String.format(
            "[SOBREMESA] #%d %s | %d kcal | Lactose: %s | Preço final: R$ %.2f",
            id, nome, calorias,
            (temLactose ? "Sim" : "Não"),
            calcularPrecoFinal()
        );
    }

    // Getters e Setters
    public boolean isTemLactose() {
        return temLactose;
    }

    public void setTemLactose(boolean temLactose) {
        this.temLactose = temLactose;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        if (calorias < 0) {
            throw new IllegalArgumentException("Calorias não podem ser negativas");
        }
        this.calorias = calorias;
    }
}
