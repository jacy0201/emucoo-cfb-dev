package com.emucoo.service.form;

import com.emucoo.dto.modules.abilityForm.AbilityFormMain;
import com.emucoo.dto.modules.form.FormIn;
import com.emucoo.dto.modules.form.FormOut;
import com.emucoo.model.SysUser;
import com.emucoo.model.TBrandInfo;
import com.emucoo.model.TFormMain;
import com.emucoo.model.TShopInfo;

import java.util.List;

/**
 * Created by sj on 2018/4/20.
 */
public interface FormService {

    List<TFormMain> listForm();

    FormOut checkoutFormInfo(SysUser user, Long formId);

    boolean checkinFormResult(SysUser user, FormIn formIn);

    TShopInfo findShopInfo(FormIn formIn);

    TBrandInfo findShopBrand(Long brandId);

    boolean saveAbilityFormResult(AbilityFormMain formIn, SysUser user);
}
