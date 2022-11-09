package com.example.userservice.feignclients;

import com.example.userservice.models.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "bike-service")
@RequestMapping("/bike")
public interface BikeFeignClient {

    @PostMapping()
    public Bike save(@RequestBody Bike bike);

    @GetMapping("/user/{userId}")
    public List<Bike> getBikes(@PathVariable("userId") int userId);
}
