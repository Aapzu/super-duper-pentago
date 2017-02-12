package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.game.Move;
import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Direction;
import org.junit.After;
import org.junit.Before;
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
        assertEquals(30, alphaBetaPruning.getBest(node, 1).getNodeValue());
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
        assertEquals("test", ((TestNode) alphaBetaPruning.getBest(node, 10)).getName());
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
                new TestNode("c", 0, new TestNode[]{
                        new TestNode("ca", -1),
                        new TestNode("cb", 0, new TestNode[]{
                                new TestNode(1)
                        })
                })
        });
        assertEquals("b", ((TestNode) alphaBetaPruning.getBest(node, 2)).getName());
        assertEquals("a", ((TestNode) alphaBetaPruning.getBest(node, 3)).getName());
    }

    @Test
    public void itWorksWithPentagoIfDepthIs2() {
        Pentago game = new Pentago();
        game.addHumanPlayer("a");
        game.addHumanPlayer("b");
        game.addMarble(1, 1);
        game.rotateTile(1, 1, Direction.CLOCKWISE);
        game.addMarble(4, 1);
        game.rotateTile(1, 1, Direction.CLOCKWISE);
        game.addMarble(4, 4);
        game.rotateTile(1, 1, Direction.CLOCKWISE);
        System.out.println(game.getBoard());
        String best = ((PentagoNode) alphaBetaPruning.getBest(new PentagoNode(game), 2)).getSerializationString();
        game.deserialize(best);
        System.out.println(game.getBoard());
        Move m = game.getLastMove();
        assertNotNull(m);
        assertEquals(3, m.getMarbleX());
        assertEquals(2, m.getMarbleY());
    }
}
