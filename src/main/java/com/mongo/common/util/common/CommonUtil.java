package com.mongo.common.util.common;

/*
* 类描述：
* @auther linzf
* @create 2018/4/11 0011 
*/
public class CommonUtil {

    /**
     * 将首字母变小写
     * @param str
     * @return
     */
    public static String toFirstCharLowerCase(String str){
        char[]  columnCharArr = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < columnCharArr.length; i++) {
            char cur = columnCharArr[i];
            if(i==0){
                sb.append(Character.toLowerCase(cur));
            }else{
                sb.append(cur);
            }
        }
        return sb.toString();
    }
}
