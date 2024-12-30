package com.example.postservice.dto;

import com.example.postservice.domain.RouteDistanceToOffice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RedisHash(value = "PostOfficeDto")
public class PostalCarDto {
    private Long carId;
    private List<RouteDistanceToOffice> routeDistanceToOfficeList = new ArrayList<>();
    private List<RouteDistanceToOfficeDto> routeDistanceToOfficeDtoList = new ArrayList<>();
}
