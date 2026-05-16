package com.ia.para.devs.mockai.domain.model;

import lombok.Value;

@Value
public class FileData {

    String originalFilename;
    byte[] content;
}
