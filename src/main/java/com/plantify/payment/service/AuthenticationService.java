package com.plantify.payment.service;

public interface AuthenticationService {

    void validateOwnership(Long ownerId);
    Long getUserId();
}
