public class Song {
    private final String title;
    private final String duration;

    public Song(String title, String duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String toString() {
        return this.getTitle() + " [" + this.getDuration() + "]";
    }
}

