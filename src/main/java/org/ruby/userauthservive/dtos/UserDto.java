package org.ruby.userauthservive.dtos;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
  private Long id;
  private String name;
  private String email;
  private List<String> roles = new ArrayList<>();
}
