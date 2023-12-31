package br.dev.jstec.mobilplan.domain.valueobject;

import static lombok.AccessLevel.PRIVATE;

import java.io.InputStream;
import java.util.UUID;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
class ImagemFixture {

    public static Imagem buildComFilename() {

        return Imagem.of(UUID.randomUUID().toString());
    }

    public static Imagem buildComFilenameEInputStream() {

        return Imagem.of(UUID.randomUUID().toString(), loadPngImageAsStream());
    }

    private static InputStream loadPngImageAsStream() {

        return ImagemFixture.class.getResourceAsStream(
                "/teste1200x1200.png");
    }
}
