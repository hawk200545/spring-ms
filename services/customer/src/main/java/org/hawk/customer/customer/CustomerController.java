package org.hawk.customer.customer;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(
            @RequestBody @Valid CustomerRequest request
    ){
        customerService.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<Customer> findCustomerById(
            @PathVariable("customer-id") String id
    ){
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAllCustomers(){
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> customerExist(
            @PathVariable("customer-id") String id
    ){
        return ResponseEntity.ok(customerService.customerExist(id));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<?> deleteCustomer(
            @PathVariable("customer-id") String id
    ){
        customerService.deleteCustomer(id);
        return ResponseEntity.accepted().build();
    }

}
