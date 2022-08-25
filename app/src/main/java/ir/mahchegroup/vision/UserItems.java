package ir.mahchegroup.vision;

public class UserItems {

    public static final String PATH = "https://mahchegroup.ir/tick_vision/";
    public static final String ADD_USER = "add_user.php";
    public static final String GET_USER = "get_user.php";
    public static final String HAS_VISION = "has_vision.php";
    public static final String CREATE_USER_TABLE = "create_user_table.php";

    public static final String IS_FIRST_TIME = "is_first_time";
    public static final String IS_WRITE_USER_INFO = "is_write_user_info";
    public static final String IS_CHECKED_REMEMBER = "is_checked_remember";
    public static final String IS_RUN_TIMER_SERVICE = "is_run_timer";

    public static final String MAIL = "item_mail";
    public static final String USERNAME = "item_username";
    public static final String PASSWORD = "item_password";
    public static final String USER_MAIL = "item_user_mail";
    public static final String USER_TABLE_NAME = "item_user_table_name";
    public static final String NAME_VISION = "item_name_vision";

    private String mail, username, password;

    public String getMail() {
        return mail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
