package com.example.userservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.example.userservice.models.Car;

import java.util.List;

@FeignClient(name = "car-service")
@RequestMapping("/car")
public interface CarFeignClient {
    @PostMapping()
    public Car save(@RequestBody Car car);

    @GetMapping("/user/{userId}")
    public List<Car> getCars(@PathVariable("userId") int userId);
}
