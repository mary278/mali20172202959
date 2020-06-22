package cn.smbms.tools;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String,Date> {

    private String datePattrern;

    public StringToDateConverter(String datePattrern) {
        System.out.println("StringToDateConverter convert date:"+datePattrern);
        this.datePattrern = datePattrern;
    }


    @Override
    public Date convert(String s) {
        Date date=null;
        try {
            date=new SimpleDateFormat(datePattrern).parse(s);
            System.out.println("StringToDateConverter convert date:"+date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
}
