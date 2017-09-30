import java.lang.reflect.Field;

/**
 * Created by 张超 on 2017/9/22.
 */
public class DataClean {
    private static void loadJNILibDynamically() {
        try {
            System.setProperty("java.library.path", System.getProperty("java.library.path")
                    + ":D:/Users/zcer/R-3.4.1/library/rJava/jri/x64");
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);

            System.loadLibrary("rJava");
        } catch (Exception e) {
            // do nothing for exception
        }
    }
}
