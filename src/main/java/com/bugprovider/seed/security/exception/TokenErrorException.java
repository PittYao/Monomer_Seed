package com.bugprovider.seed.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author BugProvider
 * @date 2021/11/21
 * @apiNote
 */
public class TokenErrorException extends AuthenticationException {
    public TokenErrorException(String msg) {
        super(msg);
    }

    public TokenErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
