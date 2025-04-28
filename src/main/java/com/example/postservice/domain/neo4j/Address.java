package com.example.postservice.domain.neo4j;


import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Node("Address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private String street;
    private String houseNumber;
}
