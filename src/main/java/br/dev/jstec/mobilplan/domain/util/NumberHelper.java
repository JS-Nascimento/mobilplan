package br.dev.jstec.mobilplan.domain.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberHelper {

    public static double mmSqParaMetrosSq(double mmSq) {
        BigDecimal bd = BigDecimal.valueOf(mmSq / 1000000);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double roundDouble(double doubleToRound, int decimalPlaces) {
        BigDecimal bd = BigDecimal.valueOf(doubleToRound);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double mmParaMetros(double metragemFita) {

        if (metragemFita == 0.0) {
            return 0.0;
        }

        BigDecimal bd = BigDecimal.valueOf(metragemFita / 1000);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String formatCurrency(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        var currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        return currencyFormatter.format(bd.doubleValue());
    }


    public static String formatNumber(double value, int decimalPlaces) {
        // Arredonda o valor para o número especificado de casas decimais
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);

        // Obter uma instância de NumberFormat para formatar em estilo de moeda, usando o Locale padrão
        NumberFormat currencyFormatter = NumberFormat.getNumberInstance();
        currencyFormatter.setMinimumFractionDigits(decimalPlaces);
        currencyFormatter.setMaximumFractionDigits(decimalPlaces);

        // Formatar o BigDecimal como uma string de moeda
        return currencyFormatter.format(bd);
    }


}
