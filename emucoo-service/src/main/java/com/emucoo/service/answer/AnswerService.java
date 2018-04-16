package com.emucoo.service.answer;

import com.emucoo.dto.modules.answer.AnswerAddIn;
import com.emucoo.model.SysUser;

public interface AnswerService {
    void add(AnswerAddIn vo, SysUser user);
}
