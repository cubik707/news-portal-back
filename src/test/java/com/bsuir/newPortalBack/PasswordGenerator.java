package com.bsuir.newPortalBack;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
  public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    System.out.println("admin: " + encoder.encode("pass123"));
    System.out.println("editor1: " + encoder.encode("pass123"));
    System.out.println("user1: " + encoder.encode("pass123"));
    System.out.println("dev_anna: " + encoder.encode("pass123"));
  }
}
