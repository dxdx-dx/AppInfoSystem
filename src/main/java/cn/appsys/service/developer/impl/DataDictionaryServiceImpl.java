package cn.appsys.service.developer.impl;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.developer.DataDictionaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 3:02
 */
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    /**
     * 查询所有平台名称
     */
    @Override
    public List<DataDictionary> dataList() {
        return dataDictionaryMapper.dataList();
    }

    /**
     * 查询所有状态
     */
    @Override
    public List<DataDictionary> dataList1() {
        return dataDictionaryMapper.dataList1();
    }
}
