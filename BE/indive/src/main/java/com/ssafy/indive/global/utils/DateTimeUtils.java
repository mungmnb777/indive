package com.ssafy.indive.global.utils;

import com.ssafy.indive.global.constant.DatetimeConst;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static String format(LocalDateTime o) {
        if (ObjectUtils.isEmpty(o)) return null;
        return o.format(DateTimeFormatter.ofPattern(DatetimeConst.FORMAT));
    }
}
