package github.chorman0773.sentry.authentication;

public class AuthenticationException extends Exception {
    public AuthenticationException() {
    }

    private static final String[] MSG_BY_CODE = {
        "No such user account",
        "Password Mismatch",
        "Invalid Authentication Token",

    };

    public AuthenticationException(String msg,int code) {
        super(String.format("%s: %s",msg,MSG_BY_CODE[code]));
    }
}
