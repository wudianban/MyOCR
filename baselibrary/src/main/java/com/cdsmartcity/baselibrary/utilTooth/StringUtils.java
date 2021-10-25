package com.cdsmartcity.baselibrary.utilTooth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/**
     * 手机号码检验
     */
	public static boolean checkMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(16[0-9]{1})|(17[0-9]{1}))+\\d{8})$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
	/**
     * 对身份证号码进行处理
     */
	public static String getBarrierIdCard(String idCard) {
        String front =idCard.substring(0,4);
        String back =idCard.substring(idCard.length()-4,idCard.length());
        return front+"******"+back;
    }
	/**
     * 对手机号码进行处理
     */
	public static String getBarrierPhone(String phone) {
        if(phone==null)
            return "";
        if(phone.length()<4)
            return phone;
        String front =phone.substring(0,3);
        String back =phone.substring(phone.length()-4,phone.length());
        return front+"******"+back;
    }
	/**
     * 对Unit单位处理
     */
	public static String getUnit(String unit) {

        if (unit.equals("1")){
            return "年";
        }
        if (unit.equals("2")){
            return "月";
        }
        if (unit.equals("3")){
            return "日";
        }
        return unit;
    }

    /**
     * 检查身份证号时候符合规范
     * @param idCard 身份证
     * @return
     */
    public static boolean checkIdCard(String idCard) {
        String match = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";
        Pattern partern = Pattern.compile(match);
        Matcher m = partern.matcher(idCard);
        return m.matches();
    }

    /**
     * 检查蓝牙锁的mac地址是否正确
     * @param mac 蓝牙锁的mac地址
     * @return 是否是正确的蓝牙地址
     */
    public static boolean checkMac(String mac) {
        if(mac==null){
            return false;
        }
        //mac地址的正则表达式
        String patternMac="^[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}$";
        Pattern pa= Pattern.compile(patternMac);
        Matcher m=pa.matcher(mac);
        return m.matches();
    }

}
