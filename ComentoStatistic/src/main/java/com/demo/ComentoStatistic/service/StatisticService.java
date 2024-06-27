package com.demo.ComentoStatistic.service;

import com.demo.ComentoStatistic.config.HolidayApi;
import com.demo.ComentoStatistic.dao.StatisticMapper;
import com.demo.ComentoStatistic.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatisticService {

    @Autowired
    StatisticMapper statisticMapper;

    @Autowired
    HolidayApi holidayApi;

    public YearCountDto getYearLogins(String year) {
        log.info("Year in Service: " + year);
        return statisticMapper.selectYearLogin(year);
    }

    public YearMonthCountDto getYearMonthLogins(String year, String month) {
        return statisticMapper.selectYearMonthLogin(year + month);
    }

    public List<MonthlyCountDto> getMonthlyLogins(String year) {
        log.info("Year for Monthly Logins in Service: " + year);
        return statisticMapper.selectMonthlyLogins(year);
    }

    public List<DailyCountDto> getDailyLogins(String day) {
        log.info("Year for Monthly Logins in Service: " + day);
        return statisticMapper.selectDailyLogins(day);
    }

    public DailyAvgDto getDailyAvgLogins(String year) {
        return statisticMapper.selectDailyAvgLogins(year);
    }

    public int getLoginCountExcludingHolidays(String year) throws IOException, JSONException {
        List<String> holidays = holidayApi.getHolidays(Integer.parseInt(year));
        List<LoginDto> logins = statisticMapper.selectLoginsForYear(year);


        List<LoginDto> loginsExcludingHolidays = logins.stream()
                .filter(login -> !holidays.contains(login.getCreateDate()))
                .collect(Collectors.toList());

        return loginsExcludingHolidays.size();
    }
}
