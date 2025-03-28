package com.carlosribeiro.meatmetric.model;

public class Churrasco {

    public enum TipoChurrasco {
        SOMENTE_CARNES,
        CARNES_E_GUARNICOES
    }

    private TipoChurrasco tipo;
    private int numeroPessoas;
    private double totalCarneKg;
    private double bovino;
    private double frango;
    private double linguica;
    private double porco;

    public Churrasco(TipoChurrasco tipo, int numeroPessoas, double totalCarneKg,
                     double bovino, double frango, double linguica, double porco) {
        this.tipo = tipo;
        this.numeroPessoas = numeroPessoas;
        this.totalCarneKg = totalCarneKg;
        this.bovino = bovino;
        this.frango = frango;
        this.linguica = linguica;
        this.porco = porco;
    }

    public TipoChurrasco getTipo() {
        return tipo;
    }

    public int getNumeroPessoas() {
        return numeroPessoas;
    }

    public double getTotalCarneKg() {
        return totalCarneKg;
    }

    public double getBovino() {
        return bovino;
    }

    public double getFrango() {
        return frango;
    }

    public double getLinguica() {
        return linguica;
    }

    public double getPorco() {
        return porco;
    }
}
