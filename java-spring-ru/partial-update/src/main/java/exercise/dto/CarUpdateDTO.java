package exercise.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

// BEGIN
@Getter
@Setter
public class CarUpdateDTO {

    @NotNull
    private JsonNullable<String> model;

    @NotNull
    private JsonNullable<String> manufacturer;

    @NotNull
    private JsonNullable<Integer> enginePower;


}

// END
