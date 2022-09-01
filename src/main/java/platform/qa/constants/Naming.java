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

package platform.qa.constants;

import org.apache.commons.lang3.RandomStringUtils;

public class Naming {

    public static final String PLACEHOLDER_ID = "PLACEHOLDER_ID";
    public static final String PLACEHOLDER_NAME = "PLACEHOLDER_NAME";
    public static final String PLACEHOLDER_FORM_KEY = "PLACEHOLDER_FORM_KEY";
    public static final String PLACEHOLDER_DMN_KEY = "PLACEHOLDER_DMN_KEY";

    public static final String autoDataPrefix = "AUTO_DF";
    public static final String autoDataIntPrefix = "AUTO_DF_INT";
    public static final String autoOPPrefix = "AUTO_OP";
    public static final String autoCPPrefix = "AUTO_CP";
    public static final String autoLCIntPrefix = "AUTO_LC_INT";

    /**
     * Creates a new random definition key for bpmn file.
     * Characters will be chosen from the set of alphanumeric characters.
     *
     * @return the random string with specific "Process_" prefix and "_AUTO" suffix
     */
    public static String getProcessDefinitionKey() {
        return String.format("Process_%s_AUTO", RandomStringUtils.randomAlphanumeric(5));
    }

    /**
     * Creates a new random definition name for bpmn file.
     * Characters will be chosen from the set of alphanumeric characters.
     *
     * @return the random string with specific "ProcessName_" prefix and "_AUTO" suffix
     */
    public static String getProcessDefinitionName() {
        return String.format("ProcessName_%s_AUTO", RandomStringUtils.randomAlphanumeric(5));
    }

    /**
     * Creates a new random form key for json file.
     * Characters will be chosen from the set of alphanumeric characters.
     *
     * @return the random string with specific "AUTO" suffix
     */
    public static String getFormKey() {
        return String.format("%sAUTO", RandomStringUtils.randomAlphabetic(10));
    }

    /**
     * Creates a new random decision definition key for dmn file.
     * Characters will be chosen from the set of alphanumeric characters.
     *
     * @return the random string with specific "AUTOValidationRule" prefix
     */
    public static String getDecisionDefinitionKey() {
        return String.format("AUTOValidationRule%s", RandomStringUtils.randomAlphanumeric(5));
    }

    /**
     * Creates a new random decision definition name for dmn file.
     * Characters will be chosen from the set of alphanumeric characters.
     *
     * @return the random string with specific "ValidationRule_" prefix and "_AUTO" suffix
     */
    public static String getDecisionDefinitionName() {
        return String.format("ValidationRule_%s_AUTO", RandomStringUtils.randomAlphanumeric(5));
    }
}
