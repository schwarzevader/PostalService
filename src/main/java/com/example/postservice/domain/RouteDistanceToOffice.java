package com.example.postservice.domain;


import com.example.postservice.util.utilTreeForPostOffice.EntityVisitor;
import com.example.postservice.util.utilTreeForPostOffice.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "routeDistances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "route_distances")
@RedisHash(value = "RouteDistanceToOffice")
public class RouteDistanceToOffice implements Serializable , Identifiable {


    public static EntityVisitor<RouteDistanceToOffice, PostalCar> ENTITY_VISITOR = new EntityVisitor<RouteDistanceToOffice, PostalCar>(RouteDistanceToOffice.class) {
        @Override
        public PostalCar getParent(RouteDistanceToOffice visitingObject) {
            return visitingObject.getPostalCar();
        }

        @Override
        public List<RouteDistanceToOffice> getChildren(PostalCar parent) {
            return parent.getRouterDirectoryAndDistance();
        }

        @Override
        public void setChildren(PostalCar parent) {
            parent.setRouterDirectoryAndDistance(new ArrayList<RouteDistanceToOffice>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "route_distance_id" , unique = true, nullable = false)
    private Long id;



    private double distance;


    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "post_office_id")
    private PostOffice toPostOffice;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "postal_car_id")
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
