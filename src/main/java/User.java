import org.apache.commons.lang3.RandomStringUtils;


public class User {
    private String email;
    private String password;
    private String name;


    public static User getRandom () {
        String email = (RandomStringUtils.randomAlphanumeric(5)+"@"+ RandomStringUtils.randomAlphanumeric(5)+".ru").toLowerCase();
        String password = RandomStringUtils.randomAlphanumeric(10);
        String name = RandomStringUtils.randomAlphanumeric(10);
        return new User(email, password, name);
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
