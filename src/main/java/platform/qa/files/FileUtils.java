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

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.List;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Log4j2
public class FileUtils {

    @SneakyThrows
    public static <T> List<T> readCsvFile(File csvFile, char separator, Class<T> clazz) {
        CsvMapper csvMapper = new CsvMapper();

        CsvSchema csvSchema = csvMapper
                .typedSchemaFor(clazz)
                .withHeader()
                .withColumnSeparator(separator)
                .withComments();

        try (MappingIterator<T> iterator = csvMapper
                .readerWithTypedSchemaFor(clazz)
                .with(csvSchema)
                .readValues(csvFile)) {
            return iterator.readAll();
        }
    }

    public static <T> List<T> readCsvFile(File csvFile, Class<T> clazz) {
        return readCsvFile(csvFile, ',', clazz);
    }
}
