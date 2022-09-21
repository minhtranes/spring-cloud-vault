package vn.ifa.study.vault;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/customers")
public class CustomerAPIController {
    @PostMapping
    Customer addOne(@RequestBody final Customer customer) {

        log.info("Received customer info: {}", customer.toString());
        return customer;
    }

    @GetMapping
    Customer getOne() {

        return Customer.builder().firstName("Minh").lastName("Tran").cipher_pin("1234").build();
    }
}
