package com.angelokezimana.starter.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponseDTO(@JsonProperty("access_token") String accessToken,
                                        @JsonProperty("refresh_token") String refreshToken) {
}
