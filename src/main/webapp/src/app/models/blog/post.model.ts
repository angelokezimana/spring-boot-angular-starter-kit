import Author from "./author.model";
import PhotoPost from "./photo-post.model";

export default interface Post {
  id: number;
  text: string;
  publishedOn: Date;
  photoPosts: PhotoPost[];
  author: Author;
}
