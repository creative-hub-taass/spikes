package com.registrazione.demo.appuser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {


    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String nickname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    @OneToMany //accetta solo i type basic
    private List<AppUser> listFans;
    @OneToMany
    private List<AppUser> listInspirers;
    private Long idCreator;
    private Boolean locked = false;
    private Boolean enabled = false;

    public AppUser(String nickname,
                   String email,
                   String password,
                   AppUserRole appUserRole) {

        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.idCreator = (long) 0;
        listInspirers = new ArrayList<>(); // arraylist con capacità iniziale 10
        listFans = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    public String getNickname(){
        return nickname;
    }

    public Long getIdCreator(){
        return idCreator;
    }

    public List<AppUser> getListInspirers(){
        return listInspirers;
    }

    public List<AppUser> getListFans(){
        if(idCreator != 0)return listFans;
            else return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}