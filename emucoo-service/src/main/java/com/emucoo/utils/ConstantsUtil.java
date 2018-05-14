package com.emucoo.utils;

/**
 * 常量工具类
 * 
 * @author zhangxq
 * @date 2018-03-18
 */
public final class ConstantsUtil {

	public interface LoopWork {
		// t_loop_work 1常规任务 2指派任务 3改善任务 4 巡店安排 5工作备忘
		public static final Integer TYPE_ONE = 1;
		public static final Integer TYPE_TWO = 2;
		public static final Integer TYPE_THREE = 3;
		public static final Integer TYPE_FOUR = 4;
		public static final Integer TYPE_FIVE = 5;
		
		// 1：未提交 2：已提交 3：已过期
		public static final Integer WORK_STATUS_ONE = 1;
		public static final Integer WORK_STATUS_TWO = 2;
		public static final Integer WORK_STATUS_THREE = 3;
	}

	public interface WorkDataAppend {
		// t_work_data_append 1数值 2百分比 3货币
		public static final Integer DATA_TYPE_ONE = 1;
		public static final Integer DATA_TYPE_TWO = 2;
		public static final Integer DATA_TYPE_THREE = 3;
	}

	public interface WorkImgAppend {
		// t_work_img_append 1：执行者提交，2：审核者提交
		public static final Integer INPUT_TYPE_ONE = 1;
		public static final Integer INPUT_TYPE_TWO = 2;
	}

	/**
	 * 分隔符
	 */
	public interface Separator {
		// 图片
		public static final String SEPARATOR_IMGURL = ",";
		// 经纬度
		public static final String SEPARATOR_LOCATION = "|";
		// 时间戳
		public static final String SEPARATOR_DATE = ",";
	}

}
