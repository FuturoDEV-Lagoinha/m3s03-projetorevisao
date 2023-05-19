package br.com.lagoinha.m3s03projetorevisao.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private DateUtil() {
    }

    public static Date somarDias(Date data, int qtd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DATE, qtd);
        return cal.getTime();
    }

}
