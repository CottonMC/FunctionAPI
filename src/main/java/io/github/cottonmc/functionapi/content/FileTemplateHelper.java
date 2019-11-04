package io.github.cottonmc.functionapi.content;

import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileTemplateHelper<T> {

    private final TemplateFunction<T> templateCreator;
    private final List<File> rootFolder;
    private final String extension;

    public FileTemplateHelper(TemplateFunction<T> templateCreator, List<File> rootFolder,String extension) {
        this.templateCreator = templateCreator;
        this.rootFolder = rootFolder;
        this.extension = extension;
    }


    public Map<Identifier, T> getTemplates() {

        Map<Identifier, T> result = new HashMap<>();

        for (File namespaceRoot : rootFolder) {
            String namespace = namespaceRoot.getParentFile().getName();
            String[] files = namespaceRoot.list();
            if (files != null) {
                for (String childFile : files) {
                    if(!childFile.endsWith(extension)){
                        continue;
                    }
                    File templateFile = new File(namespaceRoot.getAbsolutePath() + "/" + childFile);

                    String path = templateFile
                            .getAbsolutePath()
                            .replace("\\", "/")
                            .replace(namespaceRoot.getAbsolutePath().replace("\\", "/"), "")
                            .replace("."+extension, "")
                            .replaceFirst("^/\\w*/", "")
                            .replace("^/", "");

                    try {
                        String s = path.replaceAll("^/", "");
                        if (!s.isEmpty())
                            result.put(new Identifier(namespace.replaceAll(":", ""), s), templateCreator.get(templateFile));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }

    public interface TemplateFunction<T> {
        T get(File source) throws FileNotFoundException;
    }
}
