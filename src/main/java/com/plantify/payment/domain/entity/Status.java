package com.plantify.payment.domain.entity;

import java.io.Serializable;

public enum Status implements Serializable {
    PENDING,
    SUCCESS,
    CANCELLED,
    FAILED
}