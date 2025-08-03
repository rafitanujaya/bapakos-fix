package org.bapakos.controller;

import org.bapakos.service.AuthService;

import java.sql.SQLException;

public class RegisterController {
    private AuthService authService;

    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    public void handleRegister() throws SQLException {}
}
