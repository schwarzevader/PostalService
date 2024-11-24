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

@Entity(name = "routeDirection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "route_directions")
public class RouteDirection implements Serializable , Identifiable {


    public static EntityVisitor<RouteDirection, PostOffice> ENTITY_VISITOR = new EntityVisitor<RouteDirection, PostOffice>(RouteDirection.class) {
        @Override
        public PostOffice getParent(RouteDirection visitingObject) {
            return visitingObject.getPostOfficeStart();
        }

        @Override
        public List<RouteDirection> getChildren(PostOffice parent) {
            return parent.getRouteDirections();
        }

        @Override
        public void setChildren(PostOffice parent) {
            parent.setRouteDirections(new ArrayList<RouteDirection>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "route_direction_id", unique = true, nullable = false)
    private Long id;

    private double distance;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postOffice_id")
    private PostOffice postOfficeStart;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postOffice_id", insertable = false, updatable = false)
    private PostOffice postOfficeGoal;


    @OneToMany(mappedBy = "routeDirection",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CarRoutDirectionItem> carRoutDirectionItemList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteDirection that = (RouteDirection) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
