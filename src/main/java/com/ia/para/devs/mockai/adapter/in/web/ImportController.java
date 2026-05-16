package com.ia.para.devs.mockai.adapter.in.web;

import com.ia.para.devs.mockai.adapter.in.web.dto.ImportResponse;
import com.ia.para.devs.mockai.application.port.in.ValidateFileUseCase;
import com.ia.para.devs.mockai.domain.model.FileData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImportController {

    private final ValidateFileUseCase validateFileUseCase;

    public ImportController(ValidateFileUseCase validateFileUseCase) {
        this.validateFileUseCase = validateFileUseCase;
    }

    @PostMapping(value = "/import", consumes = "multipart/form-data")
    public ResponseEntity<ImportResponse> importFile(@RequestPart("file") MultipartFile file) throws IOException {
        FileData fileData = new FileData(file.getOriginalFilename(), file.getBytes());
        validateFileUseCase.validate(fileData);
        return ResponseEntity.status(201).body(new ImportResponse("Arquivo importado com sucesso"));
    }
}
