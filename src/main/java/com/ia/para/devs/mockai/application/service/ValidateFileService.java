package com.ia.para.devs.mockai.application.service;

import com.ia.para.devs.mockai.application.port.in.ValidateFileUseCase;
import com.ia.para.devs.mockai.domain.exception.InvalidExtensionException;
import com.ia.para.devs.mockai.domain.model.FileData;
import org.springframework.stereotype.Service;

@Service
public class ValidateFileService implements ValidateFileUseCase {

    @Override
    public void validate(FileData file) {
        String filename = file.getOriginalFilename();
        int lastDotIndex = filename.lastIndexOf('.');

        if (lastDotIndex == -1) {
            throw new InvalidExtensionException("Arquivo com extensão inválida, deve ser .json");
        }

        String extension = filename.substring(lastDotIndex + 1);

        if (!extension.equalsIgnoreCase("json")) {
            throw new InvalidExtensionException("Arquivo com extensão inválida, deve ser .json");
        }
    }
}
