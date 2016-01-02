package com.gsandchase.mileagelogger;

/**
 * Created by Rusty on 12/22/2015.
 */

        import android.os.Build;

        import java.io.File;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.util.Date;

public class Logger {

    private File logFile;

    public Logger() {

    }

    public Logger(String fileName) {

        logFile = new File(fileName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            logFile.setWritable(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            logFile.setReadable(true);
        }

        if(logFile.canWrite())
            System.out.println("file is writeable");
        else
            System.out.println("file is not writeable");

        if(logFile.exists())
            System.out.println("file exists");
        else
            System.out.println("file does not exist");
    }

    public Logger(File f) {
        logFile = f;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            logFile.setWritable(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            logFile.setReadable(true);
        }


        if(logFile.canWrite())
            System.out.println("file is writeable");
        else
            System.out.println("file is not writeable");

        if(logFile.exists())
            System.out.println("file exists");
        else
            System.out.println("file does not exist");

    }

    public void log(String s) {

        try {
            FileWriter fw = new FileWriter(this.logFile,true);
            String date = new Date().toString();
            fw.write(date+" : "+s);
            fw.write('\n');
            fw.close();
        } catch (IOException ex) {
            System.err.println("Couldn't log this: "+s);
        }

    }

}