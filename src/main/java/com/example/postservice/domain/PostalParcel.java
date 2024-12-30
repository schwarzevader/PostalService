package com.example.postservice.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "postalParcel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "postal_parcels")
public class PostalParcel implements Serializable {


    // добавиить курьерскую доставку по адресу updatable=false

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "postal_parcel_id" ,unique = true, nullable = false)
    private Long id;


    @Enumerated(EnumType.STRING)
    private  ParcelStatus parcelStatus;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "parcel_client_id")
    private ParcelClient parcelSender;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "parcel_client_id" , insertable = false, updatable = false)
    private ParcelClient parcelRecipient;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "post_office_id")
//    @JsonBackReference
    private PostOffice start;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "post_office_id" , insertable = false, updatable = false)
    private PostOffice end;


    @Enumerated(EnumType.STRING)
    private  MethodOfReceiving methodOfReceiving;


    private  double parcelWidth;
    private  double parcelHeight;
    private  double parcelLength;
    private  double parcelWeight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostalParcel that = (PostalParcel) o;
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
                ", parcelStatus=" + parcelStatus.toString() +
                ", parcelSender=" + parcelSender.toString() +
                ", parcelRecipient=" + parcelRecipient.toString() +
                ", city=" + city.toString() +
                ", start=" + start.getId() +
                ", end=" + end.getId() +
                ", methodOfReceiving=" + methodOfReceiving.toString() +
                ", parcelWidth=" + parcelWidth +
                ", parcelHeight=" + parcelHeight +
                ", parcelLength=" + parcelLength +
                ", parcelWeight=" + parcelWeight +
                '}';
    }
}
