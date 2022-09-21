package vn.ifa.study.vault;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerName(String customerName);

    List<Order> findByProductName(String productName);
}