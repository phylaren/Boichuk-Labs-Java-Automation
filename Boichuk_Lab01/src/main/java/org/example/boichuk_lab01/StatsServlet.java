package org.example.boichuk_lab01;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

@WebServlet(name = "statsServlet", value = "/stats")
public class StatsServlet extends HttpServlet {

    private static void printTag(String tag, String text, PrintWriter out){
        out.println("<"+tag+">" + text + "</"+tag+">");
    }

    private static String toMB(Number value) {
        double bytes = value.doubleValue();
        double mb = bytes / (1024.0 * 1024.0);
        return String.format("%.2f MB", mb);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Runtime runtime = Runtime.getRuntime();

        try(PrintWriter out = response.getWriter()){
            out.println("<html><body>");

            printTag("h1", "STATS (in MB)",out);

            printTag("h2", "OS: " + os.getName(),out);
            printTag("h2", "version: " + os.getVersion(),out);

            printTag("h2", "processors: " + os.getAvailableProcessors(),out);
            printTag("h2", "CPU load: " + String.format("%.1f%%", os.getCpuLoad()*100),out);

            printTag("h2", "total memory: " + toMB(os.getTotalMemorySize()),out);
            printTag("h2", "free memory: " + toMB(os.getFreeMemorySize()),out);
            printTag("h2", "used memory: " + toMB(os.getTotalMemorySize()-os.getFreeMemorySize()),out);
            printTag("h2", "max memory for JVM: " + toMB(runtime.maxMemory()),out);

            out.println("</body></html>");
        }
    }

    public void destroy() {
    }
}