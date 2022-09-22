package vn.ifa.study.vault;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CustomerProcessor {

    @Bean
    Consumer<Customer> processCustomer() {

        return c -> {
            log.info("Processing customer: {}", c.toString());
        };
    }
}
