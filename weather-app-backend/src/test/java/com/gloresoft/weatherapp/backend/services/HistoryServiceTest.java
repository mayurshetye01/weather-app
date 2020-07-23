package com.gloresoft.weatherapp.backend.services;

import com.gloresoft.weatherapp.backend.converters.WeatherConverter;
import com.gloresoft.weatherapp.backend.dto.WeatherDto;
import com.gloresoft.weatherapp.backend.entities.User;
import com.gloresoft.weatherapp.backend.entities.WeatherHistory;
import com.gloresoft.weatherapp.backend.repository.UserRepository;
import com.gloresoft.weatherapp.backend.repository.WeatherHistoryRepository;
import com.gloresoft.weatherapp.backend.services.impl.HistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class HistoryServiceTest {

    @InjectMocks
    HistoryServiceImpl historyService;

    @Mock
    private WeatherHistoryRepository weatherHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    private WeatherConverter weatherConverter;

    private String testUser = "test";
    private int sampleDataSize = 5;

    @BeforeEach
    public void init(){
        // Assume that user authentication is successful
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void getAllRecordsTest() {
        List<WeatherHistory> testUser1History = generateHistoryForSampleUser("test1");
        List<WeatherHistory> testUser2History = generateHistoryForSampleUser("test2");
        List<WeatherHistory> testUser3History = generateHistoryForSampleUser("test3");

        List<WeatherHistory> allTestRecords = new ArrayList<>();
        allTestRecords.addAll(testUser1History);
        allTestRecords.addAll(testUser2History);
        allTestRecords.addAll(testUser3History);

        when(weatherHistoryRepository.getAllRecords()).thenReturn(allTestRecords);

        List<WeatherDto> result = historyService.getAllRecords();

        assertEquals(result.size(), allTestRecords.size());
    }

    @Test
    public void getUserSearchHistoryTest() {
        List<WeatherHistory> testRecords = generateHistoryForSampleUser(testUser);

        when(weatherHistoryRepository.getAllRecordsForUser(any())).thenReturn(testRecords);

        List<WeatherDto> result = historyService.getUserSearchHistory();

        assertEquals(result.size(), testRecords.size());
    }

    @Test
    public void addEntryTest() {
        WeatherDto testEntry = new WeatherDto();

        historyService.addEntry(testEntry);

        verify(weatherHistoryRepository, times(1)).saveOrUpdate(any(WeatherHistory.class));

    }

    @Test
    public void updateEntryTest() {
        WeatherDto testEntry = new WeatherDto();

        historyService.updateEntry(testEntry);

        verify(weatherHistoryRepository, times(1)).update(any(WeatherHistory.class));
    }

    @Test
    public void deleteEntryTest() {
        String testCity = "testCity";
        WeatherHistory testRecord = new WeatherHistory();
        testRecord.setCity(testCity);

        when(weatherHistoryRepository.findByCity(eq(testCity), any())).thenReturn(testRecord);

        historyService.deleteEntry(testCity);

        verify(weatherHistoryRepository, times(1)).delete(any(WeatherHistory.class));
    }

    @Test
    public void bulkDeleteTest() {
        List<WeatherHistory> allRecordsOfTestUser = generateHistoryForSampleUser(testUser);

        when(weatherHistoryRepository.getAllRecordsForUser(any())).thenReturn(allRecordsOfTestUser);

        historyService.bulkDelete();

        verify(weatherHistoryRepository, times(1)).deleteAllForUser(any(List.class));
    }

    private List<WeatherHistory> generateHistoryForSampleUser(String username){
        List<WeatherHistory> testRecords = new ArrayList<>();

        for(int i = 0 ; i < sampleDataSize; i++) {
            WeatherHistory dummyHistory = new WeatherHistory();
            dummyHistory.setUser(getDummyUserWithUsername(username));
            testRecords.add(dummyHistory);
        }

        return testRecords;
    }

    private User getDummyUserWithUsername(String username){
        User testUser = new User();
        testUser.setUsername(username);
        return testUser;
    }
}
