package model;

public class Produto {
    private int identificacao;
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(int identificacao, String nome, double preco, int quantidade) {
        this.identificacao = identificacao;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getIdentificacao() {
        return identificacao;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return "Produto ID:" + identificacao + ", \nNome:" + nome + "\nPreço:" + preco + ",\nQuantidade:" + quantidade;
    }
}
