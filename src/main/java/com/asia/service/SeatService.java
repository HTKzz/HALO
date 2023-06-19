package com.asia.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.asia.dto.CountDto;
import com.asia.dto.SeatDto;
import com.asia.dto.UpdateDto;
import com.asia.entity.SeatA;
import com.asia.entity.SeatB;
import com.asia.entity.SeatC;
import com.asia.repository.SeatARepository;
import com.asia.repository.SeatBRepository;
import com.asia.repository.SeatCRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatService {

	private final SeatARepository seatARepository;
	private final SeatBRepository seatBRepository;
	private final SeatCRepository seatCRepository;

	public void saveSeatA(SeatA seatA) throws Exception {
		String stat = "seat";

		seatA.updateSeatA(stat);
		seatARepository.save(seatA);
	}

	public void saveSeatB(SeatB seatB) throws Exception {
		String stat = "seat";

		seatB.updateSeatB(stat);
		seatBRepository.save(seatB);
	}

	public void saveSeatC(SeatC seatC) throws Exception {
		String stat = "seat";

		seatC.updateSeatC(stat);
		seatCRepository.save(seatC);
	}

	public List<CountDto> getCountA() {
		List<CountDto> count = seatARepository.getCount();
		return count;
	}

	public List<CountDto> getCountB() {
		List<CountDto> count = seatBRepository.getCount();
		return count;
	}

	public List<CountDto> getCountC() {
		List<CountDto> count = seatCRepository.getCount();
		return count;
	}

	public List<SeatDto> getSeatA(Long anum) {
		List<SeatDto> seat = seatARepository.getSeat(anum);
		return seat;
	}

	public List<SeatDto> getSeatB(Long num) {
		List<SeatDto> seat = seatBRepository.getSeat(num);
		return seat;
	}

	public List<SeatDto> getSeatC(Long anum) {
		List<SeatDto> seat = seatCRepository.getSeat(anum);
		return seat;
	}

	public void updateSeat(UpdateDto updateDto, Long anum, String seat1) {

		ObjectMapper objectMapper = new ObjectMapper();
		Map result = objectMapper.convertValue(updateDto, Map.class);
		
		if (seat1.equals("A")) {
			List<SeatDto> seat = seatARepository.getSeat(anum);
			for (int i = 0; i < seat.size(); i++) {
				SeatA seatA = new SeatA();
				seatA = seatARepository.findByNum(seat.get(i).getNum());
				String A = (String) result.get(seat.get(i).getSeat().toLowerCase());
				seatA.setStat(A);
				seatARepository.save(seatA);
			}
		}
		
		if (seat1.equals("B")) {
			List<SeatDto> seat = seatBRepository.getSeat(anum);
			for (int i = 0; i < seat.size(); i++) {
				SeatB seatB = new SeatB();
				seatB = seatBRepository.findByNum(seat.get(i).getNum());
				String B = (String) result.get(seat.get(i).getSeat().toLowerCase());
				seatB.setStat(B);
				seatBRepository.save(seatB);
			}
		}
		
		if (seat1.equals("C")) {
			List<SeatDto> seat = seatCRepository.getSeat(anum);
			for (int i = 0; i < seat.size(); i++) {
				SeatC seatC = new SeatC();
				seatC = seatCRepository.findByNum(seat.get(i).getNum());
				String C = (String) result.get(seat.get(i).getSeat().toLowerCase());
				seatC.setStat(C);
				seatCRepository.save(seatC);
			}
		}
	}
	
	public void cancelUpdateSeat(String seatDetail, Long anum, String[] array) {
		
		if (seatDetail.equals("A")) {
			List<SeatDto> seat = seatARepository.getSeat(anum);
			
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < seat.size(); j++) {
					if (seat.get(j).getSeat().equals(array[i])) {
						seat.get(j).setStat("seat");
						SeatA seatA = new SeatA();
						seatA = seatARepository.findByNum(seat.get(j).getNum());
						String A = seat.get(j).getStat();
						seatA.setStat(A);
						seatARepository.save(seatA);
					}
				}
			}
		}
		
		if (seatDetail.equals("B")) {
			List<SeatDto> seat = seatBRepository.getSeat(anum);
			
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < seat.size(); j++) {
					if (seat.get(j).getSeat().equals(array[i])) {
						seat.get(j).setStat("seat");
						SeatB seatB = new SeatB();
						seatB = seatBRepository.findByNum(seat.get(j).getNum());
						String B = seat.get(j).getStat();
						seatB.setStat(B);
						seatBRepository.save(seatB);
					}
				}
			}
		}
		
		if (seatDetail.equals("C")) {
			List<SeatDto> seat = seatCRepository.getSeat(anum);
			
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < seat.size(); j++) {
					if (seat.get(j).getSeat().equals(array[i])) {
						seat.get(j).setStat("seat");
						SeatC seatC = new SeatC();
						seatC = seatCRepository.findByNum(seat.get(j).getNum());
						String C = seat.get(j).getStat();
						seatC.setStat(C);
						seatCRepository.save(seatC);
					}
				}
			}
		}
	}
}
