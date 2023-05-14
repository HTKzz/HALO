package com.asia.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.asia.dto.CountDto;
import com.asia.dto.SeatADto;
import com.asia.dto.UpdateDto;
import com.asia.entity.SeatA;
import com.asia.repository.ApplicationRepository;
import com.asia.repository.SeatARepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatService {

	private final ApplicationRepository applicationRepository;
	private final SeatARepository seatARepository;

	public void saveSeatA(SeatA seatA) throws Exception {
		String stat = "seat";
		
		seatA.updateSeatA(stat);
		seatARepository.save(seatA);
	}
	
	public List<CountDto> getCount() {
		List<CountDto> count = seatARepository.getCount();
		return count;
	}
	
	public List<SeatADto> getSeat(int anum) {
		List<SeatADto> seat = seatARepository.getSeat(anum);
		return seat;
	}
	
	public void updateSeat(UpdateDto updateDto, int anum) {
		List<SeatADto> seat = seatARepository.getSeat(anum);
		System.out.println(seat.get(0).getNum());
		System.out.println(updateDto.getClass().getName());
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map result = objectMapper.convertValue(updateDto, Map.class);
		
		System.out.println(result.get(seat.get(0).getSeat().toLowerCase()));
		for(int i = 0; i < seat.size(); i++) {
			
			SeatA seatA = new SeatA();
			seatA = seatARepository.findByNum(seat.get(i).getNum());
			System.out.println(seatA);
			String A = (String) result.get(seat.get(i).getSeat().toLowerCase());
			System.out.println(A);
			seatA.updateSeat(A);
		}
		
		
//		item.updateItem(itemFormDto);
		
	}
	
}
