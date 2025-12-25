package com.restaurant.restaurant_api.dto;

/**
 * Simple DTO for authentication requests (login/register).
 */
public record AuthRequest(String username, String password) {}
