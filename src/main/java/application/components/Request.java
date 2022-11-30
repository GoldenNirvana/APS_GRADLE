package application.components;

import org.jetbrains.annotations.NotNull;

public record Request(int patientId, @NotNull String requestId, double startTime) {
}