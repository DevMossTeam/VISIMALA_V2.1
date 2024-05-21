package session;

public class session {
    private static String username;
    private static String id_user;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        session.username = username;
    }

    public static String getId_user() {
        return id_user;
    }

    public static void setId_user(String id_user) {
        session.id_user = id_user;
    }
}