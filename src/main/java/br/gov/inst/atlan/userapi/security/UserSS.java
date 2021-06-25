package br.gov.inst.atlan.userapi.security;

import br.gov.inst.atlan.userapi.domain.enums.PerfilEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;

	private UUID id;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {
	}

	public UserSS(UUID id, String email, String password, Set<PerfilEnum> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao()))
				.collect(Collectors.toList());
	}

	public UUID getId() {
		return id;
	}

	public String getRoles() {
		return this.getAuthorities().stream().map(item -> item.getAuthority()).reduce((s1, s2) -> s1 + "," + s2)
				.orElse(null);
	}

	public boolean hasRole(final PerfilEnum perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
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

}
