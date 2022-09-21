package vn.ifa.study.vault.mapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.vault.core.VaultOperations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CipherFieldSerializer extends JsonSerializer<Object> {

    private final VaultOperations vaultOps;
    private final JsonSerializer<Object> originalCustomSerializer;

    public CipherFieldSerializer(final VaultOperations vaultOps, final JsonSerializer<Object> jsonSerializer) {

        this.vaultOps = vaultOps;
        this.originalCustomSerializer = jsonSerializer;

    }

    @Override
    public void serialize(final Object value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {

        final String plaintext = value.toString();
        // final byte[] plaintextJson = serializePlaintext(value, gen, provider);
//        final Map<String, Object> encrypted = cryptoManager.encrypt(plaintextJson, annotation.encrypter());
        gen.writeObject(vaultOps.opsForTransit().encrypt("order", plaintext));
    }

    @SuppressWarnings("unused")
    private byte[] serializePlaintext(final Object value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {

        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (JsonGenerator plaintextGenerator = gen.getCodec().getFactory().createGenerator(out)) {

            if (originalCustomSerializer != null) {
                originalCustomSerializer.serialize(value, plaintextGenerator, provider);
            } else {
                provider.defaultSerializeValue(value, plaintextGenerator);
            }

        }

        return out.toByteArray();
    }

}
