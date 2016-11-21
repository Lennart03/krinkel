package com.realdolmen.chiro.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class AuthTokenFinder {

    /**
     * Searches for an authentication token in the header of the request
     * or in a cookie with the name "Authorization".
     *
     * @param request
     * @return
     * @throws AuthTokenNotFoundException when no token is found.
     */
    public AuthToken findAuthenticationToken(HttpServletRequest request) throws AuthTokenNotFoundException {
        final String authHeader = request.getHeader("Authorization");

        String token;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // The part after "Bearer "
        } else {
            Cookie authenticationCookie = getAuthenticationCookie(request.getCookies());
            String authCookieToken = authenticationCookie.getValue();
            token = authCookieToken;
        }

        if (token == null) {
            throw new AuthTokenNotFoundException("No authentication header or cookie found");
        }

        return new AuthToken(token);
    }

    /**
     * Get the cookie with the name "Authorization" from the list of cookies.
     *
     * @param cookies List of cookies from the request
     * @return Authorization cookie (JWT) or null if no cookie is found, or the array of cookies is empty or null.
     */
    protected Cookie getAuthenticationCookie(Cookie[] cookies) throws AuthTokenNotFoundException {
        Cookie authCookie = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    authCookie = cookie;
                }
            }
        }

        if (authCookie != null) {
            return authCookie;
        } else {
            throw new AuthTokenNotFoundException("No authentication header or cookie found");
        }
    }
}
