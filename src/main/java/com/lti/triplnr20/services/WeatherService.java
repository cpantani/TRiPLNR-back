package com.lti.triplnr20.services;

import java.util.List;

import com.lti.triplnr20.models.weather.Day;

public interface WeatherService {
	
	 Day getCurrentWeather(String address);
	 
	 Day getDestinationWeather(String address, int day);
}
