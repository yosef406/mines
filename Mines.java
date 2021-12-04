package mines;

import java.util.Random;

public class Mines {
    private int height, width;
    private Dot[][] dots;

    public Mines(int height, int width, int numMines) {
        this.height = height;
        this.width = width;
        dots = new Dot[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                dots[i][j] = new Dot();
            }
        }
        for (int i = 0; i < numMines; i++) {
            if (!addMine(new Random().nextInt(height), new Random().nextInt(width)))
                i--;
        }
    }

    public boolean addMine(int i, int j) {
        if (!dots[i][j].mine) {
            dots[i][j].mine = true;
            for (int m = 0; m < height; m++)
                for (int n = 0; n < width; n++)// save for each spot the number of mines around
                    dots[m][n].minesAround = cuntMines(m, n);
            return true;
        }
        return false;
    }

    public boolean open(int i, int j) {
        if (!dots[i][j].opened)// if not opened
            if (dots[i][j].mine == true) {// if this is a mine
                return false;// return false to indicate that it is a mine
            } else {// if not a mine
                dots[i][j].opened = true;// set as opened
                if (dots[i][j].minesAround == 0) {// if there are no mines around
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

    // put's a flag if there is not and removes it if there is
    public void toggleFlag(int x, int y) {
        if (dots[x][y].flag == true) {
            dots[x][y].flag = false;
        } else
            dots[x][y].flag = true;
    }

    // checks if all the dot that are not mines are opened or closed
    // if 1 dot is closed retures false
    public boolean isDone() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!dots[i][j].mine)
                    if (!dots[i][j].opened)
                        return false;
            }
        }
        return true;

    }

    public String get(int i, int j) {
        return dots[i][j].toString();
    }

    public void setShowAll(boolean showAll) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                dots[i][j].show = showAll;
            }
        }
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                str = new String(str + dots[i][j].toString());
            }
            str = new String(str + "\n");
        }
        return str;
    }

    // count the mines arround evry dot
    private int cuntMines(int i, int j) {
        int count = 0;
        if (i > 0) {
            if (dots[i - 1][j].mine)
                count++;
        }
        if (i < height - 1) {
            if (dots[i + 1][j].mine)
                count++;
        }
        if (j > 0) {
            if (dots[i][j - 1].mine)
                count++;
        }
        if (j < width - 1) {
            if (dots[i][j + 1].mine)
                count++;
        }
        if (i > 0 && j > 0) {
            if (dots[i - 1][j - 1].mine)
                count++;
        }
        if (i < height - 1 && j > 0) {
            if (dots[i + 1][j - 1].mine)
                count++;
        }
        if (i > 0 && j < width - 1) {
            if (dots[i - 1][j + 1].mine)
                count++;
        }
        if (i < height - 1 && j < width - 1) {
            if (dots[i + 1][j + 1].mine)
                count++;
        }
        return count;
    }

    private class Dot {
        boolean mine, opened, flag, show;
        int minesAround;

        private Dot() {
            mine = false;
            opened = false;
            flag = false;
            show = false;
            minesAround = 0;

        }

        public String toString() {
            if (!this.opened && !this.show) {
                if (!this.flag) {
                    return ".";
                } else
                    return "F";
            } else {
                if (this.mine) {
                    return "X";
                } else if (this.minesAround == 0) {
                    return " ";
                } else
                    return "" + minesAround;
            }
        }
    }
}