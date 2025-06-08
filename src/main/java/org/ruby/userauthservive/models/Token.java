package org.ruby.userauthservive.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Token extends BaseModel {
  private String value;
  private Date expire_At;
  @ManyToOne private User user;
}
/*
Token User
1       1
m         1
-->m:1
 */
