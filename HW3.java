/*This class contains methods for matrix manipulations 
 * and determinant calculations.
 * 
 * @author Ny Pham
 * */

public class HW3{
  
  /*This method removes the specified row within the matrix.
   * 
   * @param matrix - the input matrix to be manipulated
   * @param row - index of the row to be removed from the matrix; integer value
   * 
   * @return the resulting matrix after the row has been removed
   */
  public static double[][] removeRow(double[][] matrix, int row){
    boolean rowRemoved = false;                                     //checks if specified row has been removed
    if(row < 0 || row > matrix.length - 1){
      return matrix;
    }
    if(matrix.length == 1 && row == 0){
      double[][] newMatrix = new double[0][];                     //stores the returned matrix for this condition
      return newMatrix;
    }
    else{
      double[][] newMatrix = new double[matrix.length - 1][];     //stores the returned matrix for this condition
      /*loop goes through the matrix, row-by-row; if the row index of the matrix is equal to the
       * specified row to be removed then the loop skips that row and adds the next row to the
       * new matrix to be returned
       */
      for(int i = 0; i < matrix.length; i++){
        if(i == row){
          rowRemoved = true;
        }
        else{
          if(rowRemoved){
            newMatrix[i-1] = matrix[i];
          }
          else{
            newMatrix[i] = matrix[i];
          }
        }
      }
      return newMatrix;
    }
  }
  
  /*this method removes a row from the matrix
   * 
   * @param matrix - the input matrix
   * @param row - the row to be removed from the matrix
   * 
   * @return the matrix with the row removed in SparseMatrix representation
   * 
   */
  public static SparseMatrix removeRow(SparseMatrix matrix, int row){
    double[] nonZeroValues = matrix.getNonZeroValues();                //stores the input matrix's non-zero values
    int[] columnPositions = matrix.getColumnPositions();               //stores the input matrix's non-zero values' column positions
    int[] rowStarts = matrix.getRowStarts();                           // stores the index of the non-zero value that begins each row in the matrix
    if (row < 0 || row > rowStarts.length - 1){
      return matrix;
    }
    if(row == 0 && rowStarts.length == 2){
      SparseMatrix newMatrix = new SparseMatrix(new double[0][]);
      return newMatrix;
    }
    else{
      int rowRemoveStartIndex = rowStarts[row];                                            //stores the index of the non-zero value that begins the row to be removed
      int rowRemoveEndIndex = rowStarts[row + 1];                                          //stores the index of the non-zero value that ends the row to be removed
      int numberOfNonZerosInRow = rowRemoveEndIndex - rowRemoveStartIndex;                 //stores the number of non-zero values in the row to be removed
      double[] newNonZeroValues = new double[nonZeroValues.length - numberOfNonZerosInRow];//stores the new non-zero values
      int[] newColumnPositions = new int[nonZeroValues.length - numberOfNonZerosInRow];    //stores the new column positions of the non-zero values
      int[] newRowStarts = new int[rowStarts.length - 1];                                  //stores the new row indices that begin each row
      if(numberOfNonZerosInRow == 0){
        newNonZeroValues = nonZeroValues;
        newColumnPositions = columnPositions;
        boolean rowRemoved = false;                                                        //stores whether the row has been removed
        int newRowStartsIndex = 0;
        /*loop works by going through the index of the input matrix's rowStarts array,
         * when the loop index equals the row to be removed, the element is not appended to the output matrix*/
        for(int i = 0; i < rowStarts.length; i++){
          if(i == row){
            rowRemoved = true;
          }
          else{
            newRowStarts[newRowStartsIndex] = rowStarts[i];
            newRowStartsIndex++;
          }
        }
        SparseMatrix newSparseMatrix = new SparseMatrix(newNonZeroValues, newColumnPositions, newRowStarts);
        return newSparseMatrix;
      }
      else{
        boolean rowRemoved = false;                                                        //stores whether the row has been removed
        int newNonZeroValuesIndex = 0;
        int newRowStartsIndex = 0;
        int nonZeroValuesReferenceIndex = 0;
        /*loop goes through the index values of the rowStarts array. if the indexer equals
         *the row to be removed, then that row is not appended to the new matrix*/
        for(int i = 0; i < rowStarts.length; i++){
          boolean hasRowStarted = false;                                                    //stores whether a new row has started
          if(i == row){
            rowRemoved = true;
            nonZeroValuesReferenceIndex = nonZeroValuesReferenceIndex + numberOfNonZerosInRow;
          }
          else{
            //this loop works by appending the non-zero values in each row that isn't removed
            for(int j = rowStarts[i]; j < rowStarts[i + 1]; j++){
              newNonZeroValues[newNonZeroValuesIndex] = nonZeroValues[nonZeroValuesReferenceIndex];
              newColumnPositions[newNonZeroValuesIndex] = columnPositions[nonZeroValuesReferenceIndex];
              if(hasRowStarted == false){
                newRowStarts[newRowStartsIndex] = newNonZeroValuesIndex;
                hasRowStarted = true;
                newRowStartsIndex++;
              }
              newNonZeroValuesIndex++;
            }
          }
        }
        newRowStarts[newRowStartsIndex] = newNonZeroValues.length;
        SparseMatrix newSparseMatrix = new SparseMatrix(newNonZeroValues, newColumnPositions, newRowStarts);
        return newSparseMatrix;
      }
    }
  }
  
  /*this method removes the specified column from the input matrix
   * 
   * @param matrix - the input matrix; it is assumed that each row has the same number of elements
   * @param column - index of the column to be removed; value can be 0 or a positive integer
   * 
   * @return - the output matrix will be the input matrix without the specified column*/
  public static double[][] removeColumn(double[][] matrix, int column){
    double[][] newMatrix = new double[matrix.length][];                 //stores the new matrix
    //this loop works by iterating through the rows of the input matrix
    //if the column to be removed is not valid, then the original row is appended to the new matrix
    for(int i = 0; i < matrix.length; i++){
      if(column < 0 || column > matrix[i].length - 1){
        newMatrix[i] = matrix[i];
      }
      if(column == 0 && matrix[i].length == 1){
        newMatrix[i] = new double[0];
      }
      else{
        newMatrix[i] = new double[matrix[i].length - 1];
        int newMatrixColumnIndex = 0;
        if(column == matrix[i].length - 1){
          //this loop works by appending the elements of the row, except for the last element
          for(int j = 0; j < matrix[i].length - 1; j++){
              newMatrix[i][newMatrixColumnIndex] = matrix[i][j];
              newMatrixColumnIndex++;
          }
        }
        else{
          //this loop works by appending the elements that are not in the column index to be removed
          for(int j = 0; j < matrix[i].length; j++){
            if(j != column){
              newMatrix[i][newMatrixColumnIndex] = matrix[i][j];
              newMatrixColumnIndex++;
            }
          }
        }
      }
    }
    return newMatrix;
  }
  
  /*This method removes a column from the input matrix
   * 
   * @param matrix - the matrix to be manipulated
   * @param int column - the column index to be removed
   * 
   * @return - the matrix is returned in a sparse matrix representation
   * 
   */
  public static SparseMatrix removeColumn(SparseMatrix matrix, int column){
    double[] nonZeroValues = matrix.getNonZeroValues();                //stores the input matrix's non-zero values
    int[] columnPositions = matrix.getColumnPositions();               //stores the input matrix's non-zero values' column positions
    int[] rowStarts = matrix.getRowStarts();                           //stores the index of the non-zero value that begins each row in the matrix
    int numberOfValuesInColumn = 0;                                    //stores the number of values in a column
    //this loop works by going through the columnPositions array and if
    //one of the elements equals the column to be removed then numberOfValuesInColumn is incremented
    for(int i = 0; i < columnPositions.length; i++){
      if(columnPositions[i] == column)
        numberOfValuesInColumn++;
    }
    int maxNumberColumnIndex = 0;                                      //stores the largest column index from columnPositions
    //this loop works by comparing each element in the columnPositions array,
    //if the value is larger then it is stored in maxNumberColumnIndex
    for(int i = 0; i < columnPositions.length; i++){
      if(columnPositions[i] > maxNumberColumnIndex)
        maxNumberColumnIndex = columnPositions[i];
    }
    if(column < 0 || column > maxNumberColumnIndex)
      return matrix;
    boolean oneColumn = true;                                          //stores whether the matrix only has one column
    //this loop works by checking whether all of the values in columnPositions are the same
    for(int i = 0; oneColumn && i < columnPositions.length - 1; i++){
      if(columnPositions[i] != columnPositions[i + 1])
           oneColumn = false;
    }
    if(oneColumn && column == rowStarts[0]){
      nonZeroValues = new double[0];
      columnPositions = new int[0];
      rowStarts = new int[0];
      return new SparseMatrix(nonZeroValues, columnPositions, rowStarts);
    }
    else{
      double[] newNonZeroValues = new double[nonZeroValues.length - numberOfValuesInColumn];
      int[] newColumnPositions = new int[nonZeroValues.length - numberOfValuesInColumn];
      int newNonZeroValueIndex = 0;
      for(int i = 0; i < columnPositions.length; i++){
        if(column != columnPositions[i]){
          newNonZeroValues[newNonZeroValueIndex] = nonZeroValues[i];
          if(column < columnPositions[i]){
            newColumnPositions[newNonZeroValueIndex] = columnPositions[i] - 1;
          }
          else{
            newColumnPositions[newNonZeroValueIndex] = columnPositions[i];
          }
          newNonZeroValueIndex++;
        }
        else{
          for(int j = 0; j < rowStarts.length; j++){
            if(j != rowStarts.length - 1 && i >= rowStarts[j] && i < rowStarts[j + 1] && rowStarts[j] != rowStarts[j + 1]){
              for(int l = j + 1; l < rowStarts.length; l++){
                rowStarts[l] = rowStarts[l] - 1;
              }
            }
          }
        }
      }
      SparseMatrix newSparseMatrix = new SparseMatrix(newNonZeroValues, newColumnPositions, rowStarts);
      return newSparseMatrix;
    }
  }
  
  /*This method calculates the determinant of a matrix
   * 
   * @param matrix - the matrix to be used to calculate a determinant
   * 
   * @return - the determinant value
   */
  public static double determinant(double[][] matrix){
    double determinant = 0;                                   //stores the determinant value
    if(matrix.length == 0){
      return 0;
    }
    if(matrix.length == 1){
      //loop works by adding the values at the even indices and subtract the values
      //of the odd indices from determinant
      for(int j = 0; j < matrix[0].length; j++){
        if(j == 0 || j % 2 == 0)
          determinant = determinant + matrix[0][j];
        else
          determinant = determinant - matrix[0][j];
        }
      return determinant;
      }
    else{
      double evenTotal = 0;                                //stores the total of the even indices and their subdeterminant
      double oddTotal = 0;                                 //stores the total of the odd indices and their subdeterminant
      //this loop works by going through each value of row 0 and calculating
      //the subdeterminant by recursion
      for(int j = 0; j < matrix[0].length; j++){
        if(j == 0 || j % 2 == 0){
          evenTotal = evenTotal + matrix[0][j] * HW3.determinant(HW3.removeRow(HW3.removeColumn(matrix,j), 0));
        }
        else{
          oddTotal = oddTotal + matrix[0][j] * HW3.determinant(HW3.removeRow((HW3.removeColumn(matrix,j)), 0));
        }
      }
      determinant = evenTotal - oddTotal;
      return determinant;
    }
  }
  
  /*This method calculates the determinant of the input matrix.
   * 
   * @param matrix - the matrix to be used to calculate the determinant
   * 
   * @return - the determinant value
   */
  public static double determinant(SparseMatrix matrix){
    double[] nonZeroValues = matrix.getNonZeroValues();                   //stores the non-zero values of the matrix
    int[] columnPositions = matrix.getColumnPositions();                  //stores the column positions of the non-zero values of the matrix
    int[] rowStarts = matrix.getRowStarts();                              //stores the indices of the start of each row of the matrix
    double determinant = 0;                                               //stores the determinant value
    if(rowStarts.length == 0){
      return 0;
    }
    if(rowStarts.length == 2){
      double evenTotal = 0;                                                      //stores the total of the non-zero values in even column indices
      double oddTotal = 0;                                                       //stores the total of the non-zero values in odd column indices
      //this loop works by going through the row and adding the non-zero values in the 
      //even column indices and subtracting the non-zero values in the odd column indices of the determinant
      for(int i = 0; i < columnPositions.length; i++){
        if(columnPositions[i] == 0 || columnPositions[i] % 2 == 0)
          evenTotal = evenTotal + nonZeroValues[i];
        else
          oddTotal = oddTotal + nonZeroValues[i];
      }
      determinant = evenTotal - oddTotal;
      return determinant;
    }
    double evenTotal = 0;                                                         //stores the total of the non-zero values in even column indices
    double oddTotal = 0;                                                          //stores the total of the non-zero values in odd column indices
    int numberOfValuesInFirstRow = rowStarts[1] - rowStarts[0];                   //stores the number of non-zero values in the first row of the matrix
    double[] firstRow = new double[numberOfValuesInFirstRow];                     //stores the non-zero values from the first row of the matrix
    int[] firstRowColumnPositions = new int[numberOfValuesInFirstRow];            //stores the column positions of the non-zero values from the first row of the matrix
    int firstRowIndex = 0;
    //this loop works by appending all of the non-zero values with indices between
    //the first two values of the rowStarts array to the firstRow array ad firstRowColumnPositions array
    for(int i = rowStarts[0]; i < rowStarts[1]; i++){
      firstRow[firstRowIndex] = nonZeroValues[i];
      firstRowColumnPositions[firstRowIndex] = columnPositions[i];
    }
    //this loop works by checking whether the column position of the elements in firstRow are even or odd.
    //it then calculates the subdeterminant, scales the subdeterminant by the respective first row element, and
    //adds the value of the respective subtotal (oddTotal or evenTotal)
    for(int i = 0; i < firstRow.length; i++){
      if(firstRowColumnPositions[i] == 0 || firstRowColumnPositions[i] % 2 == 0)
        evenTotal = evenTotal + firstRow[i] * HW3.determinant(HW3.removeRow(HW3.removeColumn(matrix, firstRowColumnPositions[i]), 0));
      else
        oddTotal = oddTotal + firstRow[i] * HW3.determinant(HW3.removeRow(HW3.removeColumn(matrix, firstRowColumnPositions[i]), 0));
    }
    determinant = evenTotal - oddTotal;
    return determinant;
  }
  
  /*This method swaps the two input rows in the input matrix.
   * 
   * @param matrix - the input matrix to be manipulated
   * @param row 1 - the index of the row to be swapped with row2; an integer value
   * @param row2 - the index of the row to be swapped with row1; an integer value
   */
  public static void swapRows(double[][] matrix, int row1, int row2){
    double[][] newMatrix = new double[matrix.length][];                    //stores the output matrix
    boolean hasSwapped = false;                                            //stores whether the swap has occured in the matrix
    /*loop goes through the matrix, row-by-row; if the row index is equal 
     *to either of the specified rows to be swapped, the swap occurs*/
    for(int i = 0; i < matrix.length; i++){
      if(i == row1 || i == row2){
        if(hasSwapped == false){
          if(i == row1)
            newMatrix[row2] = matrix[row1];
          if(i == row2)
            newMatrix[row1] = matrix[row2];
          hasSwapped = true;
        }
      }
      else{
        newMatrix[i] = matrix[i];
      }
    }
  }
  
  /* This method takes every element in the specified row and multiplies it by the specified scale.
   * 
   * @param double - the input matrix to be manipulated
   * @param row - the row to be scaled
   * @param scale - what each element will be scaled by
   */
  public static void scaleRows(double[][] matrix, int row, double scale){
    double[][] newMatrix = new double[matrix.length][];                       //the new matrix
    /*this loop works by going through each row in the input matrix,
     *when the loop is on the requested row to be scaled, each element
     *in that row is multiplied by the scale value*/
    for(int i = 0; i < matrix.length; i++){
      if(i == row){
        if(scale == 0){
          newMatrix[i] = new double[]{};
        }
        else{
          for(int j = 0; j < matrix[i].length; j++){
            newMatrix[i][j] = matrix[i][j] * scale;
          }
        }
      }
      else{
        newMatrix[i] = matrix[i];
      }
    }
  }
  
  /*This method takes each element in the toRow row of matrix and adds the elements
   * of fromRow (in the same column) multiplied by the scale.
   * 
   * @param matrix - the input matrix to be manipulated
   * @param toRow - the row index in matrix where its elements will be changed
   * @param fromRow - the row index in matrix where its elements will be scaled and added to element in toRow
   */
  public static void addRows(double[][] matrix, int toRow, int fromRow, double scale){
    if(matrix[fromRow].length > matrix[toRow].length){
      double[] tempToRow = new double[matrix[fromRow].length];                        //will store the temporay row with trailing zeros
      //this loop works by going through the new tempToRow and placing zeros as all of its elements
      for(int j = 0; j < tempToRow.length; j++){
        tempToRow[j] = 0;
      }
      //this loop works by going through the toRow of matrix and adding its values to tempToRow
      for(int j = 0; j < matrix[toRow].length; j++){
        tempToRow[j] = matrix[toRow][j];
      }
      matrix[toRow] = tempToRow;
    }
    //this loop works by adding the scaled fromRow element to the toRow element in the same column
    for(int i = 0; i < matrix[toRow].length; i++){
      matrix[toRow][i] = matrix[toRow][i] + matrix[fromRow][i] * scale;
    }
    boolean zerosAtEnd = true;                                                        //will store whether or not there are still trailing zeros in toRow of matrix
    //this loop works by checking if the last element in matrix[toRow] is a zero,
    //if it is then it is removed and replaced by a new array with that zero truncated.
    //if it isn't then the loop ends.
    for(int i = matrix[toRow].length - 1; zerosAtEnd;){
      if(matrix[toRow][i] == 0){
        double[] newToRow = new double[matrix[toRow].length - 1];                     //stores the new toRow array of the matrix
        //this loop goes through each element in the original matrix and appends
        //the untruncated values to the new array
        for(int j = 0; j < newToRow.length; j++){
          newToRow[j] = matrix[toRow][j];
        }
        matrix[toRow] = newToRow;
      }
      else{
        zerosAtEnd = false;
      }
    }
  }
  /*This method inverts the given matrix.
   * 
   * @param matrix - this matrix will be inverted
   * 
   * @return - the return is the inverted matrix
   */
  public static double[][] invert(double[][] matrix) throws NotInvertibleException{
    //this method works by checking that each row in the matrix is the same length as there are number of rows
    for(int i = 0; i < matrix.length; i++){
      try{
        if(matrix[i].length > matrix.length){
          throw new NotInvertibleException();
        }
      }
      catch(NotInvertibleException e){
      }
    }
    try{
      if(matrix.length == 0)
        throw new NotInvertibleException();
    }
    catch(NotInvertibleException e){
    }
    double[][] workingMatrix = matrix;                                                   //stores the matrix that will be used to manipulate the result matrix
    double[][] resultMatrix = new double[matrix.length][matrix.length];                  //stores the inverted matrix to be returned
    //this loop works by looking for the values in each row that will create the diagonal 
    //of 1s in workingMatrix
    for(int i = 0; i < workingMatrix.length; i++){
      if(workingMatrix[i][i] == 0){
        boolean rowFound = false;                                                         //this stores whether there is a non-zero value in the column
        //this loop works by checking whether a non-zero exists in the current column
        //if a non-zero does not exist, then an exception is thrown
        for(int j = i + 1; rowFound == false && j < workingMatrix.length; j++){
          if(workingMatrix[j][i] != 0){
            HW3.swapRows(workingMatrix, i, j);
            HW3.swapRows(resultMatrix, i, j);
            rowFound = true;
          }
        }
        try{
          if(rowFound == false){
            throw new NotInvertibleException();
          }
        }
        catch(NotInvertibleException e){
        }
      }
      //this loop works by scaling and adding the rows that are not in the row
      //that the non-zero value is in
      for(int j = i; j < workingMatrix.length; j++){
        if(j != i){
          double scaleFactor = -1 * workingMatrix[j][i] / workingMatrix[i][i];            //stores the scale factor that will be done to add the rows
          HW3.addRows(workingMatrix, i, j, scaleFactor);
          HW3.addRows(resultMatrix, i, j, scaleFactor);
        }
      }
      double scaleFactor = 1 / workingMatrix[i][i];                                      //stores the scale factor to scale the rows
      HW3.scaleRows(workingMatrix, i, scaleFactor);
      HW3.scaleRows(resultMatrix, i , scaleFactor);
    }
    return resultMatrix;
  } 
}