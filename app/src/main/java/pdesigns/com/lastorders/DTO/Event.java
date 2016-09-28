package pdesigns.com.lastorders.DTO;

import java.io.Serializable;

/**
 * Created by Patrick on 04/05/2016.
 */
public class Event implements Serializable, Comparable {


    String title, cityName, CountryName, countryAbbr, regionName, startTime, description, address, longa, lata;


    private Integer distanceAway = 0;

    public Event(String title, String cityName, String countryName, String countryAbbr, String regionName, String startTime, String description, String address, String longa, String lata) {
        this.title = title;
        this.cityName = cityName;
        CountryName = countryName;
        this.countryAbbr = countryAbbr;
        this.regionName = regionName;
        this.startTime = startTime;
        this.description = description;
        this.address = address;
        this.longa = longa;
        this.lata = lata;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryAbbr() {
        return countryAbbr;
    }

    public void setCountryAbbr(String countryAbbr) {
        this.countryAbbr = countryAbbr;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLonga() {
        return longa;
    }

    public void setLonga(String longa) {
        this.longa = longa;
    }

    public String getLata() {
        return lata;
    }

    public void setLata(String lata) {
        this.lata = lata;
    }

    public Integer getDistanceAway() {
        return distanceAway;
    }

    public void setDistanceAway(Integer distanceAway) {
        this.distanceAway = distanceAway;
    }

    @Override
    public int compareTo(Object o) {
        Event b2 = (Event) o;
        if (getDistanceAway() < b2.getDistanceAway()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (getTitle() != null ? !getTitle().equals(event.getTitle()) : event.getTitle() != null)
            return false;
        if (getCityName() != null ? !getCityName().equals(event.getCityName()) : event.getCityName() != null)
            return false;
        if (getCountryName() != null ? !getCountryName().equals(event.getCountryName()) : event.getCountryName() != null)
            return false;
        if (getCountryAbbr() != null ? !getCountryAbbr().equals(event.getCountryAbbr()) : event.getCountryAbbr() != null)
            return false;
        if (getRegionName() != null ? !getRegionName().equals(event.getRegionName()) : event.getRegionName() != null)
            return false;
        if (getStartTime() != null ? !getStartTime().equals(event.getStartTime()) : event.getStartTime() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(event.getDescription()) : event.getDescription() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(event.getAddress()) : event.getAddress() != null)
            return false;
        if (getLonga() != null ? !getLonga().equals(event.getLonga()) : event.getLonga() != null)
            return false;
        if (getLata() != null ? !getLata().equals(event.getLata()) : event.getLata() != null)
            return false;
        return getDistanceAway() != null ? getDistanceAway().equals(event.getDistanceAway()) : event.getDistanceAway() == null;

    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getCityName() != null ? getCityName().hashCode() : 0);
        result = 31 * result + (getCountryName() != null ? getCountryName().hashCode() : 0);
        result = 31 * result + (getCountryAbbr() != null ? getCountryAbbr().hashCode() : 0);
        result = 31 * result + (getRegionName() != null ? getRegionName().hashCode() : 0);
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getLonga() != null ? getLonga().hashCode() : 0);
        result = 31 * result + (getLata() != null ? getLata().hashCode() : 0);
        result = 31 * result + (getDistanceAway() != null ? getDistanceAway().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", cityName='" + cityName + '\'' +
                ", CountryName='" + CountryName + '\'' +
                ", countryAbbr='" + countryAbbr + '\'' +
                ", regionName='" + regionName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", longa='" + longa + '\'' +
                ", lata='" + lata + '\'' +
                '}';
    }


}
