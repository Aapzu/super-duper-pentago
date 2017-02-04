package fi.aapzu.pentago.ai;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlphaBetaPruningTest {

    @Test
    public void itReturnsBestValueWhenDepthIs1() {
        Node node = new TestNode("test", 0, new TestNode[]{
                new TestNode(10),
                new TestNode(20),
                new TestNode(30),
                new TestNode(25),
                new TestNode(15),
        });
        assertEquals(30, AlphaBetaPruning.getBest(node, 1).getNodeValue());
    }

    @Test
    public void itReturnsBestValueFromFirstLayerWhenDepthIs1() {
        Node node = new TestNode("test", 0, new TestNode[]{
                new TestNode(10, new TestNode[]{
                    new TestNode(-1000, new TestNode[]{
                        new TestNode(10000)
                    })
                }),
                new TestNode(20),
                new TestNode(30),
                new TestNode(25),
                new TestNode(15)
        });
        assertEquals(30, AlphaBetaPruning.getBest(node, 1).getNodeValue());
    }

    @Test
    public void itReturnsOverallBestValueWhenDepthIsEnough() {
        Node node = new TestNode("moi", 0, new TestNode[]{
                new TestNode(10, new TestNode[]{
                    new TestNode(-1000, new TestNode[]{
                        new TestNode(10000)
                    })
                }),
                new TestNode(20),
                new TestNode(30),
                new TestNode("test", 25, new TestNode[]{
                    new TestNode(-1000, new TestNode[]{
                        new TestNode(100000)
                    })
                }),
                new TestNode(15)
        });
        assertEquals("test", ((TestNode)AlphaBetaPruning.getBest(node, 10)).getName());
    }

    @Test
    public void itWorksWithBiggerTree() {
        Node node = new TestNode("moi", 0, new TestNode[]{
            new TestNode("b", 0, new TestNode[]{
                    new TestNode("ba", 0, new TestNode[]{
                            new TestNode(1)
                    }),
                    new TestNode("bb", 0, new TestNode[]{
                            new TestNode(1)
                    })
            }),
            new TestNode("a", 0, new TestNode[]{
                new TestNode("aa", -1),
                new TestNode("ab", 0, new TestNode[]{
                    new TestNode(1)
                })
            }),
            new TestNode("c", 0, new TestNode[]{
                new TestNode("ca", -1),
                new TestNode("cb", 0, new TestNode[]{
                    new TestNode(1)
                })
            })
        });
        assertEquals("b", ((TestNode)AlphaBetaPruning.getBest(node, 10)).getName());
    }

    @Test
    public void itWorksWithBiggerTreeAndDepth() {
        Node node = new TestNode("moi", 0, new TestNode[]{
            new TestNode("b", 0, new TestNode[]{
                new TestNode("ba", 0, new TestNode[]{
                        new TestNode(1)
                }),
                new TestNode("bb", 0, new TestNode[]{
                        new TestNode(1)
                })
            }),
            new TestNode("a", 0, new TestNode[]{
                new TestNode("aa", -1),
                new TestNode("ab", 0, new TestNode[]{
                    new TestNode(1, new TestNode[]{
                        new TestNode(100)
                    })
                })
            }),
            new TestNode("c", 0,new TestNode[]{
                new TestNode("ca", -1),
                new TestNode("cb", 0, new TestNode[]{
                    new TestNode(1)
                })
            })
        });
        assertEquals("b", ((TestNode)AlphaBetaPruning.getBest(node, 2)).getName());
        assertEquals("a", ((TestNode)AlphaBetaPruning.getBest(node, 3)).getName());
    }

}
