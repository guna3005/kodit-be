package com.kodit.application.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{
//    @Override
//    public void printStackTrace() {
//        super.printStackTrace();
//    }

    private final ApiExceptionResponse errorResponse;

}
