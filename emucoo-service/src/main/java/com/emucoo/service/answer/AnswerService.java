package com.emucoo.service.answer;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.answer.AnswerAddIn;
import com.emucoo.model.Answer;
import com.emucoo.model.User;

/**
 * 
 * @author zhangxq
 * @date 2018-03-23
 */
public interface AnswerService extends BaseService<Answer> {
   
	void add(AnswerAddIn answerAddIn, User user);

}
