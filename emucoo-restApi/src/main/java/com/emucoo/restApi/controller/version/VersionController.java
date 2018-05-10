package com.emucoo.restApi.controller.version;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.sys.VersionQuery;
import com.emucoo.dto.modules.sys.VersionVO;
import com.emucoo.model.SysAppVersion;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.service.version.SysAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RestController
@RequestMapping("/api/version")
public class VersionController extends AppBaseController {

	@Autowired
    private SysAppVersionService sysAppVersionService;

    /**
     * 检查更新
     * @param params
     * @return
     */
    @PostMapping("/checkUpdate")
    public AppResult<VersionVO> checkUpdate(@RequestBody ParamVo<VersionQuery> params) {
        VersionQuery versionQuery=params.getData();
        VersionVO versionVO=null;
        checkParam(versionQuery.getVersion(), "版本号不能为空！");
        checkParam(versionQuery.getAppType(), "appType不能为空！");
        SysAppVersion sysAppVersion=new SysAppVersion();
        sysAppVersion.setAppType(versionQuery.getAppType());
        sysAppVersion.setAppVersion(versionQuery.getVersion());
        sysAppVersion.setIsDel(false);
        SysAppVersion appVersion=sysAppVersionService.findOne(sysAppVersion);
        Example example=new Example(SysAppVersion.class);
        example.createCriteria().andEqualTo("appType",appVersion.getAppType()).andEqualTo("isDel",false)
                .andEqualTo("isUse",true).andGreaterThan("id",appVersion.getId());
        example.setOrderByClause("id desc");
        List<SysAppVersion> list=sysAppVersionService.selectByExample(example);
        if( null!=list && list.size()>0 ){
           SysAppVersion sysAppVersion2= list.get(0);
            versionVO=new VersionVO();
            versionVO.setAppUrl(sysAppVersion2.getAppUrl());
            versionVO.setIsUpdateInstall(sysAppVersion2.getIsUpdateInstall());
            versionVO.setRemark(sysAppVersion2.getRemark());
            versionVO.setVersion(sysAppVersion2.getAppVersion());
        }
        return success(versionVO);
    }


}
