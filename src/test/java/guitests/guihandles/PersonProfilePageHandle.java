package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handle for the {@code PersonProfilePageHandle} in the GUI.
 */
public class PersonProfilePageHandle extends NodeHandle<Node> {
    public static final String PERSON_PROFILE_PAGE_ID = "#personListView";

    public PersonProfilePageHandle(Node personProfilePageNode) {
        super(personProfilePageNode);
    }
}
