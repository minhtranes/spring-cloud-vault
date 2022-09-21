package vn.ifa.study.vault.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.vault.core.VaultOperations;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public class CipherModule extends Module {

    @Autowired
    private VaultOperations vaultOps;

    @Override
    public String getModuleName() {

        return "Cihpher Module";
    }

    @Override
    public void setupModule(final SetupContext context) {

        context.addBeanSerializerModifier(new CipherSerializationModifier(vaultOps));
        context.addBeanDeserializerModifier(new CipherDeserializationModifier(vaultOps));
    }

    @Override
    public Version version() {

        return new Version(1, 0, 0, null, "vn.ifa.study", getModuleName());
    }

}
