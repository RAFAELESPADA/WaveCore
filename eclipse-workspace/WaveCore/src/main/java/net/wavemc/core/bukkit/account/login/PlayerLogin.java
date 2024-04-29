package net.wavemc.core.bukkit.account.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class PlayerLogin {

    private final String name;
    private final LoginType type;
    private String password;
    private final long registeredDate;
    private long lastLoginDate;

    public boolean isRegistered() {
        return type.equals(LoginType.PREMIUM) || password != null && !password.isEmpty();
    }
}
