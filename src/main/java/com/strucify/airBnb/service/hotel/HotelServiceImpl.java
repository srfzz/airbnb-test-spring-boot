package com.strucify.airBnb.service.hotel;

import com.strucify.airBnb.dto.hotelDto.Hoteldto;
import com.strucify.airBnb.entity.Hotel;
import com.strucify.airBnb.exceptions.ResourceNotFoundException;
import com.strucify.airBnb.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Hoteldto createHotel(Hoteldto hoteldto) {

        Hotel hotel = modelMapper.map(hoteldto, Hotel.class);
        hotel.setActive(false);

        Hotel savedHotel=hotelRepository.save(hotel);
        log.info("Hotel saved successfully");
        return modelMapper.map(savedHotel, Hoteldto.class);

    }

    @Override
    @Transactional(readOnly = true)
    public Hoteldto getByHotelId(Long hotelId) {
        log.info("Getting hotel by hotelId {}", hotelId);
        Hotel hotel=hotelRepository.findById(hotelId).orElseThrow(()->new ResourceNotFoundException("Hotel not found"));
        log.info("Hotel found successfully");
        return modelMapper.map(hotel,Hoteldto.class);

    }

    @Override
    public Hoteldto updateHotelById(Long id, Hoteldto hoteldto) {
        Hotel hotel=hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel not found"));

        modelMapper.map(hoteldto,hotel);

       hotelRepository.save(hotel);
       return modelMapper.map(hotel,Hoteldto.class);

    }

    @Override
    public void deleteHotelById(Long id) {
        Hotel hotel=hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel not found"));

      hotelRepository.delete(hotel);
      log.info("Hotel deleted successfully");
      //delete the future Inventories for this hotel
    }

    @Override
    public Hoteldto partialUpdateHotelById(Long id, Hoteldto hoteldto) {

      log.info("Patching hotel ID {}: updates {}", id, hoteldto);
      Hotel hotel=hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel not found"));

      return modelMapper.map(hotel, Hoteldto.class);
    }

    @Override
    public void setActiveHotel(Long hotelId) {
        Hotel hotel=hotelRepository.findById(hotelId).orElseThrow(()->new ResourceNotFoundException("Hotel not found"));
        hotel.setActive(!hotel.getActive());
        hotelRepository.save(hotel);
        /// TODO: create inventory for all rooms
    }


}
