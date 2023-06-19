package com.asia.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CountDto {
	
	private Long application_id;
	
	private Long count;
	
	@QueryProjection
	public CountDto(Long application_id, Long count) {
		this.application_id = application_id;
		this.count = count;
	}
	
}
