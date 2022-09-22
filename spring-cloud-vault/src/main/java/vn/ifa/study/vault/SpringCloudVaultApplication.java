package vn.ifa.study.vault;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.vault.authentication.SessionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.Module;

import lombok.extern.slf4j.Slf4j;
import vn.ifa.study.vault.mapper.CipherModule;

@Slf4j
@RestController
@SpringBootApplication
public class SpringCloudVaultApplication {

    public static void main(final String[] args) {

        SpringApplication.run(SpringCloudVaultApplication.class, args);
    }

    @Autowired
    private SessionManager sessionManager;

    @Bean
    Module cipherModule() {

        return new CipherModule();
    }

    @GetMapping("/users")
    public Map<String, String> getUsers() {

        log.info("Getting user properties");
        return userProperties();
    }

    @PostConstruct
    public void initIt() throws Exception {

        log.info("Got Vault Token: " + sessionManager.getSessionToken().getToken());
    }

    @Bean
    @ConfigurationProperties(prefix = "user-properties")
    Map<String, String> userProperties() {

        return new HashMap<>();
    }
}
