package cn.appsys.service.developer;

import cn.appsys.pojo.DataDictionary;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 3:02
 */
public interface DataDictionaryService {
    //查询所有平台名称
    List<DataDictionary> dataList();

    //查询所有状态
    List<DataDictionary> dataList1();

}
