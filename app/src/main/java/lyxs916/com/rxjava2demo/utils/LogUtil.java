package lyxs916.com.rxjava2demo.utils;

import android.util.Log;

/**
 * A log util
 * Created by zhangdroid on 2017/7/21.
 *
 * @date qinglinyi 2018年05月22日
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class LogUtil {
    private static boolean sIsDebugEnabled = true;
    
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    
    private static int sFormatLogMethodIndex = 6;
    private static final FormatLog sFormatLog = new FormatLog();
    
    private static FormatAdapter sFormatAdapter = new FormatAdapter() {
        @Override
        public String formatContent(int methodIndex, String msg) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String className = stackTrace[methodIndex].getFileName();
            String methodName = stackTrace[methodIndex].getMethodName();
            int lineNumber = stackTrace[methodIndex].getLineNumber();
            
            methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
            
            return "[(" + className + ":" + lineNumber + ")#" + methodName + "] " + msg;
        }
    };
    
    private LogUtil() {
    }
    
    public static void setIsDebugEnabled(boolean isDebugEnabled) {
        sIsDebugEnabled = isDebugEnabled;
    }
    
    public static void v(String tag, String msg) {
        if (sIsDebugEnabled) {
            Log.v(tag, msg);
        }
    }
    
    public static void d(String tag, String msg) {
        if (sIsDebugEnabled) {
            Log.d(tag, msg);
        }
    }
    
    public static void i(String tag, String msg) {
        if (sIsDebugEnabled) {
            Log.i(tag, msg);
        }
    }
    
    public static void w(String tag, String msg) {
        if (sIsDebugEnabled) {
            Log.w(tag, msg);
        }
    }
    
    public static void e(String tag, String msg) {
        if (sIsDebugEnabled) {
            Log.e(tag, msg);
        }
    }
    
    public static FormatLog f() {
        return f(6);
    }
    
    public static FormatLog f(int index) {
        sFormatLogMethodIndex = index;
        return sFormatLog;
    }
    
    public static void setFormatAdapter(FormatAdapter formatAdapter) {
        if (formatAdapter == null) {
            return;
        }
        sFormatAdapter = formatAdapter;
    }
    
    private static void format(int type, String tag, String msg) {
        String logStr = sFormatAdapter.formatContent(sFormatLogMethodIndex, msg);
        switch (type) {
            case VERBOSE:
                Log.v(tag, logStr);
                break;
            case DEBUG:
                Log.d(tag, logStr);
                break;
            case INFO:
                Log.i(tag, logStr);
                break;
            case WARN:
                Log.w(tag, logStr);
                break;
            case ERROR:
                Log.e(tag, logStr);
                break;
            default:
                break;
        }
    }
    
    public static class FormatLog {
        public void v(String tag, String msg) {
            if (sIsDebugEnabled) {
                format(VERBOSE, tag, msg);
            }
        }
        
        public void d(String tag, String msg) {
            if (sIsDebugEnabled) {
                format(DEBUG, tag, msg);
            }
        }
        
        public void i(String tag, String msg) {
            if (sIsDebugEnabled) {
                format(INFO, tag, msg);
            }
        }
        
        public void w(String tag, String msg) {
            if (sIsDebugEnabled) {
                format(WARN, tag, msg);
            }
        }
        
        public void e(String tag, String msg) {
            if (sIsDebugEnabled) {
                format(ERROR, tag, msg);
            }
        }
    }
    
    public interface FormatAdapter {
        String formatContent(int methodIndex, String msg);
    }
    
}
