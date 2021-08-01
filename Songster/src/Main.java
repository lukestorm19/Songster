import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LinkedList<Song> playlist = new LinkedList<>();
    private static final List<Album> storage = new ArrayList<>();

    public static void main(String[] args) {
        storage.add(new Album("album 1"));
        storage.add(new Album("album 2"));

        storage.get(0).addSong("song 1", "2:23");
        storage.get(0).addSong("song 2", "3:44");
        storage.get(0).addSong("song 3", "2:03");
        storage.get(0).addSong("song 4", "2:45");
        storage.get(0).addSong("song 5", "2:28");
        storage.get(1).addSong("song a", "3:12");
        storage.get(1).addSong("song b", "6:37");
        storage.get(1).addSong("song c", "4:23");
        storage.get(1).addSong("song d", "5:51");
        storage.get(1).addSong("song e", "3:00");
        storage.get(1).addSong("song f", "2:28");

        musicPlayer();

    }

    public static void musicPlayer() {
        boolean quit = false;
        int action;
        String yourChoice = "Choose an option:";

        printMenu();

        while (!quit) {
            System.out.println(yourChoice);
            action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0:
                    printMenu();
                    break;
                case 1:
                    addNewAlbum();
                    break;
                case 2:
                    addSongToAlbum();
                    break;
                case 3:
                    Album album = getAlbum();
                    if (album != null) {
                        album.printSongsList();
                    }
                    break;
                case 4:
                    addSongToPlaylist();
                    break;
                case 5:
                    runPlaylist();
                    break;
                case 6:
                    printAllSongs();
                    break;
                case 7:
                    quit = true;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + action);
            }
        }
    }

    public static void printMenu() {
        System.out.println("********** MENU **********");
        System.out.println("0 - show MENU\n" +
                "1 - add a new album\n" +
                "2 - add a song to an album\n" +
                "3 - show songs in a chosen album\n" +
                "4 - add a song to a playlist\n" +
                "5 - start a playlist\n" +
                "6 - show songs on the playlist\n" +
                "7 - quit ");
    }

    public static void addNewAlbum() {
        System.out.println("Enter name of the album:");
        final String albumName = scanner.nextLine();
        storage.add(new Album(albumName));
        System.out.println("Album " + albumName + " added to the storage.");
    }

    public static void addSongToAlbum() {

        if (storage.size() > 0) {
            Album album = getAlbum();

            if (album != null) {
                System.out.println("Enter a song name:");
                String songName = scanner.nextLine();
                System.out.println("Enter duration:");
                String duration = scanner.nextLine();

                if (album.addSong(songName, duration)) {
                    System.out.println("Successfully added " + songName + " to the album.");
                    return;
                }
                System.out.println("Song is already in the album");
            }
        }
    }

    public static void addSongToPlaylist() {
        Album album = getAlbum();

        if (album != null) {
            album.printSongsList();

            System.out.println("Enter a song number or enter it's title:");
            if (scanner.hasNextInt()) {
                final int songNumber = scanner.nextInt();
                scanner.nextLine();

                album.addToPlayList(songNumber, playlist);
            } else {
                final String songTitle = scanner.nextLine();

                album.addToPlayList(songTitle, playlist);
            }
        }
    }

    public static void runPlaylist() {
        ListIterator<Song> listIterator = playlist.listIterator();
        boolean quit = false;
        boolean forward = true;
        int action;
        String currentSong = null;
        String yourChoice = "Choose an option:";

        printPlaylistMenu();

        while (!quit) {

            if (currentSong != null) {
                System.out.println("***** Currently playing: " + currentSong + " *****\n" +
                        "(Enter 4 for MENU options)");
            } else {
                System.out.println("##### MUSIC IS OFF #####");
            }

            System.out.println(yourChoice);
            action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0:
                    quit = true;
                    printMenu();
                    break;
                case 1:
                    if (!forward) {
                        if (listIterator.hasNext()) {
                            listIterator.next();
                        }
                    }

                    if (listIterator.hasNext()) {
                        System.out.println("Next song.");
                        currentSong = listIterator.next().toString();
                        forward = true;
                        break;
                    }
                    System.out.println("No more songs available.");
                    forward = false;
                    break;
                case 2:
                    if (forward) {
                        if (listIterator.hasPrevious()) {
                            listIterator.previous();
                        }
                    }

                    if (listIterator.hasPrevious()) {
                        System.out.println("Previous song.");
                        currentSong = listIterator.previous().toString();
                        forward = false;
                        break;
                    }
                    System.out.println("You've reached the start of the list.");
                    forward = true;
                    break;
                case 3:
                    if (currentSong != null) {
                        if (forward && listIterator.hasPrevious()) {
                            listIterator.previous();
                        } else if (!forward && listIterator.hasNext()) {
                            listIterator.next();
                        }

                        forward = !forward;
                        listIterator.remove();
                        System.out.println("Song " + currentSong + " removed from playlist.");
                        currentSong = null;
                    } else {
                        System.out.println("First start the playlist.");
                    }
                    break;

                case 4:
                    printPlaylistMenu();
                    break;
                case 5:
                    printAllSongs();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + action);
            }
        }
    }

    public static void printPlaylistMenu() {
        System.out.println("********** PLAYLIST OPTIONS **********");
        System.out.println("0 - quit to main menu\n" +
                "1 - next song\n" +
                "2 - previous song\n" +
                "3 - remove currently playing from playlist\n" +
                "4 - show playlist options\n" +
                "5 - show all songs on a playlist");
    }

    public static void printAllSongs() {
        System.out.println("----------PLAYLIST----------");
        for (Song song : playlist) {
            System.out.println(song.toString());
        }
        System.out.println("----------PLAYLIST----------");
    }

    public static Album getAlbum() {
        for (int i = 0; i < storage.size(); i++) {
            System.out.println((i + 1) + ". " + storage.get(i).getName());
        }

        System.out.println("Enter a number representing chosen album:");
        int albumIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if ((albumIndex + 1) > 0)
            if (storage.size() >= (albumIndex + 1)) {
                return storage.get(albumIndex);
            }

        System.out.println("Wrong album number");
        return null;
    }
}