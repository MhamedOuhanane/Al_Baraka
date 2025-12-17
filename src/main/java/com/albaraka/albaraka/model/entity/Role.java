package com.albaraka.albaraka.model.entity;

import java.util.List;
import java.util.UUID;

public class Role {
    private Long id;
    private UUID uuid;
    private String name;
    private List<User> users;
}
