package ru.simbirsoft.warehouse_management.dto.createForms;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel(description = "Form for writeoff create")
public class WriteoffCreateDto {

  @ApiModelProperty(
      value = "id of the warehouse",
      name = "warehouse id",
      dataType = "Long",
      example = "42")
  @NotNull
  private Long warehouseId;

  @ApiModelProperty(
      value = "time of the writeoff",
      name = "time",
      dataType = "LocalDateTime",
      example = "yyyy-MM-dd-HH-mm-ss")
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
  private LocalDateTime localDateTime;

  @ApiModelProperty(
      value =
          "Map of the items and their count for writeoff. Key is id of item, Value is the count of this item",
      name = "itemCount")
  @NotNull
  @NotEmpty(message = "Please provide  items and their counts")
  private Map<Long, Integer> itemCount;
}
