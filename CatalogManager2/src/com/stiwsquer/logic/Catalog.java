package com.stiwsquer.logic;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Catalog {

    private String directory;
    private HashSet<FileInfo> files;

    public Catalog() {
        files = new HashSet<>();
    }

    public Catalog(String directory) {
        files = new HashSet<>();
        this.directory = directory;
        createFileInfoObjects();

    }

    public static void main(String args[]){
        Catalog catalog = new Catalog("C:\\Users\\kuba0\\Desktop\\DirectoryTest");
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
        createFileInfoObjects();
    }

    public HashSet<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(HashSet<FileInfo> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return directory.equals(catalog.directory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directory);
    }

    private void createFileInfoObjects(){

        Set<String> filesNames =  readFiles();

        for (String file:
                filesNames) {
            FileInfo fileInfo = new FileInfo(file);
            fileInfo.setFileDigest(calculateFileDigest(directory + "\\" + fileInfo.getFileName()));
            files.add(fileInfo);
            System.out.println(fileInfo.getFileName() + " : " + fileInfo.getFileDigest());
        }

//        Scanner sc= new Scanner(System.in);
////        System.out.print("Enter first number- ");
////        int a= sc.nextInt();
////        System.out.println("hasDirectoryChanged: " +hasDirectoryChanged());

    }

    public void trackDirectory(){
        for (FileInfo fileInfo:
             files) {
            fileInfo.setFileDigest(calculateFileDigest(directory + "\\" + fileInfo.getFileName()));
        }
    }


    private Set<String> readFiles(){

        try (Stream<Path> stream = Files.walk(Paths.get(directory), 1)) {

            Set<String> filesNames =  stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());

            return filesNames;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String calculateFileDigest(String pathToFile){

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        byte[] digest = null;

        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(pathToFile));
            messageDigest.update(fileContent);
            digest =  messageDigest.digest(fileContent);
            //digest =  messageDigest.digest();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return Base64.getEncoder().encodeToString(digest);
    }



    public boolean hasDirectoryChanged(){

                Set<String> filesNames =  readFiles();

                if(files.size() != filesNames.size()){
                    return true;
                }

                for (String fileName:
                        filesNames) {

                    for(FileInfo theFile: files) {
                        if (theFile.getFileName().equals(fileName)) {
                            if(hasFileChanged(directory + "\\" + fileName)){
                                return true;
                            }

                        }
                    }
                }

            return false;
    }

    public boolean hasFileChanged(String filePath){

            for(FileInfo theFile: files) {
                if ((directory + "\\" + theFile.getFileName()).equals(filePath)) {

                    System.out.println("theFile.getFileName() "+ theFile.getFileName());
                    System.out.println("filePath: " + filePath);
                    String digest = calculateFileDigest( filePath);
                    System.out.println("fileName digest " + digest);

                    if (!theFile.getFileDigest().equals(digest)) {
                        return true;
                    }
                }
            }


        return false;
    }

    @Override
    public String toString() {
        return  directory ;
    }
}
