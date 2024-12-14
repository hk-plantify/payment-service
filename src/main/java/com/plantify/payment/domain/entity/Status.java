package com.plantify.payment.domain.entity;

import java.io.Serializable;

public enum Status implements Serializable {

    PENDING,
    PAYMENT,
    REFUND,
    CANCELLATION,
    SUCCESS,
    FAILED
}