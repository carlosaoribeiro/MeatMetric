package com.carlosribeiro.meatmetric.util;

import com.carlosribeiro.meatmetric.model.Churrasco;
import com.carlosribeiro.meatmetric.model.Churrasco.TipoChurrasco;

public class CalculoUtil {

    public static Churrasco calcularChurrasco(TipoChurrasco tipo, int numeroPessoas) {
        int gramasPorPessoa = (tipo == TipoChurrasco.SOMENTE_CARNES) ? 500 : 350;
        int totalGramas = numeroPessoas * gramasPorPessoa;
        double totalKg = totalGramas / 1000.0;

        double bovino = totalKg * 0.5;
        double frango = totalKg * 0.25;
        double linguica = totalKg * 0.15;
        double suino = totalKg * 0.1;

        return new Churrasco(tipo, numeroPessoas, totalKg, bovino, frango, linguica, suino);
    }
}
