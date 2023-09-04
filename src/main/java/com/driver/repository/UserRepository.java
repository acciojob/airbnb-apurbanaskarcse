package com.driver.repository;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

@Repository
public class UserRepository{
    //	name and hotel
    HashMap<String,Hotel> hotelsDb=new HashMap<>();

    //	adharCardNo and user
    HashMap<Integer,User> usersDb=new HashMap<>();

    //	uuid and booking
    HashMap<String,Booking> bookingsDb=new HashMap<>();


    public String addHotel(Hotel hotel){
        try{
            if(hotel==null || hotel.getHotelName()==null || hotelsDb.containsKey(hotel.getHotelName())) return "FAILURE";
            hotelsDb.put(hotel.getHotelName(), hotel);
            return "SUCCESS";
        }catch(Exception e){
            return "FAILURE";
        }

    }

    public Integer addUser( User user){
        try{
            if(user==null || user.getName()==null || usersDb.containsKey(user.getaadharCardNo())) return -1;
            usersDb.put(user.getaadharCardNo(), user);
            return user.getaadharCardNo();
        }catch(Exception e){
            return -1;
        }


    }

    public String getHotelWithMostFacilities() {
        try{
            if(hotelsDb.isEmpty()) return "";
            int max=-1;
            for(String name:hotelsDb.keySet()) {

                max=Math.max(max,hotelsDb.get(name).getFacilities().size());
            }
            if(max<1) return "";
            List<String> l=new ArrayList<>();

            for(String name:hotelsDb.keySet()) {
                if(hotelsDb.get(name).getFacilities().size()==max) {
                    l.add(name);
                }
            }

            if(l.size()>1) Collections.sort(l);

            return l.get(0);
        }catch (Exception e){
            return "";
        }


    }

    public int bookARoom (Booking booking){
        try{
            if(booking ==null || booking.getBookingPersonName()==null || booking.getHotelName()==null) return -1;
            String hotelName=booking.getHotelName();


            int roomsBooked=booking.getNoOfRooms();

            if(hotelsDb.isEmpty() || !hotelsDb.containsKey(hotelName) || hotelsDb.get(hotelName)==null) return -1;

            if(roomsBooked==0 || roomsBooked>hotelsDb.get(hotelName).getAvailableRooms()) return -1;

            int leftRooms=hotelsDb.get(hotelName).getAvailableRooms()-roomsBooked;

            hotelsDb.get(hotelName).setAvailableRooms(leftRooms);

            int totalamount=hotelsDb.get(hotelName).getPricePerNight()*roomsBooked;

            booking.setAmountToBePaid(totalamount);

            String bookingId = UUID.randomUUID().toString();


            booking.setBookingId(bookingId);

            bookingsDb.put(bookingId, booking);

            return totalamount;

        }catch(Exception e){
            return -1;
        }


    }

    public int getBookings(Integer aadharCard) {
        try{
            if(aadharCard==null) return 0;
            int totalBookings=0;
            for(String uuid:bookingsDb.keySet()) {
                if(bookingsDb.get(uuid).getBookingAadharCard()==aadharCard) totalBookings++;
            }
            return totalBookings;
        }catch(Exception e){
            return 0;
        }

    }

    public Hotel updateFacilities(List<Facility> newFacilities,String hotelName)  {
        try{
            if(hotelName==null || newFacilities==null || !hotelsDb.containsKey(hotelName) ||
                    hotelsDb.get(hotelName)==null || hotelsDb.get(hotelName).getFacilities()==null)
                return new Hotel();
            Set<Facility> newUniqueFacilities=new HashSet<>();
            for(Facility f:newFacilities) {
                if(!hotelsDb.get(hotelName).getFacilities().contains(f)) newUniqueFacilities.add(f);
            }
            for(Facility f:newUniqueFacilities) {
                hotelsDb.get(hotelName).getFacilities().add(f);
            }
            System.out.println(hotelsDb.get(hotelName).getFacilities().size());
            return hotelsDb.get(hotelName);
        }catch (Exception e){
            return new Hotel();
        }

    }

}