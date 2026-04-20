package org.hawk.customer.customer;

import ch.qos.logback.core.util.StringUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.hawk.customer.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepo customerRepo;

    public String createCustomer(CustomerRequest customerRequest){
            Customer customer = Customer.builder()
                    .firstName(customerRequest.firstname())
                    .lastName(customerRequest.lastname())
                    .age(customerRequest.age())
                    .address(customerRequest.address())
                    .build();
            return customerRepo.save(customer).id;
    }


    public void updateCustomer(@NotNull  CustomerRequest request) {
        var customer = this.customerRepo.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer found with the provided ID: %s", request.id())
                ));
        mergeCustomer(customer, request);
        this.customerRepo.save(customer);
    }
    private void mergeCustomer(Customer customer, CustomerRequest request){
        if(StringUtils.isNotBlank(request.firstname())){
            customer.setFirstName(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())){
            customer.setLastName(request.lastname());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }
        if(request.age() != 0){
            customer.setAge((request.age()));
        }
    }

    public Customer findCustomerById(String id) {
        return customerRepo.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException(
                        String.format("Customer with id : %s is not found", id)
                ));
    }

    public List<Customer> findAllCustomers() {
        return customerRepo.findAll();
    }

    public Boolean customerExist(String id) {
        return customerRepo.existsById(id);
    }

    public void deleteCustomer(String id) {
        customerRepo.deleteById(id);
    }
}
