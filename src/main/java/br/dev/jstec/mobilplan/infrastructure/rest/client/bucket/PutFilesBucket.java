package br.dev.jstec.mobilplan.infrastructure.rest.client.bucket;

import java.io.InputStream;
import java.net.URISyntaxException;

public interface PutFilesBucket {

    String put(String folderName, String key, InputStream inputStream, long contentLength) throws URISyntaxException;
}
