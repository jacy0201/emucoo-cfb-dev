package com.emucoo.service.form;

import com.emucoo.dto.modules.form.FormIn;
import com.emucoo.dto.modules.form.FormOut;
import com.emucoo.model.SysUser;
import com.emucoo.model.TFormMain;

import java.util.List;

/**
 * Created by sj on 2018/4/20.
 */
public interface FormService {

    List<TFormMain> listForm();

    FormOut checkoutFormInfo(SysUser user, FormIn formIn);

    void checkinFormResult(SysUser user, FormIn formIn);
}
