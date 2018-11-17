package com.example.metfone.colorracemetfone.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static boolean checkNumberPhone(String numberPhone) {
        numberPhone = numberPhone.trim().replace("+", "");
        // String regex1 =
        // "^97[0-9][0-9]{6,6}$|^88[0-9][0-9]{6,6}$|^71[0-9][0-9]{6,6}$";
//        String regex1 = "^(71|97|88|68|90|67|60|31|66"
//                + "|13|83|84|8|12|17|89|92|77|78|14|36|11|99|76|85|61|23|18|1|93|86|98|69|7|96|15|16|81|87"
//                + "|526|236|246|426|726|736|436|756|746|446|346|256|336|356"
//                + "|366|326|636|626|656|646|536|546|266|556|234|344|364|424|444|544|634|644|244|254|264|324"
//                + "|334|354|434|524|534|554|624|654|724|734|744|754|071|097|088|068|090|067|060|031"
//                + "|066|0526|0236|0246|0426|0726|0736|0436|0756|0746|0446|0346|0256|0336|0356|0366"
//                + "|0326|0636|0626|0656|0646|0536|0546|0266|0556|0234|0344|0364|0424|0444|0544|0634"
//                + "|0644|0244|0254|0264|0324|0334|0354|0434|0524|0534|0554|0624|0654|0724|0734|0744"
//                + "|0754|85571|85597|85588|85568|85590|85567|85560|85531|85566|855526|855236|855246"
//                + "|855426|855726|855736|855436|855756|855746|855446|855346|855256|855336|855356"
//                + "|855366|855326|855636|855626|855656|855646|855536|855546|855266|855556|855234"
//                + "|855344|855364|855424|855444|855544|855634|855644|855244|855254|855264|855324"
//                + "|855334|855354|855434|855524|855534|855554|855624|855654|855724|855734|855744"
//                + "|855754)[0-9]+$";
        String regex1 = "^((71|97|88|68|90|67|60|31|66|526|236|246|426|726|736|436|756|746|446|346|256|336|356|366|326|636|626|656|646|536|546" +
                "|266|556|234|344|364|424|444|544|634|644|244|254|264|324|334|354|434|524|534|554|624|654|724|734|744|754|071|097|088|068|090|067" +
                "|060|031|066|0526|0236|0246|0426|0726|0736|0436|0756|0746|0446|0346|0256|0336|0356|0366|0326|0636|0626|0656|0646|0536|0546|0266|0556" +
                "|0234|0344|0364|0424|0444|0544|0634|0644|0244|0254|0264|0324|0334|0354|0434|0524|0534|0554|0624|0654|0724|0734|0744|0754|85571|85597" +
                "|85588|85568|85590|85567|85560|85531|85566|855526|855236|855246|855426|855726|855736|855436|855756|855746|855446|855346|855256|855336" +
                "|855356|855366|855326|855636|855626|855656|855646|855536|855546|855266|855556|855234|855344|855364|855424|855444|855544|855634|855644" +
                "|855244|855254|855264|855324|855334|855354|855434|855524|855534|855554|855624|855654|855724|855734|855744|855754)[0-9])[0-9]+$";
        String numberForService = cutPhoneNumber(numberPhone);
        Matcher matcher = Pattern.compile(regex1).matcher(numberForService);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    private static String cutPhoneNumber(String phoneNumber) {
        String tmp = "";
        if (phoneNumber.length() < 8 || phoneNumber.length() > 13) {

        } else {
            String strStart0 = phoneNumber.substring(0, 1);
            String strStart855 = phoneNumber.substring(0, 3);
            String strStartAdd855 = phoneNumber.substring(0, 4);
            int length = phoneNumber.length();
            tmp = phoneNumber;
            if ("855".equals(strStart855)) {
                tmp = phoneNumber.substring(3, length);
            }
            if ("0".equals(strStart0)) {
                tmp = phoneNumber.substring(1, length);
            }
            if ("+855".equals(strStartAdd855)) {
                tmp = phoneNumber.substring(4, length);
            }
        }
        return tmp;
    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static boolean compareDatetimeEvent(String today, String setDay) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateToday = null;
        Date dateSetDay = null;
        try {
            dateToday = simpleDateFormat.parse(today);
            dateSetDay = simpleDateFormat.parse(setDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //  0 comes when two date are same,
        //  1 comes when date1 is higher then date2
        // -1 comes when date1 is lower then date2

        if (dateToday.compareTo(dateSetDay) == 0) {

            return true;
        }
        return false;
    }

}
