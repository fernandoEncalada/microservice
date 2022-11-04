package com.example.bikeservice.controller;

import com.example.bikeservice.entities.Bike;
import com.example.bikeservice.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> listBikes(){
        List<Bike> bikes = bikeService.getAll();
        if(bikes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBike(@PathVariable("id") int id){
        Bike bike = bikeService.getBikeById(id);
        if (bike == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    @PostMapping
    public ResponseEntity<Bike> saveBike(@RequestBody Bike bike){
        Bike newBike = bikeService.save(bike);
        return ResponseEntity.ok(newBike);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bike>> listCarByUserId(@PathVariable("userId") int userId){
        List<Bike> bikes = bikeService.byUserId(userId);
        if(bikes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }
}
