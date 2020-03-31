package com.stage_facile.stage_facile.security.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stage_facile.stage_facile.models.User;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -5776612641089270906L;
	private Long id;
	private String username;
	private @JsonIgnore String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(Long id, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(User user) {
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().name()); 
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);

		return new UserDetailsImpl(
				user.getId(), 
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
