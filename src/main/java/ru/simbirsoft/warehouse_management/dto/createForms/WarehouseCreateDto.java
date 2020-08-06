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
@ApiModel(description = "Form for warehouse create")
public class WarehouseCreateDto {
    @ApiModelProperty(
            value = "address of the warehouse",
            name = "address",
            dataType = "String",
            example = "USA, New-York, Wall Street")
    @NotNull
    @NotEmpty(message = "Please provide a address")
    private String address;
}
