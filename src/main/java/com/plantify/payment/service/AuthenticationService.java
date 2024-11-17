package com.plantify.payment.service;

public interface AuthenticationService {

    boolean validateAdminRole();
    void validateOwnership(Long ownerId);
    Long getUserId();
}
