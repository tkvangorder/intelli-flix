package org.tkv.intelliflix.ui;

import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.ui.EditorTextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Needed a wrapping version of the editor text field that also allows for a preferred size.
 */
public class WrappingEditorTextField  extends EditorTextField {

    private final int preferredWidth;
    private final int preferredHeight;

    public WrappingEditorTextField(int preferredWidth, int preferredHeight) {
        super();
        this.preferredWidth = preferredWidth;
        this.preferredHeight = preferredHeight;
    }

    @Override
    protected @NotNull EditorEx createEditor() {
        final EditorEx editor = super.createEditor();
        editor.setOneLineMode(false);
        editor.setCaretEnabled(true);
        editor.getScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        editor.getComponent().setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        editor.getSettings().setUseSoftWraps(true);

        return editor;
    }
}
