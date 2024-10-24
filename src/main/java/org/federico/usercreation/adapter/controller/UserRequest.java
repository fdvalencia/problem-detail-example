package org.federico.usercreation.adapter.controller;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.federico.usercreation.application.User;

@Value
public class UserRequest {
    @NotNull
    Name name;
    @Schema(example = "fdvalencia1@gmail.com")
    String email;
    Address address;
    Phone phone;
    Preference preference;

    public User toUser() {
        return User.builder()
                .name(User.Name.builder()
                        .title(name.title)
                        .first(name.first)
                        .last(name.last)
                        .build())
                .email(email)
                .address(User.Address.builder()
                        .street(User.Address.Street.builder()
                                .name(address.street.name)
                                .number(address.street.number)
                                .build())
                        .postalCode(address.postalCode)
                        .city(address.city)
                        .countryCode(address.countryCode)
                        .build())
                .phone(User.Phone.builder()
                        .countryCode(phone.countryCode)
                        .number(phone.number)
                        .build())
                .preference(User.Preference.builder()
                        .marketingEmailsConsent(preference.marketingEmailsConsent)
                        .sendPrintedMemberCard(preference.sendPrintedMemberCard)
                        .preferredLanguage(preference.preferredLanguage)
                        .build())
                .build();
    }

    @Value
    public static class Name {

        @Schema(example = "Mr")
        String title;
        @Schema(example = "Federico")
        String first;
        @Schema(example = "Valencia")
        String last;

    }

    @Value
    public static class Phone {
        @Schema(example = "ES")
        String countryCode;
        @Schema(example = "123456789")
        String number;
    }

    @Value
    public static class Address {
        Street street;
        @Schema(example = "08025")
        String postalCode;
        @Schema(example = "Barcelona")
        String city;

        String countryCode;

        @Value
        public static class Street {
            @Schema(example = "Castillejos")
            String name;
            @Schema(example = "361")
            String number;

        }
    }

    @Value
    public static class Preference {
        boolean marketingEmailsConsent;
        boolean sendPrintedMemberCard;
        @Schema(example = "ES")
        String preferredLanguage;
    }
}
