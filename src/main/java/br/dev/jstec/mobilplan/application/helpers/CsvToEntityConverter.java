package br.dev.jstec.mobilplan.application.helpers;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_AO_IMPORTAR_ARQUIVO;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CONVERSAO_CAMPO;
import static br.dev.jstec.mobilplan.application.helpers.ClassHelper.getFieldNamesAndTypes;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class CsvToEntityConverter {

    public static <T> List<T> convert(MultipartFile file, Class<T> entityType) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema =
                mapper.schemaFor(entityType).withHeader().withColumnReordering(true).withColumnSeparator(';');

        try (MappingIterator<T> iterator = mapper.readerFor(entityType)
                .with(schema)
                .readValues(file.getInputStream())) {
            return iterator.readAll();
        } catch (Exception e) {
            log.error("Erro ao importar arquivo", e);
            throw new BusinessException(ERRO_AO_IMPORTAR_ARQUIVO, "Ferragens" + e.getMessage());
        }
    }

    @SuppressWarnings("deprecation")
    public static <T> List<T> convert(MultipartFile file, List<String> columns, Class<T> entityType) {
        List<T> entities = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader()
                             .withIgnoreHeaderCase()
                             .withTrim()
                             .withDelimiter(';'))) {

            Map<String, Integer> headerMap = csvParser.getHeaderMap();
            Map<String, Integer> cleanHeaderMap = new HashMap<>();

            var fieldNames = getFieldNamesAndTypes(entityType);
            log.info("Nomes dos campos da entidade: {}", fieldNames);

            // Limpar os cabeçalhos de caracteres invisíveis ou espaços extras
            for (String header : headerMap.keySet()) {
                String cleanHeader = header.trim().replace("\uFEFF", ""); // Remove BOM e espaços
                cleanHeaderMap.put(cleanHeader, headerMap.get(header));
            }

            log.info("Importando colunas CSV: {}", csvParser.getHeaderMap().keySet());
            log.info("Colunas mapeadas: {}", cleanHeaderMap.keySet());


            csvParser.getHeaderMap().clear();
            cleanHeaderMap.forEach(
                    (key, value) -> {
                        csvParser.getHeaderMap().put(key, value);
                    }
            );

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                T entity = entityType.getDeclaredConstructor().newInstance();

                for (String column : columns) {
                    Field field = entityType.getField(column);
                    field.setAccessible(true);
                    field.set(entity, getValueForField(field, csvRecord.get(column.trim())));
                }

                entities.add(entity);
            }
        } catch (Exception e) {
            log.error("Erro ao importar arquivo", e);
            throw new BusinessException(ERRO_AO_IMPORTAR_ARQUIVO, "Ferragens" + e.getMessage());
        }

        return entities;
    }

    private static Object getValueForField(Field field, String value) {

        if (field.getType().equals(int.class)) {
            return Integer.parseInt(value);
        } else if (field.getType().equals(double.class)) {
            return Double.parseDouble(value);
        } else if (field.getType().equals(long.class)) {
            return Long.parseLong(value);
        } else if (field.getType().equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (field.getType().equals(String.class)) {
            return value;
        } else if (field.getType().equals(LocalDateTime.class)) {
            return LocalDateTime.parse(value);
        } else if (field.getType().equals(List.class)) {
            return List.of(value.split(","));
        } else {
            throw new BusinessException(ERRO_CONVERSAO_CAMPO, value, field.getType().toString());
        }
    }
}
