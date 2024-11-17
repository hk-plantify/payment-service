package com.plantify.payment.service;

import com.plantify.payment.client.UserInfoProvider;
import com.plantify.payment.global.exception.ApplicationException;
import com.plantify.payment.global.exception.errorcode.PaymentErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserInfoProvider userInfoProvider;
    private final List<String> adminRoles;

    @Override
    public boolean validateAdminRole() {
        String role = userInfoProvider.getUserInfo().role();
        if (adminRoles.contains(role)) {
            return true;
        }
        throw new ApplicationException(PaymentErrorCode.UNAUTHORIZED_ACCESS);
    }

    @Override
    public void validateOwnership(Long ownerId) {
        Long userId = userInfoProvider.getUserInfo().userId();
        if (!userId.equals(ownerId)) {
            throw new ApplicationException(PaymentErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    @Override
    public Long getUserId() {
        return userInfoProvider.getUserInfo().userId();
    }
}
