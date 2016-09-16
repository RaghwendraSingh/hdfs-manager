package org.raghwendra.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Raghwendra on 16/9/16.
 */
public class HDFSManager {
    private Configuration conf = null;
    private FileSystem local = null;
    private FileSystem hdfs = null;

    public HDFSManager(String hdfsURI) throws IOException, URISyntaxException {
        conf = new Configuration();
        hdfs = FileSystem.get(new URI(hdfsURI), conf);
        local = FileSystem.getLocal(conf);
    }

    public void listFiles(String hdfsPath) throws IOException {
        Path hdfsDir = new Path(hdfsPath);
        FileStatus[] hdfsDirStatus = hdfs.listStatus(hdfsDir);
        Path[] hdfsDirFiles = FileUtil.stat2Paths(hdfsDirStatus);
        for (Path path:
             hdfsDirFiles) {
            System.out.println(path.getName());
        }
    }
    public void put(String inputDir, String outputPath) throws IOException {
        hdfs.copyFromLocalFile(new Path(inputDir), new Path(outputPath));
    }

    public void delete(String dirToDelete, boolean recursive) throws IOException {
        Path hdfsPath = new Path(dirToDelete);
        hdfs.delete(hdfsPath, recursive);
    }

    public void create(String hdfsDirToCreate, boolean override) throws IOException {
        hdfs.mkdirs(new Path(hdfsDirToCreate));
    }
}
