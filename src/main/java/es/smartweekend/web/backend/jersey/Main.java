package es.smartweekend.web.backend.jersey;


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.smartweekend.web.backend.jersey.resources.NewsResource;
import es.smartweekend.web.backend.jersey.resources.SponsorResource;
import es.smartweekend.web.backend.jersey.resources.UserResource;
import es.smartweekend.web.backend.jersey.util.CORSResponseFilter;
import es.smartweekend.web.backend.jersey.util.ServiceExceptionMapper;
import es.smartweekend.web.backend.model.userService.UserService;
import es.smartweekend.web.backend.model.util.session.SessionManager;

/**
 * @author Miguel Ángel Castillo
 */
public class Main {

	private static Properties properties;

	static {
		try {
			properties = new Properties();
			InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("backend.properties");
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Could not read config file: " + e.getMessage());
		}
	}

	private static final int SESSION_TIMEOUT = Math.abs(Integer.parseInt(properties.getProperty("session.timeout")));
	private static final int SESSION_CLEAN_FREQUENCY = Math.abs(Integer.parseInt(properties.getProperty("session.cleanFrequency")));
	private static final URI SERVER_URI = URI.create(properties.getProperty("server.baseUri"));
	private static final String KEYSTORE_FILE = properties.getProperty("server.keystoreFile");
	private static final String KEYSTORE_PASS = properties.getProperty("server.keystorePass");
	private static final String TRUSTSTORE_FILE = properties.getProperty("server.truststoreFile");
	private static final String TRUSTSTORE_PASS = (properties.getProperty("server.truststorePass"));
	private static final boolean IS_SECURE = Boolean.parseBoolean(properties.getProperty("server.isSecure"));

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer() {

		final ResourceConfig rc = new ResourceConfig();
		
		rc.register(JacksonFeature.class);
		rc.register(ServiceExceptionMapper.class);
		
		rc.register(NewsResource.class);
		rc.register(SponsorResource.class);
		rc.register(UserResource.class);
		
		rc.register(CORSResponseFilter.class);
		
		SSLContextConfigurator sslContext = new SSLContextConfigurator();
		sslContext.setKeyStoreFile(KEYSTORE_FILE);
		sslContext.setKeyStorePass(KEYSTORE_PASS);
		sslContext.setTrustStoreFile(TRUSTSTORE_FILE);
		sslContext.setTrustStorePass(TRUSTSTORE_PASS);

		return GrizzlyHttpServerFactory.createHttpServer(SERVER_URI, rc, IS_SECURE, new SSLEngineConfigurator(sslContext).setClientMode(false).setNeedClientAuth(true));
	}

	public static void main(String[] args) throws IOException {

		// Spring context initialization.
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");

		// UserService Initialization
		UserService userService = ctx.getBean(UserService.class);
		userService.initialize();

		// Session clean thread initialization
		Thread scth = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					while (true) {
						Thread.sleep(SESSION_CLEAN_FREQUENCY);
						SessionManager.cleanOldSessions(SESSION_TIMEOUT);
					}
				} catch (Exception e) {
					throw new RuntimeException("Session clean thread error: " + e.getMessage());
				}
			}
		};
		scth.start();

		// Server Start
		final HttpServer server = startServer();   
		//Añadir parte estatica en la siguente linea
		server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("//web//frontend"),"/");
		System.out.println(String.format("Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...", properties.getProperty("server.baseUri")));
		System.in.read();
		//server.shutdownNow();
	}
}
