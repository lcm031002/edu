/**  
 * @Title: Unit.java
 * @Package com.edu.report.framework
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年5月3日 下午12:09:52
 * @version KLXX ERPV4.0  
 */
package com.edu.report.framework;

/**
 * @ClassName: Unit
 * @Description: 任务单位类型
 * @author zhuliyong zly@entstudy.com
 * @date 2017年5月3日 下午12:09:52
 * 
 */
public enum Unit {
	D(1), H(2), M(3), S(4), MS(5);

	private int value;

	private Unit(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static Unit genEnum(String value) {
		if (Unit.D.name().equalsIgnoreCase(value)) {
			return Unit.D;
		}
		if (Unit.H.name().equalsIgnoreCase(value)) {
			return Unit.H;
		}
		if (Unit.M.name().equalsIgnoreCase(value)) {
			return Unit.M;
		}
		if (Unit.S.name().equalsIgnoreCase(value)) {
			return Unit.S;
		}
		if (Unit.MS.name().equalsIgnoreCase(value)) {
			return Unit.MS;
		}
		return Unit.S;
	}
}