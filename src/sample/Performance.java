package sample;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class Performance {

    //To get the CPU usage
    public static OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean.class);
    //check the used Memory
    public double checkMemory(){

        //Memeory usage
        Runtime runtime = Runtime.getRuntime();

        // Run the garbage collector
        runtime.gc();

        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        double memoryMB = memory * 0.000001;
        System.out.printf("Used memory is megabytes: %.2f MB",memoryMB);
        return memoryMB;
    }


    //calculate cpu usage in percentage
    public double cpuUsage(){

        // What % CPU load this current JVM is taking, from 0.0-1.0
         double cpu = osBean.getProcessCpuLoad();

         double percent = cpu * 100;

        return percent;
    }
}
