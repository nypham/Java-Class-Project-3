/*This class tests the methods from HW3 and SparseMatrix.
 * 
 * @author Ny Pham
 * */

import org.junit.*;
import static org.junit.Assert.*;

/*
 * Testes class HW3
 */

public class HW3Tester{
  /*
   * Helper method to compare two dimensional arrays.
   * */
  private void equals2DArray(double[][] expected, double[][] received){
    assertEquals(expected.length, received.length);
    for(int i = 0; i < expected.length; i++){
      assertArrayEquals(expected[i], received[i], 0);
    }
  }
  
  /*
   * Helper method to compare sparce matrices
   * */
  private void equalsSparseMatrix(SparseMatrix expected, SparseMatrix received){
    assertArrayEquals(expected.getNonZeroValues(), received.getNonZeroValues(), 0);
    assertArrayEquals(expected.getColumnPositions(), received.getColumnPositions());
    assertArrayEquals(expected.getRowStarts(), received.getRowStarts());
  }
  
  /*
   * Tests the removeRow method
   */
  @Test
  public void testRemoveRow(){
    double[][] array = {{1, 2, 3, 4}, {11, 12, 13, 14, 15, 16}, {21, 22, 23, 24}, {31, 32, 33}};
    
    equals2DArray(new double[0][], HW3.removeRow(new double[0][],0));                                                        //tests 0 length
    equals2DArray(new double[0][], HW3.removeRow(new double[][]{{1, 2, 3, 4}}, 0));                                          //tests 1 length
    equals2DArray(new double[][]{{11, 12, 13, 14, 15, 16}, {21, 22, 23, 24}, {31, 32, 33}}, HW3.removeRow(array, 0));        //tests many length and first
    
    equals2DArray(new double[][]{{1, 2, 3, 4}, {21, 22, 23, 24}, {31, 32, 33}}, HW3.removeRow(array, 1));                    //tests middle
    equals2DArray(new double[][]{{1, 2, 3, 4}, {11, 12, 13, 14, 15, 16}, {21, 22, 23, 24}}, HW3.removeRow(array, 3));        //tests last
    
    equals2DArray(array, HW3.removeRow(array, -5));                                                                          //tests special case
    equals2DArray(array, HW3.removeRow(array, 10));                                                                          //tests special case
  }
  
  /*
   * Tests the removeRow method
   */
  @Test
  public void testRemoveRowSparse(){
    SparseMatrix matrix = new SparseMatrix(new double[]{1, 3, 2, 5, 4}, new int[]{1, 0, 2, 0, 1}, new int[]{0, 1, 3, 3, 5});
    
    equalsSparseMatrix(new SparseMatrix(new double[0], new int[0], new int[0]), HW3.removeRow(new SparseMatrix(new double[0], new int[0], new int[0]), 0)); //tests 0 length
    equalsSparseMatrix(new SparseMatrix(new double[0], new int[0], new int[0]), HW3.removeRow(new SparseMatrix(new double[][]{{0, 1, 0}}), 0));             //tests 1 length
    equalsSparseMatrix(new SparseMatrix(new double[][]{{3, 0, 2}, {0, 0, 0}, {5, 4, 0}}), HW3.removeRow(matrix, 0));                                        //tests many length and first
    
    equalsSparseMatrix(new SparseMatrix(new double[][]{{0, 1, 0}, {0, 0, 0}, {5, 4, 0}}), HW3.removeRow(matrix, 1));                                        //tests middle
    equalsSparseMatrix(new SparseMatrix(new double[][]{{0, 1, 0}, {3, 0, 2}, {0, 0, 0}}), HW3.removeRow(matrix, 3));                                        //tests last
    
    equalsSparseMatrix(matrix, HW3.removeRow(matrix, -5));                                                                                                  //tests special case
    equalsSparseMatrix(matrix, HW3.removeRow(matrix, 15));                                                                                                  //tests special case 
  }
  
  /*
   * Tests the removeColumn method
   */
  @Test
  public void testRemoveColumn(){
    double[][] array = {{1, 2, 3, 4}, {11, 12, 13, 14, 15, 16}, {21, 22, 23, 24}, {31, 32, 33}};
    
    equals2DArray(new double[0][0], HW3.removeColumn(new double[0][0], 0));                                                             //tests 0 length
    equals2DArray(new double[][]{{2, 3, 4}}, HW3.removeColumn(new double[][]{{1, 2, 3, 4}}, 0));                                        //tests 1 length
    equals2DArray(new double[][]{{2, 3, 4}, {12, 13, 14, 15, 16}, {22, 23, 24}, {32, 33}}, HW3.removeColumn(array, 0));                 //tests many length and first
    
    equals2DArray(new double[][]{{1, 3, 4}, {11, 13, 14, 15, 16}, {21, 23, 24}, {31, 33}}, HW3.removeColumn(array, 1));                 //tests middle length
    equals2DArray(new double[][]{{1, 2, 3, 4}, {11, 12, 13, 14, 15}, {21, 22, 23, 24}, {31, 32, 33}}, HW3.removeColumn(array, 5));      //tests last
    
    equals2DArray(array, HW3.removeColumn(array, -5));                                                                                   //tests special case
    equals2DArray(array, HW3.removeColumn(array, 13));                                                                                   //tests special case
  }
  
  /*
   * Tests the removeColumn method
   */
  @Test
  public void testRemoveColumnSparse(){
    SparseMatrix matrix = new SparseMatrix(new double[]{1, 3, 2, 5, 4}, new int[]{1, 0, 2, 0, 1}, new int[]{0, 1, 3, 3, 5});
    
    equalsSparseMatrix(new SparseMatrix(new double[0], new int[0], new int[0]), HW3.removeColumn(new SparseMatrix(new double[0], new int[0], new int[0]), 0));      //tests 0 length
    equalsSparseMatrix(new SparseMatrix(new double[0], new int[0], new int[0]), HW3.removeColumn(new SparseMatrix(new double[][]{{0}, {3}, {0}, {5}}), 0));         //tests 1 length
    equalsSparseMatrix(new SparseMatrix(new double[][]{{1, 0}, {0, 2}, {0, 0}, {4, 0}}), HW3.removeColumn(matrix, 0));                                              //tests many length and first
    
    equalsSparseMatrix(new SparseMatrix(new double[][]{{0, 0}, {3, 2}, {0, 0}, {5, 0}}), HW3.removeColumn(matrix, 1));                                              //tests middle
    equalsSparseMatrix(new SparseMatrix(new double[][]{{0, 1}, {3, 0}, {0, 0}, {5, 4}}), HW3.removeColumn(matrix, 2));                                              //tests last
    
    equalsSparseMatrix(matrix, HW3.removeColumn(matrix, -5));                                                                                                       //tests special case
    equalsSparseMatrix(matrix, HW3.removeColumn(matrix, 18));                                                                                                       //tests special case
  }
  
  /*
   * Tests the determinant method
   */
  @Test
  public void testDeterminat(){
    
    assertEquals(0, HW3.determinant(new double[][]{{}}) , 0);                            //tests 0 length
    assertEquals(7, HW3.determinant(new double[][]{{4, 2, 5}}), 0);                      //tests 1 length
    assertEquals(-28, HW3.determinant(new double[][]{{8, 2, 0}, {6, 5, 4}}), 0);         //tests many lengths
  }
  
  /*
   * Tests the determinant method
   */
  @Test
  public void testDeterminatSparse(){
    
    assertEquals(0, HW3.determinant(new SparseMatrix(new double[][]{{}})), 0);                       //tests 0 length
    assertEquals(7, HW3.determinant(new SparseMatrix(new double[][]{{5, 4, 6}})), 0);                //tests 1 length
    assertEquals(-28, HW3.determinant(new SparseMatrix(new double[][]{{8, 2, 0}, {6, 5, 4}})), 0);   //tests many length
  }
  
  /*
   * Tests the swapRows, scaleRows, addRows, and invert method
   */
  @Test
  public void testInvert(){
  }
  
  /*
   * Tests the SparseMatrix class
   */
  @Test
  public void testSparseMatrix(){
    
    equalsSparseMatrix(new SparseMatrix(new double[0], new int[0], new int[0]), new SparseMatrix(new double[][]{{}}));                                                                                     //tests 0 length
    equalsSparseMatrix(new SparseMatrix(new double[]{1}, new int[]{0}, new int[]{0}), new SparseMatrix(new double[][]{{1}}));                                                                              //tests 1 length
    equalsSparseMatrix(new SparseMatrix(new double[]{1, 3, 2, 5, 4}, new int[]{1, 0, 2, 0, 1}, new int[]{0, 1, 2, 2, 4}), new SparseMatrix(new double[][] {{0, 1, 0}, {3, 0, 2}, {0, 0, 0}, {5, 4, 0}}));  //tests many lengths
  }
}