package com.stiwsquer.logic;

import java.util.Objects;

public class FileInfo {


    private String FileName;
    private String fileDigest;

    public FileInfo(String fileName) {
        FileName = fileName;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFileDigest() {
        return fileDigest;
    }

    public void setFileDigest(String fileDigest) {
        this.fileDigest = fileDigest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileInfo file = (FileInfo) o;
        return FileName.equals(file.FileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FileName);
    }




}
