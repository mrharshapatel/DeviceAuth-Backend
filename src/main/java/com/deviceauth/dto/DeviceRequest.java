package com.deviceauth.dto;
import jakarta.validation.constraints.*;
public record DeviceRequest(@NotBlank @Size(max=100) String deviceName,@NotBlank @Size(max=160) String deviceIdentifier) {}
