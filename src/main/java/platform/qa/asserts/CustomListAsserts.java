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

package platform.qa.asserts;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CustomListAsserts {

    /**
     * Asserts that list of Cyrillic strings is sorted alphabetically in ASC order.
     *
     * @param list – the list to check
     */
    public static void assertAlphabeticSorting(List<String> list) {
        List<String> expectedList = new ArrayList<>(list);

        Locale dLocale = new Locale.Builder().setLanguage("ru").setScript("Cyrl").build();
        list.sort(Collator.getInstance(dLocale));
        assertThat(list)
                .as("Перелік заданих значень не відсортований у алфавітному порядку")
                .isEqualTo(expectedList);
    }

    /**
     * Asserts that list of Cyrillic strings is sorted alphabetically in DESC order.
     *
     * @param list – the list to check
     */
    public static void assertAlphabeticReverseSorting(List<String> list) {
        List<String> expectedList = new ArrayList<>(list);

        Locale dLocale = new Locale.Builder().setLanguage("ru").setScript("Cyrl").build();
        list.sort(Collator.getInstance(dLocale));
        Collections.reverse(list);
        assertThat(list)
                .as("Перелік заданих значень не відсортований у алфавітному спадаючому порядку")
                .isEqualTo(expectedList);
    }

    public static void assertListSize(List expectedList, List actualList) {
        assertThat(actualList.size())
                .as("Поточна кількість значень у обох списках не збігається з очікуваним")
                .isEqualTo(expectedList.size());
    }

    public static void assertReverseSorting(List list) {
        assertThat(list)
                .as("Перелік заданих значень не відсортований у спадаючому порядку")
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    public static void assertSorting(List list) {
        assertThat(list)
                .as("Перелік заданих значень не відсортований у наростаючому порядку")
                .isSorted();
    }

    /**
     *  Застосовується для сортування дат, у спадаючому порядку
     *  у вигляді рядка: "16.12.2020 19:36",
     *  порівняння дат відбувається, після приведення до типу "Дата"
     */
    public static void assertDateReverseSorting(List<String> list) {
        List<String> expectedList = new ArrayList<>(list);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        expectedList.sort(Comparator.comparing(s -> LocalDateTime.parse(s, formatter)));
        Collections.reverse(list);

        assertThat(list)
                .as("Перелік заданих значень дат не відсортований у спадаючому порядку")
                .isEqualTo(expectedList);
    }

    /**
     *  Застосовується для сортування дат, у зростаючому порядку
     *  у вигляді рядка: "16.12.2020 19:36",
     *  порівняння дат відбувається, після приведення до типу "Дата"
     */
    public static void assertDateSorting(List<String> list) {
        List<String> expectedList = new ArrayList<>(list);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        expectedList.sort(Comparator.comparing(s -> LocalDateTime.parse(s, formatter)));

        assertThat(list)
                .as("Перелік заданих значень дат не відсортований у зростаючому порядку")
                .isEqualTo(expectedList);
    }
}
