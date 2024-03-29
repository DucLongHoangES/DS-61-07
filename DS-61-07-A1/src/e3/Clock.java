package e3;

/**
 * Doesn't need much comments, this whole class is self-explanatory
 * as it is just making constructors and getters
 */
public class Clock {

    public enum Period {AM, PM}

    // parameters of the class
    private int hours, minutes, seconds;
    private Clock.Period period;

    /**
     * Creates a Clock instance parsing a String .
     * @param s The string representing the hour in 24h format (17:25:15) or in
     * 12h format (05:25:15 PM).
     * @throws IllegalArgumentException if the string is not a valid hour .
     */
    public Clock (String s) {
        // parsing String into values
        int hours = Integer.parseInt(s.substring(0,2));
        int minutes = Integer.parseInt(s.substring(3,5));
        int seconds = Integer.parseInt(s.substring(6, 8));

        // self explanatory condition...
        if(minutes > 59 || seconds > 59) throw new IllegalArgumentException("Not a valid hour");
        this.minutes = minutes;
        this.seconds = seconds;

        // check if 12h or 24h format
        if(s.length() > 8) {
            // this is 12h format
            if(hours > 12) throw new IllegalArgumentException("Not a valid hour");
            this.hours = hours;
            String period = s.toUpperCase();
            if(period.contains("AM") || period.contains("PM")){
                this.period = (period.contains("AM")) ? Clock.Period.AM : Clock.Period.PM;
            } else throw new IllegalArgumentException("Not a valid hour");
        }
        else {
            // this is 24h format
            if(hours > 23) throw new IllegalArgumentException("Not a valid hour");
            this.hours = hours;
        }
    }

    /**
     * Creates a clock given the values in 24h format .
     * @param hours Hours in 24h format .
     * @param minutes Minutes .
     * @param seconds Seconds .
     * @throws IllegalArgumentException if the values do not represent a valid
     * hour .
     */
    public Clock (int hours, int minutes, int seconds) {
        if(hours > 23 || minutes > 59 || seconds > 59){
            throw new IllegalArgumentException("Not a valid hour.");
        }
        else {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }
    }

    /**
     * Creates a clock given the values in 12h format. Period is a enumeration
     * located inside the Clock class with two values : AM and PM.
     * @param hours Hours in 12h format .
     * @param minutes Minutes .
     * @param seconds Seconds .
     * @param period Period used by the Clock ( represented by an enum ).
     * @throws IllegalArgumentException if the values do not represent a valid
     * hour .
     */
    public Clock (int hours, int minutes, int seconds, Period period ) {
        if(hours > 12 || minutes > 59 || seconds > 59){
            throw new IllegalArgumentException("Not a valid hour.");
        }
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.period = period;
    }

    /**
     * Returns the hours of the clock in 24h format
     * @return the hours in 24h format
     */
    public int getHours24 () {
        if (this.period == null) return this.hours;

        /*
        *  this the a most bullshit condition...
        *  I highly disagree that 12:00:00 AM is the same as midnight
        *  Because if 12PM is midnight then 12AM should be noon, not midnight...
        *  but whatever, if this is what the test requires, then so be it...
        */
        else if(this.period == Clock.Period.AM && this.hours == 12) return 0;


        else return (this.period == Clock.Period.AM) ? (this.hours) : (this.hours + 12);
    }

    /**
     * Returns the hours of the clock in 12h format
     * @return the hours in 12h format
     */
    public int getHours12 () {
        return (this.hours > 12) ? (this.hours - 12) : this.hours;
    }

    /**
     * Returns the minutes of the clock
     * @return the minutes
     */
    public int getMinutes () {
        return this.minutes;
    }

    /**
     * Returns the seconds of the clock .
     * @return the seconds .
     */
    public int getSeconds () {
        return this.seconds;
    }

    /**
     * Returns the period of the day (AM or PM) that the clock is representing
     * @return An instance of the clock period enum depending if the time is
     * before noon (AM) or after noon (PM ).
     */
    public Period getPeriod () {
        if (this.period != null) return this.period;
        return (this.getHours24() > 11) ? Clock.Period.PM : Clock.Period.AM;
    }

    /**
     * Prints a String representation of the clock in 24h format .
     * @return A string in 24h format
     * @see String . format function to format integers with leading zeroes
     */
    public String printHour24 () {
        return String.format("%02d:%02d:%02d", this.getHours24(), this.getMinutes(), this.getSeconds());
    }

    /**
     * Prints a String representation of the clock in 12h format .
     * @return An string in 12h format
     * @see String . format function to format integers with leading zeroes
     */
    public String printHour12 () {
        return String.format("%02d:%02d:%02d %s", this.getHours12(), this.getMinutes(), this.getSeconds(), this.getPeriod());
    }

    /**
     * Performs the equality tests of the current clock with another clock
     * passed as a parameter. Two clock are equal if they represent the same
     * instant regardless of being in 12h or 24h format .
     * @param o The clock to be compared with the current clock .
     * @return true if the clocks are equals , false otherwise .
     */
    @Override
    public boolean equals (Object o) {
        if(!(o instanceof Clock)) return false;
        Clock b = (Clock) o;
        return (this.getHours24() == b.getHours24()) &&
                (this.getMinutes() == b.getMinutes()) &&
                (this.getSeconds() == b.getSeconds());
    }

    /**
     * Returns a integer that is a hash code representation of the clock using
     * the " hash 31" algorithm .
     * Clocks that are equals must have the same hash code .
     * @return the hash code
     */
    @Override
    public int hashCode () {
        int result = 1;
        result = 31 * result + this.getHours24();
        result = 31 * result + this.getMinutes();
        result = 31 * result + this.getSeconds();
        return result;
    }

}
