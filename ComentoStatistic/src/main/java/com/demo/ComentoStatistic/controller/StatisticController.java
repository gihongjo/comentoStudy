package com.demo.ComentoStatistic.controller;

import com.demo.ComentoStatistic.dto.DailyAvgDto;
import com.demo.ComentoStatistic.dto.MonthlyCountDto;
import com.demo.ComentoStatistic.dto.YearCountDto;
import com.demo.ComentoStatistic.dto.YearMonthCountDto;
import com.demo.ComentoStatistic.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class StatisticController {

    @Autowired
    StatisticService statisticService;

    @RequestMapping(value="/api/v1/logins/{year}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<YearCountDto> getYearLoginCount(@PathVariable("year") String year) {
        log.info("Received request for login count for year: {}", year);
        YearCountDto yearCountDto = statisticService.getYearLogins(year);
        log.info("Returning response: {}", yearCountDto);
        return ResponseEntity.ok(yearCountDto);
    }

    @RequestMapping(value="/api/v1/logins/{year}/{month}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<YearMonthCountDto> getYearMonthLoginCount(@PathVariable("year") String year, @PathVariable("month") String month) {
        log.info("Received request for login count for year: {}, month: {}", year, month);
        YearMonthCountDto yearMonthCountDto = statisticService.getYearMonthLogins(year, month);
        log.info("Returning response: {}", yearMonthCountDto);
        return ResponseEntity.ok(yearMonthCountDto);
    }

    @RequestMapping(value="/api/v1/logins/monthly/{year}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<MonthlyCountDto>> getMonthlyLoginCount(@PathVariable("year") String year) {
        log.info("Received request for monthly login count for year: {}", year);
        List<MonthlyCountDto> monthlyCounts = statisticService.getMonthlyLogins(year);
        log.info("Returning response: {}", monthlyCounts);
        return ResponseEntity.ok(monthlyCounts);
    }

    @RequestMapping(value="/api/v1/logins/daily-average/{year}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<DailyAvgDto> getDailyAverageLogin(@PathVariable("year") String year) {
        log.info("Received request for daily average login count for year: {}", year);
        DailyAvgDto dailyAverage = statisticService.getDailyAvgLogins(year);
        log.info("Returning response: {}", dailyAverage);
        return ResponseEntity.ok(dailyAverage);
    }

    @RequestMapping(value="/api/v1/logins/excluding-holidays/{year}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Integer> getLoginCountExcludingHolidays(@PathVariable("year") String year) {
        log.info("Received request for login count excluding holidays for year: {}", year);
        try {
            int loginCount = statisticService.getLoginCountExcludingHolidays(year);
            log.info("Returning response: {}", loginCount);
            return ResponseEntity.ok(loginCount);
        } catch (IOException | JSONException e) {
            log.error("Error retrieving login count excluding holidays: ", e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
