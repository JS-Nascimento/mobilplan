package br.dev.jstec.efurniture.application.domain.valueobject;

import static lombok.AccessLevel.PRIVATE;

import java.io.InputStream;
import java.util.UUID;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
class LogomarcaFixture {

    public static Logomarca buildComFilename() {

        return Logomarca.of(UUID.randomUUID().toString());
    }

    public static Logomarca buildComFilenameEInputStream() {

        return Logomarca.of(UUID.randomUUID().toString(), loadPngImageAsStream());
    }

    private static InputStream loadPngImageAsStream() {

        InputStream imageStream = LogomarcaFixture.class.getResourceAsStream(
            "/teste1200x1200.png");
        return imageStream;
    }
}
