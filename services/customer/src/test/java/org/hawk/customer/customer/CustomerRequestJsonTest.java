package org.hawk.customer.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerRequestJsonTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldDeserializeNestedAddress() throws Exception {
        var payload = """
                {
                  "id": "cust-1",
                  "firstname": "Hawk",
                  "lastname": "User",
                  "email": "hawk@example.com",
                  "age": 25,
                  "address": {
                    "doorNumber": 42,
                    "fullAddress": "MG Road",
                    "pinCode": 560001
                  }
                }
                """;

        var request = objectMapper.readValue(payload, CustomerRequest.class);

        assertNotNull(request.address());
        assertEquals(42, request.address().getDoorNumber());
        assertEquals("MG Road", request.address().getFullAddress());
        assertEquals(560001, request.address().getPinCode());
    }

    @Test
    void shouldValidateAddressFieldsWhenNestedValuesAreNull() throws Exception {
        var payload = """
                {
                  "firstname": "Hawk",
                  "lastname": "User",
                  "email": "hawk@example.com",
                  "age": 25,
                  "address": {
                    "doorNumber": null,
                    "fullAddress": "MG Road",
                    "pinCode": null
                  }
                }
                """;

        var request = objectMapper.readValue(payload, CustomerRequest.class);
        var violations = validator.validate(request);

        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("address.doorNumber")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("address.pinCode")));
    }
}
