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

package platform.qa.files;

import static com.google.common.base.CharMatcher.invisible;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

@Log4j2
public class FileUtils {

    public static List<Map<String, String>> readCsvFile(File file) {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(false)
                .withIgnoreLeadingWhiteSpace(true)
                .withQuoteChar('"')
                .build();
        return readCsvFile(file, parser);
    }

    public static List<Map<String, String>> readCsvFile(File file, CSVParser parser) {
        List<Map<String, String>> results = new LinkedList<>();

        try (Reader reader = Files.newBufferedReader(file.toPath())) {
            List<String[]> allRecords;
            try (CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()) {
                allRecords = csvReader.readAll();
            }

            if (allRecords != null && !allRecords.isEmpty()) {
                String[] headers = allRecords.get(0);
                results = allRecords
                        .subList(1, allRecords.size()).stream()
                        .map(line -> IntStream.range(0, line.length)
                                .boxed()
                                .collect(toMap(i -> invisible().trimFrom(headers[i]),
                                        i -> invisible().trimFrom(line[i]))))
                        .collect(toList());
            }

        } catch (IOException | CsvException e) {
            log.info("CSV file: " + file.getAbsolutePath() + " was not parsed!!!");
        }
        return results;
    }
}
