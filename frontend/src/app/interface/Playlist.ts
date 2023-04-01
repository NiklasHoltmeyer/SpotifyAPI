import { ExplicitContent } from './ExplicitContent';
import { ExternalUrls } from './ExternalUrls';
import { Followers } from './Followers';
import { Image } from './Image';

export interface Playlist{
  id: String;
  href: String;
  uri: String;
  type: String;

  collaborative: Boolean;
  description: String;
  external_urls: ExternalUrls;
  followers: Followers;
  images: Image[];
  name: String;
  owner: Owner;
  public: Boolean;
  snapshot_id: String;
  tracks: Track[];
}

