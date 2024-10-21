package com.uzu.user.exceptions.advice.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // General Errors
    BAD_REQUEST("400"),

    NOT_FOUND("404"),

    DUPLICATE_RECORD("409"),

    INTERNAL_SERVER_ERROR("500"),

    // User-Related Errors
    INVALID_CREDENTIALS("401"),

    USER_NOT_FOUND("404"),

    USER_ALREADY_EXISTS("409"),

    INSUFFICIENT_PERMISSIONS("403"),

    PASSWORD_MISMATCH("400"),

    INVALID_EMAIL("400"),

    INVALID_PHONE_NUMBER("400"),

    INVALID_PASSWORD_FORMAT("400"),

    ACCOUNT_DISABLED("403"),

    ACCOUNT_LOCKED("403"),

    TOO_MANY_FAILED_LOGIN_ATTEMPTS("403"),

    VERIFICATION_CODE_INVALID("400"),

    VERIFICATION_CODE_EXPIRED("400"),

    PASSWORD_RESET_TOKEN_INVALID("400"),

    PASSWORD_RESET_TOKEN_EXPIRED("400"),

    PROFILE_UPDATE_FAILED("500"),

    // Role-Related Errors

    ROLE_NOT_FOUND("404"),

    ROLE_ALREADY_EXISTS("409"),

    INSUFFICIENT_ROLE_PERMISSIONS("403"),

    // Group-Related Errors
    GROUP_NOT_FOUND("404"),

    GROUP_ALREADY_EXISTS("409"),

    USER_ALREADY_IN_GROUP("409"),

    USER_NOT_IN_GROUP("404"),

    // Activity Log-Related Errors
    ACTIVITY_LOG_NOT_FOUND("404"),

    // API Key-Related Errors
    API_KEY_NOT_FOUND("404"),

    API_KEY_INVALID("401"),

    API_KEY_EXPIRED("401"),

    API_KEY_REVOKED("401"),

    API_KEY_RATE_LIMIT_EXCEEDED("403"),

    // Webhook-Related Errors
    WEBHOOK_NOT_FOUND("404"),

    WEBHOOK_INVALID_URL("400"),

    WEBHOOK_DELIVERY_FAILED("500"),

    // Other Errors
    UNSUPPORTED_OPERATION("400"),

    INVALID_INPUT("400"),

    SERVER_BUSY("503"),

    SERVICE_UNAVAILABLE("503"),

    TIMEOUT("504");

    private final String code;
}
