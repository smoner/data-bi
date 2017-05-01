import org.eclipse.jetty.server.Server;
import org.springside.modules.test.jetty.JettyFactory;
import org.springside.modules.test.spring.Profiles;

/**
 * 使用Jetty运行调试Web应用, 在Console输入回车快速重新加载应用.
 */
public class PortalQuickStart2 {

    public static final int PORT = 8082;
    public static final String CONTEXT = "/cpu-bi";
    public static final String[] TLD_JAR_NAMES = new String[]{};

    public static void main(String[] args) throws Exception {
        // 设定Spring的profile
        Profiles.setProfileAsSystemProperty(Profiles.PRODUCTION);
        System.setProperty("org.apache.jasper.compiler.disablejsr199","true");
        // 启动Jetty
        Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
        //JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);
        System.setProperty("DDEBUG_VERBOSE", "-1");
        try {
            server.start();
            System.out.println("[INFO] Server running at http://localhost:" + PORT + CONTEXT);
            System.out.println("[HINT] Hit Enter to reload the application quickly");
            System.out.println("[INFO] server started");

            // 等待用户输入回车重载应用.
            while (true) {
                char c = (char) System.in.read();
                if (c == '\n') {
                    JettyFactory.reloadContext(server);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
