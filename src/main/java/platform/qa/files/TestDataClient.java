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

import lombok.Getter;
import platform.qa.constants.Naming;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * A collection of methods to deal with various file related activities.
 */
public class TestDataClient {
    @Getter
    private String rootPath = "src/test/resources/data/";
    @Getter
    private String folder = "files";
    private HashMap<String, String> toReplace = new HashMap<>();
    private String outputFile = "main-liquibase.xml";

    @Getter
    private HashMap<String, String> ids = new HashMap<>();

    @Getter
    private HashMap<String, String> paths = new HashMap<>();

    @Getter
    private List<String> outputContent;

    public TestDataClient setPatternToReplace(String textPattern, String replaceWith) {
        toReplace.put(textPattern, replaceWith);
        return this;
    }

    /**
     * Replaces "PLACEHOLDER_ID" text with provided id in file.
     *
     * @param id - the string with id to set
     * @return {@link TestDataClient}
     */
    public TestDataClient setId(String id) {
        toReplace.put("PLACEHOLDER_ID", id);
        return this;
    }

    public TestDataClient setIds() {
        toReplace.put("PLACEHOLDER_ID", "any");
        return this;
    }

    /**
     * Replaces "PLACEHOLDER_NAME" text with provided name in file.
     *
     * @param name - the string with name to set
     * @return {@link TestDataClient}
     */
    public TestDataClient setName(String name) {
        toReplace.put("PLACEHOLDER_NAME", name);
        return this;
    }

    public TestDataClient setNames(String name) {
        toReplace.put("PLACEHOLDER_NAME", "any");
        return this;
    }

    /**
     * Replaces "PLACEHOLDER_PATH" text with provided path in file.
     *
     * @param name - the string with path to set
     * @return {@link TestDataClient}
     */
    public TestDataClient setPath(String name) {
        toReplace.put("PLACEHOLDER_PATH", name);
        return this;
    }

    public TestDataClient setPaths(String name) {
        toReplace.put("PLACEHOLDER_PATH", "any");
        return this;
    }

    /**
     * Replaces "PLACEHOLDER_FORM_KEY" text with provided form key in file.
     *
     * @param key - the string with form key to set
     * @return {@link TestDataClient}
     */
    public TestDataClient setFormKey(String key) {
        toReplace.put("PLACEHOLDER_FORM_KEY", key);
        return this;
    }

    public TestDataClient setFormKeys(List<String> keys) {
        AtomicInteger keyNumber = new AtomicInteger(1);
        if (keys.size() == 1) {
            setFormKey(keys.get(0));
            return this;
        }
        keys.forEach(key ->
                toReplace.put("PLACEHOLDER_FORM_KEY_" + (keyNumber.getAndIncrement()), key));
        return this;
    }

    /**
     * Replaces "PLACEHOLDER_DMN_KEY" text with provided dmn key in file.
     *
     * @param key - the string with dmn key to set
     * @return {@link TestDataClient}
     */
    public TestDataClient setDmnKey(String key) {
        toReplace.put("PLACEHOLDER_DMN_KEY", key);
        return this;
    }

    public TestDataClient setRootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }

    public TestDataClient setFolder(String folder) {
        this.folder = folder;
        return this;
    }

    public String updateFile(String fileName) {
        return updateFile(fileName, RandomStringUtils.randomAlphabetic(5).concat(fileName));
    }

    public String updateFile(String fileName, String outputFile) {
        outputContent = readUpdatedFile(fileName);
        createTempFile("target", outputFile, outputContent);
        return outputFile;
    }

    public List<String> readUpdatedFile(String fileName) {
        List<String> lines = readFromFile(fileName).collect(Collectors.toList());
        if (!toReplace.keySet().isEmpty()) {
            for (var key : toReplace.keySet()) {
                var value = "any".equals(toReplace.get(key)) ?
                        Naming.getFormKey() :
                        toReplace.get(key);
                ids.put(fileName, value);
                paths.put(fileName, value);
                lines = lines.stream().map(line
                                -> line.replaceAll(key, value))
                        .collect(Collectors.toList());
            }
        }
        return lines;
    }

    public List<String> updateFiles(String directory) {
        var list = readFromFolder(directory);
        folder = folder + "/" + directory;
        return list.stream().map(fileName ->
                        updateFile(fileName, RandomStringUtils.randomAlphabetic(5).concat(fileName)))
                .collect(Collectors.toList());
    }

    public String renameAsOutput(String inputFile) {
        Stream<String> lines = readFromFile(inputFile);
        createTempFile("target", outputFile, lines.collect(Collectors.toList()));
        return outputFile;
    }

    /**
     * Copies provided file to the target folder.
     *
     * @param folder   - the string with the folder name to search in
     * @param filename - the string with the name of file that should be copied
     * @return copied file name
     */
    public String copyFile(String folder, String filename) {
        Path source = Paths.get(folder, FilenameUtils.getName(filename));
        String copiedFileName = RandomStringUtils.randomAlphabetic(5).concat(filename);
        Path destination = Paths.get("target/", FilenameUtils.getName(copiedFileName));
        try {
            Files.copy(source, destination);
        } catch (IOException e) {
            throw new RuntimeException("File was not copied!", e);
        }
        return copiedFileName;
    }

    private Stream<String> readFromFile(String name) {
        String path = rootPath + folder;
        try {
            return Files.lines(Path.of(path, FilenameUtils.getName(name)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("File was not found!: ", e);
        }
    }

    private List<String> readFromFolder(String directory) {
        Path path = Path.of(rootPath + folder, FilenameUtils.getName(directory));
        return Arrays.asList(path.toFile().list());
    }

    /**
     * Created temporary file with provided context.
     *
     * @param folder       - the string with the name of folder where the file should be created
     * @param tempFilename - the string with the name of file that should be created
     * @param context      - the list with the context to write in created file
     * @return - created file
     */
    private File createTempFile(String folder, String tempFilename, List<String> context) {
        Path path;
        try {
            path = Path.of(folder, FilenameUtils.getName(tempFilename));
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.write(path, context);
        } catch (Exception e) {
            throw new RuntimeException("File was not created!", e);
        }
        return path.toFile();
    }
}
