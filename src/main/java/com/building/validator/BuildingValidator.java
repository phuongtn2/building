package com.building.validator;

import com.building.dto.BuildingDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by phuongtn1 on 11/9/2016.
 */
public class BuildingValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        //just validate the Customer instances
        return BuildingDto.class.isAssignableFrom(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
                "required.userName", "Field name is required.");
    }
}
