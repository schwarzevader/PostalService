package com.example.postservice.domain;


import com.example.postservice.util.utilTreeForPostOffice.EntityVisitor;
import com.example.postservice.util.utilTreeForPostOffice.Identifiable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "postalCar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "postal_cars")
public class PostalCar implements Serializable, Identifiable {



    public static EntityVisitor<PostalCar, PostOffice> ENTITY_VISITOR = new EntityVisitor<PostalCar, PostOffice>(PostalCar.class) {

        @Override
        public PostOffice  getParent(PostalCar visitingObject) {
            return visitingObject.getPostOffice();
        }

        @Override
        public List<PostalCar> getChildren(PostOffice parent) {
            return parent.getPostalCars();
        }

        @Override
        public void setChildren(PostOffice parent) {
            parent.setPostalCars(new ArrayList<PostalCar>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "postal_car_id" , unique = true, nullable = false)
    private Long id;

    private String color;

    private String vinCode;

    private String carNumber;



    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "postOffice_id")
    private PostOffice postOffice;

    @OneToMany(	mappedBy = "postalCar",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<RouteDistanceToOffice> toPostOffices =new ArrayList<>();

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
}

