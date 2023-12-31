package br.dev.jstec.mobilplan.infrastructure.rest.client.bucket;

import static java.util.Map.of;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static software.amazon.awssdk.core.sync.RequestBody.fromInputStream;

import java.io.InputStream;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3BucketClient implements PutFilesBucket {

    private final S3Client s3Client;
    private final String bucketName;
    private static final Map<String, String> MIME_TYPES = of(
            "png", "image/png",
            "jpg", "image/jpeg",
            "jpeg", "image/jpeg"
    );

    public S3BucketClient(S3Client s3Client, @Value("${BUCKET_NAME}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public String put(String folderName, String filename, InputStream inputStream, long contentLength) {
        final var key = folderName + "/" + filename;

        try (var is = inputStream) {
            var putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                    .contentType(getContentType(filename))
                .build();

            s3Client.putObject(putObjectRequest,
                fromInputStream(inputStream, contentLength));

            var uri = s3Client.utilities()
                .getUrl(b -> b.bucket(bucketName).key(key)).toURI();

            return uri.toString();

        } catch (Exception e) {

            return EMPTY;
        }
    }

    private String getContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        return MIME_TYPES.getOrDefault(extension, "application/octet-stream");
    }
}
