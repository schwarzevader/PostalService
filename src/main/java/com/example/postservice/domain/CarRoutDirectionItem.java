package com.example.postservice.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "carRoutDirectionItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car_rout_direction_items")
public class CarRoutDirectionItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "car_rout_direction_item_id" , unique = true, nullable = false)
    private Long id;





    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postal_car_id")
    private PostalCar postalCar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_direction_id")
    private RouteDirection routeDirection;


}
