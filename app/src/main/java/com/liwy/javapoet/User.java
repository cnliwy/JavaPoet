package com.liwy.javapoet;

import java.lang.String;
import java.lang.System;

public final class User {
  private String name;

  private int age;

  public static void printUser(String name) {
    System.out.println(name);
  }

  public String getUserName() {
    return "tom";
  }
}
