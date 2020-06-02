package cn.appsys.dao.datadictionary;

import cn.appsys.pojo.DataDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:31
 */
public interface DataDictionaryMapper {
    //查询所有平台名称
    List<DataDictionary> dataList();

    //查询所有状态
    List<DataDictionary> dataList1();
}
