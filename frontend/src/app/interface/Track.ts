export interface Track{
  id: String;
  href: String;
  uri: String;
  type: String;

  album: Album;
  artists: Artist[];
  available_markets: String[];
  disc_number: Integer;
  duration_ms: Integer;
  explicit: Boolean;
  external_ids: ExternalIds;
  external_urls: ExternalUrls;
  is_playable: Boolean;
  linked_from: LinkedFrom;
  restrictions: Reason;
  name: String;
  popularity: Integer;
  preview_url: String;
  track_number: Integer;
  is_local: Boolean;
}
