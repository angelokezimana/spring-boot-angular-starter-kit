import Author from "./author.model";
import PhotoPost from "./photo-post.model";
import Comment from "./comment.model";

export default interface PostDetail {
  id: number;
  text: string;
  imageCover: string;
  publishedOn: Date;
  photoPosts: PhotoPost[];
  author: Author;
}
