package entity;

import org.springframework.security.core.GrantedAuthority;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_CUSTOMER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
