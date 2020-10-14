package Models.Game;


import java.util.ArrayList;

public class PlayerData {
    private long getBinDate = 0;
    private ArrayList<Bin> bins = new ArrayList<>();


    public long getGetBinDate() {
        return getBinDate;
    }

    public void setGetBinDate(int getBinDate) {
        this.getBinDate = getBinDate;
    }

    public void setGetBinDate(long getBinDate) {
        this.getBinDate = getBinDate;
    }

    public ArrayList<Bin> getBins() {
        return bins;
    }

    public void setBins(ArrayList<Bin> bins) {
        this.bins = bins;
    }
}
