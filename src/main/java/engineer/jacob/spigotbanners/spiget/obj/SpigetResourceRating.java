package engineer.jacob.spigotbanners.spiget.obj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpigetResourceRating {
    private int count;
    private int averageStars;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAverageStars() {
        return averageStars;
    }

    public void setAverageStars(int averageStars) {
        this.averageStars = averageStars;
    }
}
