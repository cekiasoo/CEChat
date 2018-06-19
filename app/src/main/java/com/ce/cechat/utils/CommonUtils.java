package com.ce.cechat.utils;

import android.text.TextUtils;

import com.ce.cechat.model.bean.User;
import com.hyphenate.util.HanziToPinyin;

import java.util.ArrayList;

/**
 * @author CE Chen
 * <p>
 * 作用 : 常用工具类
 */
public class CommonUtils {

    private CommonUtils() {
        throw new UnsupportedOperationException("工具类不需要实例对象");
    }

    /**
     * set initial letter of according user's nickname( username if no nickname)
     *
     * @param user user
     */
    public static String getInitialLetter(User user) {
        final String defaultLetter = "#";
        String letter = defaultLetter;

        final class GetInitialLetter {
            String getLetter(String name) {
                if (TextUtils.isEmpty(name)) {
                    return defaultLetter;
                }
                char char0 = name.toLowerCase().charAt(0);
                if (Character.isDigit(char0)) {
                    return defaultLetter;
                }
                ArrayList<HanziToPinyin.Token> l = HanziToPinyin.getInstance().get(name.substring(0, 1));
                if (l != null && l.size() > 0 && l.get(0).target.length() > 0)
                {
                    HanziToPinyin.Token token = l.get(0);
                    String letter = token.target.substring(0, 1).toUpperCase();
                    char c = letter.charAt(0);
                    if (c < 'A' || c > 'Z') {
                        return defaultLetter;
                    }
                    return letter;
                }
                return defaultLetter;
            }
        }

        if ( !TextUtils.isEmpty(user.getName()) ) {
            letter = new GetInitialLetter().getLetter(user.getName());
            return letter;
        }
        return defaultLetter;
    }

}
