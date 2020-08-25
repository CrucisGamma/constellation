/*
 * Copyright 2010-2020 Australian Signals Directorate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package au.gov.asd.tac.constellation.plugins.importexport.delimited;

import au.gov.asd.tac.constellation.graph.Graph;
import au.gov.asd.tac.constellation.views.JavaFxTopComponent;
import javafx.application.Platform;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * The Delimited Import Top Component. This class provides the Import Delimited
 * window and handles all interactions with the graph.
 *
 * @author aldebaran30701
 */
@TopComponent.Description(
        preferredID = "DelimitedImportTopComponent",
        iconBase = "au/gov/asd/tac/constellation/plugins/importexport/delimited/resources/importDelimited.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(
        mode = "explorer",
        openAtStartup = false)
@ActionID(
        category = "Window",
        id = "au.gov.asd.tac.constellation.plugins.importexport.delimited.DelimitedImportTopComponent")
@ActionReferences({
    @ActionReference(path = "Menu/File/Import", position = 0),
    @ActionReference(path = "Toolbars/File", position = 0)})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ImportDelimitedFileAction",
        preferredID = "DelimitedImportTopComponent")
@Messages({
    "CTL_ImportDelimitedFileAction=From Delimited File...",
    "HINT_ImportDelimitedFile=Import from Delimited File"})
public final class DelimitedImportTopComponent extends JavaFxTopComponent<DelimitedImportPane> {

    private final DelimitedImportPane delimitedImportPane;

    public DelimitedImportTopComponent() {
        setName(Bundle.CTL_ImportDelimitedFileAction());
        setToolTipText(Bundle.HINT_ImportDelimitedFile());
        initComponents();

        delimitedImportPane = new DelimitedImportPane(this);
        initContent();
    }

    @Override
    protected String createStyle() {
        return "resources/delimited-importer.css";
    }

    @Override
    protected DelimitedImportPane createContent() {
        return delimitedImportPane;
    }

    @Override
    protected void handleNewGraph(final Graph graph) {
        preparePane();
    }

    @Override
    protected void handleGraphOpened(final Graph graph) {
        preparePane();
    }

    @Override
    protected void handleGraphClosed(final Graph graph) {
        preparePane();
    }

    private void preparePane() {
        Platform.runLater(() -> {
            delimitedImportPane.getSourcePane().updateDestinationGraphCombo();
            delimitedImportPane.updateSourcePane();
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
