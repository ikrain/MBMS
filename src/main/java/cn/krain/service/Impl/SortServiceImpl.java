package cn.krain.service.Impl;

import cn.krain.dao.SortDao;
import cn.krain.entity.Role;
import cn.krain.entity.Sort;
import cn.krain.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/17 - 12:06
 */
@Service
public class SortServiceImpl implements SortService {

    @Autowired
    private SortDao sortDao;

    @Override
    public List<Sort> querySort(Sort sort) {
        return sortDao.selectSortByName(sort);
    }

    @Override
    public void delSortById(String id) {
        sortDao.deleteSort(id);
    }

    @Override
    public void addSort(Sort sort) {
        sortDao.insertSort(sort);
    }

    @Override
    public void modifySort(Sort sort) {
        sortDao.updateSort(sort);
    }
}
