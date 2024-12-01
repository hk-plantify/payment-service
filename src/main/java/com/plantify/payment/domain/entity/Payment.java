package com.plantify.payment.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(
        indexes = {
                @Index(name = "idx_payment_userId", columnList = "userId"),
                @Index(name = "idx_payment_status", columnList = "status"),
                @Index(name = "idx_payment_orderId", columnList = "orderId")
        }
)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long paymentId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long payId;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String orderName;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Method method;

    @Column(nullable = true)
    private String failureReason;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
