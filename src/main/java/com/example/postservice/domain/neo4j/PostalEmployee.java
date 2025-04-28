package com.example.postservice.domain.neo4j;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.io.Serializable;

@Node("postal_employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostalEmployee  implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
}
