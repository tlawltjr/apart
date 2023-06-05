package com.project.apart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ApartDTO {
    //지역관련
    private Long region_code;

    //날짜관련
    private Long year;
    private Long month;
    private Long day;

    //아파트 정보 관련
    private String apart_name;
    private String post_zip;//우편번호
    private String road_zip;//도로명
    private Long const_year;//건축년도
    private Object dedicated_area;//전용면적
    private String apart_coast;//거래금액
    private String bjd;//법정동
    private String floor;//층


    private String selected;
    private int id;



}
