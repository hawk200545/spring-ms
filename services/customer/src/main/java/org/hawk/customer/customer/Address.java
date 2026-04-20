package org.hawk.customer.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @NotNull(message = "Door number is required")
    private Integer doorNumber;

    @NotBlank(message = "Full address is required")
    private String fullAddress;

    @NotNull(message = "Pin code is required")
    private Integer pinCode;
}
