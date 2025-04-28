package com.example.postservice.domain.neo4j;


import com.example.postservice.util.utilTreeForPostOffice.EntityVisitor;
import com.example.postservice.util.utilTreeForPostOffice.Identifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Node("routeDistances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteDistanceToOffice implements Serializable {




    @Id
    @GeneratedValue
    private Long id;



    private double distance;


    @Relationship(type = "", direction = Relationship.Direction.INCOMING)
    private PostOffice toPostOffice;

    @Relationship(type = "", direction = Relationship.Direction.INCOMING)
    private PostalCar postalCar;

    public RouteDistanceToOffice(double distance, PostOffice toPostOffice, PostalCar postalCar) {
        this.distance = distance;
        this.toPostOffice = toPostOffice;
        this.postalCar = postalCar;
    }


    @Override
    public String toString() {
        return "RouteDistanceToOffice{" +
                "id=" + id +
                ", distance=" + distance +
                ", toPostOffice=" + toPostOffice.getId()+
                ", postalCar=" + postalCar.getId() +
                '}';
    }
}
