package cn.krain.service;

import cn.krain.entity.Role;
import cn.krain.entity.Sort;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/17 - 11:54
 */
public interface SortService {
    List<Sort> querySort(Sort sort);

    void delSortById(String id);

    void addSort(Sort sort);

    void modifySort(Sort sort);
}
