# Implementation Plan: import-endpoint

## Overview

Implementation tasks for the `POST /import` endpoint. The feature receives a binary file via `multipart/form-data`, validates the `.json` extension (case-insensitive), and returns HTTP 201 on success or HTTP 400 on failure. Follows Clean Architecture and SOLID principles.

---

## Tasks

- [x] 1. Create `InvalidExtensionException` in the domain layer
  - Create `InvalidExtensionException extends RuntimeException` in `com.ia.para.devs.mockai.domain.exception`
  - The class must have no imports outside the `domain` package
  - Accept a `String message` in the constructor and pass it to `super(message)`
  - **Requirement:** Requirements 4.1, 4.2, 5.3

- [x] 2. Create `FileData` domain model
  - Create class `FileData` in `com.ia.para.devs.mockai.domain.model`
  - Fields: `String originalFilename`, `byte[] content`
  - No Spring or JPA annotations — pure Java
  - Provide an all-args constructor and getters (use Lombok `@Value` or manual implementation)
  - **Requirement:** Design document — `FileData` component

- [x] 3. Create `ValidateFileUseCase` input port
  - Create interface `ValidateFileUseCase` in `com.ia.para.devs.mockai.application.port.in`
  - Single method: `void validate(FileData file)`
  - No Spring annotations on the interface itself
  - **Requirement:** Requirements 5.1, 5.2

- [x] 4. Create `ValidateFileService` application service
  - Create class `ValidateFileService implements ValidateFileUseCase` in `com.ia.para.devs.mockai.application.service`
  - Annotate with `@Service`
  - Implement the validation algorithm from the design:
    1. Get `originalFilename` from `FileData`
    2. Find the last index of `.`
    3. If no `.` found, throw `InvalidExtensionException`
    4. Extract the substring after the last `.`
    5. Compare with `"json"` using `equalsIgnoreCase`
    6. If different, throw `InvalidExtensionException`
    7. If equal, return normally
  - No persistence, HTTP, or external communication logic
  - **Requirement:** Requirements 2.1, 2.2, 2.3, 2.4, 5.2

- [x] 5. Create `ImportResponse` DTO
  - Create `ImportResponse` in `com.ia.para.devs.mockai.adapter.in.web.dto`
  - Single field: `String message`
  - Use Java `record` or a class with constructor and getter
  - **Requirement:** Requirements 3.2, 4.2

- [x] 6. Create `ImportController` REST adapter
  - Create class `ImportController` in `com.ia.para.devs.mockai.adapter.in.web`
  - Annotate with `@RestController`
  - Inject `ValidateFileUseCase` exclusively via constructor (no field injection, no concrete type)
  - Expose `POST /import` consuming `multipart/form-data` with `@RequestPart("file") MultipartFile file`
  - Map `MultipartFile` to `FileData` using `file.getOriginalFilename()` and `file.getBytes()`
  - Delegate to `validateFileUseCase.validate(fileData)`
  - Return `ResponseEntity.status(201).body(new ImportResponse("Arquivo importado com sucesso"))` on success
  - Do NOT catch any exceptions — delegate entirely to the `@ControllerAdvice`
  - **Requirement:** Requirements 1.1, 1.3, 3.1, 3.2, 5.1, 5.4, 5.6

- [x] 7. Create `GlobalExceptionHandler` controller advice
  - Create class `GlobalExceptionHandler` in `com.ia.para.devs.mockai.adapter.in.web.handler`
  - Annotate with `@ControllerAdvice`
  - Add method `handleInvalidExtension(InvalidExtensionException ex)` annotated with `@ExceptionHandler(InvalidExtensionException.class)`
  - Return `ResponseEntity.badRequest().body(new ImportResponse("Arquivo com extensão inválida, deve ser .json"))`
  - The response body must use the exact literal string — do NOT use `ex.getMessage()`
  - **Requirement:** Requirements 4.1, 4.2, 5.5, 5.6

## Task Dependency Graph

```json
{
  "waves": [
    { "wave": 1, "tasks": ["1"] },
    { "wave": 2, "tasks": ["2", "5"] },
    { "wave": 3, "tasks": ["3"] },
    { "wave": 4, "tasks": ["4"] },
    { "wave": 5, "tasks": ["6", "7"] }
  ]
}
```

## Notes

- All tasks are pure implementation tasks.
- All class names in this plan use the English names from the design document (`ValidateFileService`, `ValidateFileUseCase`, `InvalidExtensionException`, etc.) — the glossary in requirements.md maps Portuguese names to these English equivalents.
