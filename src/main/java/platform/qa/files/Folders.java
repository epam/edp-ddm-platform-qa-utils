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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Folders in Registry tree.
 */
@AllArgsConstructor
@Getter
public enum Folders {
    BPMN_FOLDER ("bpmn"),
    DMN_FOLDER ("dmn"),
    FORMS_FOLDER ("forms"),
    GLOBAL_VARS_FOLDER ( "global-vars"),
    DATA_FOLDER("data-model"),
    TARGET_FOLDER ("target"),
    BP_AUTH_FOLDER ("bp-auth"),
    ROLES ("roles"),
    REPORTS("reports");

    private final String name;
}
