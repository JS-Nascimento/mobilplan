package br.dev.jstec.mobilplan.infrastructure.rest.client.bucket;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static software.amazon.awssdk.core.sync.RequestBody.fromInputStream;

import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3BucketClient implements PutFilesBucket {

    private final S3Client s3Client;
    private final String bucketName;

    public S3BucketClient(S3Client s3Client, @Value("${BUCKET_NAME}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public String put(String filename, InputStream inputStream, long contentLength) {

        final var key = "logomarca/" + filename;

        try (var is = inputStream) {
            var putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/png")
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
}
