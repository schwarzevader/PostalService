package com.example.postservice.domain.neo4j;


import com.example.postservice.domain.MethodOfReceiving;
import com.example.postservice.domain.ParcelStatus;
import org.springframework.data.neo4j.core.schema.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.Objects;

@Node("postal_parcel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostalParcel implements Serializable {


    // добавиить курьерскую доставку по адресу updatable=false

    @Id
    @GeneratedValue

    private Long id;




    private ParcelStatus parcelStatus;

    @Relationship(type = "parcelSender", direction = Relationship.Direction.INCOMING)
    private ParcelClient parcelSender;

    @Relationship(type = "parcelRecipient", direction = Relationship.Direction.INCOMING)
    private ParcelClient parcelRecipient;

    @Relationship(type = "CITY", direction = Relationship.Direction.INCOMING)
    private City city;

    @Relationship(type = "start", direction = Relationship.Direction.INCOMING)
    private PostOffice start;

    @Relationship(type = "CITY", direction = Relationship.Direction.INCOMING)
    private PostOffice end;


    private MethodOfReceiving methodOfReceiving;


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
