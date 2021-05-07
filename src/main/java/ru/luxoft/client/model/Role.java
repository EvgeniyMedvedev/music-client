package ru.luxoft.client.model;

import lombok.Data;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.UUID;

@Data
public class Role implements GrantedAuthority {

    private UUID id;

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
