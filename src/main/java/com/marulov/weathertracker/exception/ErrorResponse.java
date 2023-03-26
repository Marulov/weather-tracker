package com.marulov.weathertracker.exception;

public record ErrorResponse (
        String success,
        Error error
) { }
