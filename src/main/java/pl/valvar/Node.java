package pl.valvar;

public class Node {

    int value = 0;
    int row_value = 0;
    int col_value = 0;
    int square_row = 0;
    int square_col = 0;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean corrCheckNode(Node[][] n){                                                       //check if all constrains is provided
        for (int i = 0; i < 9; i++) {                                                               //Check if same number in column
            if(i!=this.row_value){
                if(n[i][this.col_value].getValue()==this.value){
                    return false;
                }
            }
        }
        for (int i = 0; i < 9; i++) {                                                               //check if same number in row
            if(i!=this.col_value) {
                if (n[this.row_value][i].getValue() == this.value) {
                    return false;
                }
            }
        }
        for (int i = 0; i < 3; i++) {                                                                           //check if same number in the square
            for (int j = 0; j < 3; j++) {
                if((3*this.square_row+i)!=this.row_value && (3*this.square_col+j)!=this.col_value){
                    if(n[3*this.square_row+i][3*this.square_col+j].getValue()==this.getValue()){
                        return false;
                    }
                }
            }
        }
        return true;                                                                                        //if all constrains good then return true
    }

    public Node(int value, int row_value, int col_value) {                      //initialization of node
        this.row_value = row_value;
        this.col_value = col_value;
        if (value!=0){                                                          //check if node is not empty
            this.value = value;
        }
        assignSquare();                                                         //sets its square
    }

    private void assignSquare(){                                //needed to get the square of each node in sudoku
        if(this.row_value>=0 && this.row_value<3 && this.col_value>=0 && this.col_value<3){
            this.square_row = 0;
            this.square_col = 0;
        }
        if(this.row_value>=0 && this.row_value<3 && this.col_value>=3 && this.col_value<6){
            this.square_row = 0;
            this.square_col = 1;
        }
        if(this.row_value>=0 && this.row_value<3 && this.col_value>=6 && this.col_value<9){
            this.square_row = 0;
            this.square_col = 2;
        }
        if(this.row_value>=3 && this.row_value<6 && this.col_value>=0 && this.col_value<3){
            this.square_row = 1;
            this.square_col = 0;
        }
        if(this.row_value>=3 && this.row_value<6 && this.col_value>=3 && this.col_value<6){
            this.square_row = 1;
            this.square_col = 1;
        }
        if(this.row_value>=3 && this.row_value<6 && this.col_value>=6 && this.col_value<9){
            this.square_row = 1;
            this.square_col = 2;
        }
        if(this.row_value>=6 && this.row_value<9 && this.col_value>=0 && this.col_value<3){
            this.square_row = 2;
            this.square_col = 0;
        }
        if(this.row_value>=6 && this.row_value<9 && this.col_value>=3 && this.col_value<6){
            this.square_row = 2;
            this.square_col = 1;
        }
        if(this.row_value>=6 && this.row_value<9 && this.col_value>=6 && this.col_value<9){
            this.square_row = 2;
            this.square_col = 2;
        }
    }
}
