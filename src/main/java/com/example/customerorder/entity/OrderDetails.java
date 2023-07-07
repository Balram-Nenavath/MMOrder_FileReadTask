package com.example.customerorder.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "item_quantity")
    private Integer itemQuantity;

    @Column(name = "total_amount")
    private Double totalAmount;

    @CreatedDate
    @Column(name = "item_created_at")
    private LocalDateTime itemCreatedAt;

    @CreatedBy
    @Column(name = "item_created_by")
    private String itemCreatedBy;

    @LastModifiedBy
    @Column(name = "item_updated_by")
    private String itemUpdatedBy;

    @LastModifiedDate
    @Column(name = "item_updated_at")
    private LocalDateTime itemUpdatedAt;

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", order=" + order +
                ", itemName='" + itemName + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", itemQuantity=" + itemQuantity +
                ", totalAmount=" + totalAmount +
                ", itemCreatedAt=" + itemCreatedAt +
                ", itemCreatedBy='" + itemCreatedBy + '\'' +
                ", itemUpdatedBy='" + itemUpdatedBy + '\'' +
                ", itemUpdatedAt=" + itemUpdatedAt +
                '}';
    }
}