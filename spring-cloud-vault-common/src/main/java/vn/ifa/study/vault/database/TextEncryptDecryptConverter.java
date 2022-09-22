package vn.ifa.study.vault.database;

import javax.persistence.AttributeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Ciphertext;
import org.springframework.vault.support.Plaintext;

public class TextEncryptDecryptConverter implements AttributeConverter<String, String> {

    @Autowired
    private VaultOperations vaultOps;

    @Value("${app.security.encryption.in-transit.key-name}")
    private String keyName;

    @Override
    public String convertToDatabaseColumn(final String decryptedContent) {

        final Plaintext plaintext = Plaintext.of(decryptedContent);
        final String cipherText = vaultOps.opsForTransit().encrypt(keyName, plaintext).getCiphertext();
        return cipherText;
    }

    @Override
    public String convertToEntityAttribute(final String encryptedContent) {

        final Ciphertext ciphertext = Ciphertext.of(encryptedContent);
        final String plaintext = vaultOps.opsForTransit().decrypt(keyName, ciphertext).asString();
        return plaintext;
    }

}
