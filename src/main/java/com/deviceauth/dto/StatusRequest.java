package com.deviceauth.dto;
import com.deviceauth.entity.DeviceStatus;
import jakarta.validation.constraints.NotNull;
public record StatusRequest(@NotNull DeviceStatus status) {}
