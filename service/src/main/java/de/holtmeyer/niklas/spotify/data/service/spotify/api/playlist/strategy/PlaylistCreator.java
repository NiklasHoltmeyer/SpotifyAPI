package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.strategy;

import java.util.Optional;

public abstract class PlaylistCreator {
    PlaylistCreateStrategy  createStrategy;
    PlaylistListFilterStrategy listFilterStrategy;
    PlaylistListMapperStrategy listMapperStrategy;
    PlaylistListStrategy listStrategy;


    public PlaylistCreator() {
        this.createStrategy = this.createStrategy();
        this.listFilterStrategy = this.listFilterStrategy();
        this.listMapperStrategy = this.listMapperStrategy();
        this.listStrategy = this.listStrategy();
    }

    abstract PlaylistCreateStrategy createStrategy(); // defaults einfügen?
    abstract PlaylistListFilterStrategy listFilterStrategy();
    abstract PlaylistListMapperStrategy listMapperStrategy();
    abstract PlaylistListStrategy listStrategy();

    abstract Optional<String> preProcess();
    boolean list(){
        var strategy = listStrategy();

        //TODO neben jeden StartegyREcord ein Executer ala ListExecuter ? der macht alles und hier nur passen?

//        strategy.userPlaylistsCurrentUserFollows()
//        strategy.savedSongs()
//        strategy.currentUserPlaylists()

        // list tracks;
        throw new RuntimeException("unsupported"); // list tracks & filter & sort & todo sort strategy
    }

    Optional<String> create(){ // playlist creation - return playlist id
        throw new RuntimeException("unsupported"); // create playlist
    }

    // String Debug info, fill playlist
    Optional<String> fill(String playlist_id){
        throw new RuntimeException("unsupported"); // create playlist
    }

    abstract Optional<String> postProcess();




}
