package cn.krain.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CC
 * @data 2020/12/16 - 19:43
 */
public class MenuMapping {

    /**
     * 用户权限：系统中文模块对应路径
     */

    public static String HOME = "/home";

    public static String USER = "/user";

    public static String ROLE = "/role";

    public static String MOVIE = "/movie";

    public static String NEWS = "/news";

    public static String COMMENT = "/comment";

    public static Map<String, String> MENU_VALUE = new HashMap<>();

    static {
        MENU_VALUE.put("主页模块", HOME);
        MENU_VALUE.put("用户模块", USER);
        MENU_VALUE.put("角色模块", ROLE);
        MENU_VALUE.put("电影模块", MOVIE);
        MENU_VALUE.put("新闻模块", NEWS);
        MENU_VALUE.put("影评模块", COMMENT);
    }

}
