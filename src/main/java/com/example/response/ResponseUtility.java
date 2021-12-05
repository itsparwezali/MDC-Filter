package com.example.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseUtility {

    public static <T> ServerResponse<T> getSuccessfulServerResponseDTO(T data,String message) {

        ServerResponse<T> serverResponse = new ServerResponse<>();
        serverResponse.setSuccess(true);
        serverResponse.setTimestamp(LocalDateTime.now());
        serverResponse.setMessage(message);
        serverResponse.setData(data);
        serverResponse.setHttpStatus(HttpStatus.OK);
        return serverResponse;
    }

    public static ServerResponse getFailedServerResponse(String message){

         return ServerResponse.builder()
                 .success(false)
                 .timestamp(LocalDateTime.now())
                 .message(message)
                 .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                 .build();
    }

    public static ServerResponse getCreatedServerResponse(String message) {
        return ServerResponse.builder()
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(message)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public static ServerResponse getUpdatedServerResponse(String message) {
        return ServerResponse.builder()
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(message)
                .httpStatus(HttpStatus.ACCEPTED)
                .build();
    }


    public static ServerResponse getNotFoundServerResponse(String message) {
        return ServerResponse.builder()
                .success(false)
                .timestamp(LocalDateTime.now())
                .message(message)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    public static ServerResponse getBadRequestServerResponse(String message) {
        return ServerResponse.builder()
                .success(false)
                .timestamp(LocalDateTime.now())
                .message(message)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
}