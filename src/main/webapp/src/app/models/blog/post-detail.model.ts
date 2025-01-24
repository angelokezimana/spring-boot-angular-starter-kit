import Author from "./author.model";
import PhotoPost from "./photo-post.model";
import Comment from "./comment.model";

export default interface PostDetail {
  id: number;
  title: string;
  text: string;
  imageCover: string;
  publishedOn: Date;
  numberOfComments: number;
  photoPosts: PhotoPost[];
  author: Author;
}
