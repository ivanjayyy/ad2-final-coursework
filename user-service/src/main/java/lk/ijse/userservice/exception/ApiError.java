package lk.ijse.userservice.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ApiError {
    private Instant timestamp = Instant.now();
    private int status;
    private String error;
    private String message;
    private String path;

    public ApiError(int status, String message, String error, String path) {
        this.path = path;
        this.message = message;
        this.error = error;
        this.status = status;
    }
}
