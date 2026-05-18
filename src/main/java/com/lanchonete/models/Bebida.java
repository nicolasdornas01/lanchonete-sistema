package com.lanchonete.models;

/**
 * Subclasse Bebida - herda de Produto.
 * Sobrescreve calcularPrecoFinal() aplicando POLIMORFISMO:
 * - Se gelada, acrescenta 10% no preço
 */
public class Bebida extends Produto {
    private int volumeMl;
    private boolean gelada;
    private boolean comGas;

    public Bebida() {
        super();
    }

    public Bebida(String nome, double preco, int volumeMl,
                  boolean gelada, boolean comGas) {
        super(nome, preco);
        this.volumeMl = volumeMl;
        this.gelada = gelada;
        this.comGas = comGas;
    }

    public Bebida(int id, String nome, double preco, int volumeMl,
                  boolean gelada, boolean comGas) {
        super(id, nome, preco);
        this.volumeMl = volumeMl;
        this.gelada = gelada;
        this.comGas = comGas;
    }

    @Override
    public double calcularPrecoFinal() {
        double precoFinal = this.preco;
        if (this.gelada) {
            precoFinal *= 1.10; // 10% a mais se gelada
        }
        return precoFinal;
    }

    @Override
    public String exibirInfo() {
        return String.format(
            "[BEBIDA] #%d %s | %dml | Gelada: %s | Com gás: %s | Preço final: R$ %.2f",
            id, nome, volumeMl,
            (gelada ? "Sim" : "Não"),
            (comGas ? "Sim" : "Não"),
            calcularPrecoFinal()
        );
    }

    // Getters e Setters
    public int getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(int volumeMl) {
        this.volumeMl = volumeMl;
    }

    public boolean isGelada() {
        return gelada;
    }

    public void setGelada(boolean gelada) {
        this.gelada = gelada;
    }

    public boolean isComGas() {
        return comGas;
    }

    public void setComGas(boolean comGas) {
        this.comGas = comGas;
    }
}
