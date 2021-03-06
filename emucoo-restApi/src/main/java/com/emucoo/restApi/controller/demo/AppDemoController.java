package com.emucoo.restApi.controller.demo;


import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.demo.demoVo_I;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/Content")
public class AppDemoController extends AppBaseController {

//	 @Resource
//	 private ContentService apiDemoService;

    /**
     * 查 请求方式POST
     *
     * @param
     * @return entity
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public AppResult<String> saveContent(@RequestBody ParamVo<demoVo_I> base) {

        demoVo_I vo = base.getData();
        checkParam(vo.getTitle(), "title不能为空！");

//		Content content = new Content();
//        BeanUtils.copyProperties(vo, content);
//
//		apiDemoService.save(content);

        return success("success");
    }

    /**
     * 查 请求方式DELETE
     *
     * @param
     * @return {id}
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public AppResult<String> delete(@PathVariable("id") Long id) {

//		apiDemoService.deleteById(id);

        return success("success");
    }

    /**
     * 查 请求方式PUT
     *
     * @param
     * @return entity
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public AppResult<String> updateContent(@RequestBody ParamVo<demoVo_I> base) {

        demoVo_I vo = base.getData();
        checkParam(vo, "");

//		Content content = new Content();
//        BeanUtils.copyProperties(vo, content);
//        Content temp = apiDemoService.findById(content.getId());
//
//        temp.setContent(content.getContent());
//        temp.setTitle(content.getTitle());
//
//		apiDemoService.update(temp);

        return success("success");
    }


    /**
     * 查 请求方式GET
     *
     * @param
     * @return {id}
     */
//	@RequestMapping(method = RequestMethod.GET, value="/{id}")
//    public AppResult<Content> query(@PathVariable Long id) {
//		Content content = apiDemoService.findById(id);
//		return success(content);
//    }

}
