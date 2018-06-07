package com.emucoo.utils;

import com.emucoo.model.TTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskExeDateGenerator {

    public static List<Date> generateExeDatesByTask(TTask task) {
        List<Date> dts = new ArrayList<>();
        int repeatType = task.getLoopCycleType() == null ? 0 : task.getLoopCycleType();
        switch (repeatType) {
            case 0:
                dts.add(task.getTaskStartDate());
                break;

            case 1:
                Date sdt = task.getTaskStartDate();
                Date edt = task.getTaskEndDate();
                while (DateUtil.compare(sdt, edt) <= 0) {
                    dts.add(sdt);
                    sdt = DateUtil.dateAddDay(sdt, 1);
                }
                break;

            case 2:
                String loopValue = task.getLoopCycleValue() == null ? "1,2,3,4,5,6,7" : task.getLoopCycleValue();
                List<Integer> wkdays = Arrays.asList(loopValue.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
                Date sdt1 = task.getTaskStartDate();
                Date edt1 = task.getTaskEndDate();
                while (DateUtil.compare(sdt1, edt1) <= 0) {
                    if (wkdays.contains(DateUtil.getDayOfWeek(sdt1))) {
                        dts.add(sdt1);
                    }
                    sdt1 = DateUtil.dateAddDay(sdt1, 1);
                }
                break;

        }
        return dts;
    }

    public static boolean isContainsDate(List<Date> dates, Date dt) {
        for (Date date : dates) {
            if (DateUtil.simple(date).equals(DateUtil.simple(dt))) {
                return true;
            }
        }
        return false;
    }
}
