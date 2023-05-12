package com.asia.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.asia.dto.CountDto;
import com.asia.dto.SeatADto;
import com.asia.entity.SeatA;
import com.asia.repository.ApplicationRepository;
import com.asia.repository.SeatARepository;

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
	
}
