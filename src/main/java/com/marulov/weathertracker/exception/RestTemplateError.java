package com.marulov.weathertracker.exception;

public record RestTemplateError (
        String timestamp,
        String status,
        String error,
        String path
){ }
