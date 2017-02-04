package fi.aapzu.pentago.ai;

public class TestNode implements Node {

    private String name;
    private Integer value;
    private Node[] children;

    TestNode(String name, Integer value, Node[] children) {
        this.name = name;
        this.value = value;
        this.children = children;
    }

    TestNode(int value, Node[] children) {
        this(null, value, children);
    }

    TestNode(String name, int value) {
        this(name, value, new Node[0]);
    }

    TestNode(int value) {
        this(null, value, new Node[0]);
    }

    public String getName() {
        return name;
    }

    @Override
    public int getNodeValue() {
        return value;
    }

    @Override
    public Node[] getChildren() {
        return children;
    }
}
