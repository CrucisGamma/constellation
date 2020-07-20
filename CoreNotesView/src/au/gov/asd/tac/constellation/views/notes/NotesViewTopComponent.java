/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.gov.asd.tac.constellation.views.notes;

import au.gov.asd.tac.constellation.graph.Graph;
import au.gov.asd.tac.constellation.views.JavaFxTopComponent;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 *
 * @author sol695510
 */
@TopComponent.Description(
        preferredID = "NotesViewTopComponent",
        iconBase = "au/gov/asd/tac/constellation/views/notes/resources/notes-view.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(
        mode = "explorer",
        openAtStartup = false)
@ActionID(
        category = "Window",
        id = "au.gov.asd.tac.constellation.views.notes.NotesViewTopComponent")
@ActionReferences({
    @ActionReference(path = "Menu/Experimental/Views", position = 500),
    @ActionReference(path = "Shortcuts", name = "CS-N")})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_NotesViewAction",
        preferredID = "NotesViewTopComponent")
@Messages({
    "CTL_NotesViewAction=Notes View",
    "CTL_NotesViewTopComponent=Notes View",
    "HINT_NotesViewTopComponent=Notes View"})

public class NotesViewTopComponent extends JavaFxTopComponent<NotesViewPane> {

    private final NotesViewController notesViewController;
    private final NotesViewPane notesViewPane;

    /**
     * Creates new NotesViewTopComponent
     */
    public NotesViewTopComponent() {
        setName(Bundle.CTL_NotesViewTopComponent());
        setToolTipText(Bundle.HINT_NotesViewTopComponent());
        initComponents();

        notesViewController = new NotesViewController(NotesViewTopComponent.this);
        notesViewPane = new NotesViewPane(notesViewController);

        initContent();

        // May need to add this after state has been created for notes view
//        addAttributeValueChangeHandler(LayersViewConcept.MetaAttribute.LAYERS_VIEW_STATE, graph -> {
//            notesViewController.readState();
//        });
    }

    // Below are actions that are called when graph is opened - can use these to reload notes when change graph occurs
    @Override
    protected void handleNewGraph(final Graph graph) {
        if (graph != null) {
            //preparePane();
        }
    }

    @Override
    protected void handleGraphOpened(final Graph graph) {
        if (graph != null) {
            //preparePane();
        }
    }

    @Override
    protected void handleGraphClosed(final Graph graph) {
        if (graph != null) {
            //preparePane();
        }
    }

    @Override
    protected void handleComponentOpened() {
        //preparePane();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the FormEditor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        setLayout(new java.awt.BorderLayout());

    }//GEN-END:initComponents

    @Override
    protected String createStyle() {
        return "resources/notes-view.css";
    }

    @Override
    protected NotesViewPane createContent() {
        return notesViewPane;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}