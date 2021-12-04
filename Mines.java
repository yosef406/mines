package mines;

import java.util.Random;

public class Mines {
    private int height, width;
    private Place[][] mines;

    public Mines(int height, int width, int numMines) {
        this.height = height;
        this.width = width;
        mines = new Place[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mines[i][j] = new Place();// populate the board
            }
        }
        if (numMines > 0)// populate the board with mines randomly
            for (int i = 0; i < numMines; i++) {
                if (!addMine(new Random().nextInt(height), new Random().nextInt(width)))
                    i--;// if there is a mine in the spot chosen try to place it again
            }
    }

    public boolean addMine(int i, int j) {
        if (mines[i][j].mine != true) {// if there is no mine in the spot
            mines[i][j].mine = true;// add a mine
            for (int m = 0; m < height; m++)
                for (int n = 0; n < width; n++)// save for each spot the number of mines around
                    mines[m][n].setMinesAround(getNumOfMinesAround(m, n));
            return true;
        }
        return false;
    }

    public boolean open(int i, int j) {
        if (!mines[i][j].opened)// if not opened
            if (mines[i][j].mine == true) {// if this is a mine
                return false;// return false to indicate that it is a mine
            } else {// if not a mine
                mines[i][j].opened = true;// set as opened
                if (mines[i][j].minesAround == 0) {// if there are no mines around
                    // try to open sounding aria
                    if (i > 0) {
                        open(i - 1, j);
                    }
                    if (i < height - 1) {
                        open(i + 1, j);
                    }
                    if (j > 0) {
                        open(i, j - 1);
                    }
                    if (j < width - 1) {
                        open(i, j + 1);
                    }
                    if (i > 0 && j > 0) {
                        open(i - 1, j - 1);
                    }
                    if (i < height - 1 && j > 0) {
                        open(i + 1, j - 1);
                    }
                    if (i > 0 && j < width - 1) {
                        open(i - 1, j + 1);
                    }
                    if (i < height - 1 && j < width - 1) {
                        open(i + 1, j + 1);
                    }
                }
                return true;
            }
        else
            return true;// if the spot is already open no need to try again
        // but it is not a false move
    }

    public void toggleFlag(int x, int y) {
        // if thre is no flag add one
        // and if there is a flag remove it
        mines[x][y].flag = (!mines[x][y].flag);
    }

    public boolean isDone() {
        boolean allAreOpened = true;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (mines[i][j].mine == false) {// if not a mine
                    if (mines[i][j].opened == false) {// and is not opened
                        allAreOpened = false;
                        break;// no need to continue cheking
                    }
                }
            }
        }
        return allAreOpened;
    }

    public String get(int i, int j) {
        return mines[i][j].toString();
    }

    private int getNumOfMinesAround(int i, int j) {
        // check the number of srounding mines for each spot
        int count = 0;
        if (i > 0) {
            if (mines[i - 1][j].mine)
                count++;
        }
        if (i < height - 1) {
            if (mines[i + 1][j].mine)
                count++;
        }
        if (j > 0) {
            if (mines[i][j - 1].mine)
                count++;
        }
        if (j < width - 1) {
            if (mines[i][j + 1].mine)
                count++;
        }
        if (i > 0 && j > 0) {
            if (mines[i - 1][j - 1].mine)
                count++;
        }
        if (i < height - 1 && j > 0) {
            if (mines[i + 1][j - 1].mine)
                count++;
        }
        if (i > 0 && j < width - 1) {
            if (mines[i - 1][j + 1].mine)
                count++;
        }
        if (i < height - 1 && j < width - 1) {
            if (mines[i + 1][j + 1].mine)
                count++;
        }
        return count;
    }

    public void setShowAll(boolean showAll) {
        // set the board to show all
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mines[i][j].show = showAll;
            }
        }
    }

    public String toString() {
        // represent the board as a string
        String toReturn = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                toReturn = new String(toReturn + get(i, j));
            }
            toReturn = new String(toReturn + "\n");
        }
        return toReturn;
    }

    private class Place {
        private boolean mine, opened, flag, show;
        private int minesAround = 0;

        public Place() {
            mine = false;
            opened = false;
            flag = false;
            show = false;
        }

        public void setMinesAround(int minesAround) {
            this.minesAround = minesAround;
        }

        public String toString() {
            if (opened || show) {
                if (mine)
                    return "X";
                else if (minesAround == 0)
                    return " ";
                else
                    return "" + minesAround;
            } else {
                if (flag)
                    return "F";
                else
                    return ".";
            }
        }
    }

}
