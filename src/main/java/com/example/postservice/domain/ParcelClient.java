package com.example.postservice.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "parcelClient")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parcel_clients")
public class ParcelClient implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "parcel_client_id" ,  unique = true, nullable = false)
    private Long id;

    @NaturalId
    private String phoneNumber;

    private String mail;


    @OneToMany(	mappedBy = "parcelSender",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PostalParcel> sentParcels = new ArrayList<>();

    @OneToMany(	mappedBy = "parcelRecipient",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PostalParcel> receivedParcels = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParcelClient that = (ParcelClient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
