package br.dev.jstec.mobilplan.infrastructure.rest.client.bucket;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_INFORMACAO_INCONSISTENTE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static software.amazon.awssdk.core.sync.RequestBody.fromInputStream;

import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@Slf4j
public class S3BucketClient implements PutFilesBucket {

    private final S3Client s3Client;
    private final String bucketName;
    private static final Map<String, String> MIME_TYPES = new HashMap<>();

    static {
        MIME_TYPES.put("png", "image/png");
        MIME_TYPES.put("jpg", "image/jpg");
        MIME_TYPES.put("jpeg", "image/jpeg");
    }

    public S3BucketClient(S3Client s3Client, @Value("${BUCKET_NAME}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public static String getShortContentType(String mimeType) {

        return getKeyByValue(mimeType)
                .orElseThrow(() -> new RequestException(BAD_REQUEST,
                        ERRO_INFORMACAO_INCONSISTENTE,
                        S3BucketClient.class.getSimpleName()));
    }

    private static String getContentType(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "application/octet-stream";
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }

    private static Optional<String> getKeyByValue(String value) {
        return S3BucketClient.MIME_TYPES.entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Override
    public String put(String folderName, String filename, InputStream inputStream, long contentLength) {

        final var key = folderName + "/" + filename;
        try {
            var putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(MIME_TYPES.get(getContentType(filename)))
                    .build();

            s3Client.putObject(putObjectRequest, fromInputStream(inputStream, contentLength));

            var uri = s3Client.utilities()
                    .getUrl(b -> b.bucket(bucketName).key(key)).toURI();

            return uri.toString();

        } catch (Exception e) {
            log.warn("Erro ao salvar arquivo no bucket: {}", e.getMessage());
            return EMPTY;
        }
    }
}
