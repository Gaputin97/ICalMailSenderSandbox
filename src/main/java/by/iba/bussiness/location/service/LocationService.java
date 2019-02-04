package by.iba.bussiness.location.service;

import by.iba.bussiness.location.Location;

import javax.servlet.http.HttpServletRequest;

public interface LocationService {
    Location getLocationByCode(HttpServletRequest request, String code);
}
