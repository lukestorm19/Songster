
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Album {
    private final String name;
    private final List<Song> songs;

    public Album(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean addSong(String title, String duration) {
        if (findSong(title) != null) {
            return false;
        }
        this.songs.add(new Song(title, duration));
        return true;
    }

    public void printSongsList() {

        printSongListHelper();

        for (int i = 0; i < this.songs.size(); i++) {

            Song song = this.songs.get(i);
            System.out.println((i + 1) + ". " + song.toString());
        }
        printSongListHelper();
    }

    private void printSongListHelper() {
        System.out.println("---------" + this.name.toUpperCase() + "---------");
    }

    private Song findSong(String title) {
        for (Song checkedSong : this.songs) {
            if (checkedSong.getTitle().equals(title)) {
                return checkedSong;
            }
        }
        return null;
    }

    public boolean findSongIndex(int index) {
        return ((index >= 0) && (index <= this.songs.size()));
    }

    public void addToPlayList(int trackNumber, LinkedList<Song> playList) {
        if (!findSongIndex(trackNumber - 1)) {
            System.out.println("This album does not have a track " + trackNumber + ".");
            return;
        }
        if (isOnPlayList(this.songs.get(trackNumber - 1).getTitle(), playList)) {
            System.out.println("Track " + trackNumber + " is already added to the playlist.");
            return;
        }
        playList.add(this.songs.get(trackNumber - 1));
    }

    public void addToPlayList(String title, LinkedList<Song> playList) {
        Song checkedSong = findSong(title);
        if (checkedSong != null) {
            if (isOnPlayList(title, playList)) {
                System.out.println("The song " + title + " is already added to the playlist.");
                return;
            }
            playList.add(checkedSong);
        } else {
            System.out.println("The song " + title + " is not in this album.");
        }
    }

    public boolean isOnPlayList(String title, LinkedList<Song> playList) {
        Song checkSong = findSong(title);
        if (checkSong != null) {
            for (Song playlistSong : playList) {
                return (checkSong == playlistSong);
            }

        }
        return false;
    }
}
