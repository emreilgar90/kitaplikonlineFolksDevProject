package com.kitaplik.libraryservice.exception;

public record ExceptionMessage(

        /**record final class'dır. **/
        String timestamp,
        int status,
        String error,
        String message,
        String path) {
}
