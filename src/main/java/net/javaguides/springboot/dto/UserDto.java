package net.javaguides.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "UserDto Model Information"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @Schema(
            description = "User first name"
    )
    @NotEmpty(message = "User first name should not be empty")
    private String firstName;

    @Schema(
            description = "User last name"
    )
    @NotEmpty(message = "User last name should not be empty")
    private String lastName;

    @Schema(
            description = "User email address"
    )
    @NotEmpty(message = "User email should not be empty")
    @Email
    private String emailAddress;
}
