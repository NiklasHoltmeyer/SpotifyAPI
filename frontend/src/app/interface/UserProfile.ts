import { ExplicitContent } from './ExplicitContent';
import { ExternalUrls } from './ExternalUrls';
import { Followers } from './Followers';
import { Image } from './Image';
export interface UserProfile{
  country: String;
  display_name: String;
  email: String;
  explicit_content: ExplicitContent;
  external_urls: ExternalUrls;
  followers: Followers;
  href: String;
  id: String;
  images: Image[];
  product: String;
  type: String;
  uri: String;
}

