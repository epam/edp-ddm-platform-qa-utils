/*
 * Copyright 2022 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package platform.qa.date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateConverter {

    /**
     * Converts given string to Date due to given pattern.
     *
     * @param dateTimeStr - the string with date to convert (i.e. "2022-07-05T08:52:11.635Z")
     * @param pattern     - the pattern describing the date and time format
     * @return formatted Date
     */
    public static Date convertDateTimeByPattern(String dateTimeStr, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return dateParse(dateTimeStr, simpleDateFormat);
    }

    /**
     * Converts current date to Date due to given pattern.
     *
     * @param pattern - the pattern describing the date and time format
     * @return formatted current Date
     */
    public static Date convertCurrentDateTimeByPattern(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return dateParse(simpleDateFormat.format(new Date()), simpleDateFormat);
    }

    private static Date dateParse(String dateTimeStr, SimpleDateFormat simpleDateFormat) {
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(dateTimeStr);
        } catch (ParseException e) {
            log.error("Date " + dateTimeStr + " was not parsed!");
        }
        return date;
    }
}
