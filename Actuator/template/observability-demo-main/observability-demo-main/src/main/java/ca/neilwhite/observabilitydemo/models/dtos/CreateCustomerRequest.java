package ca.neilwhite.observabilitydemo.models.dtos;

import jakarta.validation.constraints.NotEmpty;

public record CreateCustomerRequest(@NotEmpty String name) {
}
