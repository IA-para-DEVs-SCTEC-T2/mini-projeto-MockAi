# Requirements Document

## Introduction

This feature implements the `POST /import` endpoint of the MockAI system, responsible for receiving a binary file via `multipart/form-data` and validating the file extension. The endpoint returns a success response when the file has a `.json` extension, or an error response when the extension is invalid. Reading the file content and persisting it to a database are out of scope for this feature.

## Glossary

- **Import_Controller**: Component in the `adapter/in/web` layer responsible for exposing the `POST /import` endpoint and delegating execution to the use case.
- **Validar_Arquivo_Use_Case**: Interface in the `application/port/in` layer that defines the input contract for validating the received file.
- **Validar_Arquivo_Service**: Implementation in the `application/service` layer that executes the file extension validation logic.
- **Arquivo**: Representation of the binary file received via `multipart/form-data`, identified by its original name and byte content.
- **Extensão**: Suffix of the file name after the last `.` character, used to identify the file type (e.g., `.json`).
- **Import_Response**: DTO in the `adapter/in/web` layer that encapsulates the response message returned to the client.
- **Extensao_Invalida_Exception**: Domain exception thrown when the file extension is not `.json`.

## Requirements

### Requirement 1: File Reception via REST Endpoint

**User Story:** As a developer, I want to send a file via `POST /import`, so that the system validates the extension and informs me whether the file is accepted.

#### Acceptance Criteria

1. THE `Import_Controller` SHALL expose the `POST /import` endpoint that accepts `multipart/form-data` requests with a mandatory file field named `file`.
2. IF a `POST /import` request is received without the `file` field, THEN THE `Import_Controller` SHALL return HTTP status 400 with a body containing the error message.
3. IF a `POST /import` request is received with the `file` field present, THEN THE `Import_Controller` SHALL delegate validation to the `Validar_Arquivo_Use_Case`.

### Requirement 2: File Extension Validation

**User Story:** As a developer, I want the system to validate the extension of the uploaded file, so that only `.json` files are accepted.

#### Acceptance Criteria

1. WHEN the received file name ends with the `.json` extension (case-insensitive), THE `Validar_Arquivo_Service` SHALL complete validation successfully without throwing an exception.
2. WHEN the received file name does not end with the `.json` extension, THE `Validar_Arquivo_Service` SHALL throw an `Extensao_Invalida_Exception`.
3. WHEN the received file name contains no `.` character, THE `Validar_Arquivo_Service` SHALL throw an `Extensao_Invalida_Exception`.
4. THE `Validar_Arquivo_Service` SHALL perform the extension comparison without case distinction (e.g., `.JSON`, `.Json` must be accepted).

### Requirement 3: Success Response for `.json` Files

**User Story:** As a developer, I want to receive a clear confirmation when the uploaded file is valid, so that I know the import was accepted.

#### Acceptance Criteria

1. WHEN the `Import_Controller` receives a file upload request and the file extension is `.json`, THEN THE `Import_Controller` SHALL return HTTP status 201.
2. WHEN the `Import_Controller` receives a file upload request and the file extension is `.json`, THEN THE `Import_Controller` SHALL return an `Import_Response` with the `message` field equal to `"Arquivo importado com sucesso"`.

### Requirement 4: Error Response for Invalid Extension

**User Story:** As a developer, I want to receive a descriptive error message when the uploaded file has an invalid extension, so that I know the reason for rejection.

#### Acceptance Criteria

1. WHEN an `Extensao_Invalida_Exception` is thrown, THEN THE exception handler SHALL return HTTP status 400 without persisting any data.
2. WHEN an `Extensao_Invalida_Exception` is thrown, THEN THE exception handler SHALL return an `Import_Response` with the `message` field equal to `"Arquivo com extensão inválida, deve ser .json"`.

### Requirement 5: Responsibility Isolation by Layer (Clean Architecture)

**User Story:** As a developer, I want the implementation to follow Clean Architecture and SOLID principles, so that the code is maintainable and testable in isolation.

#### Acceptance Criteria

1. THE `Import_Controller` SHALL depend exclusively on the `Validar_Arquivo_Use_Case` interface, with no field, constructor parameter, or import of any concrete implementation type.
2. THE `Validar_Arquivo_Service` SHALL implement the `Validar_Arquivo_Use_Case` interface and contain exclusively the extension validation logic, with no persistence, external communication, or HTTP transformation responsibilities.
3. IF the `Extensao_Invalida_Exception` contains any import of a type outside the `domain` package, THEN the build SHALL fail.
4. THE `Import_Controller` SHALL receive the `Validar_Arquivo_Use_Case` exclusively via constructor injection.
5. WHEN a domain exception is thrown, THEN THE exception handler SHALL map it to the corresponding HTTP response without exposing stack trace, class name, or raw exception message to the client.
6. THE `Import_Controller` SHALL not catch domain exceptions directly; exception handling SHALL be delegated to the exception handling component (`@ControllerAdvice`).
