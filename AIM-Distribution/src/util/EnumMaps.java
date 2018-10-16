package util;

import java.util.HashMap;
import java.util.Map;

import model.Department;
import model.ProcessStatus;

public class EnumMaps {

	private static HashMap<String, ProcessStatus> statusMap = new HashMap<String, ProcessStatus>() {
		{
			put("ORDERED", ProcessStatus.ORDERED);
			put("IN_PROGRESS", ProcessStatus.IN_PROGRESS);
			put("DELAYED", ProcessStatus.DELAYED);
			put("FINISHED", ProcessStatus.FINISHED);
			put("PAID", ProcessStatus.PAID);

		}
	};

	private static HashMap<String, Department> departmentMap = new HashMap<String, Department>() {
		{
			put("MARKETING", Department.MARKETING);
			put("WAREHOUSE", Department.WAREHOUSE);
			put("DISTRIBUTION", Department.DISTRIBUTION);
			put("HUMAN_RESOURCES", Department.HUMAN_RESOURCES);
			put("IT_SUPPORT", Department.IT_SUPPORT);
			put("CEO", Department.CEO);

		}
	};

	public static ProcessStatus getProcessStatus(String key) {
		return statusMap.get(key);
	}

	public static Department getDepartment(String key) {
		return departmentMap.get(key);
	}

}
