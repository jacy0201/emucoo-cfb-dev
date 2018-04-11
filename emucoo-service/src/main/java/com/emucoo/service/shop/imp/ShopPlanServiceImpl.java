/**
 * 
 */
package com.emucoo.service.shop.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.shop.PatrolShopCycle;
import com.emucoo.dto.modules.shop.PrecinctArr;
import com.emucoo.dto.modules.shop.ShopPlanListVO;
import com.emucoo.dto.modules.shop.ShopPlanProgressVO;
import com.emucoo.mapper.DepartmentMapper;
import com.emucoo.mapper.DeptRoleUnionMapper;
import com.emucoo.mapper.shop.TFrontPlanMapper;
import com.emucoo.mapper.shop.TLoopPlanSrcMapper;
import com.emucoo.model.Department;
import com.emucoo.model.DeptRoleUnion;
import com.emucoo.model.shop.TFrontPlan;
import com.emucoo.model.shop.TLoopPlanSrc;
import com.emucoo.service.shop.ShopPlanService;
import com.emucoo.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jacy
 *
 */
@Service
public class ShopPlanServiceImpl extends BaseServiceImpl<TFrontPlan> implements ShopPlanService {

	private static final Logger logger = LoggerFactory.getLogger(ShopPlanServiceImpl.class);

	@Resource
	private TFrontPlanMapper tFrontPlanMapper;

	@Resource
	private TLoopPlanSrcMapper tLoopPlanSrcMapper;

	@Resource
	private DepartmentMapper departmentMapper;

	@Resource
	private DeptRoleUnionMapper deptRoleUnionMapper;

	@Value(("${cfb.precinct_id}"))
	private String precinctId;

	@Value(("${cfb.precinct_name}"))
	private String precinctName;

	@Value(("${cfb.total_shops}"))
	private String totalShops;


	@Override
	public ShopPlanListVO getShopPlanList(String month){
		int paramMonth=4;
		int paramYear=2018;
		if(null==month ||"".equals(month)){
			paramYear= DateUtil.getYear();
			paramMonth=DateUtil.getMonth();
		}else{
		 String dateStr=formatStrDate(month);
		 String [] str=dateStr.split("-");
			paramYear=Integer.parseInt(str[0]);
			paramMonth=Integer.parseInt(str[1]);
		}

		ShopPlanListVO shopPlanListVO=new ShopPlanListVO();
		//月份和计划集合
		List<PatrolShopCycle> cycleList=new ArrayList<PatrolShopCycle>();
		//区域店面集合
		List<PrecinctArr> precinctArrList=new ArrayList<PrecinctArr>();

		//当前写死一个区域,从application.yml 配置中取
		PrecinctArr precinctArr=new PrecinctArr();
		//设置区域ID
		precinctArr.setPrecinctID(Integer.parseInt(precinctId));
		//设置区域名称
		precinctArr.setPrecinctName(precinctName);

		//店铺集合
		List<PrecinctArr.Shop> shopList=new ArrayList<PrecinctArr.Shop>();

		PatrolShopCycle patrolShopCycle=null;
		//查询所传月份的店面计划集合
		Example exampleSrc= new Example(TLoopPlanSrc.class);
		exampleSrc.createCriteria().andEqualTo("year",paramYear).andEqualTo("month",paramMonth);
		List<TLoopPlanSrc> loopPlanList=tLoopPlanSrcMapper.selectByExample(exampleSrc);

		Example example = new Example(TFrontPlan.class);
		PrecinctArr.Shop shop=null;

		for (TLoopPlanSrc loopPlanSrc: loopPlanList){
			shop=new PrecinctArr.Shop();
			shop.setShopID(loopPlanSrc.getDptId());
            shop.setSubID(loopPlanSrc.getId());
			//店铺名称
			Department departmentVO = departmentMapper.selectByPrimaryKey(loopPlanSrc.getDptId());
			shop.setShopName(departmentVO.getDptname());
			//status :1未安排 2已安排 3已完成 4未计划
			shop.setShopStatus(1);

			shop.setExPatrloShopArrangeDate("");
            shop.setPatrolShopArrangeID(null);
			// 查询巡店计划已安排的实例
			if(loopPlanSrc.getIsPlan()){
				example.clear();
				example.createCriteria().andEqualTo("loopPlanSrcId",loopPlanSrc.getId().longValue());
				List<TFrontPlan> listFront=tFrontPlanMapper.selectByExample(example);
				if(null!=listFront && listFront.size()>0){
                    TFrontPlan frontPlan=listFront.get(0);
                    //状态 false：未巡店  true：已巡店'
                    if(frontPlan.getStatus())
                        shop.setShopStatus(3);
                    else
                        shop.setShopStatus(2);

                    shop.setExPatrloShopArrangeDate(com.emucoo.utils.DateUtil.simple(frontPlan.getPlanTime()));
                    shop.setPatrolShopArrangeID(frontPlan.getId());
                }

			}

			shopList.add(shop);
		}
		precinctArr.setShopArr(shopList);
		precinctArr.setShopNum(shopList.size());
		precinctArrList.add(precinctArr);

      //查询巡店计划月份
		List<TLoopPlanSrc> planSrcList =tLoopPlanSrcMapper.getPlanMonth();
		PatrolShopCycle psc=null;
		for (TLoopPlanSrc lps :planSrcList){
			psc=new PatrolShopCycle();
			psc.setPlanID(lps.getLoopPlanId());
			psc.setMonth( lps.getYear()+String.format("%02d",lps.getMonth()));
			cycleList.add(psc);
		}
		shopPlanListVO.setPrecinctArr(precinctArrList);
		shopPlanListVO.setPatrolShopCycle(cycleList);
		//设置当前月
		shopPlanListVO.setPresentMonth(DateUtil.simple3(new Date()));
		return  shopPlanListVO;

	}

	@Override
	public ShopPlanProgressVO getShopPlanProgress(Long userId){
		ShopPlanProgressVO shopPlanProgressVO=new ShopPlanProgressVO();

       //获取当前月份已完成的巡店安排
		Example example=new Example(TFrontPlan.class);
		//status:true  已巡店
		example.createCriteria().andEqualTo("status",true).andEqualTo("year",DateUtil.getYear())
				.andEqualTo("month",DateUtil.getMonth());
		//已完成的店面数
		Float finishCount = Float.valueOf(tFrontPlanMapper.selectCountByExample(example));
		//巡店计划店面总数 （本期总店数2）
		Float progress= finishCount/Float.valueOf(totalShops);

		//1-正常；2-异常
		if(progress>=0.5)
		    shopPlanProgressVO.setProgressStatus(1);
		else
			shopPlanProgressVO.setProgressStatus(2);

		DecimalFormat decimalFormat=new DecimalFormat("##0.00");
		String progressStr=decimalFormat.format(progress);//format 返回的是字符串
		shopPlanProgressVO.setProgress(progressStr);
		shopPlanProgressVO.setBrandID(41);
		shopPlanProgressVO.setBrandName("DQ");
		//根据用户ID  获取部门信息
		logger.info("userID："+userId);
		Example ex=new Example(DeptRoleUnion.class);
		ex.createCriteria().andEqualTo("userId",userId);
		List<DeptRoleUnion> druList=deptRoleUnionMapper.selectByExample(ex);
		DeptRoleUnion deptRoleUnion=null;
        if(null!=druList && druList.size()>0) {
			deptRoleUnion = druList.get(0);
			//店铺名称
			Department departmentVO = departmentMapper.selectByPrimaryKey(deptRoleUnion.getDptId());
			if(null!=departmentVO) {
				logger.info("DepartmentID："+departmentVO.getId());
				shopPlanProgressVO.setDepartmentID(departmentVO.getId());
				shopPlanProgressVO.setDepartmentName(departmentVO.getDptname());
			}
		}
		return shopPlanProgressVO;
	}

    public String formatStrDate(String dateString){
		Date dt=DateUtil.strToYYMMDate(dateString);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M");
		return formatter.format(dt);
	}
}
