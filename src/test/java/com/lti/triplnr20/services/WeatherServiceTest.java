package com.lti.triplnr20.services;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import org.assertj.core.api.Assertions;
@SpringBootTest(classes=WeatherServiceImpl.class)
class WeatherServiceTest {
	@Autowired
	WeatherService ws = new WeatherServiceImpl();
	static String weather;
	@BeforeAll
	static void setUp(){
		
	}
	
	@Test
	void getCurrentWeather() {
		assertNotNull(ws.getCurrentWeather("Uncasville,CT,US"));
	}
	
	@Test
	void getCurrentWeatherFail() {
		assertNotNull(ws.getCurrentWeather("Uncasville,CT,US"));
	}
	
	@Test
	void  getDestiationWeatherPass(){
		String address = "waller,tx";
		assertNotNull(ws.getDestinationWeather(address, 1));
	}
	
	@Test
	void  getDestiationWeatherFail(){
		String address = "Uncasville,CT";
		assertNotEquals(ws.getDestinationWeather(address, 1), ws.getDestinationWeather(address, 2));
		
	}
	
	
	
}
