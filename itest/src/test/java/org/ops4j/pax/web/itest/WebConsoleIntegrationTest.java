package org.ops4j.pax.web.itest;

import static org.junit.Assert.assertFalse;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.CoreOptions.workingDirectory;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;
import static org.ops4j.pax.exam.MavenUtils.asInProject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.web.service.spi.ServletEvent;
import org.ops4j.pax.web.service.spi.ServletListener;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Toni Menzel (tonit)
 * @since Mar 3, 2009
 */
@RunWith(JUnit4TestRunner.class)
public class WebConsoleIntegrationTest extends ITestBase {
	Logger LOG = LoggerFactory.getLogger(WarIntegrationTest.class);

	@Configuration
	public static Option[] configure() {

	    return OptionUtils.combine(configureJetty(),
				workingDirectory("target/paxexam/"),
				systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("TRACE"),
				systemProperty("org.osgi.service.http.hostname").value("127.0.0.1"),
				systemProperty("org.osgi.service.http.port").value("8181"),
				systemProperty("java.protocol.handler.pkgs").value("org.ops4j.pax.url"),
				systemProperty("org.ops4j.pax.url.war.importPaxLoggingPackages").value("true"),
				systemProperty("org.ops4j.pax.web.log.ncsa.enabled").value("true"),
				mavenBundle().groupId("org.apache.felix")
					.artifactId("org.apache.felix.bundlerepository").version("1.6.2"),
				mavenBundle().groupId("org.apache.felix")
					.artifactId("org.apache.felix.configadmin").version("1.2.8"),
				mavenBundle().groupId("org.apache.felix")
					.artifactId("org.apache.felix.shell").version("1.4.2"),
				mavenBundle().groupId("org.apache.felix")
					.artifactId("org.apache.felix.shell.tui").version("1.4.1"),
				mavenBundle().groupId("org.apache.felix")
					.artifactId("org.apache.felix.webconsole").version("3.1.8"),
					

			    //HTTP Client needed for UnitTesting
				mavenBundle("commons-codec", "commons-codec").version(asInProject())//,
//				wrappedBundle(mavenBundle("org.apache.httpcomponents",
//						"httpclient", "4.1")),
//				wrappedBundle(mavenBundle("org.apache.httpcomponents",
//								"httpcore", "4.1"))
				);
	}

	/**
	 * You will get a list of bundles installed by default plus your testcase,
	 * wrapped into a bundle called pax-exam-probe
	 */
	@Test
	public void listBundles() {
		for (Bundle b : bundleContext.getBundles()) {
			System.out.println("Bundle " + b.getBundleId() + " : "
					+ b.getSymbolicName());
		}

	}

	@Test
	public void testBundlesPath() throws Exception {
		
		testWebPath("http://localhost:8181/system/console/bundles", "", 401, false );
		
		testWebPath("http://localhost:8181/system/console/bundles", "Apache Felix Web Console<br/>Bundles", 200, true);

	}

}