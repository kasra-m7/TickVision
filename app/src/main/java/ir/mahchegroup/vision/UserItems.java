package ir.mahchegroup.vision;

public class UserItems {

    public static final String PATH = "https://mahchegroup.ir/tick_vision/";
    public static final String ADD_USER = "add_user.php";
    public static final String GET_USER = "get_user.php";
    public static final String HAS_VISION = "has_vision.php";
    public static final String CREATE_USER_TABLE = "create_user_table.php";
    public static final String GET_NUMBER_NEW_VISION = "get_number_new_vision.php";
    public static final String CREATE_VISION_TABLE = "create_vision_table.php";
    public static final String GET_ITEM_VISIONS = "get_item_visions.php";
    public static final String GET_VISION_INFO = "get_vision_info.php";
    public static final String GET_VISION_TABLE_NAME = "get_vision_table_name.php";
    public static final String GET_TABLE_INFO = "get_table_info.php";
    public static final String GET_PRICE_FROM_SERVER = "get_price_from_server.php";

    public static final String IS_FIRST_TIME = "is_first_time";
    public static final String IS_WRITE_USER_INFO = "is_write_user_info";
    public static final String IS_CHECKED_REMEMBER = "is_checked_remember";
    public static final String IS_RUN_TIMER_SERVICE = "is_run_timer";
    public static final String SELECTED_VISION = "selected_vision";

    public static final String MAIL = "item_mail";
    public static final String USERNAME = "item_username";
    public static final String PASSWORD = "item_password";
    public static final String USER_MAIL = "item_user_mail";
    public static final String USER_TABLE_NAME = "item_user_table_name";
    public static final String NAME_VISION = "item_name_vision";
    public static final String VISION_TABLE_NAME = "item_vision_table_name";
    public static final String DATE_VISION = "item_date_vision";
    public static final String MONEY_VISION = "item_money_vision";
    public static final String DAY_VISION = "item_day_vision";
    public static final String ONE_DAY_VISION = "item_one_day_vision";

    private String mail, username, password, money_vision, day_vision, one_day_vision, receive, payment, profit, leftover, ss, mm, hh, is_tick;

    public String getMail() {
        return mail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMoneyVision() {
        return money_vision;
    }

    public String getDayVision() {
        return day_vision;
    }

    public String getOneDayVision() {
        return one_day_vision;
    }

    public String getReceive() {
        return receive;
    }

    public String getPayment() {
        return payment;
    }

    public String getProfit() {
        return profit;
    }

    public String getLeftover() {
        return leftover;
    }

    public String getSs() {
        return ss;
    }

    public String getMm() {
        return mm;
    }

    public String getHh() {
        return hh;
    }

    public String getIsTick() {
        return is_tick;
    }
}
