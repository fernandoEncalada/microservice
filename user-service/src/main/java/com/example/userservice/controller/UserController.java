package com.example.userservice.controller;

import com.example.userservice.entities.User;
import com.example.userservice.models.Bike;
import com.example.userservice.models.Car;
import com.example.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        List<User> users = userService.getAll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id){
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User newUser = userService.save(user);
        return ResponseEntity.ok(newUser);
    }
    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId){
        User user = userService.getUserById(userId);
        if(user == null)
            return ResponseEntity.notFound().build();

        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }
    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId){
        User user = userService.getUserById(userId);
        if(user == null)
            return ResponseEntity.notFound().build();

        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }
    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/car/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId, @RequestBody Car car){
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }
    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
    @PostMapping("/bike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId, @RequestBody Bike bike){
        Bike newBike = userService.saveBike(userId, bike);
        return ResponseEntity.ok(newBike);
    }
    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/all/{userId}")
    public ResponseEntity<Map<String, Object>> listAllVehicles(@PathVariable("userId") int userId){
        Map<String, Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") int id, RuntimeException exception){
        return new ResponseEntity("The user: " + id + "have the cars in the workshop", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCar(@PathVariable("userId") int id, @RequestBody Car car, RuntimeException exception){
        return new ResponseEntity("The user: " + id + "does not have money for the cars", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") int id, RuntimeException exception){
        return new ResponseEntity("The user: " + id + "have the bikes in the workshop", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallBackSaveBike(@PathVariable("userId") int id, @RequestBody Bike bike, RuntimeException exception){
        return new ResponseEntity("The user: " + id + "does not have money for the bikes", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>>  fallBackGetAll(@PathVariable("userId") int id, RuntimeException exception){
        return new ResponseEntity("The user: " + id + "have the vehicles in the workshop", HttpStatus.OK);
    }

}
