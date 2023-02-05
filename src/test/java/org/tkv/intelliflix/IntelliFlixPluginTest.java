package org.tkv.intelliflix;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PsiErrorElementUtil;
import org.tkv.intelliflix.chatgpt.ChatGptClient;
import org.tkv.intelliflix.services.IntelliFlixProjectService;

import static org.assertj.core.api.Assertions.assertThat;

@TestDataPath(value = "$CONTENT_ROOT/src/test/testData")
public class IntelliFlixPluginTest extends BasePlatformTestCase {
    public void testXMLFile() {
        PsiFile psiFile = myFixture.configureByText(XmlFileType.INSTANCE, "<foo>bar</foo>");
        XmlFile xmlFile = assertInstanceOf(psiFile, XmlFile.class);

        assertThat(PsiErrorElementUtil.hasErrors(getProject(), xmlFile.getVirtualFile())).isFalse();
        assertThat(xmlFile.getRootTag()).isNotNull();

        assertThat(xmlFile.getRootTag().getName()).isEqualTo("foo");
        assertThat(xmlFile.getRootTag().getValue().getText()).isEqualTo("bar");
    }

    public void testRename() {
        myFixture.testRename("foo.xml", "foo_after.xml", "a2");
    }

    public void testProjectService() {
        IntelliFlixProjectService projectService = getProject().getService(IntelliFlixProjectService.class);
        ChatGptClient client = projectService.getChatGptClient();
        assertThat(client).isNotNull();
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/rename";
    }
}
