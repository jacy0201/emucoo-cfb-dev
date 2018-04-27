package com.emucoo.job.handle;

import com.alibaba.fastjson.JSON;
import com.emucoo.model.SysUser;
import com.emucoo.service.sys.SysUserService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/* 任务Handler示例（Bean模式）
 *
 * 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 */
 @JobHandler(value="demoJobHandler")
 @Component
public class DemoJobHandler extends IJobHandler {


	@Resource
	private SysUserService sysUserService;

	@Override
	public ReturnT execute(String  param) throws Exception {
		SysUser sysUser = sysUserService.findById(1L);
		XxlJobLogger.log("<>查询结果={0}",param );
		ReturnT rt =new ReturnT();
		sysUser.setPassword("");
		rt.setContent(sysUser.getRealName()+":"+sysUser.getUsername());
		rt.setMsg("查询用户表信息成功");
		return rt;
	}

}
