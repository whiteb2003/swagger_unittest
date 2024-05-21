package com.swagger.swagger.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.swagger.swagger.config.ApplicationContextHolder;
import com.swagger.swagger.internationalization.MyLocalResolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

/**
 * Enum defining error codes and messages.
 *
 * <p>
 * Error codes are categorized as follows:
 * </p>
 * <ul>
 * <li>1000000: Successfully!</li>
 * <li>1000001-1999999: Authentication Errors - Errors occurring during
 * authentication processes.</li>
 * <li>2000001-2999999: Authorization Errors - Errors related to unauthorized
 * access attempts.</li>
 * <li>3000001-3999999: Service Errors - Errors occurring during server-side
 * processing.</li>
 * <li>4000001-4999999: Formatting Errors - Errors due to data format
 * mismatches.</li>
 * <li>5000001-5999999: Query Errors - Errors related to database queries.</li>
 * <li>6000001-6999999: Network Errors - Errors occurring during network
 * communication.</li>
 * <li>7000001-7999999: Access Resources Errors - Resource access errors occur
 * when accessing the wrong path or cors error,...</li>
 * <li>9000001-9999999: Undefined Errors - Errors not categorized into specific
 * types.</li>
 * </ul>
 */
@Getter
public enum ErrorCode {
    // 1000001-1999999: Authentication Errors
    UNAUTHENTICATED(1000001, "l10n.msg.authentication.unauthenticated", HttpStatus.UNAUTHORIZED),

    // 2000001-2999999: Authorization Errors
    UNAUTHORIZED(2000001, "l10n.msg.authentication.unauthorized", HttpStatus.FORBIDDEN),

    // 3000001-3999999: Service Errors
    FIELD_DUPLICATE(3000001, "l10n.msg.service.field_duplicate", HttpStatus.BAD_REQUEST),
    FIELD_NOT_EXISTED(3000002, "l10n.msg.service.field_not_existed", HttpStatus.BAD_REQUEST),
    FIELD_INCORRECT(3000003, "l10n.msg.service.field_incorrect", HttpStatus.BAD_REQUEST),
    LOGIN_INCORRECT(3000004, "l10n.msg.service.login_incorrect", HttpStatus.BAD_REQUEST),

    // 4000001-4999999: Formatting Errors
    FIELD_INCORRECT_FORMAT(4000001, "l10n.msg.formatting.field_incorrect_format", HttpStatus.BAD_REQUEST),
    FIELD_LENGTH_INVALID(4000002, "l10n.msg.formatting.field_length_invalid", HttpStatus.BAD_REQUEST),
    FIELD_MIN_INVALID(4000003, "l10n.msg.formatting.field_min_invalid", HttpStatus.BAD_REQUEST),
    FIELD_MAX_INVALID(4000004, "l10n.msg.formatting.field_max_invalid", HttpStatus.BAD_REQUEST),
    FIELD_NOT_NULL(4000005, "l10n.msg.formatting.field_not_null", HttpStatus.BAD_REQUEST),
    FIELD_NOT_EMPTY(4000006, "l10n.msg.formatting.field_not_empty", HttpStatus.BAD_REQUEST),
    FIELD_NOT_BLANK(4000007, "l10n.msg.formatting.field_not_blank", HttpStatus.BAD_REQUEST),

    // 5000001-5999999: Query Errors

    // 6000000-6999999: Network Errors

    // 7000000-7999999: Access Resources Errors
    NOT_FOUND(7000001, "l10n.msg.access_resources.not_found", HttpStatus.NOT_FOUND),

    // 9000000-9999999: Undefined Errors
    UNCATEGORIZED_EXCEPTION(9999999, "l10n.msg.undefined.uncategorized_exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9000001, "l10n.msg.undefined.invalid_key", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String messageKey;
    private final HttpStatus statusCode;

    ErrorCode(int code, String messageKey, HttpStatus statusCode) {
        this.code = code;
        this.messageKey = messageKey;
        this.statusCode = statusCode;
    }

    // public String getMessage(HttpServletRequest request, Object... args) {
    // MessageSource messageSource =
    // ApplicationContextHolder.getContext().getBean(MessageSource.class);
    // MyLocalResolver myLocalResolver = new MyLocalResolver();
    // return messageSource.getMessage(messageKey, args,
    // myLocalResolver.resolveLocale(request));
    // }
}
