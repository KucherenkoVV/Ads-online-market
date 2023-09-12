package ru.skypro.homework.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserDto {

    @NotNull
    @Size.List
            ({      @Size(min = 3),
                    @Size(max = 10)
            })
    private String lastName;

    @NotNull
    @Size.List
            ({
                    @Size(min = 3),
                    @Size(max = 10)
            })
    private String firstName;

    @NotNull
    @Pattern
            (
                    regexp = "(\\+)?(7|8)?\\d{10}"
            )
    private String phone;
}
