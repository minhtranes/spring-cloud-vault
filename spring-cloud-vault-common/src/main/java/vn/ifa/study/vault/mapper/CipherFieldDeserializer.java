package vn.ifa.study.vault.mapper;

import java.io.IOException;

import org.springframework.vault.core.VaultOperations;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

public class CipherFieldDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {

    private final VaultOperations vaultOps;

    public CipherFieldDeserializer(final VaultOperations vaultOps) {

        this.vaultOps = vaultOps;

    }

    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext ctxt, final BeanProperty property)
            throws JsonMappingException {

        return new CipherFieldDeserializer(vaultOps);
    }

    @Override
    public Object deserialize(final JsonParser p, final DeserializationContext ctxt)
            throws IOException, JacksonException {

        final String ciphertext = p.getText();
        return vaultOps.opsForTransit().decrypt("order", ciphertext);
    }

}
