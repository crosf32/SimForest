package fr.crosf32.fxtest.security;

public class HashHandler {

    public String hash(String raw) {

        return BCrypt.hashpw(raw, BCrypt.gensalt());
    }

    public boolean is(String hash, String rawPassword) {
        return BCrypt.checkpw(rawPassword, hash);
    }
}

