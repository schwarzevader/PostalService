package com.example.postservice.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "city")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "city_id" , unique = true, nullable = false)
    private Long id;

    private String cityName;

    @OneToMany(	mappedBy = "city",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PostOffice> postOffices = new ArrayList<>();


    @OneToMany(	mappedBy = "city",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
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
                ", postOffices:" + postOffices.toString() +
//                ", postalParcels:" + postalParcels.toString() +
                '}';
    }
}

