package cn.appsys.controller.developer;

import cn.appsys.pojo.*;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppInfoService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 开发者功能控制类
 *
 * @author Matrix
 * @date 2020/5/16 2:52
 */
@Controller
@RequestMapping("/devApp")
public class AppController {
    @Resource
    private AppInfoService appInfoService;
    @Resource
    private AppCategoryService appCategoryService;
    @Resource
    private DataDictionaryService dataDictionaryService;
    @Resource
    private AppVersionService appVersionService;

    /**
     * 验证apk名称是否存在
     */
    @RequestMapping(value = "apkexist.json", method = RequestMethod.GET)
    @ResponseBody
    public Object apkNameIsExist(@RequestParam String APKName) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (StringUtils.isNullOrEmpty(APKName)) {
            resultMap.put("APKName", "empty");
        } else {
            AppInfo appInfo = null;
            try {
                appInfo = appInfoService.getAppInfo(null, APKName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null != appInfo) {
                resultMap.put("APKName", "exist");
            } else {
                resultMap.put("APKName", "noexist");
            }
        }
        return JSONArray.toJSONString(resultMap);
    }

    /**
     * 新增App基础信息
     */
    @RequestMapping(value = "appinfoadd", method = RequestMethod.GET)
    public String appinfoadd(Model model) {
        List<DataDictionary> dataDictionaryList = dataDictionaryService.dataList();
        List<AppCategory> categoryLevel1List = appCategoryService.getCategoryLevel1();
        model.addAttribute("categoryLevel1List", categoryLevel1List);
        model.addAttribute("flatFormList", dataDictionaryList);
        return "developer/appinfoadd";
    }

    /**
     * 保存新增appInfo（主表）的数据
     */
    @RequestMapping(value = "/appinfoaddsave", method = RequestMethod.POST)
    public String addSave(
            AppInfo appInfo,
            HttpSession session,
            HttpServletRequest request,
            @RequestParam(value = "a_logoPicPath", required = false) MultipartFile attach) {
        String logoPicPath = null;
        String logoLocPath = null;
        if (!attach.isEmpty()) {
            String path = request
                    .getSession()
                    .getServletContext()
                    .getRealPath(
                            "statics" + java.io.File.separator + "uploadfiles");
            String oldFileName = attach.getOriginalFilename();// 原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);// 原文件后缀
            int filesize = 500000;
            if (attach.getSize() > filesize) {// 上传大小不得超过 50k
                request.setAttribute("fileUploadError",
                        Constants.FILEUPLOAD_ERROR_4);
                return "developer/appinfoadd";
            } else if (prefix.equalsIgnoreCase("jpg")
                    || prefix.equalsIgnoreCase("png")
                    || prefix.equalsIgnoreCase("jepg")
                    || prefix.equalsIgnoreCase("pneg")) {// 上传图片格式
                String fileName = appInfo.getAPKName() + ".jpg";// 上传LOGO图片命名:apk名称.apk
                File targetFile = new File(path, fileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("fileUploadError",
                            Constants.FILEUPLOAD_ERROR_2);
                    return "developer/appinfoadd";
                }
                logoPicPath = request.getContextPath()
                        + "/statics/uploadfiles/" + fileName;
                logoLocPath = path + File.separator + fileName;
            } else {
                request.setAttribute("fileUploadError",
                        Constants.FILEUPLOAD_ERROR_3);
                return "developer/appinfoadd";
            }
        }
        appInfo.setCreatedBy(((DevUser) session
                .getAttribute(Constants.DEVUSER_SESSION)).getId());
        appInfo.setCreationDate(new Date());
        appInfo.setLogoPicPath(logoPicPath);
        appInfo.setLogoLocPath(logoLocPath);
        appInfo.setDevId(((DevUser) session
                .getAttribute(Constants.DEVUSER_SESSION)).getId());
        appInfo.setStatus(1);
        try {
            if (appInfoService.add(appInfo)) {
                return "redirect:/devApp/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "developer/appinfoadd";
    }

    /**
     * 增加appversion信息（跳转到新增app版本页面）
     */
    @RequestMapping(value = "appversionadd", method = RequestMethod.GET)
    public String addVersion(@RequestParam(value = "id") String appId, AppVersion appVersion,
                             Model model) {
        appVersion.setAppId(Integer.parseInt(appId));
        List<AppVersion> appVersionList = null;
        try {
            appVersionList = appVersionService.getAppVersionList(Integer.parseInt(appId));
            System.out.println(appVersionList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("appVersionList", appVersionList);
        model.addAttribute(appVersion);
        return "developer/appversionadd";
    }

    /**
     * 添加app版本信息
     */
    @RequestMapping(value = "/addversionsave", method = RequestMethod.POST)
    public String addVersionSave(
            AppVersion appVersion,
            HttpSession session,
            HttpServletRequest request,
            @RequestParam(value = "a_downloadLink", required = false) MultipartFile attach) {
        String downloadLink = null;
        String apkLocPath = null;
        String apkFileName = null;
        if (!attach.isEmpty()) {
            String path = request.getSession().getServletContext()
                    .getRealPath("statics" + File.separator + "uploadfiles");
            String oldFileName = attach.getOriginalFilename();// 原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);// 原文件后缀
            if (prefix.equalsIgnoreCase("apk")) {// apk文件命名：apk名称+版本号+.apk
                String apkName = null;
                try {
                    apkName = appInfoService.getAppInfo(appVersion.getAppId(),
                            null).getAPKName();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (apkName == null || "".equals(apkName)) {
                    return "redirect:/devApp/appversionadd?id="
                            + appVersion.getAppId() + "&error=error1";
                }
                apkFileName = apkName + "-" + appVersion.getVersionNo()
                        + ".apk";
                File targetFile = new File(path, apkFileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "redirect:/devApp/appversionadd?id="
                            + appVersion.getAppId() + "&error=error2";
                }
                downloadLink = request.getContextPath()
                        + "/statics/uploadfiles/" + apkFileName;
                apkLocPath = path + File.separator + apkFileName;
            } else {
                return "redirect:/devApp/appversionadd?id="
                        + appVersion.getAppId() + "&error=error3";
            }
        }
        appVersion.setCreatedBy(((DevUser) session
                .getAttribute(Constants.DEVUSER_SESSION)).getId());
        appVersion.setCreationDate(new Date());
        appVersion.setDownloadLink(downloadLink);
        appVersion.setApkLocPath(apkLocPath);
        appVersion.setApkFileName(apkFileName);
        try {
            if (appVersionService.appsysadd(appVersion)) {
                return "redirect:/devApp/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/devApp/appversionadd?id="
                + appVersion.getAppId();
    }

    /**
     * 修改最新的appVersion信息（跳转到修改appVersion页面）
     */
    @RequestMapping(value = "/appversionmodify", method = RequestMethod.GET)
    public String modifyAppVersion(@RequestParam("vid") String versionId,
                                   @RequestParam("aid") String appId,
                                   @RequestParam(value = "error", required = false) String fileUploadError,
                                   Model model) {
        AppVersion appVersion = null;
        List<AppVersion> appVersionList = null;
        if (null != fileUploadError && fileUploadError.equals("error1")) {
            fileUploadError = Constants.FILEUPLOAD_ERROR_1;
        } else if (null != fileUploadError && fileUploadError.equals("error2")) {
            fileUploadError = Constants.FILEUPLOAD_ERROR_2;
        } else if (null != fileUploadError && fileUploadError.equals("error3")) {
            fileUploadError = Constants.FILEUPLOAD_ERROR_3;
        }
        try {
            appVersion = appVersionService.getAppVersionById(Integer.parseInt(versionId));
            appVersionList = appVersionService.getAppVersionList(Integer.parseInt(appId));
            for (AppVersion a : appVersionList
            ) {
                System.out.println(a + "*************************************************************");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute(appVersion);
        model.addAttribute("appVersionList", appVersionList);
        model.addAttribute("fileUploadError", fileUploadError);
        return "developer/appversionmodify";
    }

    /**
     * 保存修改后的appVersion
     */
    @RequestMapping(value = "/appversionmodifysave", method = RequestMethod.POST)
    public String modifyAppVersionSave(AppVersion appVersion, HttpSession session, HttpServletRequest request,
                                       @RequestParam(value = "attach", required = false) MultipartFile attach) {
        String downloadLink = null;
        String apkLocPath = null;
        String apkFileName = null;
        if (!attach.isEmpty()) {
            String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
            String oldFileName = attach.getOriginalFilename();//原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
            if (prefix.equalsIgnoreCase("apk")) {//apk文件命名：apk名称+版本号+.apk
                String apkName = null;
                try {
                    apkName = appInfoService.getAppInfo(appVersion.getAppId(), null).getAPKName();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (apkName == null || "".equals(apkName)) {
                    return "redirect:/devApp/appversionmodify?vid=" + appVersion.getId()
                            + "&aid=" + appVersion.getAppId()
                            + "&error=error1";
                }
                apkFileName = apkName + "-" + appVersion.getVersionNo() + ".apk";
                File targetFile = new File(path, apkFileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "redirect:/devApp/appversionmodify?vid=" + appVersion.getId()
                            + "&aid=" + appVersion.getAppId()
                            + "&error=error2";
                }
                downloadLink = request.getContextPath() + "/statics/uploadfiles/" + apkFileName;
                apkLocPath = path + File.separator + apkFileName;
            } else {
                return "redirect:/devApp/appversionmodify?vid=" + appVersion.getId()
                        + "&aid=" + appVersion.getAppId()
                        + "&error=error3";
            }
        }
        appVersion.setModifyBy(((DevUser) session.getAttribute(Constants.DEVUSER_SESSION)).getId());
        appVersion.setModifyDate(new Date());
        appVersion.setDownloadLink(downloadLink);
        appVersion.setApkLocPath(apkLocPath);
        appVersion.setApkFileName(apkFileName);
        try {
            if (appVersionService.modify(appVersion)) {
                return "redirect:/devApp/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "developer/appversionmodify";
    }

    /**
     * ajax查询所属平台下拉框
     */
    @RequestMapping(value = "datadictionarylist.json", method = RequestMethod.GET)
    @ResponseBody
    public Object Platform() {
        List<DataDictionary> dictionariesList = null;
        dictionariesList = dataDictionaryService.dataList();
        return JSON.toJSONString(dictionariesList);
    }

    /**
     * ajax联动分类下拉框
     */
    @RequestMapping(value = "categorylevellist.json", method = RequestMethod.GET)
    @ResponseBody
    public Object Classification(@RequestParam String pid) {
        List<AppCategory> categoriesList = null;
        if (pid != null && pid != "") {
            categoriesList = appCategoryService.getCategoryLevel2(Integer.valueOf(pid));
        } else {
            categoriesList = appCategoryService.getCategoryLevel1();
        }
        return JSON.toJSONString(categoriesList);
    }

    /**
     * app基础信息修改页面(待审核状态)
     */
    @RequestMapping(value = "/appinfomodeify.html", method = RequestMethod.GET)
    public String appinfomodeify(@RequestParam Integer id, Model model) {
        AppInfo info = appInfoService.getAppinfoById(id);
        List<DataDictionary> dataDictionaryList = dataDictionaryService.dataList();
        List<AppCategory> categoryLevel1List = appCategoryService.getCategoryLevel1();
        model.addAttribute("appInfo", info);
        model.addAttribute("categoryLevel1List", categoryLevel1List);
        model.addAttribute("flatFormList", dataDictionaryList);
        return "developer/appinfomodify";
    }

    /**
     * 修改app基础信息方法n
     */
    @RequestMapping(value = "/appinfomodifysave.html", method = RequestMethod.POST)
    public String appinfomodeifySave(AppInfo appinfo, HttpSession session, HttpServletRequest request,
                                     @RequestParam(value = "attach", required = false) MultipartFile attach,
                                     @RequestParam("status") Integer status) {
        //文件上传
        String idPicPath = null;
        String idTocPath = null;
        //判断文件是否为空
        if (!attach.isEmpty()) {
            // 定义上传的目标路径
			/*String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");*/
          //  String path = "E:\\workspace_s2\\AppInfoSystem\\WebContent\\statics\\uploadfiles";
 String path ="G:\\AppInfoSystem\\src\\main\\webapp\\statics\\uploadfiles"
            // 获取原文件名
            String oldFileName = attach.getOriginalFilename();

            // 获取原文件名的后缀
            String prefix = FilenameUtils.getExtension(oldFileName);// 原文件后缀

            int filesize = 500000;

            // 上传大小不得超过 500k
            if (attach.getSize() > filesize) {

                request.setAttribute("uploadFileError", " * 上传大小不得超过 500k");
                return "jsp/useradd";

            } else if (prefix.equalsIgnoreCase("jpg")
                    || prefix.equalsIgnoreCase("png")
                    || prefix.equalsIgnoreCase("jpeg")
                    || prefix.equalsIgnoreCase("pneg")) {// 上传图片格式不正确
                // 新的照片名称，毫秒数加随机数，确保不能重复
                System.out.println("-----------------进入");
                String fileName = System.currentTimeMillis()
                        + RandomUtils.nextInt(1000000) + "_Personal.jpg";
                System.out.println("-----------------ddd");
                // 创建文件对象，此文件对象用于接收用户上传的文件流
                File targetFile = new File(path, fileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                // 保存
                try {
                    // 把MultipartFile中的文件流数据的数据输出至目标文件中
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("uploadFileError", " * 上传失败！");
                    return "jsp/useradd";
                }
                idTocPath = request.getContextPath() + "/statics/uploadfiles/" + fileName;
                // 获取文件的的名称保存到数据库中
                idPicPath = path + File.separator + fileName;
            } else {
                request.setAttribute("uploadFileError", " * 上传图片格式不正确");
                return "jsp/useradd";
            }
        }
        //更新者
        appinfo.setModifyBy(((DevUser) session.getAttribute(Constants.DEVUSER_SESSION)).getId());
        //更新时间
        appinfo.setModifyDate(new Date());
        //图片路径
        appinfo.setLogoPicPath(idTocPath);
        appinfo.setLogoLocPath(idPicPath);
        int count = appInfoService.appinfomodify(appinfo);
        if (count > 0) {
            //判断提交是否需要审核
            //logger.debug("=====>提交并审核状态：" + appinfo.getStatus());
            if (status == 1) {
                if (appVersionService.modifystatus(appinfo.getStatus(), appinfo.getId()) > 0) {
                } else {
                    return "redirect:/devApp/appinfomodeify.html";
                }
            }
            return "redirect:/devApp/list";

        } else {
            return "redirect:/devApp/appinfomodeify.html";
        }
    }

    /**
     * 查看app详细信息
     */
    @RequestMapping("appview/{id}")
    public String view(@PathVariable Integer id, Model model) {
        List<AppVersion> appVersionList = appVersionService.appVersion(id);
        AppInfo appInfo = appInfoService.appinfo(id);
        model.addAttribute("appInfo", appInfo);
        model.addAttribute("appVersionList", appVersionList);
        return "developer/appinfoview";
    }

    /**
     * 刪除功能
     */
    @RequestMapping(value = "/delapp.json")
    @ResponseBody
    public Object delApp(@RequestParam String id) {
        //定义返回状态集合
        HashMap<String, String> resultMap = new HashMap<String, String>();
        //判断id是否为空
        if (StringUtils.isNullOrEmpty(id)) {
            resultMap.put("delResult", "notexist");
        } else {
            try {//判断是否删除成功
                if (appInfoService.appdelete(Integer.parseInt(id)))
                    resultMap.put("delResult", "true");
                else
                    resultMap.put("delResult", "false");
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }//返回json格式的數據
        return JSONArray.toJSONString(resultMap);
    }

    /**
     * 上下架
     */
    @RequestMapping(value = "/{appid}/sale", method = RequestMethod.PUT)
    @ResponseBody
    public Object upSatus(@PathVariable String appid, HttpSession session) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        Integer appIdInteger = 0;
        try {
            appIdInteger = Integer.parseInt(appid);
        } catch (Exception e) {
            appIdInteger = 0;
        }
        resultMap.put("errorCode", "0");
        if (appIdInteger > 0) {
            try {
                DevUser devUser = (DevUser) session.getAttribute(Constants.DEVUSER_SESSION);
                AppInfo appInfo = new AppInfo();
                appInfo.setId(appIdInteger);
                appInfo.setModifyBy(devUser.getId());
                if (appInfoService.updateSatus(appInfo)) {
                    resultMap.put("resultMsg", "success");
                } else {
                    resultMap.put("resultMsg", "failed");
                }
            } catch (Exception e) {
                resultMap.put("errorCode", "exception000001");
            }
        } else {
            //errorCode:0 为正常
            resultMap.put("errorCode", "param000001");
        }
        return resultMap;
    }

    /**
     * APP列表信息
     */
    @RequestMapping("list")
    public String list(@RequestParam(value = "pageIndex", required = false) String pageIndex,
                       @RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
                       @RequestParam(value = "queryFlatformId", required = false) String queryFlatformId,
                       @RequestParam(value = "queryStatus", required = false) String queryStatus,
                       @RequestParam(value = "queryCategoryLevel1", required = false) String queryCategoryLevel1,
                       @RequestParam(value = "queryCategoryLevel2", required = false) String queryCategoryLevel2,
                       @RequestParam(value = "queryCategoryLevel3", required = false) String queryCategoryLevel3,
                       Model model) {
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
        int _queryStatus;
        if (queryStatus == null || queryStatus.equals("")) {
            _queryStatus = 0;
        } else {
            _queryStatus = Integer.parseInt(queryStatus);
        }
        PageSupport pageSupport = new PageSupport();
        pageSupport.setPageSize(Constants.PAGE_SIZE);
        pageSupport.setCurrentPageNo(_pageIndex);
        //查询总条数
        int tatalCount = appInfoService.getAppinfoCount(querySoftwareName, _queryFlatformId,
                _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, _queryStatus);
        pageSupport.setTotalCount(tatalCount);
        List<AppInfo> appInfoList = appInfoService.getAppInfoList(querySoftwareName, _queryFlatformId,
                _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, _queryStatus, pageSupport.getCurrentPageNo(), Constants.PAGE_SIZE);
        List<DataDictionary> dataDictionaryList = dataDictionaryService.dataList();
        List<DataDictionary> dataDictionaryList1 = dataDictionaryService.dataList1();
        List<AppCategory> categoryLevel1List = appCategoryService.getCategoryLevel1();
        List<AppCategory> categoryLevel2List = appCategoryService.getCategoryLevel2(_queryCategoryLevel1);
        List<AppCategory> categoryLevel3List = appCategoryService.getCategoryLevel2(_queryCategoryLevel2);
        model.addAttribute("flatFormList", dataDictionaryList);
        model.addAttribute("statusList", dataDictionaryList1);
        model.addAttribute("categoryLevel1List", categoryLevel1List);
        model.addAttribute("categoryLevel2List", categoryLevel2List);
        model.addAttribute("categoryLevel3List", categoryLevel3List);
        model.addAttribute("appInfoList", appInfoList);
        model.addAttribute("pages", pageSupport);
        model.addAttribute("querySoftwareName", querySoftwareName);
        model.addAttribute("queryStatus", queryStatus);
        model.addAttribute("querySoftwareName", querySoftwareName);
        model.addAttribute("queryFlatformId", queryFlatformId);
        model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
        model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
        model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
        System.out.println("laodushishabi");
        return "/developer/appinfolist";
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

        Integer parentId = Integer.valueOf(pid);
        List<AppCategory> categoryLevel1List = appCategoryService.getCategoryLevel2(parentId);
        return JSONArray.toJSONString(categoryLevel1List);
    }
}
