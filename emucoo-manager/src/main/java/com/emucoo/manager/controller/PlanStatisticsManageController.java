package com.emucoo.manager.controller;

import com.emucoo.annotation.FieldName;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.modules.statistics.plan.GeneratePlanReportIn;
import com.emucoo.model.RVRReportStatistics;
import com.emucoo.model.TFormType;
import com.emucoo.model.TFormValue;
import com.emucoo.service.planStatistics.PlanStatisticsService;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sj on 2018/5/31.
 */
@Controller
@RequestMapping("/planStatisticsManage")
public class PlanStatisticsManageController extends BaseResource {


    @Autowired
    private PlanStatisticsService planStatisticsService;

    @RequestMapping("/generatePlanReport")
    public void generatePlanReport(@RequestParam String beginDate, @RequestParam String endDate, @RequestParam Integer formType, @RequestParam Long formId, HttpServletResponse response) {
        try {
            GeneratePlanReportIn reportIn = new GeneratePlanReportIn();
            reportIn.setBeginDate(beginDate);
            reportIn.setEndDate(endDate);
            reportIn.setFormType(formType);
            reportIn.setFormId(formId);
            List<TFormType> formTypes = planStatisticsService.findFormTypeValues(formId);
            List<RVRReportStatistics> statistics = planStatisticsService.generatePlanReport(reportIn);
            if(CollectionUtils.isNotEmpty(statistics)) {
                int cnt = 0;
                String fileName = "test.xls";
                fileName = URLEncoder.encode(fileName, "UTF-8");

                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                response.setContentType("application/octet-stream;charset=UTF-8");

                OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
                WritableWorkbook writableWorkbook = Workbook.createWorkbook(outputStream);
                WritableSheet sheet = writableWorkbook.createSheet("First sheet", 0);// 创建新的一页
                Label label;
                List<Field> list = Arrays.asList(RVRReportStatistics.class.getDeclaredFields());
                int col = 0;
                for(Field field : list) {
                    String filedAnnotationValue = ((FieldName) field.getAnnotations()[0]).value();
                    if (!"otherValueList".equals(filedAnnotationValue)) {
                        label = new Label(col++, 0, filedAnnotationValue);
                        sheet.addCell(label);
                    }

                }
                for(TFormType type : formTypes) {
                    label = new Label(col++, 0, type.getTypeName());
                    sheet.addCell(label);
                }
                int row = 1;

                for (RVRReportStatistics statisticsObject : statistics) {
                    col = 0;
                    for(Field field : list) {
                        String filedAnnotationValue = ((FieldName) field.getAnnotations()[0]).value();
                        if (!"otherValueList".equals(filedAnnotationValue)) {
                            field.setAccessible(true);
                            label = new Label(col++, row, (String) field.get(statisticsObject));
                            sheet.addCell(label);
                        }
                    }
                    List<TFormValue> formValues = statisticsObject.getOtherValueList();
                    for(TFormType type : formTypes) {
                        for (TFormValue formValue : formValues) {
                            if(type.getId().equals(formValue.getFromTypeId())) {
                                label = new Label(col++, row, formValue.getScore() + "/" + formValue.getTotal());
                                sheet.addCell(label);
                                break;
                            }
                        }
                    }

                    row++;
                }

                writableWorkbook.write(); // 加入到文件中
                writableWorkbook.close(); // 关闭文件，释放资源
            }
        } catch (Exception e) {

        }

    }

}
