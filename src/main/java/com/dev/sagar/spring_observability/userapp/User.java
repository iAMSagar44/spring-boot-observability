package com.dev.sagar.spring_observability.userapp;

public record User(int id, String name, String username,
                   String email, Address address, String phone, String website){

}
