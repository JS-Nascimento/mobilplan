package br.dev.jstec.mobilplan.infrastructure.rest.client.bucket;

import java.io.InputStream;
import java.net.URISyntaxException;

public interface StorageGateway {

    String put(String folderName, String key, InputStream inputStream, long contentLength) throws URISyntaxException;

    boolean delete(String userDirectory, String fileName);

    boolean deleteFromUrl(String fileUrl);
}
