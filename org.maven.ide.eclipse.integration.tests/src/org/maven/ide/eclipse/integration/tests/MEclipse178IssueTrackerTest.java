package org.maven.ide.eclipse.integration.tests;

import java.io.File;

import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.windowtester.runtime.swt.UITestCaseSWT;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.eclipse.JobsCompleteCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import com.windowtester.runtime.locator.XYLocator;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.swt.locator.CTabItemLocator;

public class MEclipse178IssueTrackerTest extends UIIntegrationTestCase {

	private File tempDir;

  /**
	 * Main test method.
	 *   <issueManagement>
    <system>Jira</system>
    <url>http://issues.sonatype.org</url>
  </issueManagement>
	 */
	public void testIssueTracker() throws Exception {
	  
    // Import the test project
    tempDir = doImport("projects/ch07project.zip");

		IUIContext ui = getUI();
		ui.click(new TreeItemLocator("simple-parent", new ViewLocator(
				"org.eclipse.jdt.ui.PackageExplorer")));
		ui.contextClick(new TreeItemLocator("simple-parent", new ViewLocator(
				"org.eclipse.jdt.ui.PackageExplorer")),
				"Maven/Open Issue Tracker");
		ui.wait(new ShellShowingCondition("Open Browser"));
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Open Browser"));
		
		openPomFile("simple-parent/pom.xml");
		ui.click(new CTabItemLocator("pom.xml"));
		replaceText("</modules>", "</modules><issueManagement><system>JIRA</system><url>http://issues.sonatype.org</url></issueManagement>");
		ui.keyClick(SWT.MOD1, 's');
		ui.wait(new JobsCompleteCondition());
		
		ui.click(new TreeItemLocator("simple-parent", new ViewLocator(
				"org.eclipse.jdt.ui.PackageExplorer")));
		ui.contextClick(new TreeItemLocator("simple-parent", new ViewLocator(
				"org.eclipse.jdt.ui.PackageExplorer")),
				"Maven/Open Issue Tracker");
		ui.wait(new JobsCompleteCondition());
		ui.click(new CTabItemLocator("http://issues.sonatype.org"));
	}

  protected void tearDown() throws Exception {
    
    super.tearDown();
    
    if(tempDir != null && tempDir.exists()) {
      deleteDirectory(tempDir);
      tempDir = null;
    }

  }

}