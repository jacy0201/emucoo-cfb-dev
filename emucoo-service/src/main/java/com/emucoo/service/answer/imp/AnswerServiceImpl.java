package com.emucoo.service.answer.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.answer.AnswerAddIn;
import com.emucoo.mapper.AnswerMapper;
import com.emucoo.model.Answer;
import com.emucoo.model.User;
import com.emucoo.service.answer.AnswerService;

@Transactional
@Service
public class AnswerServiceImpl extends BaseServiceImpl<Answer> implements AnswerService {

	@Autowired
	private AnswerMapper mapper;

	@Override
	public void add(AnswerAddIn answerAddIn, User user) {
		Answer answer = new Answer();
		// TODO
		mapper.add(answer);
	}

}
