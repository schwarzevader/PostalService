package com.example.postservice.domain.neo4j;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Node("ParcelClient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParcelClient {

    @Id
    @GeneratedValue
    private Long id;


    private String phoneNumber;

    private String mail;

    @Relationship(type = "SENT_PARCEL", direction = Relationship.Direction.OUTGOING)
    private List<PostalParcel> sentParcels = new ArrayList<>();

    @Relationship(type = "RECEIVED_PARCEL", direction = Relationship.Direction.OUTGOING)
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
