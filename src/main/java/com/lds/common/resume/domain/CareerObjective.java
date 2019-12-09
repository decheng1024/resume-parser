package com.lds.common.resume.domain;


import com.lds.common.resume.annatations.Label;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CareerObjective {

    @Label(keys ="期望月薪")
    private String expectingSalary;
    @Label(keys ="意向行业")
    private String expectingIndustry;
    @Label(keys ="意向岗位")
    private String expectingPosition;
    @Label(keys ="意向地区")
    private String expectingLocation;
    @Label(keys ="到岗时间")
    private String hiredate;
    @Label(keys ="住房要求")
    private String requireApartment;

}
