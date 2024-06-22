package com.zj.zs.utils;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
* 中文转化为拼音工具类
*
*/
@Slf4j
public class Pinyin4jUtil {

	/**
	 * 获得汉语拼音首字母 大写
	 *
	 * @param chines 汉字
	 * @return
	 */
	public static String getAlpha2UpperCase(String chines) {
		StringBuilder pinyinName = new StringBuilder();
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : nameChar) {
            if (c > 128) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    System.out.println("获得汉语拼音首字母异常:}" + e.getMessage());
                }
            } else {
                pinyinName.append(c);
            }
        }
		return pinyinName.toString();
	}

	/**
	 * 将字符串中的中文转化为拼音,英文字符不变
	 *
	 * @param inputString 汉字
	 * @return 拼音字符串 -隔开
	 */
	public static String getPingYin(String inputString) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder output = new StringBuilder();
		if (null != inputString && !inputString.isEmpty() && !"null".equals(inputString)) {
			char[] input = inputString.trim().toCharArray();
			try {
                for (char c : input) {
                    if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, format);
                        output.append(temp[0]).append("-");
                    } else {
                        output.append(c);
                    }
                }
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				System.out.println("将字符串中的中文转化为拼音,英文字符不变异常 :" + e.getMessage());
			}
		} else {
			return "";
		}
		if (output.toString().endsWith("-")) {
			output.deleteCharAt(output.length() - 1);
		}
		return output.toString();
	}

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 *
	 * @param chines 汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chines) {
		StringBuilder pinyinName = new StringBuilder();
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : nameChar) {
            if (c > 128) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    System.out.println("汉字转换位汉语拼音首字母，英文字符不变异常 :" + e.getMessage());
                }
            } else {
                pinyinName.append(c);
            }
        }
		return pinyinName.toString();
	}

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变(小写)
	 *
	 * @param chines 汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpellSmal(String chines) {
		StringBuilder pinyinName = new StringBuilder();
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : nameChar) {
            if (c > 128) {
                try {
                    if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                        pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0].charAt(0));
                    } else {
                        pinyinName.append(c);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    System.out.println("汉字转换位汉语拼音首字母，英文字符不变异常:" + e.getMessage());
                }
            } else {
                pinyinName.append(c);
            }
        }
		return pinyinName.toString();
	}
}


