package io.github.cottonmc.functionapi.util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class DirectoryManager {

    private static DirectoryManager INSTANCE = new DirectoryManager();

    private String rootFolder = "functionapi";
    private String itemFolder = "/item";
    private String blockColorFolder = "/color/block";
    private String itemColorFolder = "/color/item";
    private String toolMaterialFolder = "/tool_material";
    private String armorMaterialFolder = "/armor_material";

    private DirectoryManager() {
    }

    public static DirectoryManager getINSTANCE() {
        return INSTANCE;
    }

    public List<File> getContentFolder() {
        String contentFolder = "/content";
        return getFolders(contentFolder);
    }

    private List<File> getFolders(String targetFolder){
        createRoot();
        File file = new File(rootFolder);

        List<File> blockFolders = new LinkedList<>();

        if (file.exists()) {
            File[] namespaces = file.listFiles();
            if (namespaces != null)
                for (File namespace : namespaces) {
                    String namespaceAbsolutePath = namespace.getAbsolutePath();
                    blockFolders.add(new File(namespaceAbsolutePath+targetFolder));
                }
        }

        return blockFolders;
    }

    private void createRoot(){
        File file = new File(rootFolder);
        file.mkdirs();
        if(!file.exists()){
            throw new DirectoryStateException(file);
        }
    }
    class DirectoryStateException extends RuntimeException {
        DirectoryStateException(File directory) {
            super("Could not create directory: " + directory.getAbsolutePath());
        }
    }

}
