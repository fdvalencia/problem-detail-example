package org.federico.usercreation.application;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    Name name;
    String email;
    Address address;
    Phone phone;
    Preference preference;

    @Value
    @Builder
    public static class Name {
        String title;
        String first;
        String last;
    }

    @Value
    @Builder
    public static class Phone {
        String countryCode;
        String number;
    }

    @Value
    @Builder
    public static class Address {
        Street street;
        String postalCode;
        String city;
        String countryCode;

        @Value
        @Builder
        public static class Street {
            String name;
            String number;
        }
    }

    @Value
    @Builder
    public static class Preference {
        boolean marketingEmailsConsent;
        boolean sendPrintedMemberCard;
        String preferredLanguage;
    }
}
