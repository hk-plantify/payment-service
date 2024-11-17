package com.plantify.payment.service;

import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

public interface InternalService {

    Status validateStatusTransition(Status status, Payment payment);
}
