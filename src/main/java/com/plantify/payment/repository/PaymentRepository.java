package com.plantify.payment.repository;

import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStatus(Status status);
    List<Payment> findByUserId(Long userId);
}
