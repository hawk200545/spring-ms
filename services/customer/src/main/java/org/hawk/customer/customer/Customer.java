package org.hawk.customer.customer;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Document
public class Customer {
    @Id
    String id;
    String firstName;
    String lastName;
    Address address;
    int age;
}
