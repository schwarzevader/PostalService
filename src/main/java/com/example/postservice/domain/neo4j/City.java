package com.example.postservice.domain.neo4j;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Node("City")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Property("cityName")
    private String cityName;

    @Relationship(type = "HAS_POST_OFFICE", direction = Relationship.Direction.OUTGOING)
    private List<PostOffice> postOffices = new ArrayList<>();

    @Relationship(type = "HAS_PARCEL", direction = Relationship.Direction.OUTGOING)
    private List<PostalParcel> postalParcels = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", cityName:'" + cityName + '\'' +
//                ", postOffices:" + postOffices.toString() +
//                ", postalParcels:" + postalParcels.toString() +
                '}';
    }
}

