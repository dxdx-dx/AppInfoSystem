package cn.appsys.controller.backend;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.backend.AppService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台功能控制类
 *
 * @author Matrix
 * @date 2020/5/16 2:47
 */
@Controller
@RequestMapping("/backendApp")
public class AppCheckController {
    @Resource
    private AppService appService;

    /**
     * 获得app列表
     */
    @RequestMapping("/list")
    public String userList(@RequestParam(value = "pageIndex", required = false) String pageIndex,
                           @RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
                           @RequestParam(value = "queryFlatformId", required = false) String queryFlatformId,
                           @RequestParam(value = "queryCategoryLevel1", required = false) String queryCategoryLevel1,
                           @RequestParam(value = "queryCategoryLevel2", required = false) String queryCategoryLevel2,
                           @RequestParam(value = "queryCategoryLevel3", required = false) String queryCategoryLevel3,
                           Model model) {
        //app名称
        if (querySoftwareName == null) {
            querySoftwareName = "";
        }
        int _queryFlatformId;
        if (queryFlatformId == null || queryFlatformId.equals("")) {
            _queryFlatformId = 0;
        } else {
            _queryFlatformId = Integer.parseInt(queryFlatformId);
        }
        int _queryCategoryLevel1;
        if (queryCategoryLevel1 == null || queryCategoryLevel1.equals("")) {
            _queryCategoryLevel1 = 0;
        } else {
            _queryCategoryLevel1 = Integer.parseInt(queryCategoryLevel1);
        }
        int _queryCategoryLevel2;
        int _queryCategoryLevel3;
        if (queryCategoryLevel2 == null || queryCategoryLevel2.equals("")) {
            _queryCategoryLevel2 = 0;
        } else {
            _queryCategoryLevel2 = Integer.parseInt(queryCategoryLevel2);
        }
        if (queryCategoryLevel3 == null || queryCategoryLevel3.equals("")) {
            _queryCategoryLevel3 = 0;
        } else {
            _queryCategoryLevel3 = Integer.parseInt(queryCategoryLevel3);
        }
        int _pageIndex;
        if (pageIndex == null || pageIndex.equals("")) {
            _pageIndex = 1;
        } else {
            _pageIndex = Integer.parseInt(pageIndex);
        }
        //分页对象
        PageSupport pageSupport = new PageSupport();
        pageSupport.setPageSize(Constants.PAGE_SIZE);
        pageSupport.setCurrentPageNo(_pageIndex);
        //查询总条数
        int tatalCount = appService.getAppinfoCount(querySoftwareName, _queryFlatformId,
                _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, 1);
        pageSupport.setTotalCount(tatalCount);
        List<AppInfo> appInfoList = appService.getAppInfoList(querySoftwareName, _queryFlatformId,
                _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, 1, pageSupport.getCurrentPageNo(), Constants.PAGE_SIZE);
        List<DataDictionary> dataDictionaryList = appService.dataList();
        List<AppCategory> categoryLevel1List = appService.getCategoryLevel1();
        List<AppCategory> categoryLevel2List = appService.getCategoryLevel2(_queryCategoryLevel1);
        List<AppCategory> categoryLevel3List = appService.getCategoryLevel2(_queryCategoryLevel2);
        model.addAttribute("categoryLevel2List", categoryLevel2List);
        model.addAttribute("categoryLevel3List", categoryLevel3List);
        model.addAttribute("pages", pageSupport);
        model.addAttribute("categoryLevel1List", categoryLevel1List);
        model.addAttribute("flatFormList", dataDictionaryList);
        model.addAttribute("appInfoList", appInfoList);
        model.addAttribute("querySoftwareName", querySoftwareName);
        model.addAttribute("queryFlatformId", queryFlatformId);
        model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
        model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
        model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
        return "/backend/applist";
    }

    /**
     * ajax动态加载二三级分类
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/5/19 17:14
     */
    @RequestMapping("/categorylevellist")
    @ResponseBody
    public String logout(@RequestParam(value = "pid", required = true) String pid, Model model) {
        //System.out.println(pid);
        Integer parentId = Integer.valueOf(pid);
        List<AppCategory> categoryLevel1List = appService.getCategoryLevel2(parentId);
       /* for (AppCategory app : categoryLevel1List) {
            System.out.println(app);
        }*/
        return JSONArray.toJSONString(categoryLevel1List);
    }

    /**
     * 跳转app审核页面
     */
    @RequestMapping("/check")
    public String check(@RequestParam(value = "aid", required = false) Integer aid,
                        @RequestParam(value = "vid", required = false) Integer vid,
                        Model model) {
        System.out.println(aid);
        System.out.println(vid);
        AppVersion appVersion = appService.appVersiondetal(vid);
        AppInfo appInfo = appService.appinfodetal(aid);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("appInfo", appInfo);
        return "/backend/appcheck";
    }

    /**
     * APP审核操作
     * n
     */
    @RequestMapping("/checksave")
    public String checksave(@RequestParam(value = "status", required = false) Integer status,
                            @RequestParam(value = "id", required = false) Integer id) {
        try {
            if (appService.updateSatus(status, id) > 0) {
                return "redirect:/backendApp/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "backendApp/check";
    }

}
