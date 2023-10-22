package br.dev.jstec.efurniture.infrastructure.rest.client.bucket;

import java.io.InputStream;
import java.net.URISyntaxException;

public interface PutFilesBucket {

    String put(String key, InputStream inputStream, long contentLength) throws URISyntaxException;
}
