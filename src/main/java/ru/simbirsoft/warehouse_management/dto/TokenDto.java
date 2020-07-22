package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/** TokenDto is used to transfer JWT-token Contains jwt-token string */
@AllArgsConstructor
@Data
public class TokenDto {
  private String token;
}
