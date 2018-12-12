package by.iba.bussines.token.service;

import by.iba.bussines.token.model.JavaWebToken;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    JavaWebToken getJavaWebToken(HttpServletRequest request);
}
