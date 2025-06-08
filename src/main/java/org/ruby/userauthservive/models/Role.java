package org.ruby.userauthservive.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Role extends BaseModel {
  private String name;
  // If needed we can list of permissions or other attributes related to the role

}
