/**
 * 
 */
package com.emucoo.service.shop;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.shop.PatrolShopArrangeDetailVO;
import com.emucoo.model.Log;

/**
 * @author Simon
 * @since 2018.3.26
 *
 */
public interface PatrolShopArrangeDetailService {
	PatrolShopArrangeDetailVO findPatrolShopArrangeDetail(int patrolShopArrangeID);
}
