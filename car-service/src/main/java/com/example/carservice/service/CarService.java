package com.example.carservice.service;

import com.example.carservice.entities.Car;
import com.example.carservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAll(){
        return carRepository.findAll();
    }

    public Car getCarById(int id){
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car user) {
        Car newUser = carRepository.save(user);
        return newUser;
    }

    public List<Car> byUserId(int userId){
        return carRepository.findByUserId(userId);
    }
}
