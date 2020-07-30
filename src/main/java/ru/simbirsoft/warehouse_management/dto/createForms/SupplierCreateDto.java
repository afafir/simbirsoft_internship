package ru.simbirsoft.warehouse_management.dto.createForms;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel(description = "Form for supplier create")
public class SupplierCreateDto {

    @ApiModelProperty(
            value = "name of the supplier",
            name = "name",
            dataType = "String",
            example = "Super retail group")
    @NotNull
    @NotEmpty(message = "Please provide a name")
    private String name;
}
