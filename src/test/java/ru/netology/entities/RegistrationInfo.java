package ru.netology.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegistrationInfo {
    private final String city;
    private final String dateMeeting;
    private final String dateMeetingOut;
    private final String dateMeetingInvalid;
    private final String name;
    private final String phoneNumber;
}