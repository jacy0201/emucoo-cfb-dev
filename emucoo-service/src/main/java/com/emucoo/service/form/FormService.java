package com.emucoo.service.form;

import com.emucoo.dto.modules.RYGForm.RYGForm;
import com.emucoo.dto.modules.RYGForm.RYGFormOut;
import com.emucoo.dto.modules.abilityForm.AbilityFormMain;
import com.emucoo.dto.modules.form.FormIn;
import com.emucoo.dto.modules.form.FormOut;
import com.emucoo.dto.modules.shop.PlanArrangeGetFormIn;
import com.emucoo.model.SysUser;
import com.emucoo.model.TBrandInfo;
import com.emucoo.model.TFormMain;
import com.emucoo.model.TShopInfo;

import java.util.List;

/**
 * Created by sj on 2018/4/20.
 */
public interface FormService {

    List<TFormMain> listForm(PlanArrangeGetFormIn getFormIn);

    FormOut checkoutFormInfo(SysUser user, Long formId);

    boolean checkinFormResult(SysUser user, FormIn formIn);

    TShopInfo findShopInfo(FormIn formIn);

    TBrandInfo findShopBrand(Long brandId);

    Long saveAbilityFormResult(AbilityFormMain formIn, SysUser user);

    RYGFormOut getRYGFormInfo(RYGForm formIn, SysUser user);

    boolean saveRYGFormResult(SysUser user, RYGForm formIn);
}
