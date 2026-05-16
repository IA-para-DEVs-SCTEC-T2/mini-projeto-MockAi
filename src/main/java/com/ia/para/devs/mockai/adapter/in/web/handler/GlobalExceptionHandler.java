package com.ia.para.devs.mockai.adapter.in.web.handler;

import com.ia.para.devs.mockai.adapter.in.web.dto.ImportResponse;
import com.ia.para.devs.mockai.domain.exception.InvalidExtensionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidExtensionException.class)
    public ResponseEntity<ImportResponse> handleInvalidExtension(InvalidExtensionException ex) {
        return ResponseEntity.badRequest().body(new ImportResponse("Arquivo com extensão inválida, deve ser .json"));
    }
}
