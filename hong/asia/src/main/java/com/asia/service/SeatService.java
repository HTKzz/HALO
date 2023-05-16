package com.asia.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.asia.dto.CountDto;
import com.asia.dto.SeatADto;
import com.asia.dto.SeatBDto;
import com.asia.dto.SeatCDto;
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

	public List<SeatADto> getSeatA(int anum) {
		List<SeatADto> seat = seatARepository.getSeat(anum);
		return seat;
	}

	public List<SeatBDto> getSeatB(int num) {
		List<SeatBDto> seat = seatBRepository.getSeat(num);
		System.out.println(seat);
		return seat;
	}

	public List<SeatCDto> getSeatC(int anum) {
		List<SeatCDto> seat = seatCRepository.getSeat(anum);
		return seat;
	}

	public void updateSeat(UpdateDto updateDto, int anum, String seat1) {

		ObjectMapper objectMapper = new ObjectMapper();
		Map result = objectMapper.convertValue(updateDto, Map.class);
		
		System.out.println(result);

		if (seat1.equals("A")) {
			List<SeatADto> seat = seatARepository.getSeat(anum);
			for (int i = 0; i < seat.size(); i++) {

				SeatA seatA = new SeatA();
				seatA = seatARepository.findByNum(seat.get(i).getNum());
				String A = (String) result.get(seat.get(i).getSeat().toLowerCase());
				seatA.updateSeat(A);

			}
		}
		
		if (seat1.equals("B")) {
			List<SeatBDto> seat = seatBRepository.getSeat(anum);
			for (int i = 0; i < seat.size(); i++) {

				SeatB seatB = new SeatB();
				seatB = seatBRepository.findByNum(seat.get(i).getNum());
				String B = (String) result.get(seat.get(i).getSeat().toLowerCase());
				seatB.updateSeat(B);

			}
		}
		
		if (seat1.equals("C")) {
			List<SeatCDto> seat = seatCRepository.getSeat(anum);
			for (int i = 0; i < seat.size(); i++) {

				SeatC seatC = new SeatC();
				seatC = seatCRepository.findByNum(seat.get(i).getNum());
				String C = (String) result.get(seat.get(i).getSeat().toLowerCase());
				seatC.updateSeat(C);

			}
		}
	}
	
	public void cancelUpdateSeat(String seatDetail, int anum, String[] array) {
		
		if (seatDetail.equals("A")) {
			List<SeatADto> seat = seatARepository.getSeat(anum);
			
			for (int i = 0; i < array.length; i++) {
				
				for (int j = 0; j < seat.size(); j++) {
					if (seat.get(j).getSeat().equals(array[i])) {
						seat.get(j).setStat("seat");
						SeatA seatA = new SeatA();
						seatA = seatARepository.findByNum(seat.get(j).getNum());
						String A = seat.get(j).getStat();
						seatA.updateSeat(A);
					}
				}
			}
		}
		
		if (seatDetail.equals("B")) {
			List<SeatBDto> seat = seatBRepository.getSeat(anum);
			
			for (int i = 0; i < array.length; i++) {
				
				for (int j = 0; j < seat.size(); j++) {
					if (seat.get(j).getSeat().equals(array[i])) {
						seat.get(j).setStat("seat");
						SeatB seatB = new SeatB();
						seatB = seatBRepository.findByNum(seat.get(j).getNum());
						String B = seat.get(j).getStat();
						seatB.updateSeat(B);
					}
				}
			}
		}
		
		if (seatDetail.equals("C")) {
			List<SeatCDto> seat = seatCRepository.getSeat(anum);
			
			for (int i = 0; i < array.length; i++) {
				
				for (int j = 0; j < seat.size(); j++) {
					if (seat.get(j).getSeat().equals(array[i])) {
						seat.get(j).setStat("seat");
						SeatC seatC = new SeatC();
						seatC = seatCRepository.findByNum(seat.get(j).getNum());
						String C = seat.get(j).getStat();
						seatC.updateSeat(C);
					}
				}
			}
		}
	}
}
