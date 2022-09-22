package vn.ifa.study.vault.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.vault.core.VaultOperations;

import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class CipherSerializationModifier extends BeanSerializerModifier {

    class CipherPropertyWriter extends BeanPropertyWriter {

        private static final long serialVersionUID = 4685568942048605681L;

        public CipherPropertyWriter(final BeanPropertyWriter writer, final VaultOperations vaultOps) {

            super(writer, new SerializedString(writer.getName()));

            final JsonSerializer<Object> newSerializer = new CipherFieldSerializer(vaultOps, writer.getSerializer());
            _serializer = newSerializer;
            _nullSerializer = newSerializer;

        }

    }

    private final VaultOperations vaultOps;

    public CipherSerializationModifier(final VaultOperations vaultOps) {

        this.vaultOps = vaultOps;

    }

    @Override
    public List<BeanPropertyWriter> changeProperties(
        final SerializationConfig config,
        final BeanDescription beanDesc,
        final List<BeanPropertyWriter> writers) {

        final List<BeanPropertyWriter> result = new ArrayList<>();

        for (final BeanPropertyWriter writer : writers) {
            final String propName = writer.getName();
            result.add((propName != null) && propName.startsWith("cipher_") ? new CipherPropertyWriter(writer, vaultOps)
                    : writer);
        }

        return result;
    }

}
