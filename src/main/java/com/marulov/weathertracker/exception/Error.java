package com.marulov.weathertracker.exception;

public record Error(
        String code,
        String type,
        String info
) { }
