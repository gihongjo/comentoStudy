package com.demo.ComentoStatistic.dao;

import com.demo.ComentoStatistic.dto.YearCountDto;
import com.demo.ComentoStatistic.dto.YearMonthCountDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticMapper {

    YearCountDto selectYearLogin(String year);
    YearMonthCountDto selectYearMonthLogin(String yearMonth);

}