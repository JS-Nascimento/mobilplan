package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao;

public class Dimensoes {
    private final double largura;
    private final double altura;
    private final double profundidade;
    private final double espessura;
    private double totalFita;

    public Dimensoes(double largura, double altura, double profundidade, double espessura) {
        this.largura = largura;
        this.altura = altura;
        this.profundidade = profundidade;
        this.espessura = espessura;
    }

    public Dimensoes calcularDimensoesInternas() {

        double larguraInterna = largura - (2 * espessura);
        double alturaInterna = altura - (2 * espessura);
        return new Dimensoes(larguraInterna, alturaInterna, this.profundidade, this.espessura);
    }

    public double getLargura() {
        return largura;
    }

    public double getAltura() {
        return altura;
    }

    public double getProfundidade() {
        return profundidade;
    }

    public double getEspessura() {
        return espessura;
    }

    public double getTotalFita() {
        return totalFita;
    }

    @Override
    public String toString() {
        return "largura=" + largura
                + ", altura=" + altura
                + ", profundidade=" + profundidade
                + ", total de fita de bordo=" + totalFita;
    }
}
