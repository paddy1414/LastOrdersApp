package pdesigns.com.lastorders.DTO;

import java.io.Serializable;

/**
 * Created by Patrick on 08/02/2016.
 */
public class Bar implements Serializable, Comparable {

    private int id;
    private String barName;
    private String wheelChair;
    private String barLocale;
    private String phoneNo;
    private String openingHours;
    private String barPic;
    private String barFb;
    private Integer distanceAway;
    private String averageRating;


    /**
     * Instantiates a new Bar.
     *
     * @param id         the id
     * @param barName    the bar name
     * @param wheelChair the wheel chair
     * @param barLocale  the bar locale
     * @param barFb      the bar fb
     * @param barPic     the bar pic
     */
    public Bar(int id, String barName, String wheelChair, String barLocale, String barFb, String barPic) {
        this.id = id;
        this.barName = barName;
        this.wheelChair = wheelChair;
        this.barLocale = barLocale;
        this.barFb = barFb;
        this.barPic = barPic;
    }

    /**
     * Instantiates a new Bar.
     *
     * @param id           the id
     * @param barName      the bar name
     * @param barLocale    the bar locale
     * @param phoneNo      the phone no
     * @param openingHours the opening hours
     * @param barFb        the bar fb
     * @param barPic       the bar pic
     */
    public Bar(int id, String barName, String barLocale, String phoneNo, String openingHours, String barFb, String barPic, String rtn) {
        this.id = id;
        this.barName = barName;
        this.wheelChair = "";
        this.phoneNo = phoneNo;
        this.openingHours = openingHours;
        this.barLocale = barLocale;
        this.barFb = barFb;
        this.barPic = barPic;
        this.averageRating = rtn;
    }

    /**
     * Instantiates a new Bar.
     *
     * @param id           the id
     * @param barName      the bar name
     * @param barLocale    the bar locale
     * @param barFb        the bar fb
     * @param barPic       the bar pic
     * @param distanceAway the distance away
     */
    public Bar(int id, String barName, String barLocale, String barFb, String barPic, Integer distanceAway) {
        this.id = id;
        this.barName = barName;
        this.wheelChair = wheelChair;
        this.barLocale = barLocale;
        this.barFb = barFb;
        this.barPic = barPic;
    }

    /**
     * Instantiates a new Bar.
     */
    public Bar() {

    }

    /**
     * Gets phone no.
     *
     * @return the phone no
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets phone no.
     *
     * @param phoneNo the phone no
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Gets opening hours.
     *
     * @return the opening hours
     */
    public String getOpeningHours() {
        return openingHours;
    }

    /**
     * Sets opening hours.
     *
     * @param openingHours the opening hours
     */
    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets bar name.
     *
     * @return the bar name
     */
    public String getBarName() {
        return barName;
    }

    /**
     * Sets bar name.
     *
     * @param barName the bar name
     */
    public void setBarName(String barName) {
        this.barName = barName;
    }

    /**
     * Gets wheel chair.
     *
     * @return the wheel chair
     */
    public String getWheelChair() {
        return wheelChair;
    }

    /**
     * Sets wheel chair.
     *
     * @param wheelChair the wheel chair
     */
    public void setWheelChair(String wheelChair) {
        this.wheelChair = wheelChair;
    }

    /**
     * Gets bar pic.
     *
     * @return the bar pic
     */
    public String getBarPic() {
        return barPic;
    }

    /**
     * Sets bar pic.
     *
     * @param barPic the bar pic
     */
    public void setBarPic(String barPic) {
        this.barPic = barPic;
    }

    /**
     * Gets bar locale.
     *
     * @return the bar locale
     */
    public String getBarLocale() {
        return barLocale;
    }

    /**
     * Sets bar locale.
     *
     * @param barLocale the bar locale
     */
    public void setBarLocale(String barLocale) {
        this.barLocale = barLocale;
    }

    /**
     * Gets bar fb.
     *
     * @return the bar fb
     */
    public String getBarFb() {
        return barFb;
    }

    /**
     * Sets bar fb.
     *
     * @param barFb the bar fb
     */
    public void setBarFb(String barFb) {
        this.barFb = barFb;
    }

    /**
     * Gets distance away.
     *
     * @return the distance away
     */
    public Integer getDistanceAway() {
        return distanceAway;
    }

    /**
     * Sets distance away.
     *
     * @param distanceAway the distance away
     */
    public void setDistanceAway(Integer distanceAway) {
        this.distanceAway = distanceAway;
    }


    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bar)) return false;

        Bar bar = (Bar) o;

        if (getId() != bar.getId()) return false;
        if (getBarName() != null ? !getBarName().equals(bar.getBarName()) : bar.getBarName() != null)
            return false;
        if (getWheelChair() != null ? !getWheelChair().equals(bar.getWheelChair()) : bar.getWheelChair() != null)
            return false;
        if (getBarLocale() != null ? !getBarLocale().equals(bar.getBarLocale()) : bar.getBarLocale() != null)
            return false;
        if (getPhoneNo() != null ? !getPhoneNo().equals(bar.getPhoneNo()) : bar.getPhoneNo() != null)
            return false;
        if (getOpeningHours() != null ? !getOpeningHours().equals(bar.getOpeningHours()) : bar.getOpeningHours() != null)
            return false;
        if (getBarPic() != null ? !getBarPic().equals(bar.getBarPic()) : bar.getBarPic() != null)
            return false;
        if (getBarFb() != null ? !getBarFb().equals(bar.getBarFb()) : bar.getBarFb() != null)
            return false;
        return !(getDistanceAway() != null ? !getDistanceAway().equals(bar.getDistanceAway()) : bar.getDistanceAway() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getBarName() != null ? getBarName().hashCode() : 0);
        result = 31 * result + (getWheelChair() != null ? getWheelChair().hashCode() : 0);
        result = 31 * result + (getBarLocale() != null ? getBarLocale().hashCode() : 0);
        result = 31 * result + (getPhoneNo() != null ? getPhoneNo().hashCode() : 0);
        result = 31 * result + (getOpeningHours() != null ? getOpeningHours().hashCode() : 0);
        result = 31 * result + (getBarPic() != null ? getBarPic().hashCode() : 0);
        result = 31 * result + (getBarFb() != null ? getBarFb().hashCode() : 0);
        result = 31 * result + (getDistanceAway() != null ? getDistanceAway().hashCode() : 0);
        return result;
    }

    /**
     * Map infor string.
     *
     * @return the string
     */
    public String mapInfor() {
        return this.barName + "/n" +
                this.getOpeningHours() + "/n" +
                this.getOpeningHours();
    }

    @Override
    public String toString() {
        return "Bar{" +
                "id=" + id +
                ", barName='" + barName + '\'' +
                ", wheelChair='" + wheelChair + '\'' +
                ", barLocale='" + barLocale + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", openingHours='" + openingHours + '\'' +
                ", barPic='" + barPic + '\'' +
                ", barFb='" + barFb + '\'' +
                ", distanceAway=" + distanceAway +
                '}';
    }


    @Override
    public int compareTo(Object o) {
        Bar b2 = (Bar) o;
        if (getDistanceAway() < b2.getDistanceAway()) {
            return -1;
        } else {
            return 1;
        }
    }
}
