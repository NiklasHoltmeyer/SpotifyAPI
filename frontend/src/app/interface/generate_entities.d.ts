/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2023-04-01 09:02:13.

export interface Album extends HasHrefWithID {
    album_type: string;
    total_tracks: number;
    available_markets: string[];
    external_urls: ExternalUrls;
    images: Image[];
    name: string;
    release_date: string;
    release_date_precision: string;
    restrictions: Reason;
    album_group: string;
    artists: Artist[];
}

export interface Artist extends HasHrefWithID {
    external_urls: ExternalUrls;
    followers: Followers;
    genres: string[];
    images: Image[];
    name: string;
    popularity: number;
}

export interface Audiobook {
    authors: Name[];
    available_markets: string[];
    copyrights: Copyright[];
    description: string;
    html_description: string;
    edition: string;
    explicit: boolean;
    external_urls: ExternalUrls;
    href: string;
    id: string;
    images: Image[];
    languages: string[];
    media_type: string;
    name: string;
    publisher: string;
    type: string;
    uri: string;
    total_chapters: number;
    chapters: any;
}

export interface Copyright {
    text: string;
    type: string;
}

export interface Cursors {
    after: string;
}

export interface Episode {
    audio_preview_url: string;
    description: string;
    html_description: string;
    duration_ms: number;
    explicit: boolean;
    external_urls: ExternalUrls;
    href: string;
    id: string;
    images: Image[];
    is_externally_hosted: boolean;
    is_playable: boolean;
    language: string;
    languages: string[];
    name: string;
    release_date: string;
    release_date_precision: string;
    resume_point: EpisodeResumePoint;
    type: string;
    uri: string;
    restrictions: Reason;
    show: Show;
}

export interface EpisodeResumePoint {
    fully_played: boolean;
    resume_position_ms: number;
}

export interface ExplicitContent {
    filter_enabled: string;
    filter_locked: string;
}

export interface ExternalIds {
    isrc: string;
    ean: string;
    upc: string;
}

export interface Followers {
    href: string;
    total: number;
}

export interface LinkedFrom {
}

export interface Owner extends HasHrefWithID {
    external_urls: ExternalUrls;
    followers: Followers;
    display_name: string;
}

export interface Playlist extends BasePlaylist<Track[]> {
    tracks: Track[];
}

export interface PlaylistDetails {
    name: string;
    collaborative: boolean;
    description: string;
    public: boolean;
}

export interface SearchResult {
    tracks: Pageable<Track>;
    artists: Pageable<Artist>;
    albums: Pageable<Album>;
    playlists: Pageable<Playlist>;
    shows: Pageable<Show>;
    episodes: Pageable<Episode>;
    audiobooks: Pageable<Audiobook>;
}

export interface Show extends HasHrefWithID {
    available_markets: string[];
    copyrights: Copyright[];
    description: string;
    html_description: string;
    explicit: boolean;
    external_urls: ExternalUrls;
    images: Image[];
    is_externally_hosted: boolean;
    languages: string[];
    media_type: string;
    name: string;
    publisher: string;
    total_episodes: string;
}

export interface Track extends HasHrefWithID {
    album: Album;
    artists: Artist[];
    available_markets: string[];
    disc_number: number;
    duration_ms: number;
    explicit: boolean;
    external_ids: ExternalIds;
    external_urls: ExternalUrls;
    is_playable: boolean;
    linked_from: LinkedFrom;
    restrictions: Reason;
    name: string;
    popularity: number;
    preview_url: string;
    track_number: number;
    is_local: boolean;
}

export interface UserProfile extends HasHrefWithID {
    country: string;
    display_name: string;
    email: string;
    explicit_content: ExplicitContent;
    external_urls: ExternalUrls;
    followers: Followers;
    images: Image[];
    product: string;
}

export interface ArtistArtists {
    artists: Artist[];
}

export interface AddedBy extends HasHrefWithID {
    external_urls: ExternalUrls;
}

export interface ExternalUrls {
    spotify: string;
}

export interface HasHref {
    href: string;
    uri: string;
    type: string;
}

export interface HasHrefWithID extends HasHref {
    id: string;
}

export interface Image {
    url: string;
    height: number;
    width: number;
}

export interface Name {
    name: string;
}

export interface Pageable<T> extends HasHref {
    items: T[];
    limit: number;
    next: string;
    offset: number;
    previous: string;
    total: number;
    cursors: Cursors;
}

export interface Reason {
    reason: string;
}

export interface Snapshot {
    snapshot_id: string;
}

export interface URI {
    uri: string;
}

export interface Url {
    url: string;
}

export interface Albums extends Pageable<Album> {
    items: Album[];
}

export interface Artists extends Pageable<Artist> {
    items: Artist[];
}

export interface PlaylistTrackInfos extends Pageable<PlaylistTrackInfo> {
    items: PlaylistTrackInfo[];
}

export interface Playlists extends Pageable<Playlist> {
    items: Playlist[];
}

export interface PlaylistsWithMinimalTrackInfos extends Pageable<PlaylistsWithMinimalTrackInfo> {
    items: PlaylistsWithMinimalTrackInfo[];
}

export interface UserSavedTracks extends Pageable<UserSavedTrack> {
    items: UserSavedTrack[];
}

export interface BasePlaylist<TracksType> extends HasHrefWithID {
    collaborative: boolean;
    description: string;
    external_urls: ExternalUrls;
    followers: Followers;
    images: Image[];
    name: string;
    owner: Owner;
    snapshot_id: string;
    tracks: TracksType;
    public: boolean;
}

export interface PlaylistTrack extends HasHrefWithID {
    album: Album;
    artists: Artist[];
    available_markets: string[];
    disc_number: string;
    duration_ms: number;
    episode: boolean;
    explicit: boolean;
    external_ids: ExternalIds;
    external_urls: ExternalUrls;
    is_local: string;
    name: string;
    popularity: number;
    preview_url: string;
    track: any;
    track_number: number;
}

export interface PlaylistTrackInfo {
    added_at: string;
    added_by: AddedBy;
    primary_color: any;
    is_local: boolean;
    track: PlaylistTrack;
    video_thumbnail: Url;
}

export interface PlaylistTracksInfo extends HasHrefWithID {
    total: number;
}

export interface PlaylistsWithMinimalTrackInfo extends BasePlaylist<PlaylistTracksInfo> {
    tracks: PlaylistTracksInfo;
}

export interface UserSavedTrack {
    added_at: string;
    track: Track;
}

export interface TrackDeleteRequestBody {
    tracks: URI[];
}

export interface TrackReorderRequestBody {
    snapshot_id: string;
    range_start: number;
    insert_before: number;
    range_length: number;
}

export interface UrisRequestBody {
    uris: string[];
}

export interface PagableResponseReduced<T, I> extends Response<I[]> {
    body?: I[];
    httpResponse: HttpResponse<I[]>;
}

export interface Response<T> {
    body?: T;
    success: boolean;
    httpResponse: HttpResponse<T>;
}

export interface ResponseArray<MultipleEntities, SingleEntity> extends Response<SingleEntity[]> {
    body?: SingleEntity[];
    httpResponse: HttpResponse<SingleEntity[]>;
}

export interface ResponseList<T> {
    body: T[];
    httpResponse: HttpResponse<T>[];
}

export interface UsersTopArtistsResponse extends UsersTopItemsResponse<Artist> {
    items: Artist[];
}

export interface UsersTopItemsResponse<T> {
    href: string;
    items: T[];
    limit: number;
    next: string;
    offset: number;
    previous: string;
    total: number;
}

export interface UsersTopTracksResponse extends UsersTopItemsResponse<any> {
    items: any[];
}

export interface HttpResponse<T> {
    cookies: Cookie[];
    status: number;
    success: boolean;
    body: T;
    requestSummary: HttpRequestSummary;
    statusText: string;
    parsingError?: UnirestParsingException;
    headers: Headers;
}

export interface Cookie {
    name: string;
    value: string;
    domain: string;
    path: string;
    httpOnly: boolean;
    maxAge: number;
    secure: boolean;
    sameSite: SameSite;
    urlDecodedValue: string;
    expiration: Date;
}

export interface HttpRequestSummary {
    rawPath: string;
    url: string;
    httpMethod: HttpMethod;
}

export interface UnirestParsingException extends UnirestException {
    originalBody: string;
}

export interface Headers {
}

export interface HttpMethod {
}

export interface Throwable extends Serializable {
    cause: Throwable;
    stackTrace: StackTraceElement[];
    message: string;
    suppressed: Throwable[];
    localizedMessage: string;
}

export interface StackTraceElement extends Serializable {
    classLoaderName: string;
    moduleName: string;
    moduleVersion: string;
    methodName: string;
    fileName: string;
    lineNumber: number;
    nativeMethod: boolean;
    className: string;
}

export interface UnirestException extends RuntimeException {
}

export interface Serializable {
}

export interface RuntimeException extends Exception {
}

export interface Exception extends Throwable {
}

export type SearchType = "ALBUM" | "PLAYLIST" | "TRACK";

export type SameSite = "None" | "Strict" | "Lax";
