package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.game.Bot;
import fi.aapzu.pentago.game.Move;
import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Direction;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AlphaBetaPruningTest {

    private AlphaBetaPruning alphaBetaPruning;

    @Before
    public void setUp() {
        alphaBetaPruning = new AlphaBetaPruning();
    }

    @After
    public void tearDown() {
        alphaBetaPruning = null;
    }

    @Test
    public void itReturnsBestValueWhenDepthIs1() {
        Node node = new TestNode("test", 0, new TestNode[]{
                new TestNode(10),
                new TestNode(20),
                new TestNode(30),
                new TestNode(25),
                new TestNode(15),
        });
        assertEquals(30, alphaBetaPruning.getBest(node, 1).getNodeValue());
    }

    @Test
    public void itReturnsBestValueFromFirstLayerWhenDepthIs1() {
        TestNode n = new TestNode(30);
        Node node = new TestNode("test", 0, new TestNode[]{
                new TestNode(10, new TestNode[]{
                        new TestNode(-1000, new TestNode[]{
                                new TestNode(10000)
                        })
                }),
                new TestNode(20),
                n,
                new TestNode(25),
                new TestNode(15)
        });
        assertEquals(30, alphaBetaPruning.getBest(node, 1).getNodeValue());
        assertEquals(30, n.getAlphaBetaValue());
    }

    @Test
    public void itWorksCorrectlyWhenDepthIs2() {
        TestNode c = new TestNode("c", 30);
        Node node = new TestNode("root", 0, new TestNode[]{
                new TestNode("a", 10, new TestNode[]{
                        new TestNode(-1000, new TestNode[]{
                                new TestNode(10000)
                        })
                }),
                new TestNode("b", 20),
                c,
                new TestNode("d", 100, new TestNode[]{
                        new TestNode(-1000, new TestNode[]{
                                new TestNode(100000)
                        })
                }),
                new TestNode("e", 15)
        });
        assertEquals("c", ((TestNode) alphaBetaPruning.getBest(node, 2)).getName());
        assertEquals(30, c.getAlphaBetaValue() );
    }

    @Test
    public void itReturnsOverallBestValueWhenDepthIsEnough() {
        TestNode testNode = new TestNode("test", 25, new TestNode[]{
                new TestNode(-1000, new TestNode[]{
                        new TestNode(100000)
                })
        });
        Node node = new TestNode("moi", 0, new TestNode[]{
                new TestNode(10, new TestNode[]{
                        new TestNode(-1000, new TestNode[]{
                                new TestNode(10000)
                        })
                }),
                new TestNode(20),
                new TestNode(30),
                testNode,
                new TestNode(15)
        });
        assertEquals("test", ((TestNode) alphaBetaPruning.getBest(node, 10)).getName());
        assertEquals(100000, testNode.getAlphaBetaValue());
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
        assertEquals("b", ((TestNode) alphaBetaPruning.getBest(node, 10)).getName());
    }

    @Test
    public void itWorksWithBiggerTreeAndDepth() {
        Node node = new TestNode("moi", 0, new TestNode[]{
            new TestNode("a", 0, new TestNode[]{
                new TestNode("aa", 1),
                new TestNode("ab", 0, new TestNode[]{
                    new TestNode(1, new TestNode[]{
                        new TestNode(99)
                    })
                })
            }),
            new TestNode("b", 0, new TestNode[]{
                new TestNode("ba", 0, new TestNode[]{
                    new TestNode(1)
                }),
                new TestNode("bb", 0, new TestNode[]{
                    new TestNode(1)
                })
            }),
            new TestNode("c", 0, new TestNode[]{
                new TestNode("ca", -1),
                new TestNode("cb", -1, new TestNode[]{
                    new TestNode(100)
                })
            })
        });
        assertEquals("a", ((TestNode) alphaBetaPruning.getBest(node, 2)).getName());
        assertEquals(1, node.getChildren()[0].getAlphaBetaValue());
        assertEquals(0, node.getChildren()[1].getAlphaBetaValue());
        assertEquals(-1, node.getChildren()[2].getAlphaBetaValue());
        assertEquals("c", ((TestNode) alphaBetaPruning.getBest(node, 9)).getName());
        assertEquals(99, node.getChildren()[0].getAlphaBetaValue());
        assertEquals(1, node.getChildren()[1].getAlphaBetaValue());
        assertEquals(100, node.getChildren()[2].getAlphaBetaValue());
    }

}
