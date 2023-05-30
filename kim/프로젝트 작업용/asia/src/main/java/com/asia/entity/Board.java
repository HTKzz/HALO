package com.asia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="board")
@Getter
@Setter
@ToString
public class Board {
    
    /*
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * - 기본 키가 자동으로 할당되도록 설정하는 어노테이션이다.
     * - 기본 키 할당 전략을 선택할 수 있는데, 키 생성을 데이터베이스에 위임하는 IDENTITY 전략 사용
     */
    @Id
    @Column(name="board_num")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long num;
    
    @Column(nullable = false)
    private String writer;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String content;
    
    @Column
    private String filename;
    
    @Column
    private String filepath;

}
