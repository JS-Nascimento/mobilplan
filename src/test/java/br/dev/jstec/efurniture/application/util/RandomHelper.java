package br.dev.jstec.efurniture.application.util;

import static java.lang.Integer.MAX_VALUE;
import static lombok.AccessLevel.PRIVATE;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.range.BigDecimalRangeRandomizer;
import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;
import io.github.benas.randombeans.randomizers.range.LongRangeRandomizer;
import io.github.benas.randombeans.randomizers.text.StringRandomizer;
import io.github.benas.randombeans.randomizers.time.LocalDateRandomizer;
import io.github.benas.randombeans.randomizers.time.LocalDateTimeRandomizer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@NoArgsConstructor(access = PRIVATE)
public class RandomHelper {

    private static final EnhancedRandomBuilder random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder();
    private static final Long MIN_LONG = 0L;
    private static final Double MIN = 0.0;
    private static final Integer MIN_INTEGER = 0;
    private static final Double MAX = 999.9;

    public static String gerarString() {

        return random(String.class, new StringRandomizer());
    }

    public static String gerarString(int tamanho) {

        return RandomStringUtils.random(tamanho, true, false);
    }

    public static String gerarStringNumerica(int tamanho) {

        return RandomStringUtils.random(tamanho, false, true);
    }

    public static String gerarStringComLetraENumero(int tamanho) {

        return RandomStringUtils.random(tamanho, true, true);
    }

    public static Long gerarLong() {

        return random(Long.class, new LongRangeRandomizer(MIN_LONG, Long.MAX_VALUE));
    }

    private static <T> T random(Class<T> clazz, Randomizer<T> randomizer) {

        return random.randomize(clazz, randomizer).build().nextObject(clazz);
    }

    public static LocalDateTime gerarLocalDateTime() {

        return random(LocalDateTime.class, new LocalDateTimeRandomizer());
    }

    public static LocalDate gerarLocalDate() {

        return random(LocalDate.class, new LocalDateRandomizer());
    }

    public static BigDecimal gerarBigDecimalPositivoMoeda() {

        return random(BigDecimal.class, new BigDecimalRangeRandomizer(MIN, MAX)).setScale(2,
            RoundingMode.HALF_DOWN).stripTrailingZeros();
    }

    public static Integer gerarInteger() {

        return random(Integer.class, new IntegerRangeRandomizer(MIN_INTEGER, MAX_VALUE));
    }

    public static Integer gerarInteger(int min, int max) {

        return random(Integer.class, new IntegerRangeRandomizer(min, max));
    }

    public static <T> T gerarObject(Class<T> type) {

        return random.build().nextObject(type);
    }

    public static String gerarCpf(boolean valido) {

        var baseCpf = random(Integer.class, new IntegerRangeRandomizer(100_000_000, 999_999_999));
        var baseCpfStr = String.valueOf(baseCpf);

        var cpf = new int[11];
        for (var i = 0; i < baseCpfStr.length(); i++) {
            cpf[i] = Character.getNumericValue(baseCpfStr.charAt(i));
        }

        cpf[9] = calculateVerifierDigit(cpf, 9);
        cpf[10] = calculateVerifierDigit(cpf, 10);

        if (!valido) {
            cpf[9] = (cpf[9] + 1) % 10;
        }

        StringBuilder cpfStr = new StringBuilder();
        for (int digit : cpf) {
            cpfStr.append(digit);
        }

        return cpfStr.toString();
    }

    private static int calculateVerifierDigit(int[] cpf, int position) {
        var sum = 0;
        for (var i = 0; i < position; i++) {
            sum += cpf[i] * (position + 1 - i);
        }

        var remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    public static String gerarCnpj(boolean valido) {
        String baseCnpj = RandomStringUtils.randomNumeric(12);

        if (valido) {

            return baseCnpj + calculateVerificationDigits(baseCnpj);

        } else {

            return baseCnpj + RandomStringUtils.randomNumeric(2);
        }
    }

    private static String calculateVerificationDigits(String baseCnpj) {
        int firstDigit = calculateVerificationDigit(baseCnpj,
            new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        int secondDigit = calculateVerificationDigit(baseCnpj
            + firstDigit, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        return String.valueOf(firstDigit) + secondDigit;
    }

    private static int calculateVerificationDigit(String cnpj, int[] weights) {
        int sum = 0;

        for (int i = 0; i < cnpj.length(); i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights[i];
        }

        int remainder = sum % 11;

        return (remainder < 2) ? 0 : 11 - remainder;
    }

    public static String gerarEmail(boolean valido) {

        String nome = gerarStringComLetraENumero(5);
        String dominio = gerarStringComLetraENumero(5);

        if (valido) {

            return (nome + "@" + dominio + ".com").toLowerCase();

        } else {

            int tipoInvalido = gerarInteger() % 5;

            return switch (tipoInvalido) {
                case 0 -> nome + dominio + ".com";
                case 1 -> nome + "@" + dominio;
                case 2 -> "@" + dominio + ".com";
                case 3 -> " ";
                case 4 -> nome + " " + "@" + dominio + ".com";
                default -> nome + "@" + dominio + ".com";
            };
        }
    }
}
