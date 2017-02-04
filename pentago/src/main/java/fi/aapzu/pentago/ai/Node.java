package fi.aapzu.pentago.ai;

/**
 * Created by Aapeli on 02/02/2017.
 */
public interface Node {
    int getNodeValue();
    Node[] getChildren();
}
