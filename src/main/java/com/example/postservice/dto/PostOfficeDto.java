package com.example.postservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RedisHash(value = "PostOfficeDto")
public class PostOfficeDto implements Serializable {

    private Long postOfficeId;
    private double distance;
    private double heuristic;
    private List<PostalCarDto> postalCarDtoList = new ArrayList<>();

}
