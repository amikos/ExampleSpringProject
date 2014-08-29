package com.yummynoodlebar.web.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CustomerInfo implements Serializable {

  @NotNull
  private String name;

  @NotNull
  private String address1;

  @NotNull
  private String postcode;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

}
