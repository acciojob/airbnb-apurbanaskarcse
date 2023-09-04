package com.driver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repository.UserRepository;

@Service
public class UserService {

    //	@Autowired
    UserRepository userRepository=new UserRepository();

    public String addHotel(Hotel hotel){
        return userRepository.addHotel(hotel);
    }

    public Integer addUser( User user){

        return userRepository.addUser(user);

    }

    public String getHotelWithMostFacilities() {
        return userRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        return userRepository.bookARoom(booking);
    }

    public int getBookings(Integer aadharCard) {
        return userRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities,String hotelName)  {
        return userRepository.updateFacilities(newFacilities, hotelName);
    }
}