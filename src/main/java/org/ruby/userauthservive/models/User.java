package org.ruby.userauthservive.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel {
  private String name;
  private String email;
  private String password;
  private String phoneNumber;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Role> roles;
}

/*
user  role
1      m
m       1
 m----->m

 */
