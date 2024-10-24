package org.federico.usercreation.adapter.controller;

import lombok.Builder;
import lombok.Value;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.federico.usercreation.application.User;

@Value
@Builder
public class UserResponse {
    Name name;
    String email;
    Address address;
    Phone phone;
    Preference preference;

    @Value
    @Builder
    public static class Name {
        @Schema(example = "Mr")
        String title;
        @Schema(example = "Federico")
        String first;
        @Schema(example = "Valencia")
        String last;
    }

    @Value
    @Builder
    public static class Phone {
        @Schema(example = "ES")
        String countryCode;
        @Schema(example = "123456789")
        String number;
    }

    @Value
    @Builder
    public static class Address {
        Street street;
        @Schema(example = "08025")
        String postalCode;
        @Schema(example = "Barcelona")
        String city;
        @Schema(example = "ES")
        String countryCode;

        @Value
        @Builder
        public static class Street {
            @Schema(example = "Castillejos")
            String name;
            @Schema(example = "361")
            String number;
        }
    }

    @Value
    @Builder
    public static class Preference {
        boolean marketingEmailsConsent;
        boolean sendPrintedMemberCard;
        @Schema(example = "ES")
        String preferredLanguage;
    }

    public static UserResponse fromUser(User userSaved) {
        return UserResponse.builder()
                .name(Name.builder()
                        .title(userSaved.getName().getTitle())
                        .first(userSaved.getName().getFirst())
                        .last(userSaved.getName().getLast())
                        .build())
                .email(userSaved.getEmail())
                .address(Address.builder()
                        .street(Address.Street.builder()
                                .name(userSaved.getAddress().getStreet().getName())
                                .number(userSaved.getAddress().getStreet().getNumber())
                                .build())
                        .postalCode(userSaved.getAddress().getPostalCode())
                        .city(userSaved.getAddress().getCity())
                        .countryCode(userSaved.getAddress().getCountryCode())
                        .build())
                .phone(Phone.builder()
                        .countryCode(userSaved.getPhone().getCountryCode())
                        .number(userSaved.getPhone().getNumber())
                        .build())
                .preference(Preference.builder()
                        .marketingEmailsConsent(userSaved.getPreference().isMarketingEmailsConsent())
                        .sendPrintedMemberCard(userSaved.getPreference().isSendPrintedMemberCard())
                        .preferredLanguage(userSaved.getPreference().getPreferredLanguage())
                        .build())
                .build();
    }

}
