package cn.krain.Test;

import cn.krain.entity.Role;
import cn.krain.service.Impl.UserServiceImpl;
import cn.krain.util.MD5Util;
import cn.krain.util.MenuMapping;
import cn.krain.util.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * @author CC
 * @data 2020/12/15 - 18:43
 */
public class Test01 {

    @Value("${server.path}")
    private String SERVER_PATH;

    @Test
    public void testDemo01(){
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis()/1000);
        System.out.println(MD5Util.getMD5("123456"));
    }

    @Test
    public void testDemo02(){
        UserServiceImpl userService = new UserServiceImpl();
        Map<String, String> map = new HashMap<>();
        map.put("username", "zhangsan");
        map.put("roleName", "经理");
        //System.out.println(userService.queryUser(map));
    }

    @Test
    public void testDemo03(){
        String mm = MenuMapping.MENU_VALUE.get("用户模块");
        System.out.println(mm);
    }

    @Test
    public void testDemo06(){
        String[] typeId = new String[100];
        List<String> list = new ArrayList<>();
        list.add("4536456645465");
        System.out.println("listSize:"+list.size());
        System.out.println(list.get(0));
        for (int i = 0; i < list.size(); i++) {
            typeId[i] = list.get(i);
        }
        for (int i = 0; i < typeId.length; i++) {
            System.out.println(i+":"+typeId[i]);
        }
    }

    @Test
    public void testDemo05(){
        String[] menu = {"","home","user"};
        String[] newstr = Arrays.copyOfRange(menu,1,menu.length);
        for (int i = 0; i < newstr.length; i++) {
            System.out.println("字符:"+newstr[i]);
        }
    }

    @Test
    public void testDemo04(){
        List<Role> list = new ArrayList<>();
        Role role = new Role();
        role.setMenu("/home/user /index");
        list.add(0,role);
        Role role2 = new Role();
        role2.setMenu("/home/user /index");
        list.add(1,role2);
        String[] me = {};
        for (int i = 0; i < list.size(); i++) {
            String menu = list.get(i).getMenu();
            System.out.println("menu:"+menu);
            me = menu.split(" ");
            list.get(i).setMenus(me);
        }
        System.out.println("=============================");
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getMenus().length; j++) {
                System.out.println(list.get(i).getMenus()[j]);
            }
        }

    }

    @Test
    public void printUUID(){
        System.out.println(UUIDUtil.getUUID());
        System.out.println(UUIDUtil.getUUID());
        System.out.println(UUIDUtil.getUUID());
        System.out.println(UUIDUtil.getUUID());
        System.out.println(UUIDUtil.getUUID());
    }

    @Test
    public void testDemo07(){
        Map<String, String> typeMap = new HashMap<>();
        typeMap.put("爱情","2345");
        typeMap.put("爱情1","23453");
        typeMap.put("爱情2","23454");
        Iterator<Map.Entry<String,String>> iterator = typeMap.entrySet().iterator();
        String typeNameStr = "";
        while (iterator.hasNext()){
            Map.Entry entry = iterator.next();
            typeNameStr += entry.getKey();
            if (iterator.hasNext()){
                typeNameStr += " ";
            }
            System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
        }
        System.out.println("typeNamestr:"+typeNameStr+"0");
    }

    @Test
    public void testValue(){
        System.out.println(SERVER_PATH);
    }

}
