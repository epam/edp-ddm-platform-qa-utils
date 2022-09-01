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

import static platform.qa.files.SearchText.searchTextByRegExp;

import platform.qa.constants.Naming;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.github.javafaker.Faker;

public class TestDataUtils {

    /**
     * Searches for unique processIds and processNames values stored in the list of provided bpmn files.
     *
     * @param bpmnFiles - the list with bpmn file's names
     */
    public static Map getProcessData(List<String> bpmnFiles) {
        Map<String, Map<String, String>> processesDataMap = new HashMap<>();

        if (!bpmnFiles.isEmpty()) {
            bpmnFiles.forEach(fileName -> processesDataMap.put(fileName, getProcessData(fileName)));
        }

        return processesDataMap;
    }

    /**
     * Searches for unique processId and processName values stored in given bpmn file.
     *
     * @param bpmnFileName - the string of file name with stored information about business process
     * @return the map with processId and processName values
     */
    public static Map getProcessData(String bpmnFileName) {
        Map<String, String> processesDataMap = new HashMap<>();

        TestDataClient testDataClient = new TestDataClient()
                .setFolder("files/bpmn");

        if (!StringUtils.isEmpty(bpmnFileName)) {
            List<String> fileData = testDataClient.readUpdatedFile(bpmnFileName);
            String processData = searchTextByRegExp(String.join("\n", fileData), "bpmn:process.*\\\"");
            String processId = searchTextByRegExp(processData, "(?<=process id=\\\").+?(?=\\\")");
            String processName = searchTextByRegExp(processData, "(?<=name=\\\").+?(?=\\\")");

            processesDataMap.putAll(Map.of("processId", processId, "processName", processName));
        }

        return processesDataMap;
    }

    /**
     * @param formName
     * @param formFile
     * @return updated file with form data change form key
     */
    public static List<String> getUpdatedFormFile(String formName, String formFile) {
        Faker faker = new Faker(new Locale("uk-UA"));
        var dataClient = new TestDataClient()
                .setId(Naming.getFormKey())
                .setName(formName
                        .concat(StringUtils.SPACE)
                        .concat(faker.letterify("?????")))
                .setPath(Naming.getFormKey());
        return dataClient.readUpdatedFile(formFile);
    }

    /**
     * @param formName
     * @param formTechnicalName
     * @param formFile
     * @return updated file with form data change form technicalName
     */
    public static List<String> getUpdatedFormFile(String formName, String formTechnicalName, String formFile) {
        var dataClient = new TestDataClient()
                .setId(Naming.getFormKey())
                .setName(formName)
                .setPath(formTechnicalName);
        return dataClient.readUpdatedFile(formFile);
    }
}
