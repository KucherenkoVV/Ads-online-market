package ru.skypro.homework.dto.user;

import lombok.Data;

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
                    regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$"
            )
    private String phone;
}
