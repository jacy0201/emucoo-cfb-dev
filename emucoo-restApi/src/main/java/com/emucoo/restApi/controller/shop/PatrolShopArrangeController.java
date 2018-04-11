/**
 * 
 */
package com.emucoo.restApi.controller.shop;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.shop.PatrolShopArrangeDetailVO;
import com.emucoo.dto.modules.shop.PatrolShopArrangeDetailVO_I;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.service.shop.PatrolShopArrangeDetailService;

/**
 * @author Simon
 *
 */
@RestController
@RequestMapping("/api/shop/patrol")
public class PatrolShopArrangeController extends AppBaseController {
	@Resource
	private PatrolShopArrangeDetailService patrolShopArrangeDetailService;

	@PostMapping("/detail")
	public AppResult<PatrolShopArrangeDetailVO> fetchPatrolShopArrangeDetail(
			@RequestBody ParamVo<PatrolShopArrangeDetailVO_I> params) {
		PatrolShopArrangeDetailVO psaDetailVO = new PatrolShopArrangeDetailVO();
		PatrolShopArrangeDetailVO_I psaDetailVO_i = params.getData();
	    int patrolShopArrangeID = Integer.parseInt(psaDetailVO_i.getPatrolShopArrangeID());
	    psaDetailVO = patrolShopArrangeDetailService.findPatrolShopArrangeDetail(patrolShopArrangeID);
		return success(psaDetailVO);
	}

}
