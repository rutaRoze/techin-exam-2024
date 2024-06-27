package lt.techin.exam.enums;

public enum Role {
    ADMIN,
    USER;

    public static Role fromString(String roleString) {
        if (roleString == null) {
            throw new IllegalArgumentException("Role string cannot be null");
        }
        try {
            return Role.valueOf(roleString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + roleString);
        }
    }
}
