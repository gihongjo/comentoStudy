package com.demo.ComentoStatistic.service;

import com.demo.ComentoStatistic.dao.StatisticMapper;
import com.demo.ComentoStatistic.dto.YearCountDto;
import com.demo.ComentoStatistic.dto.YearMonthCountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatisticService {


    @Autowired
    StatisticMapper statisticMapper;

    public YearCountDto getYearLogins(String year){

      log.info("Year in Service: "+year);
        return statisticMapper.selectYearLogin(year);
    }

    public YearMonthCountDto getYearMonthLogins(String year, String month){

        return statisticMapper.selectYearMonthLogin(year+month);
    }



}