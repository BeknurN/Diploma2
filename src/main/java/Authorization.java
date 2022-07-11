public class Authorization {
    public Boolean success;
    public UserClient user;
    public String accessToken;
    public String refreshToken;

    public UserClient getUser() {
        return user;
    }

    public void setUser(UserClient user) {
        this.user = user;
    }

    public String getAccessToken(){
        return accessToken.replace("Bearer ", "");
    }
}