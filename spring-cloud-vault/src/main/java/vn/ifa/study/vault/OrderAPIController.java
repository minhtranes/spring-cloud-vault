package vn.ifa.study.vault;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderAPIController {

    @Autowired
    private OrderRepository repository;

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody final Order order) {

        return new ResponseEntity<>(repository.save(order), HttpStatus.CREATED);
    }

    @DeleteMapping
    public void deleteAllOrders() {

        repository.deleteAll();
    }

    @GetMapping
    public ResponseEntity<Collection<Order>> getAllOrders() {

        final List<Order> orders = repository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
