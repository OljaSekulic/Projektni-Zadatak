package xwsagent.wallservice.domain;

import xwsagent.wallservice.dto.UserDTO;

public class UserTokenState {

	private String accessToken;
    private Long expiresIn;
    private Authority authority;
    private UserDTO loggedUser;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.authority = null;
    }

    public UserTokenState(String accessToken, long expiresIn, Authority authority, User user ) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.authority = authority;
        this.loggedUser = new UserDTO(user.getId(), user.getUsername(), null, null);
    }

    public Authority getAuthorities() {
		return authority;
	}

	public void setAuthorities(Authority authorities) {
		this.authority = authorities;
	}

	public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

	public UserDTO getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UserDTO loggedUser) {
		this.loggedUser = loggedUser;
	}
    
}
