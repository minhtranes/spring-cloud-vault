package vn.ifa.study.vault.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.vault.core.VaultOperations;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

public class CipherDeserializationModifier extends BeanDeserializerModifier {

    private final VaultOperations vaultOps;

    public CipherDeserializationModifier(final VaultOperations vaultOps) {

        this.vaultOps = vaultOps;

    }

    @Override
    public BeanDeserializerBuilder updateBuilder(
        final DeserializationConfig config,
        final BeanDescription beanDesc,
        final BeanDeserializerBuilder builder) {

        final List<PropertyName> removed = new ArrayList<>();
        final List<SettableBeanProperty> added = new ArrayList<>();

        builder.getProperties().forEachRemaining(prop -> {
            final String propName = prop.getName();

            if ((propName != null) && propName.startsWith("cipher_")) {
                final SettableBeanProperty newProp = prop.withName(new PropertyName(propName))
                                                         .withValueDeserializer(new CipherFieldDeserializer(vaultOps));

                added.add(newProp);
                removed.add(prop.getFullName());
            }

        });

        removed.forEach(builder::removeProperty);
        added.forEach(builder::addProperty);

        return builder;
    }
}
