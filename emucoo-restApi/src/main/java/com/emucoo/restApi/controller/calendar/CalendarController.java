package com.emucoo.restApi.controller.calendar;

import com.emucoo.dto.base.ISystem;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.calendar.CalendarListDateIn;
import com.emucoo.dto.modules.calendar.CalendarListDateOut;
import com.emucoo.dto.modules.calendar.CalendarListMonthIn;
import com.emucoo.dto.modules.calendar.CalendarListMonthOut;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.restApi.utils.RedisClusterClient;
import com.emucoo.service.calendar.CalendarService;
import com.emucoo.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.Collator;
import java.util.*;


/**
 * @author Jacy
 * 行事历
 */
@RestController
@Api(description="行事历接口")
@RequestMapping("/api/calendar")
public class CalendarController extends AppBaseController {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private RedisClusterClient redisClient;
    /**
     * 获取指定用户,指定月份行事历
     * @param params
     * @return
     */
    @ApiOperation(value="查询用户某月行事历")
    @PostMapping(value = "/listMonth")
    public AppResult listMonth(@RequestBody ParamVo<CalendarListMonthIn> params) {
        CalendarListMonthIn calendarListIn = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        if (null==calendarListIn.getUserId()){ calendarListIn.setUserId(user.getId()); }
        if (null==calendarListIn.getMonth()){ calendarListIn.setMonth(DateUtil.simple4(new Date())); }
        CalendarListMonthOut calendarListMonthOut =calendarService.listCalendarMonth(calendarListIn);
        return success(calendarListMonthOut);
    }

    /**
     * 查询用户某天行事历
     * @param params
     * @return
     */
    @ApiOperation(value="查询用户某天行事历")
    @PostMapping(value = "/listDate")
    public AppResult listDate(@RequestBody ParamVo<CalendarListDateIn> params) {
        CalendarListDateIn calendarListDateIn = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        if (null==calendarListDateIn.getUserId()){ calendarListDateIn.setUserId(user.getId()); }
        if (null==calendarListDateIn.getExecuteDate()) calendarListDateIn.setExecuteDate(DateUtil.getCurrentDate());
        CalendarListDateOut calendarListDateOut =calendarService.listCalendarDate(calendarListDateIn);
        return success(calendarListDateOut);
    }


    /**
     * 查询当前用户的下级用户
     * @return
     */
    @ApiOperation(value="查询当前用户的下级用户")
    @PostMapping(value = "/listLowerUser")
    public AppResult listLowerUser() {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        if(null==redisClient.getObject(ISystem.IUSER.USER_RECENT+user.getId(),)){

        }
        List<SysUser> list= calendarService.listLowerUser(user.getId());
        //redisClient.set(ISystem.IUSER.USER_RECENT+user.getId(),list,ISystem.IUSER.USER_TOKEN_EXPIRATION_TIME);
        //返回按姓名首字母排序的集合;
        return success(orderUserList(list));
    }


    //根据姓名首字母排序
    private List<SysUser> orderUserList(List<SysUser> list){
        HashMap<String,SysUser> map =new HashMap<>();
        String [] nameArr=new String[list.size()];
        for(int i=0;i<list.size();i++){
            nameArr[i]=list.get(i).getRealName();
            map.put(nameArr[i],list.get(i));
        }

        Comparator<Object> comparator = Collator.getInstance(java.util.Locale.CHINA);
        Arrays.sort(nameArr,comparator);
        list.clear();
        for(int n=nameArr.length-1;n>=0;n--){
            if(map.containsKey(nameArr[n]))
                list.add(map.get(nameArr[n]));
        }
        return list;
    }

}
