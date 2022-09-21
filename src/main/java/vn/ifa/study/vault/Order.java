package vn.ifa.study.vault;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = TextEncryptDecryptConverter.class)
    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "product_name")
    private String productName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    @CreationTimestamp
    private Date orderDate;

}
