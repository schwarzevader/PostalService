package com.example.postservice.domain;

import com.example.postservice.util.utilTreeForPostOffice.EntityVisitor;
import com.example.postservice.util.utilTreeForPostOffice.Identifiable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "postOffice")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post_office")
@RedisHash(value = "PostOffice")
public class PostOffice implements Serializable , Identifiable {


    public static EntityVisitor<PostOffice, Identifiable> ENTITY_VISITOR = new EntityVisitor<PostOffice, Identifiable>(PostOffice.class) {};

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_office_id" , unique = true, nullable = false)
    private Long id;




    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    @JsonIgnore
    private City city;
    private String street;
    private String houseNumber;

    @Transient
    private  double fromStartToThisPostOffice;

    @Transient
    private double distance;

    @Transient
    private double heuristic;

    @OneToMany(	mappedBy = "postOffice",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PostalCar> postalCars = new ArrayList<>();


    @OneToMany(	mappedBy = "start",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
//    @JsonManagedReference
    private List<PostalParcel> parcels= new ArrayList<>();



    @OneToMany(	mappedBy = "end",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PostalParcel> receivedParcels= new ArrayList<>();

    @OneToMany(	mappedBy = "toPostOffice",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
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


