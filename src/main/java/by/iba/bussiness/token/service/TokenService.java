package by.iba.bussiness.token.service;

import by.iba.bussiness.token.model.JavaWebToken;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    JavaWebToken getJavaWebToken(HttpServletRequest request);
}
