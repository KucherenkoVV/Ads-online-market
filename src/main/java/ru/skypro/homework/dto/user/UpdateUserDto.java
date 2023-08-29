package ru.skypro.homework.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserDto {

    @Size.List
            ({      @Size(min = 3),
                    @Size(max = 10)
            })
    private String lastName;

    @Size.List
            ({
                    @Size(min = 3),
                    @Size(max = 10)
            })
    private String firstName;

    @Pattern
            (
                    regexp = "(\\+)?(7|8)?\\d{10}"
            )
    private String phone;
}
