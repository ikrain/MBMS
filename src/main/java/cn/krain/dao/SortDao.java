package cn.krain.dao;

import cn.krain.entity.Role;
import cn.krain.entity.Sort;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/17 - 11:53
 */
public interface SortDao {
    List<Sort> selectSortByName(Sort sort);

    void deleteSort(String id);

    void insertSort(Sort sort);

    void updateSort(Sort sort);

    Sort selectSortByStrName(String sort);
}
