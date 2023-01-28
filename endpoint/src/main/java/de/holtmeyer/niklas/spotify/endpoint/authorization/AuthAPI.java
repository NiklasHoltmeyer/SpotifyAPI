package de.holtmeyer.niklas.spotify.endpoint.authorization;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;

import java.io.IOException;
import java.util.Optional;

@Validated
@RequestMapping(path = "/spotify/auth/", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AuthAPI {
//    @Operation(summary = "", description = "returns all registered devices", tags={ "Device" })
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "All the devices", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))) })
    @GetMapping
    RedirectView getLogin(HttpServletResponse response, @CookieValue(name = "refresh_token", defaultValue = "") String refreshToken) throws IOException;

    @GetMapping("/callback")
    ResponseEntity getLoginCallback(@RequestParam(required=false) Optional<String> code,
                                           @RequestParam(required=false) Optional<String> state,
                                           @RequestParam(required=false) Optional<String> error,
                                           @CookieValue(name = "state") Optional<String> stateCookie,
                                           HttpServletResponse servletResponse) throws IOException;
}
