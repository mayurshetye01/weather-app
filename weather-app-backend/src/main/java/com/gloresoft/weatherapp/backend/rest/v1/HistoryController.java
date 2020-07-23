package com.gloresoft.weatherapp.backend.rest.v1;

import com.gloresoft.weatherapp.backend.dto.WeatherDto;
import com.gloresoft.weatherapp.backend.services.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/history")
@Slf4j
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @GetMapping
    public List<WeatherDto> getHistory() {
        return historyService.getUserSearchHistory();
    }

    @PostMapping
    public void addHistory(@RequestBody @Valid WeatherDto weatherDto) {
        historyService.addEntry(weatherDto);
    }

    @PutMapping
    public void updateHistory(@RequestBody @Valid WeatherDto weatherDto) {
        historyService.updateEntry(weatherDto);
    }

    @DeleteMapping
    public void deleteHistory(@RequestParam String city) {
        historyService.deleteEntry(city);
    }

    @DeleteMapping("/bulk")
    public void bulkDeleteHistory() {
        historyService.bulkDelete();
    }

}
