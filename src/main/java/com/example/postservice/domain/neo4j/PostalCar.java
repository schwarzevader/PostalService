package com.example.postservice.domain.neo4j;


import com.example.postservice.util.utilTreeForPostOffice.EntityVisitor;
import com.example.postservice.util.utilTreeForPostOffice.Identifiable;
import org.springframework.data.neo4j.core.schema.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Node("PostalCar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostalCar implements Serializable, Identifiable {

    @Id @GeneratedValue
    private Long id;

    private String color;
    private String vinCode;
    private String carNumber;
    private String carBrand;
    private String carModel;

    @Relationship(type = "HAS_CAR", direction = Relationship.Direction.INCOMING)
    private PostOffice postOffice;

    @Relationship(type = "HAS_ROUTE", direction = Relationship.Direction.OUTGOING)
    private List<RouteDistanceToOffice> routerDirectoryAndDistance = new ArrayList<>();
//    public void  addRouteDistanceToOffice(PostOffice postOffice){
//        toPostOffices.add(new RouteDistanceToOffice());
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostalCar postalCar = (PostalCar) o;
        return Objects.equals(id, postalCar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PostalCar{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", vinCode='" + vinCode + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
//                ", postOffice=" + postOffice.getPostalCars().toString() +
                ", toPostOffices=" + routerDirectoryAndDistance.toString() +
                '}';
    }
}

