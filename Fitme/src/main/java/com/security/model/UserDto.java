package com.security.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Alias("UserDto")
@Builder
@AllArgsConstructor
public class UserDto {

  private Long accountNo;
  private String username;
  private String password;
  
  @Builder.Default private String name="";
  @Builder.Default private String gender="";
  @Builder.Default private String age="";
  @Builder.Default private String address="";
  @Builder.Default private String hobby="";
  @Builder.Default private String image="";
  @Builder.Default private String role="";
  
  private String providerId;
  private String provider;
  private Date enrollDate;

  public List<String> getRoleList() {
    if (this.role.length() > 0) {
      return Arrays.asList(this.role.split(","));
    }
    return new ArrayList<>();
  }

}