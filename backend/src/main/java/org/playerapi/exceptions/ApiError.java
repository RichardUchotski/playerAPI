package org.playerapi.exceptions;

import java.time.LocalDateTime;

public record ApiError(String path, String message, int code, LocalDateTime timestamp, String details,
                       Throwable throwable, StackTraceElement[] stackTrace) {
}
