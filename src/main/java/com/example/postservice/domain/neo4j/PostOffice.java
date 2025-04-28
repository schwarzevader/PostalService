package com.example.postservice.domain.neo4j;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import org.springframework.data.redis.core.RedisHash;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Node("Post_Office")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostOffice implements Serializable  {




    @Id
    @GeneratedValue
    private Long id;




    @Relationship(type = "CITY", direction = Relationship.Direction.INCOMING)
    private City city;
    private String street;
    private String houseNumber;


    private  double fromStartToThisPostOffice;


    private double distance;


    private double heuristic;

    @Relationship(type = "", direction = Relationship.Direction.OUTGOING)
    private List<PostalCar> postalCars = new ArrayList<>();


    @Relationship(type = "", direction = Relationship.Direction.OUTGOING)
    private List<PostalParcel> parcels= new ArrayList<>();



    @Relationship(type = "", direction = Relationship.Direction.OUTGOING)
    private List<PostalParcel> receivedParcels= new ArrayList<>();

    @Relationship(type = "", direction = Relationship.Direction.OUTGOING)
    private List<RouteDistanceToOffice> routeDistanceToOffices= new ArrayList<>();

   private double latitude;
   private double longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostOffice that = (PostOffice) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
//                ", city=" + city.toString() +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", fromStartToThisPostOffice=" + fromStartToThisPostOffice +
                ", distance=" + distance +
                ", heuristic=" + heuristic +
                ", postalCars=" + postalCars +
//                ", parcels=" + parcels +
//                ", receivedParcels=" + receivedParcels +
                ", routeDistanceToOffices=" + routeDistanceToOffices.toString() +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}


