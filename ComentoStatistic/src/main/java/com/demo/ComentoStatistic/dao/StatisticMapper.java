package com.demo.ComentoStatistic.dao;

import com.demo.ComentoStatistic.dto.*;
        import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StatisticMapper {

    YearCountDto selectYearLogin(String year);
    YearMonthCountDto selectYearMonthLogin(String yearMonth);
    List<MonthlyCountDto> selectMonthlyLogins(String year);

    List<DailyCountDto> selectDailyLogins(String day);

    DailyAvgDto selectDailyAvgLogins(String year);

    List<LoginDto> selectLoginsForYear(@Param("year") String year);

}